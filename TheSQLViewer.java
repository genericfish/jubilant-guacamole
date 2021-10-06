import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
public class TheSQLViewer {
    // Contains every page
    static HashMap<String, JPanel> m_pages = new HashMap<String, JPanel>();
    static JPanel m_current_page = null;
    static JPanel m_body = null;

    public static void main(String[] args)
    {
        // Initialize the window
        JFrame frame = new JFrame("Project 4: The SQL");
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main body of the GUI. Panels will be added and removed to this component
        m_body = new JPanel();
        m_body.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Generate Pages
        generateLandingPage();
        generateViewerPage();

        frame.add(m_body);
        setPage("landingPage");

        frame.setVisible(true);
    }

    // Generates the page which the viewer would use
    public static void generateViewerPage()
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");

        page.add(title);

        m_pages.put("viewerPage", page);
    }

    // Generates the page which the user first encounters
    public static void generateLandingPage()
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("I am a?");

        JButton viewer = new JButton("Content Viewer");
        viewer.addActionListener(new ActionListener() {
            // When the button is pressed, switch m_pages
            public void actionPerformed(ActionEvent e)
            {
                setPage("viewerPage");
            }
        });

        JButton analyst = new JButton("Content Analyst");

        page.add(title);
        page.add(viewer);
        page.add(analyst);

        m_pages.put("landingPage", page);
    }

    // Removes the old page from the body and adds the new one
    public static void setPage(String pageName)
    {
        if (m_current_page != null)
            m_body.remove(m_current_page);

        m_current_page = m_pages.get(pageName);
        m_body.add(m_current_page);

        m_body.revalidate();
        m_body.repaint();
    }
}
