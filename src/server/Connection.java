package server;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Hamed-Abbaszadeh on 1/12/2018.
 */
public class Connection
{
    Socket socket ;
    boolean isBusy;
    InetAddress ip ;
    int port;

    public Connection(Socket socket,InetAddress ip , int port )
    {
        this.socket = socket;
        this.isBusy=false;
        this.ip = ip;
        this.port =port;
    }

}
