package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ReaderToWriter implements Runnable
{
    DataInputStream in ;
    DataOutputStream out;

    public ReaderToWriter(DataInputStream in, DataOutputStream out)
    {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
                out.writeUTF(in.readUTF());
        } catch (IOException e)
        {
            System.out.println("clients disconnected!");
        }
    }
}
