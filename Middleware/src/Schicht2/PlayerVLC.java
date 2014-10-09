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

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import Controller.Controller;

public class PlayerVLC {
    public static void start(String name,
            String topic)
    {
        Controller.sendMessage("start", topic);
    }

    public static void stop(String name,
            String topic)
    {
        Controller.sendMessage("stop", topic);
    }

    public static void pause(String name,
            String topic)
    {
        Controller.sendMessage("pause", topic);
    }

    public static void slower(String name,
            String topic)
    {
        Controller.sendMessage("slower", topic);
    }

    public static void faster(String name,
            String topic)
    {
        Controller.sendMessage("faster", topic);
    }
}
