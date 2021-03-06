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
package de.klemp.middleware.component_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import de.klemp.middleware.controller.Controller;
import de.klemp.middleware.controller.Sensor;
import de.klemp.middleware.controller.SimpleStructure;
import de.klemp.middleware.controller.Structure;

public class GUI_Controller {
    static String time = "";
    private static Logger logger = Logger.getLogger(GUI_Controller.class);
    static long longtime;

    public static void control(String classes,
            String name,
            Structure structure,
            String topic)
    {
        String s = Controller.queryDatabase("select event from \"" + classes + "\"where nameInput='" + name + "';", true);
        String neu = "";
        if (s.equals("rechts")) {
            neu = structure.right();
        }
        if (s.equals("links")) {
            neu = structure.left();
        }
        if (s.equals("oben")) {
            neu = structure.up();
        }
        if (s.equals("unten")) {
            neu = structure.down();
        }
        if (s.equals("ok")) {
            neu = "click";
        }
        Controller.sendMessage(neu, topic);
        System.out.println(neu + " " + topic + structure.getCurrent());
    }

    public static void saveTimeInFile(String name,
            String topic)
    {
        // Controller.getValue("select action from \"Control\"where );
        System.out.println("Steuerung2 erreicht" + topic);
        
            FileOutputStream out;
            try {
                out = new FileOutputStream(new File("sensorenTime" + name + ".txt"));
                out.write(time.getBytes(), 0, time.getBytes().length);
                out.close();
            } catch (IOException e) {
               logger.error("Time could not be stored in file",e);
            }
                
           
    }

    // Controller.sendMessage("pause", topic);
    public static void sensor(String name,
            String topic,
            Sensor sensor)
    {
        System.out.println(sensor.getList().get(0) + "," + sensor.getList().get(1) + "," + sensor.getList().get(2));
        Controller.sendMessageFast("" + sensor.getList().get(0), topic);
        if (name == "Sensor2") {
            time = time + longtime + ",";
        }
    }
}
