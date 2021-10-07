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
        //define font 
        Font  f1  = new Font(Font.SERIF, Font.PLAIN,  20);

        JPanel page = new JPanel();

        //formatting the page using grid bag layout 
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbcnt = new GridBagConstraints();
        page.setLayout(gbl);
        GridBagLayout layout = new GridBagLayout();
        page.setLayout(layout);
        
        // Page components
        JLabel title = new JLabel("Project 4: The SQL");
        title.setFont(f1); 

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

        //adding items to the page
        //first row: title
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 20;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 3;
        gbcnt.gridy = 0;
        page.add(title, gbcnt);

        //second row: In Progress, Popular, and New buttons
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 5;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 0;
        gbcnt.gridy = 1;
        page.add(InProgress, gbcnt);    
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 2;
        gbcnt.gridy = 1;
        page.add(popular, gbcnt);
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 4;
        gbcnt.gridy = 1;
        page.add(New, gbcnt);

        // third row: likes and dislikes buttons
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 0;
        gbcnt.gridy = 2;
        page.add(likes, gbcnt);
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 3;
        gbcnt.gridy = 2;
        page.add(Dislikes, gbcnt);

        //fourth row: search bar
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 1;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 3;
        page.add(search, gbcnt);

        //fifth row: table 
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 4;
        page.add(new JScrollPane(table),gbcnt);

        gPages.put("viewerPage", page);
    }

    public static void generateAnalystPage()
    {
        //define font 
        Font  f1  = new Font(Font.SERIF, Font.PLAIN,  20); 

        JPanel page = new JPanel();

        //formatting the page using grid bag layout 
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbcnt = new GridBagConstraints();
        page.setLayout(gbl);
        GridBagLayout layout = new GridBagLayout();
        page.setLayout(layout);

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");
        title.setFont(f1);

        JButton content = new JButton("Content");
        JButton director = new JButton("Directors");
        JButton actor = new JButton("Actors");
        JTextField search= new JTextField("Search", 20);

        JLabel tabletitle = new JLabel("Popular Right Now");
        tabletitle.setFont(f1); 

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

        //adding items to the page  
        //first row: title
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 20;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 3; 
        gbcnt.gridy = 0;
        page.add(title, gbcnt);

        //second row: graph (to be implemented)
        // gbcnt.fill = GridBagConstraints.HORIZONTAL;
        // gbcnt.gridwidth = 6;
        // gbcnt.gridx = 0;
        // gbcnt.gridy = 1;
        // page.add( , gbcnt);

        //third row: Content, Director, and Actors buttons
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 5;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 0;
        gbcnt.gridy = 2;
        page.add(content, gbcnt);    
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 2;
        gbcnt.gridy = 2;
        page.add(director, gbcnt);
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 4;
        gbcnt.gridy = 2;
        page.add(actor, gbcnt);

        //fourth row: search bar 
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 1;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 3;
        page.add(search, gbcnt);

        //fifth row: table 1
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 4;
        page.add(new JScrollPane(table1),gbcnt);

        //sixth row: table title 
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 10;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 3;
        gbcnt.gridy = 5;
        page.add(tabletitle, gbcnt);

        //seventh row: table2
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 6;
        page.add(new JScrollPane(table2),gbcnt);

        gPages.put("analystPage", page);
        
    }

    // Generates the page which the user first encounters
    public static void generateLandingPage()
    {
        //define font 
        Font  f1  = new Font(Font.SERIF, Font.PLAIN,  20); 

        JPanel page = new JPanel();

        //formatting the page using grid bag layout 
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbcnt = new GridBagConstraints();
        page.setLayout(gbl);
        GridBagLayout layout = new GridBagLayout();
        page.setLayout(layout);

        // Page components
        JLabel title = new JLabel("I am a?");
        title.setFont(f1); 

        JButton viewer = new JButton("Content Viewer");

        viewer.addActionListener(e -> setPage("viewerPage"));

        JButton analyst = new JButton("Content Analyst");

        analyst.addActionListener(e -> setPage("analystPage"));
        
        //adding items to the page 
        //first row: title
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 20;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 3;
        gbcnt.gridy = 0;
        page.add(title, gbcnt);

        // second row: viewer and analyst buttons
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 0;
        gbcnt.gridy = 1;
        page.add(viewer, gbcnt);
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 3;
        gbcnt.gridy = 1;
        page.add(analyst, gbcnt);

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
