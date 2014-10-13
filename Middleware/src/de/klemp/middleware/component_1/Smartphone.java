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
package de.klemp.middleware.component_1;

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

import de.klemp.middleware.controller.Controller;
import de.klemp.middleware.controller.Sensor;

@Path("/Smartphone")
public class Smartphone {
    static Controller controller;

    @GET
    @Path("/rechtsKlick/{name}")
    public static void rightClick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='rechts' where nameInput='" + name + "';", false);
        Controller.informController("Smartphone", "rechtsKlick", name);
    }

    @GET
    @Path("/leftClick/{name}")
    public static void leftClick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='links' where nameInput='" + name + "';", false);

        Controller.informController("Smartphone", "linksKlick", name);
    }

    @GET
    @Path("/obenKlick/{name}")
    public static void upClick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='oben' where nameInput='" + name + "';", false);

        Controller.informController("Smartphone", "obenKlick", name);
    }

    @GET
    @Path("/untenKlick/{name}")
    public static void downClick(@PathParam("name") String name)
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
    public static void okClick(@PathParam("name") String name)
    {
        name.replaceAll("\"", "\\\"");
        Controller.queryDatabase("update \"Smartphone\"set event='ok' where nameInput='" + name + "';", false);
        Controller.informController("Smartphone", "okKlick", name);
    }
}
