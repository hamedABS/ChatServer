package client;

import client.gui.LoginPage;
import javax.swing.*;

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
        new Client();
    }
}
