import java.awt.*;
import javax.swing.*;
public class TheSQLViewer 
{
    
    public static void main(String[] args) 
    {
        // Initialize the window
        JFrame frame = new JFrame("Project 4: The SQL");
        frame.setSize(700,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main body of the GUI. Panels will be added and removed to this component
        JPanel body = new JPanel();
        body.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(body);

        frame.setVisible(true);
    }
}