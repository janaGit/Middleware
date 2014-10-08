import java.io.IOException;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Diese Klasse empfängt die Nachrichten des Message Broker und leitet die
 * Befehle an den VLCPlayer weiter. Im Internet gibt es bereits Beipiele für die
 * Implementierung. Diese Codeabschintte wurden übernommen. Für die
 * Implementierung der Verbindung zu dem Message Broker ActiveMQ dienten
 * folgende Codebeispiele als Grundlage:
 * http://www.javablogging.com/simple-guide
 * -to-java-message-service-jms-using-activemq/ -> Für die grundlegende
 * Kommunikation.
 * http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html->
 * einen eingebetteten Broker starten
 * http://developers-blog.org/blog/d/SOA/2008/10/28/A-simple-ActiveMQ-example ->
 * für den MessageListener
 * https://forum.videolan.org/viewtopic.php?f=14&t=85347-> für die Kommunikation
 * mit dem VLCPlayer (Klasse:VLCControl)
 * http://commons.apache.org/proper/commons-net/download_net.cgi -> Bibliothek:
 * Net Framework
 * 
 * @author Jana
 *
 */
public class VLCPlayer {
    // URL of the Message Broker default-value=61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // This is the name for the topic to which this class listens.
    private static String subject = "VLCPlayer";

    private static AsyncWebResource service;

    private static Client client;

    private static ClientConfig config;

    public static void main(String[] args)
    {

        config = new DefaultClientConfig();
        client = Client.create(config);

        String url1 = "http://localhost:8088/Middleware/Controller/anmelden/1/Smartphone/+name";
        service = client.asyncResource(url1);
        service.get(String.class);
        try {
            // Create a connection to the message broker
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // Create a new session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Define to which topic to listen
            Destination destination = session.createTopic(subject);
            // Create a new Consumer for the topic
            MessageConsumer consumer = session.createConsumer(destination);
            // Create a new Listener and initialize it
            ActiveMQListener listener = new ActiveMQListener();
            listener.init();
            // set the Listener
            consumer.setMessageListener(listener);

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Vorlage ist
     * http://developers-blog.org/blog/d/SOA/2008/10/28/A-simple-ActiveMQ
     * -example
     * 
     * @author Jana, used an example and adapt it
     *
     */
    public static class ActiveMQListener implements MessageListener {
        VLCControl vlc;

        @Override
        public void onMessage(Message message)
        {
            // TODO Auto-generated method stub
            // checks if the message is a text message
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("Received message: " + textMessage.getText());
                    // Send the command to the VLC
                    try {
                        vlc.sendCommand(textMessage.getText());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        /**
         * Creates an object of VLCControl and creates a connection to the
         * VLCPlayer
         */
        public void init()
        {
            vlc = new VLCControl();
            try {
                vlc.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
