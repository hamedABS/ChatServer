package server;
import java.io.IOException;
import java.net.*;
import java.util.*;


public class Server
{
    ServerSocket serverSocket;
    HashMap<Account , Connection> clientsSockets =new HashMap<>();
    Account[] accounts = new Account[3];
    Socket client1;
    Socket client2;
    DatagramPacket packet =null;
    DatagramSocket socket =null;

    Server()
    {
        inintializeAccounts();
        response();
    }

    public void response()
    {
        try
        {
            serverSocket = new ServerSocket(8090);
            socket = new DatagramSocket(8090);

            while (true)
            {
                update();
                String[] request = receive();
                switch (request[0])
                {
                    case "Login":
                        login(request);
                        break;
                    case "List":
                        list();
                        break;
                    case "Connect":
                        connect(request);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //this method receive request from client to get response
    public String[] receive() throws IOException
    {
        byte[] buff = new byte[128];
        packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        String s = new String(packet.getData(),packet.getOffset(),packet.getLength());
        System.out.println(s);
        return s.split(" ");
    }

    public void login(String[] request)
    {
        Account account = new Account(request[1], request[2]);

        boolean isSend=false;
        for (int j = 0; j < accounts.length; j++)
        {
            if (account.equals(accounts[j]))
            {
                try
                {
                    sendResponse("OK");
                    isSend = true;
                    client1 = serverSocket.accept();
                    System.out.println("client connected");
                    System.out.println(packet.getAddress());
                    System.out.println(packet.getPort());
                    clientsSockets.put(account,new Connection(client1,packet.getAddress(),packet.getPort()));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(!isSend)
        {
            sendResponse("NotOK");
        }
    }

    public void sendResponse( String message)
    {
        byte[] buff= message.getBytes();
        try
        {
            DatagramPacket sendPacket =
                    new DatagramPacket(buff, buff.length, packet.getAddress(),packet.getPort());
            socket.send(sendPacket);
        } catch (IOException ex)
        {
            System.err.println("IO exception in sendResponse");
        }
    }

    //update client sockets when a client leave chat or close chat ...
    public void update()
    {
        Iterator it= clientsSockets.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap.Entry pair = (Map.Entry)it.next();
            if(((Connection)pair.getValue()).socket.isClosed())
            {
                it.remove();
            }
        }
    }

    public void inintializeAccounts()
    {
        accounts[0] = new Account("hamed", "hamed");
        accounts[1] = new Account("hani", "hani");
        accounts[2] = new Account("sina", "sina");
    }

    public void list()
    {
        String onlines =new String();
        Iterator it= clientsSockets.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap.Entry pair = (Map.Entry)it.next();
            if(!((Connection)pair.getValue()).isBusy)
            onlines+=((Account)pair.getKey()).getUser()+" ";
        }
        sendResponse(onlines);
    }

    public void connect(String[] request) throws IOException
    {
        System.out.println("in Connect case");
        String userName1=request[1];
        String userName2=request[2];
        InetAddress ip =null;
        int port = 0;

        Iterator it = clientsSockets.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap.Entry pair = (Map.Entry)it.next();

            if(((Account)pair.getKey()).getUser().equals(userName1))
            {
                client1=((Connection)pair.getValue()).socket;
                ((Connection)pair.getValue()).isBusy = true;
            }
            else if(((Account)pair.getKey()).getUser().equals(userName2))
            {
                client2=((Connection)pair.getValue()).socket;
                ip = ((Connection)pair.getValue()).ip;
                port= ((Connection)pair.getValue()).port;
                System.out.println(ip);
                System.out.println(port);
                ((Connection)pair.getValue()).isBusy=true;
            }
        }


        //send Connection Request to second client
        byte[] buff ="ConnectionRequest".getBytes();
        DatagramPacket packet = new DatagramPacket(buff,buff.length ,ip , port);
        socket.send(packet);

        System.out.println("thread starting");
        new Thread(new ServerConnector(client1 , client2)).start();

    }

    public void close()
    {

    }

    public static void main(String[] args)
    {
        Server server = new Server();
    }
}
