import javax.swing.*;

public class ClientTest {
    public static void main(String[] args) {
        Client app = new Client();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.waitForPackets();
    }
}
