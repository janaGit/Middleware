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

import java.io.IOException;

import org.apache.log4j.Logger;

import de.klemp.middleware.controller.Controller;
import de.klemp.middleware.controller.VLCControl;

public class VLCPlayerIntern {
    private static VLCControl vlcPlayer;
    private static Logger logger = Logger.getLogger(VLCPlayerIntern.class);
    private static void init()
    {
        if (vlcPlayer == null) {
            vlcPlayer = new VLCControl();
            try {
                vlcPlayer.connect();
            } catch (IOException e) {
                logger.error("Could not connect to VLCPLayer", e);
            }
        }
    }

    public static void start(String name,
            String topic)
    {
        send();
    }

    public static void stop(String name,
            String topic)
    {
        send();
    }

    public static void pause(String name,
            String topic)
    {
        send();
      
    }

    public static void slower(String name,
            String topic)
    {
        send();
    }

    public static void faster(String name,
            String topic)
    {
        send();
    }
private static void send(){
    init();
    //http://www.java-forum.org/java-basics-anfaenger-themen/1191-methodennamen-laufzeit-ausgeben.html
    Exception ex = new Exception();
    StackTraceElement stackTop = ex.getStackTrace()[0];
    String methodName = stackTop.getMethodName();
    try {
        vlcPlayer.sendCommand(methodName);
    } catch (IOException e) {
        logger.error("Command "+methodName+" could not be sended", e);
    }
}
}
