import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Frame {
    private JLabel title;
    private JPanel panelMain;
    private JPanel header;
    private JPanel navigator;
    private JButton backButton;
    private JButton forwardButton;
    private JPanel content;
    private JLabel textDisplay;

    private int offset = 0;
    public Frame() throws IOException {


        //Launching frames

        JFrame frame = new JFrame("Skolmaten");
        ApiHandler apiHandler = new ApiHandler();

        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        frame.setResizable(true); //prevent frame from being resized
        frame.setSize(420, 620); //set x and y dimension on frame
        frame.setVisible(true); //make frame visible

        setWeek(apiHandler.getWeek(0));
        /*frame.getContentPane().setBackground(new Color(18,18,18));*/

        // Listens for previous week button click
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offset--;
                try {
                    setWeek(apiHandler.getWeek(offset));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // Listens for next week button click
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offset++;
                try {
                    setWeek(apiHandler.getWeek(offset));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void setWeek(String value) {
        textDisplay.setText(value);
    }
}
