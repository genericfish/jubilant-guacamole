import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

public class TheSQLViewer {
    // Contains every page
    static HashMap<String, JPanel> gPages = new HashMap<String, JPanel>();
    static JPanel gCurrentPage = null;
    static JPanel gBody = null;

    public static void main(String[] args)
    {
        // Initialize the window
        JFrame frame = new JFrame("Project 4: The SQL");
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main body of the GUI. Panels will be added and removed to this component
        gBody = new JPanel();
        gBody.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Generate Pages
        generateLandingPage();
        generateViewerPage();
        generateAnalystPage(); 

        frame.add(gBody);
        setPage("landingPage");

        frame.setVisible(true);
    }

    // Generates the page which the viewer would use
    public static void generateViewerPage()
    {
        JPanel page = new JPanel();
        
        // Page components
        JLabel title = new JLabel("Project 4: The SQL");
        //page.setLayout(new BorderLayout());

        JButton InProgress = new JButton("In Progess");
        JButton popular = new JButton("Popular");
        JButton New = new JButton("New");
        JButton likes = new JButton("Likes");
        JButton Dislikes = new JButton("Dislikes"); 
        JTextField search= new JTextField("Search", 20);

        String[][] rec = {
         { "1", "Steve", "AUS", " ", " " },
         { "2", "Virat", "IND", " ", " "},
         { "3", "Kane", "NZ", " ", " " },
         { "4", "David", "AUS", " ", " " },
         { "5", "Ben", "ENG", " "," " },
         { "6", "Eion", "ENG", " "," " },
        };
        String[] header = { "Name", "Genre", "Year", "Runtime", " " };
        JTable table = new JTable(rec, header);

        page.add(title);
        page.add(InProgress);
        page.add(popular);
        page.add(New);
        page.add(likes);
        page.add(Dislikes); 
        page.add(search); 
        page.add(new JScrollPane(table));

        gPages.put("viewerPage", page);
    }

    public static void generateAnalystPage()
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");

        page.add(title);

        JButton content = new JButton("Content");
        JButton director = new JButton("Directors");
        JButton actor = new JButton("Actors");
        JTextField search= new JTextField("Search", 20);

        page.add(content);
        page.add(director);
        page.add(actor); 
        page.add(search);

        JLabel tabletitle = new JLabel("Popular Right Now");

        String[][] rec1 = {
         { "1", "Steve", "AUS" },
         { "2", "Virat", "IND"},
         { "3", "Kane", "NZ"},
         { "4", "David", "AUS" },
         { "5", "Ben", "ENG"},
         { "6", "Eion", "ENG"},
        };
        String[] header1 = { "Title", "stuff", "Popularity" };
        JTable table1 = new JTable(rec1, header1);

        String[][] rec2 = {
         { "1", "Steve", "AUS" },
         { "2", "Virat", "IND"},
         { "3", "Kane", "NZ"},
         { "4", "David", "AUS"},
         { "5", "Ben", "ENG"},
         { "6", "Eion", "ENG"},
        };
        String[] header2 = { "Genre", "Directors", "Actors"};
        JTable table2 = new JTable(rec2, header2);

        page.add(new JScrollPane(table1));
        page.add(tabletitle); 
        page.add(new JScrollPane(table2));

        gPages.put("analystPage", page);
        
    }

    // Generates the page which the user first encounters
    public static void generateLandingPage()
    {
        JPanel page = new JPanel();

        // Page components
        JLabel title = new JLabel("I am a?");

        JButton viewer = new JButton("Content Viewer");

        viewer.addActionListener(e -> setPage("viewerPage"));

        JButton analyst = new JButton("Content Analyst");

        analyst.addActionListener(e -> setPage("analystPage"));

        page.add(title);
        page.add(viewer);
        page.add(analyst);

        gPages.put("landingPage", page);
    }

    // Removes the old page from the body and adds the new one
    public static void setPage(String pageName)
    {
        if (gCurrentPage != null)
            gBody.remove(gCurrentPage);

        gCurrentPage = gPages.get(pageName);
        gBody.add(gCurrentPage);

        gBody.revalidate();
        gBody.repaint();
    }
}
