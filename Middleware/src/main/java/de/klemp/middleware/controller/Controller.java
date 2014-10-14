/**
 * Copyright Jana Klemp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package de.klemp.middleware.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.apache.commons.configuration.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.klemp.middleware.component_1.*;
import de.klemp.middleware.component_2.*;

@Path("/")
public class Controller {
    static BrokerService broker;

    private static Logger logger = Logger.getLogger(Controller.class);

    private static String component1 = "Schicht1";

    private static String component2 = "Schicht2";

    private static java.sql.Connection conn;

    private static HashMap<String, Structure> structures = new HashMap<String, Structure>();

    private static Hashtable<String, ArrayList<MethodToInvoke>> controller = new Hashtable<String, ArrayList<MethodToInvoke>>();

    private static Hashtable<String, Boolean> deviceActive = new Hashtable<String, Boolean>();

    // __________________________________________________________________________________________
    // Methods used by classes from input and output devices
    /**
     * This method is used by all methods of the first component. Every method
     * has to inform the controller about the class, the method belongs to, the
     * name of the method and the name of the input device. The method searches
     * the methods of the second component, which were defined by the GUI, and
     * invokes them.
     * 
     * @param klasse
     *            class the method belongs to
     * @param method
     *            name of the method
     * @param name
     *            name of the input device
     */
    public static void informController(String klasse,
            String method,
            String name)
    {
        isBrokerStarted();
        System.out.println("controller informed");
        ArrayList<MethodToInvoke> list = controller.get(klasse + "," + method + "," + name);
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                MethodToInvoke m = (MethodToInvoke) iterator.next();
                System.out.println("controller informed" + m.getdata());
                boolean active = deviceActive.get(m.getClasses() + "," + m.getTopic());
                if (active) {
                    Method m2 = m.getMethod();
                    String data = m.getdata();
                    if (data.equals("")) {
                        Object[] parameterWithoutdata = new Object[3];
                        parameterWithoutdata[0] = klasse;
                        parameterWithoutdata[1] = name;
                        parameterWithoutdata[2] = m.getTopic();
                       
                            try {
                                m2.invoke(null, parameterWithoutdata);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                logger.error("Method could not be invoced", e);
                            }
                        
                    }
                    if (!data.equals("Sensor")) {
                        Object[] parameter = new Object[4];
                        parameter[0] = klasse;
                        parameter[1] = name;
                        Structure data2 = structures.get(data);
                        if (data2 != null) {
                            parameter[2] = data2;
                            parameter[3] = m.getTopic();
                           
                                try {
                                    m2.invoke(null, parameter);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                  logger.error("Method of component2 could not be invoked", e);
                                   
                                }
                            
                        }
                    }
                }
            }
        }
    }

    public static void informSensorController(String klasse,
            String method,
            String name,
            Sensor sensor)
    {
        isBrokerStarted();
        System.out.println("controller informed");
        ArrayList<MethodToInvoke> list = controller.get(klasse + "," + method + "," + name);
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                MethodToInvoke m = (MethodToInvoke) iterator.next();
                System.out.println("controller informed" + m.getdata());
                if (deviceActive.get(m.getClasses() + "," + m.getTopic())) {
                    Method m2 = m.getMethod();
                    String data = m.getdata();
                    if (data.equals("Sensor")) {
                        Object[] parameter = new Object[3];
                        parameter[0] = name;
                        parameter[1] = m.getTopic();
                        parameter[2] = sensor;
                        try {
                            m2.invoke(null, parameter);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            logger.error("Method could not be invoced", e);
                            
                        }

                    }
                }
            }
        }
    }

    /**
     * This method creates a connection to the message broker Active MQ and
     * sends the message to the given topic. The method is used by all methods
     * of the second component.
     * 
     * @param message
     *            send to the output device
     * @param topic
     *            of the message broker
     */
    public static void sendMessage(String message,
            String topic)
    {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(topic);
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // Create a messages
            TextMessage message1 = session.createTextMessage(message);
            // Tell the producer to send the message
            producer.send(message1);
            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.error("Message could not be sended to activemq", e);
        }

    }

    public static void sendMessageFast(String message,
            String topic)
    {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        Connection connection;
        try {
            connectionFactory.setOptimizeAcknowledge(true);
            connectionFactory.setUseAsyncSend(true);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(topic);
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // Create a messages
            TextMessage message1 = session.createTextMessage(message);
            // Tell the producer to send the message
            producer.send(message1);
            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.error("Message could not be sended to activemq", e);
        }

    }

    /**
     * This method queries a request to the database. If the request expects a
     * result, then the variable "get" has to be set true. The returned result
     * is the first column of the resultSet. So this method is only for getting
     * one variable back. This method does not test the code of the query.
     * 
     * @param query
     *            String with the SQL-query
     * @param get
     *            true, if the query has a result
     * @return result String: result of the query. "" if there is no result.
     */
    public static String queryDatabase(String query,
            boolean get)
    {
        String s = "";
        createDBConnection();
        try {
            Statement st = conn.createStatement();
            if (get == true) {
                ResultSet result = st.executeQuery(query);
                if (result.next()) {
                    s = result.getString(1);
                }
            } else {
                st.execute(query);
            }
        } catch (SQLException e) {
            logger.error("Database could not be queried", e);
        }
        closeDBConnection();
        return s;
    }

    // Methods used by classes from input and output devices.end
    // ________________________________________________________________________________________________
    // Methods as restful webservice

    /**
     * With this method the devices can subscribe. Names as ID have to be
     * defined. The devices of the first component are saved in the table
     * "InputDevices". The devices of the second component are saved in the
     * table "OutputDevices".
     * 
     * @param component
     *            1 or 2
     * @param classes
     *            name of the class the device belong to
     * @param name
     *            name to be chosen
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/anmelden/{component}/{classes}/{name}")
    public static synchronized String subscribe(@PathParam("component") int component,
            @PathParam("classes") String classes,
            @PathParam("name") String name)
    {
        String ok = "ok";
        createDBConnection();
        name.replaceAll("\"", "\\\"");
        try {
            if (component == 1) {
                PreparedStatement st = conn.prepareStatement("select class from \"Classes\" where class=?");
                st.setString(1, classes);
                ResultSet result = st.executeQuery();
                if (result.next()) {
                    Statement statement = conn.createStatement();
                    statement.execute("insert into \"" + classes + "\"(nameinput) values ('" + name + "');");
                    st = conn.prepareStatement("insert into \"InputDevices\"(class,name) values (?,?);");
                    st.setString(1, classes);
                    st.setString(2, name);
                    st.execute();

                } else {
                    ok = "class not found";
                }

            }

            if (component == 2) {
                PreparedStatement st = conn.prepareStatement("select class from \"Classes\"where class=?");
                st.setString(1, classes);
                ResultSet result = st.executeQuery();
                if (result.next()) {
                    deviceActive.put(classes + "," + name, true);
                    createDBConnection();
                    st = conn.prepareStatement("insert into \"OutputDevices\"(class,topic,enabled) values (?,?,'t');");
                    st.setString(1, classes);
                    st.setString(2, name);
                    st.execute();

                } else {
                    ok = "class not found";
                }
            }

        } catch (SQLException e) {
            
            String message = e.getMessage();
            if (!message.contains("doppelter Schlüsselwert")) {
                logger.error("SQL Exception", e);
            }

        }
        closeDBConnection();
        return ok;
    }

    /**
     * With this method the devices can log out. Their names will be deleted
     * from the tables.
     * 
     * @param component
     *            1 or 2
     * @param classes
     *            name of the class of the device
     * @param name
     *            name, the device had subscribed.
     */
    @GET
    @Path("/abmelden/{component}/{classes}/{name}")
    public static synchronized String unsubscribe(@PathParam("component") int component,
            @PathParam("classes") String classes,
            @PathParam("name") String name)
    {
        createDBConnection();
        String ok = "ok";
        name.replaceAll("\"", "\\\"");
        try {
            if (component == 1) {
                Statement statement = conn.createStatement();
                statement.execute("delete from\"" + classes + "\"where name='" + name + "');");
                PreparedStatement st = conn.prepareStatement("delete from \"InputDevices\" where name=" + name + ";");
                st.setString(1, classes);
                st.setString(2, name);
                st.execute();
                if (st.getUpdateCount() != 1) {
                    ok = "class or method not found";
                }

            }
            if (component == 2) {
                PreparedStatement st = conn.prepareStatement("delete from \"OutputDevices\" where \"class\"=? and \"topic\"=?;");
                st.setString(1, classes);
                st.setString(2, name);
                st.execute();
                deviceActive.remove(classes + "," + name);
                if (st.getUpdateCount() != 1) {
                    ok = "class or method not found";
                }

            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);

        }
        closeDBConnection();

        return ok;
    }

    /**
     * This method updates the variable "isActive" in the database. The variable
     * "isActive" is a column of the table "OutputDevices". With this method the
     * output devices can notify if they are active and like to get messages or
     * not.
     * 
     * @param classes
     *            class of the output device
     * @param name
     *            name of the output device
     * @param active
     *            true= is active and like to get messages false=is not active
     *            and does not listen to messages
     */
    @GET
    @Path("/isActive/{classes}/{name}/{isActive}")
    public synchronized static String isActive(@PathParam("classes") String classes,
            @PathParam("name") String name,
            @PathParam("isActive") String active)
    {
        createDBConnection();
        String ok = "ok";
        PreparedStatement st;
        try {
            boolean isActive = false;

            if (active.equals("t")) {
                isActive = true;
            }
            st = conn.prepareStatement("update \"OutputDevices\" set enabled=? where \"class\"=? and \"topic\"=?;");
            st.setBoolean(1, isActive);
            st.setString(2, classes);
            st.setString(3, name);
            st.execute();
            if (st.getUpdateCount() != 1) {
                ok = "class or topic not found";
            } else {
                deviceActive.put(classes + "," + name, isActive);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in Method isActive", e);
        }
        closeDBConnection();
        return ok;
    }

    /**
     * With this method a new data can be added. If there the method already has
     * a data, the old data will be deleted.
     * 
     * @param classes
     *            class of the method
     * @param method
     *            name of the method which should get the data
     * @param name
     *            name of the data. In the GUI one can choose it.
     * @param dataString
     *            the data: <br>
     *            ",": seperates the words <br>
     *            "!": new row
     * @param startX
     *            the start value for the data. (horizontal)
     * @param startY
     *            the start value for the data. (vertical)
     */
    @POST
    @Path("/addSimpleStructure/{klasse}/{method}/{topic}/{name}/{structure}/{startX}/{startY}")
    public static String addSimpleStructure(@PathParam("klasse") String classes,
            @PathParam("method") String method,
            @PathParam("topic") String topic,
            @PathParam("name") String name,
            @PathParam("structure") String structureString,
            @PathParam("startX") String startX,
            @PathParam("startY") String startY)
    {
        SimpleStructure structure = new SimpleStructure(Integer.parseInt(startX), Integer.parseInt(startY), structureString);
        String ok = registerStructure(classes, method, topic, name, structure);
        return ok;
    }

    // http://openbook.galileocomputing.de/java7/1507_13_002.html#dodtp600d871f-3240-4241-806d-4cdc9373f62e
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/data/getXY/{name}")
    public static String getXYdata(@PathParam("name") String name)
    {

        return structures.get(name).getXY();
    }

    @GET
    @Path("/start")
    public static synchronized String start()
    {PropertyConfigurator.configure("log4j.properties");
        String ok = "ok";
        isBrokerStarted();
        controllerToList();
        ActiveDevicesToList();
        deviceActive.put("VLCPlayerIntern,----------------", true);
        createDBConnection();
        try {
            PreparedStatement st = conn.prepareStatement("insert into \"OutputDevices\"(class,topic,enabled) values ('VLCPlayer','----------------','t');");
            st.execute();

        } catch (SQLException e) {
            String message = e.getMessage();
            if (!message.contains("doppelter Schlüsselwert")) {
                logger.error("SQL Exception", e);
            }
        }
        closeDBConnection();
        return ok;
    }

    // Methods as restful webservice. end
    // _____________________________________________________________________________
    // Methods for the Servlet for the GUI

    /**
     * This method is for initializing the controller.
     */
    public static void init()
    {
        stopBroker();
        isBrokerStarted();
        deleteController();
        deleteDevices();
        searchMethods();
        start();
    }

    /**
     * This method returns the names from the classes of the defined component.
     * This method uses: the class "Servlet"
     * 
     * @param component
     *            1 or 2
     * @return String of the names. <br>
     *         "," separates the names.
     */
    public static String getClassNames(String component)
    {
        // http://www.java-forum.org/web-tier/136754-string-array-javascript.html
        ArrayList list = getClasses(component);
        String names = new String();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            String name = o.getClass().getName();
            String[] name1 = name.split("\\.");
            if (iterator.hasNext()) {
                names = names + "" + name1[1] + ",";
            } else {
                names = names + "" + name1[1] + "";
            }
        }
        return names;
    }

    /**
     * This method is for the GUI. It returns all the methods of a class. It is
     * used to add the options in the select lists.
     * 
     * @param component
     *            1 or 2
     * @param classes
     *            names of the classes the methods should be returned
     * @return a String with the names of the methods. They are separated by a
     *         ",".
     */
    public static synchronized String getMethods(int component,
            String classes)
    {
        String methods = new String();
        createDBConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select method from \"Classes\" where component=? and class=?;");
            st.setInt(1, component);
            st.setString(2, classes);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                if (!result.isLast()) {
                    methods = methods + result.getString(1) + ",";
                } else {
                    methods = methods + result.getString(1);
                }

            }

        } catch (SQLException e) {
            logger.error("SQL Exception", e);
         
        }
        closeDBConnection();
        return methods;
    }

    /**
     * This method returns the names of the devices which are registered.
     * 
     * @param component
     *            1 or 2
     * @param classes
     *            class to get the names of
     * @return a String off all names from a class
     */
    public static synchronized String getNames(int component,
            String classes)
    {
        String names = "";
        ResultSet result = null;
        if (!classes.equals("VLCPlayerIntern")) {
            createDBConnection();
            try {
                if (component == 1) {
                    PreparedStatement st = conn.prepareStatement("select name from \"InputDevices\"where class=?;");
                    st.setString(1, classes);
                    result = st.executeQuery();
                }
                if (component == 2) {
                    PreparedStatement st = conn.prepareStatement("select topic from  \"OutputDevices\" where class=?;");
                    st.setString(1, classes);
                    result = st.executeQuery();

                }
                while (result.next()) {
                    String name = result.getString(1);
                    if (!result.isLast()) {
                        names = names + "" + name + ",";
                    } else {
                        names = names + name;
                    }
                }
            } catch (SQLException e) {
                logger.error("SQL Exception in Method getNames", e);

            }
        } else {
            names = "----------------";
        }
        closeDBConnection();
        return names;
    }

    /**
     * This method returns the name of the data of a method.
     * 
     * @param classes
     *            class of the method
     * @param method
     *            name of the method
     * @return the name of the data <br>
     *         "": if there is no data <br>
     *         "Struktur fehlt": if the method need a data, but no data has been
     *         set.
     */
    public static String getDataName(String classes,
            String method,
            String topic)
    {
        String names = "";
        ResultSet result = null;
        createDBConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select data from \"Data\"where  class=? and method=? and topic=?;");
            st.setString(1, classes);
            st.setString(2, method);
            st.setString(3, "" + topic);
            result = st.executeQuery();

            if (result.next()) {
                String name = result.getString(1);
                names = name;

            } else {
                st = conn.prepareStatement("select data from \"Data\"where  class=? and method=? ;");
                st.setString(1, classes);
                st.setString(2, method);
                result = st.executeQuery();

                if (result.next()) {
                    if (result.getString(1).equals("Sensor")) {
                        names = "Sensor";
                    } else {
                        names = "data is missing";
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);

        }
        closeDBConnection();
        return names;
    }

    /**
     * This method is important for the GUI to generate the necessary rows
     * before the options with the values of the database will be added.
     * 
     * @return the number of rows, which are saved in the database.
     */
    public static synchronized int getControllerSize()
    {
        int size = 0;
        createDBConnection();
        try {
            Statement st = conn.createStatement();
            PreparedStatement stp = conn.prepareStatement("select count(*) from \"Controller\" ;");
            ResultSet r = stp.executeQuery();
            r.next();
            size = r.getInt(1);

        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        closeDBConnection();

        return size;
    }

    /**
     * This method is for the GUI. It returns the defined processes which are
     * saved in the database. The values are saved with the help of the class
     * ControllerSchema. Every process is saved in one instance of the
     * ControllerSchema and added to the ArrayList.
     * 
     * @return a list with objects of ControllerSchemas. Every schema contains
     *         one process for the GUI.
     */
    public static synchronized ArrayList<ControllerSchema> getController()
    {
        createDBConnection();
        ArrayList<ControllerSchema> listController = new ArrayList<ControllerSchema>();
        try {
            Statement st = conn.createStatement();
            PreparedStatement stp = conn.prepareStatement("select classInput,methodInput,nameInputDevice,classOutput,methodOutput,topic,data from \"Controller\" ;");
            ResultSet r = stp.executeQuery();

            while (r.next()) {
                ControllerSchema controllerSchema = new ControllerSchema();
                controllerSchema.setClassInput(r.getString("classinput"));
                controllerSchema.setMethodInput(r.getString("methodinput"));
                controllerSchema.setNameInput(r.getString("nameinputdevice"));
                controllerSchema.setClassOutput(r.getString("classoutput"));
                controllerSchema.setMethodOutput(r.getString("methodoutput"));
                controllerSchema.setTopic(r.getString("topic"));
                String data = getDataName(r.getString("classoutput"), r.getString("methodoutput"), r.getString("topic"));
                controllerSchema.setdata(data);
                listController.add(controllerSchema);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        closeDBConnection();
        return listController;
    }

    /**
     * This method saves the processes, defined with the GUI, in the database.
     * 
     * @param classInput
     *            class of the first component
     * @param methodInput
     *            method of the first component
     * @param nameInput
     *            name of the input device
     * @param classOutput
     *            class of the second component
     * @param methodOutput
     *            method of the second component
     * @param data
     *            name of the data, "data fehlt" or ""
     * @param topic
     *            topic for the message broker
     */
    public static synchronized void setController(String classInput,
            String methodInput,
            String nameInput,
            String classOutput,
            String methodOutput,
            String topic,
            String data)
    {
        createDBConnection();
        if (classInput != null && methodInput != null && nameInput != null && classOutput != null && methodOutput != null && topic != null && classInput != "" && methodInput != "" && nameInput != ""
                && classOutput != "" && methodOutput != "" && topic != "") {
            try {
                Statement st = conn.createStatement();
                PreparedStatement stp = conn.prepareStatement("insert into \"Controller\"(classInput,methodInput,nameInputDevice,classOutput,methodOutput,topic,data) values (?,?,?,?,?,?,?)");
                stp.setString(1, classInput);
                stp.setString(2, methodInput);
                stp.setString(3, nameInput);
                stp.setString(4, classOutput);
                stp.setString(5, methodOutput);
                stp.setString(6, topic);
                stp.setString(7, data);
                stp.execute();

            } catch (SQLException e) {
              
                String message = e.getMessage();
                if (!message.contains("doppelter Schlï¿½sselwert")) {
                    logger.error("SQL Exception", e);
                }
            }
            closeDBConnection();
            controllerToList();
        }
    }

    /**
     * This method deletes all saved rows in the database.
     */
    public static void deleteController()
    {
        controller.clear();
        createDBConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.execute("delete from \"Controller\";");
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }

        closeDBConnection();
    }

    /**
     * This method searches the methods from the classes of the first and the
     * second component. If a method of the second component needs a data, then
     * the String "Struktur fehlt" will be set in the column "data" of the table
     * "Classes". If a method does not need a data the column data is set="".
     */
    public static synchronized void searchMethods()
    {
        createDBConnection();
        try {
            Statement statement = conn.createStatement();
            statement.execute("delete from \"Classes\";");
            statement.execute("delete from \"Data\";");
            structures.clear();
            PreparedStatement st = conn.prepareStatement("insert into \"Classes\" (component, class, method) values (?,?,?); ");

            ArrayList classes = getClasses(component1);
            Iterator iterator = classes.iterator();
            st.setInt(1, 1);
            while (iterator.hasNext()) {
                Object class1 = iterator.next();
                Method[] methods = class1.getClass().getDeclaredMethods();
                String name1 = class1.getClass().getName();
                String[] className = name1.split("\\.");
                st.setString(2, className[1]);
                for (int i = 0; i < methods.length; i++) {
                    String name = methods[i].getName();
                    st.setString(3, name);
                    st.execute();
                }

            }
            classes = getClasses(component2);
            iterator = classes.iterator();
            st.setInt(1, 2);
            PreparedStatement st2 = conn.prepareStatement("insert into \"Data\" ( class, method,topic,data) values (?,?,?,?); ");
            while (iterator.hasNext()) {
                Object class2 = iterator.next();
                Method[] methods = class2.getClass().getDeclaredMethods();
                String name2 = class2.getClass().getName();
                String[] className = name2.split("\\.");
                st.setString(2, className[1]);
                st2.setString(1, className[1]);
                for (int i = 0; i < methods.length; i++) {
                    String method = methods[i].getName();
                    st2.setString(3, "");
                    st.setString(3, method);
                    st2.setString(2, method);
                    Class<?>[] parameter = methods[i].getParameterTypes();
                    for (int j = 0; j < parameter.length; j++) {
                        if (parameter[j].getName() == "Controller.Structure") {
                            st2.setString(4, "structure is missing");
                            st2.execute();
                            break;
                        }
                        if (parameter[j].getName() == "Controller.Sensor") {
                            st2.setString(4, "Sensor");
                            st2.execute();

                            break;
                        }

                    }
                    st.execute();
                }

            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        closeDBConnection();
    }

    // _____________________________________________________________________________________________
    // Methods which need to be changed when new classes or methods are created.
    /**
     * !!!! Important Method for creating new classes !!!! This method returns
     * the different classes of the first and second component. If a new class
     * is created, then it has to be added into this method. This method is
     * needed for the GUI. Method which use this method: getClassNames(...),
     * searchMethods(), getMethod()
     * 
     * @param component
     *            1 or 2
     * @return ArrayList: list with objects of the classes
     */
    private static ArrayList<Object> getClasses(String component)
    {
        // http://tutorials.jenkov.com/java-reflection/dynamic-class-loading-reloading.html
        // http://stackoverflow.com/questions/1456930/how-do-i-read-all-classes-from-a-java-package-in-the-classpath
        // http://www.dzone.com/snippets/get-all-classes-within-package
        ArrayList<Object> classes = new ArrayList<Object>();
        Enumeration<URL> urls;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            urls = loader.getResources(component);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                File file = new File(url.getFile());
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    String[] f = files[i].getName().split("\\.");
                    Class c;
                    
                        try {
                            c = loader.loadClass(component + "." + f[0]);
                            classes.add(c.newInstance());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            logger.error("Classes could not be refreshed", e);
                        }
                     
                }
            }
        } catch (IOException e) {
            logger.error("SQL Exception", e);
        }
        return classes;

    }

    // //Methods for the Servlet for the GUI. end
    // _____________________________________________________________________________
    // Private Methods for this class
    /**
     * This method invokes the methods from the second component. It is used by
     * the method informController(). The variables classes and methodName are
     * used to find the correct method. The variables nameInput,data and topic
     * are used for submit them to the method.
     * 
     * @param classes
     *            class from the method
     * @param methodName
     *            name of the method to invoke
     * @param nameInput
     *            name of the input device
     * @param data
     *            name of the data
     * @param topic
     *            topic for the message broker
     */
    private static Method getMethod(String classes,
            String methodName)
    {
        Method m = null;
        System.out.println(classes + " " + methodName);
        ArrayList<Object> getClasses = getClasses(component2);
        Iterator<Object> iterator = getClasses.iterator();
        // http://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string

        while (iterator.hasNext()) {
            Object c = iterator.next();
            System.out.println("Klassen der 2ten Schicht:" + c.getClass().getName());
            String name = c.getClass().getName();
            System.out.println(name);
            String[] classenName = name.split("\\.");
            if (classenName[1].equals(classes)) {
                Method[] methods = c.getClass().getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    System.out.println(methodName + "hallo:methode:" + methods[i].getName() + "methodenName:" + methodName);
                    if (methods[i].getName().equals(methodName)) {
                        try {
                            return methods[i];

                        } catch (IllegalArgumentException e) {
                            logger.error("Method of the second component could not be returned" , e);
                        }
                    }
                }
            }
        }
        return m;
    }

    /**
     * This method is used by the method informController(). It searches the
     * methods which should be invoked. The methods are saved in objects of the
     * class "MethodToInvoke"
     * 
     * @param classes
     *            class of the first component
     * @param method
     *            method of the first component
     * @param name
     *            name of the input device which has sent data
     * @return a list of the methods which were found
     */
    private static synchronized void controllerToList()
    {
        ArrayList<MethodToInvoke> methods = new ArrayList<MethodToInvoke>();
        controller.clear();
        createDBConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from \"Controller\" ;");
            ResultSet r = st.executeQuery();
            while (r.next()) {
                String key = r.getString("classinput") + "," + r.getString("methodinput") + "," + r.getString("nameinputdevice");
                ArrayList list = controller.get(key);

                Method m = getMethod(r.getString("classoutput"), r.getString("methodoutput"));
                MethodToInvoke m2 = new MethodToInvoke(r.getString("classoutput"), m, r.getString("data"), r.getString("topic"));
                if (list != null) {
                    list.add(m2);
                } else {
                    list = new ArrayList();
                    list.add(m2);
                }
                controller.put(key, list);
            }

        } catch (SQLException e) {
            logger.error("SQL Exception in controllerToList()", e);
        }
        closeDBConnection();

    }

    private static synchronized void ActiveDevicesToList()
    {

        deviceActive.clear();
        createDBConnection();
        try {
            PreparedStatement st = conn.prepareStatement("select * from \"OutputDevices\" ;");
            ResultSet r = st.executeQuery();
            while (r.next()) {
                String key = r.getString("class") + "," + r.getString("topic");
                deviceActive.put(key, r.getBoolean("enabled"));
            }

        } catch (SQLException e) {
            logger.error("SQL Exception in ActiveDevicesToList", e);
        }
        closeDBConnection();

    }

    private static String registerStructure(String classes,
            String method,
            String topic,
            String name,
            Structure structure)
    {
        String ok = "ok";
        createDBConnection();
        PreparedStatement stp;
        try {
            System.out.println(name + " " + classes + " " + method + "  " + topic);
            stp = conn.prepareStatement("select data from \"Data\" where class=? and method=? ");
            stp.setString(1, classes);
            stp.setString(2, method);
            ResultSet result = stp.executeQuery();

            if (result.next()) {
                structures.put(name, structure);
                if (!result.getString(1).equals("Sensor")) {
                    try {
                        stp = conn.prepareStatement("insert into \"Data\" (class, method, topic,data) values (?,?,?,?);");
                        stp.setString(1, classes);
                        stp.setString(2, method);
                        stp.setString(3, topic);
                        stp.setString(4, name);
                        stp.execute();
                    } catch (SQLException e) {
                        String message = e.getMessage();
                        if (!message.contains("doppelter Schlï¿½sselwert")) {
                            stp = conn.prepareStatement("Update \"Data\" set data=? where class=? and method=? and topic=?");
                            stp.setString(1, name);
                            stp.setString(2, classes);
                            stp.setString(3, method);
                            stp.setString(4, topic);
                            stp.execute();
                        }
                    }
                    stp = conn.prepareStatement("update \"Controller\" set data=? where classoutput=? and methodoutput=? and topic=?;");
                    stp.setString(1, name);
                    stp.setString(2, classes);
                    stp.setString(3, method);
                    stp.setString(4, topic);
                    stp.execute();
                    controllerToList();
                }

            }

        } catch (SQLException e) {
            
            String message = e.getMessage();
            if (!message.contains("doppelter Schlüsselwert")) {
                e.printStackTrace();
            } else {
                ok = "This method and topic has already a structure";
            }
        }

        closeDBConnection();
        return ok;
    }

    private static void deleteDevices()
    {
        createDBConnection();
        PreparedStatement stp;
        try {
            stp = conn.prepareStatement("delete from \"InputDevices\";");
            stp.execute();
            stp = conn.prepareStatement("delete from \"OutputDevices\";");
            stp.execute();
            stp =
                    conn.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' and table_name!='InputDevices'and table_name!='OutputDevices' and table_name!='Classes'and table_name!='Data'and table_name!='Controller'");
            ResultSet result = stp.executeQuery();
            while (result.next()) {
                String table = result.getString(1);
                stp = conn.prepareStatement("delete from \"" + table + "\";");
                stp.execute();
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in deleteDevices()", e);
        }
        closeDBConnection();
        deviceActive.clear();
    }

    /**
     * This method creates a connection to the postgresql database. After done
     * the requests the method closeDBConnection() has to be used. This is a
     * local method which is only used by other methods of the controller.
     */
    private static synchronized void createDBConnection()
    {
        
            Configuration config;
            try {
                config = new PropertiesConfiguration("GUIconf.properties");
                String databaseDriver = config.getString("databaseDriver");
                Class.forName(databaseDriver);

                if (conn == null || conn.isClosed()) {

                    String urlDatabase = config.getString("urlDatabase");
                    conn = DriverManager.getConnection(urlDatabase, "middleware", "queryDatabase");
                }
            } catch (ConfigurationException | ClassNotFoundException | SQLException e) {
                logger.error("Could not create a database connection", e);
            }
            

         
    }

    /**
     * This method closes the connection to the database. This is a local method
     * which is only used by other methods of the controller.
     */
    private static synchronized void closeDBConnection()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Database connection could not be closed", e);
        }
    }

    /**
     * This method checks if the embedded Broker is initialized and started
     **/

    private static void isBrokerStarted()
    {
        Configuration config;
        if (broker == null) { // Create an embedded broker

            try {
                config = new PropertiesConfiguration("GUIconf.properties");
                String tcpConnection = config.getString("tcpConnection");
                String wsConnection = config.getString("wsConnection");

                broker = new BrokerService();

                broker.addConnector(tcpConnection);
                broker.addConnector(wsConnection);
            } catch (Exception e) {
                logger.error("A new message broker (activemq) could not be created ", e);
            }

        }
        if (!broker.isStarted()) {

            try {
                broker.start();
            } catch (Exception e) {
                logger.error("Message broker could not be started", e);
            }

        }
    }

    private static void stopBroker()
    {
        try {
            broker.stop();
        } catch (Exception e) { 
            logger.error("Message broker could not be started", e);
        }
    }

}
