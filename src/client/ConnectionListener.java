package client;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Hamed-Abbaszadeh on 1/14/2018.
 */
public class ConnectionListener implements Runnable
{

    WriterInPage writerInPage;
    DatagramSocket socket ;

    public ConnectionListener(DatagramSocket socket, WriterInPage writerInPage)
    {
        this.socket =socket;
        this.writerInPage = writerInPage;
    }

    @Override
    public void run()
    {
        try
        {
            byte[] buff = new byte[128];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            String response;
            while (true)
            {
                System.out.println("before receiving...");
                socket.receive(packet);
                System.out.println("request received!!!!!!!!!");
                response = new String(packet.getData(), packet.getOffset(), packet.getLength());
                if (response.equals("ConnectionRequest"))
                {
                    JFrame chat = new JFrame("Chat Page ");
                    chat.setSize(500, 500);
                    chat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    writerInPage.initComponents();
                    chat.add(writerInPage);
                    chat.setVisible(true);
                }
            }
        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
