package Schicht1;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.xml.internal.ws.util.StringUtils;

import Controller.Controller;
import Controller.Sensor;

@Path("/Smartphone")
public class Smartphone {
    static Controller controller;

    @GET
    @Path("/rechtsKlick/{name}")
    public static void rechtsKlick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='rechts' where nameInput='" + name + "';", false);
        Controller.informController("Smartphone", "rechtsKlick", name);
    }

    @GET
    @Path("/linksKlick/{name}")
    public static void linksKlick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='links' where nameInput='" + name + "';", false);

        Controller.informController("Smartphone", "linksKlick", name);
    }

    @GET
    @Path("/obenKlick/{name}")
    public static void obenKlick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='oben' where nameInput='" + name + "';", false);

        Controller.informController("Smartphone", "obenKlick", name);
    }

    @GET
    @Path("/untenKlick/{name}")
    public static void untenKlick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='unten' where nameInput='" + name + "';", false);
        Controller.informController("Smartphone", "untenKlick", name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/rotation/{name}/{x}/{y}/{z}")
    public static void rotation(@PathParam("name") String name,
            @PathParam("x") float x,
            @PathParam("y") float y,
            @PathParam("z") float z)
    {
        // Controller.queryDatabase("Update \"Smartphone\" set rotation='"+x+";"+y+";"+z+"' where  nameInput='"+name+"'",
        // false);
        ArrayList list = new ArrayList();
        list.add(x);
        list.add(y);
        list.add(z);
        Sensor sensor = new Sensor(list);
        // System.out.println(x+","+y+","+z);
        Controller.informSensorController("Smartphone", "rotation", name, sensor);

    }

    @GET
    @Path("/okKlick/{name}")
    public static void okKlick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='ok' where nameInput='" + name + "';", false);
        Controller.informController("Smartphone", "okKlick", name);
    }
}
