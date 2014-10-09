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
package Schicht2;

import java.io.IOException;

import Controller.Controller;
import Controller.VLCControl;

public class VLCPlayerIntern {
    private static VLCControl vlcPlayer;

    private static void init()
    {
        if (vlcPlayer == null) {
            vlcPlayer = new VLCControl();
            try {
                vlcPlayer.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void start(String name,
            String topic)
    {
        init();
        try {
            vlcPlayer.sendCommand("play");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void stop(String name,
            String topic)
    {
        init();
        try {
            vlcPlayer.sendCommand("stop");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void pause(String name,
            String topic)
    {

        init();
        try {
            vlcPlayer.sendCommand("pause");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void slower(String name,
            String topic)
    {
        init();
        try {
            vlcPlayer.sendCommand("slower");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;
    }

    public static void faster(String name,
            String topic)
    {
        init();
        try {
            vlcPlayer.sendCommand("faster");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
