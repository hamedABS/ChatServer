package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerConnector implements Runnable
{
    private Socket client1;
    private Socket client2;
    ServerConnector(Socket client1 , Socket client2)
    {
        this.client1 = client1;
        this.client2=client2;
    }
    @Override
    public void run()
    {
        try
        {
            DataInputStream in1 = new DataInputStream(client1.getInputStream());
            DataInputStream in2 = new DataInputStream(client2.getInputStream());
            DataOutputStream out1 = new DataOutputStream(client1.getOutputStream());
            DataOutputStream out2 = new DataOutputStream(client2.getOutputStream());
            synchronized (client2)
            {
                System.out.println(client2.toString());
                System.out.println("notifying...");
                client2.notify();
                System.out.println("notified..");
            }
            new Thread(new ReaderToWriter(in1 , out2)).start();
            new Thread(new ReaderToWriter(in2 , out1)).start();
        }
        catch (IOException e)
        {
            System.err.println("IO Exception happened in ServerConnector Run!");
        }
    }
}
