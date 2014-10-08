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
