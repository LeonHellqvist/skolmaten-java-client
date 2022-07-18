import javax.swing.*;
import java.awt.Color;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            System.out.println("Ditt system stöder inte detta UI");
        }

        Frame frame = new Frame();
    }
}
