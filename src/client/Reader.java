package client;

/**
 * Created by Hamed-Abbaszadeh on 1/12/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.DataInputStream;

public class Reader implements Runnable
{
   private DataInputStream in ;
   private JTextArea textArea ;

    public Reader(DataInputStream in , JTextArea textArea)
    {
        this.textArea= textArea;
        this.in = in;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                String input = in.readUTF();
                if (input.equals(".close"))
                {
                    System.err.println("chat closed");
                    System.exit(1);
                    break;
                }
                else
                {
                    System.out.println(input);
                    textArea.append(input+"\n");
                }
            }

        } catch (IOException e)
        {
            System.err.println("reader run failed");
        }
    }

}
