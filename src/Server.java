import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
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

    //wait for packets to arrive, display data and echo packet to client
    public void waitForPackets(){
        while (true){
            //receive packets, display contents, return copy to client
            try {
                byte[] data = new byte[100]; //set up packet
                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                datagramSocket.receive(receivePacket); //wait to receive packets

                //display information from received packets
                displayMessage("\nPacket received "
                        + "\nfrom host: " + receivePacket.getAddress()
                        + "\nHost port: " + receivePacket.getPort()
                        + "\nLength: " + receivePacket.getLength()
                        + "\nContaining\n\t " + new String(receivePacket.getData(), 0, receivePacket.getLength()));
                sendPacketToClient( receivePacket ); //send packet to client
            }catch (IOException ioException){
                displayMessage(ioException + "\n");
                ioException.printStackTrace();

            }
        }
    }

    //echo packet to client
    private void sendPacketToClient(DatagramPacket receivePacket) throws IOException{

        displayMessage("\n\nEcho data to client...");

        //create packet to send
        DatagramPacket sendPacket = new DatagramPacket(
                receivePacket.getData(),
                receivePacket.getLength(),
                receivePacket.getAddress(),
                receivePacket.getPort());
        datagramSocket.send(sendPacket); //send packet to client
        displayMessage("Packet sent successfully\n");

    }

    //manupilates the displayArea in the event-dispatch thread
    private void displayMessage(final String messageToDisplay) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { //updates display area
                displayArea.append(messageToDisplay);
            }
        });
    }
}
