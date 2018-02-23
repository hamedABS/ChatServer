package client.gui;


import client.ConnectionListener;
import client.Reader;
import client.WriterInPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;


public class LoginPage extends JPanel
{

    JLabel serverIpLbl;
    JLabel serverPortLbl;
    JLabel userNameLbl;
    JLabel passWordLbl;
    JTextField serverIp;
    JTextField serverPort;
    JTextField userName ;
    JTextField passWord ;
    JButton connectBtn;
    Socket clientSocket;

    public LoginPage()
    {
        inint();
    }

    public void inint()
    {
        this.setSize(300 , 300);
        this.setLayout(null);
        serverIpLbl =new JLabel("server ip : ");
        serverPortLbl = new JLabel("server port : ");
        userNameLbl = new JLabel("userName : ");
        passWordLbl = new JLabel("passWord : ");
        userName =new JTextField();
        passWord = new JTextField();
        serverIp = new JTextField();
        serverPort = new JTextField();
        connectBtn = new JButton("Connect");


        serverIpLbl.setBounds(20 , 50 , 150 , 25);
        serverPortLbl.setBounds(20 , 80 , 150 , 25);
        userNameLbl.setBounds(20,110,150,25);
        passWordLbl.setBounds(20,140,150,25);
        serverIp.setBounds(130 , 50 , 200,25);
        serverPort.setBounds(130 , 80 , 200,25);
        userName.setBounds(130,110,200,25);
        passWord.setBounds(130,140,200,25);
        connectBtn.setBounds(150 , 250,100,40);
        this.add(passWord);
        this.add(passWordLbl);
        this.add(userName);
        this.add(userNameLbl);
        this.add(serverIp);
        this.add(serverIpLbl);
        this.add(serverPort);
        this.add(serverPortLbl);
        this.add(connectBtn);
        connectBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                connectBtnActionPerformed();
            }
        });


    }
    public void connectBtnActionPerformed()
    {

        String connectMessage = "Login " + userName.getText() +" "+passWord.getText();
        try
        {
            byte[] buffOut = connectMessage.getBytes();
            byte[] buffIn = new byte[128];

            DatagramSocket socket= new DatagramSocket();
            System.out.println(InetAddress.getByName(serverIp.getText()));
            DatagramPacket packet = new DatagramPacket(buffOut,buffOut.length, InetAddress.getByName(serverIp.getText()),
                    Integer.parseInt(serverPort.getText()));
            DatagramPacket packet2 = new DatagramPacket(buffIn,buffIn.length);
            socket.send(packet);
            socket.receive(packet2);
            String response =new String(packet2.getData(), packet2.getOffset(), packet2.getLength());
            if(response.equals("OK"))
            {
                clientSocket = new Socket(serverIp.getText(),Integer.parseInt(serverPort.getText()));
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                WriterInPage writerInPage = new WriterInPage(out);
                Reader reader = new Reader(in,writerInPage.getTxtArea2());
                new Thread(reader).start();
                new Thread(writerInPage).start();
                new Thread(new ConnectionListener(socket , writerInPage)).start();

                JFrame selectConnection = new JFrame("Select Connection Page");
                SelectConnectionPage  selectConnectionPage = new SelectConnectionPage(writerInPage);
                //initialize this client fields
                selectConnectionPage.setClient1(userName.getText());
                selectConnectionPage.setPort(getPort());

                selectConnection.add(selectConnectionPage);
                selectConnection.setSize(400,400);
                selectConnection.setVisible(true);
                selectConnection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            else{
                JOptionPane.showMessageDialog(this,"userName or Password incorrect!","error",JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    public Socket getClientSocket()
    {
        return clientSocket;
    }

    public int getPort()
    {
        return  Integer.parseInt(serverPort.getText());
    }
    public String  getIp()
    {
        return serverIp.getText();
    }

    public static void main(String[] args)
    {
        LoginPage loginPage = new LoginPage();
        JFrame loginFrame = new JFrame("Login Page");
        loginFrame.setSize(400, 400);
        loginFrame.add(loginPage);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
