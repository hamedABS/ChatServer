package client;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Writer implements Runnable
{
    DataOutputStream out ;
    JTextField textField ;
    Scanner scanner = new Scanner((Readable)textField);
    public Writer(DataOutputStream out , JTextField textField)
    {
        this.textField = textField;
        this.out = out;
    }

    @Override
    public void run()
    {
        String s ;
        while (true)
        {
            try
            {
                s =scanner.nextLine();
                if(s.equals(".close"))
                {
                    out.writeUTF(s);
                    System.exit(1);
                }
                else
                    out.writeUTF(s);
            } catch (IOException e)
            {
                System.err.println("Writer run failed");
            }
        }
    }
}
