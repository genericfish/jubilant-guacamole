import java.awt.*;
import java.awt.event.*;
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
        pages.put("body", body);
        body.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(body);

        // Generate Pages
        generateLandingPage(pages);
        generateViewerPage(pages);

        body.add(pages.get("landingPage"));


        frame.setVisible(true);
    }

    // Generates the page which the viewer would use
    public static void generateViewerPage(HashMap<String, JPanel> pageList)
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");

        page.add(title);

        pageList.put("viewerPage", page);
    }

    // Generates the page which the user first encounters
    public static void generateLandingPage(HashMap<String, JPanel> pageList)
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("I am a?");

        JButton viewer = new JButton("Content Viewer");
        viewer.addActionListener(new ActionListener(){
            // When the button is pressed, switch pages
            public void actionPerformed(ActionEvent e)
            {
                switchPage(pages, "viewerPage", "landingPage");
            }
        });

        JButton analyst = new JButton("Content Analyst");

        page.add(title);
        page.add(viewer);
        page.add(analyst);

        pageList.put("landingPage", page);
    }

    // Removes the old page from the body and adds the new one
    public static void switchPage(HashMap<String, JPanel> pageList, String newPage, String oldPage)
    {
        JPanel body = pageList.get("body");
        pageList.get(oldPage).setVisible(false);
        body.add(pageList.get(newPage));
        body.remove(pageList.get(oldPage));
    }
}