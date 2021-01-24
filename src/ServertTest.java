import javax.swing.*;

public class ServertTest {
    public static void main(String[] args) {
        Server application = new Server(); //creates the server
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.waitForPackets(); //run server application
    }
}
