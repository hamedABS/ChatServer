package client;

import gui.ChatPage;
import gui.LoginPage;
import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by Hamed-Abbaszadeh on 1/11/2018.
 */
public class Client
{
    JFrame loginFrame = new JFrame("login Frame");
    LoginPage loginPage =null;
    public Client()
    {
        login();
    }

    public void login()
    {
        loginPage = new LoginPage();
        loginFrame.setSize(400, 400);
        loginFrame.add(loginPage);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws InterruptedException
    {
        Client client = new Client();

       /*try(Socket socket = client.loginPage.getClientSocket();
           DataInputStream in = new DataInputStream(socket.getInputStream());
           DataOutputStream out= new DataOutputStream(socket.getOutputStream()))
        {
            new Thread(new Reader(in)).start();
            new Thread(new Writer(out,client.chatPage.getMessageTxt())).start();
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }*/

    }
}
