import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client extends JFrame {

   private   JTextField enterField;
   private JTextArea displayArea;
   private DatagramSocket socket;

   public Client(){
       enterField = new JTextField("Type message here: ");
       enterField.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //create and send packet
               try{
                   //get message from textfield
                   String message = e.getActionCommand();
                   displayArea.append("\nSending packet containing: " + message + "\n");
                   byte[] data = message.getBytes(); //converts to bytes

                   //create sendPacket
                   DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 5000);
                   socket.send(sendPacket); //send packet
                   displayArea.append("Packet sent\n");
                   displayArea.setCaretPosition(displayArea.getText().length());
               }catch (IOException ioException){
                   displayMessage(ioException + "\n");
                   ioException.printStackTrace();
               }
           }
       });
       add(enterField, BorderLayout.NORTH);
       displayArea = new JTextArea();
       add(new JScrollPane(displayArea), BorderLayout.CENTER);
       setSize(400, 300);
       setVisible(true);

       //create DatagramSocket for sending and receiving packets
       try {
           socket = new DatagramSocket();
       }catch (SocketException socketException){
           socketException.printStackTrace();
           System.exit(1);
       }
   }

   //wait for packets to arrive from Server, display packet contents
    public void waitForPackets(){
       while (true){

           //receive packets and display contents
           try{
               byte [] data = new byte[100];
               DatagramPacket receivePacket = new DatagramPacket(data, data.length);
               socket.receive(receivePacket); //wait for packets

               //display packet contents
               displayMessage("\nPacket received " +
                       "\nFrom host: " + receivePacket.getAddress() +
                       "\nHost port: " + receivePacket.getPort() +
                       "\nLength: " + receivePacket.getLength() +
                       "\nContaining:\n\t:" + new String(receivePacket.getData(), 0, receivePacket.getLength()));
           }catch (IOException ioException){
               displayMessage(ioException + "\n");
               ioException.printStackTrace();
           }
       }
    }

    private void displayMessage(final String messageToDisplay) {

       SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               displayArea.append(messageToDisplay);
           }
       });
    }
}
