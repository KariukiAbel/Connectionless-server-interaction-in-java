import javax.swing.*;
import java.awt.*;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends JFrame {

    private JTextArea displayArea; //displays packets received
    private DatagramSocket datagramSocket; //socket to connect to client

    //set up GUI and datagramSocket
    public Server(){
        super("Server");
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(400, 300);
        setVisible(true);

        try{ //create DatagramSocket for sending and receiving packets
            datagramSocket = new DatagramSocket(5000);
        }catch (SocketException socketException){
            socketException.printStackTrace();
            System.exit(1);
        }
    }


}
