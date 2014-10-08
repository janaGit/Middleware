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
