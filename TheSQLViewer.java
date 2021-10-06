import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
public class TheSQLViewer 
{ 
    // Contains every page
    static HashMap<String, JPanel> pages = new HashMap<String, JPanel>();
    
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

        // Generate Pages
        generateLandingPage(pages);
        generateViewerPage(pages);

        body.add(pages.get("landingPage"));


        frame.setVisible(true);
    }

    public static void generateViewerPage(HashMap<String, JPanel> pageList)
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");

        page.add(title);

        pageList.put("viewerPage", page);
    }

    public static void generateLandingPage(HashMap<String, JPanel> pageList)
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("I am a?");
        JButton viewer = new JButton("Content Viewer");
        JButton analyst = new JButton("Content Analyst");


        page.add(title);
        page.add(viewer);
        page.add(analyst);

        pageList.put("landingPage", page);
    }
}