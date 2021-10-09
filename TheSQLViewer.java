import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class TheSQLViewer {
    // Contains every page
    static HashMap<String, JPanel> gPages = new HashMap<String, JPanel>();
    static JPanel gCurrentPage = null;
    static JPanel gBody = null;
    static TheSQLSQL gDatabase = null;
    static String gUsername = null;
    static JTable gCurrentTable = null;

    public static void main(String[] args)
    {
        gDatabase = new TheSQLSQL();

        // Initialize the window
        JFrame frame = new JFrame("Project 4: The SQL");
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main body of the GUI. Panels will be added and removed to this component
        gBody = new JPanel();
        gBody.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Generate Pages
        generateLoginPage();
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
        Font f1 = new Font(Font.SERIF, Font.PLAIN, 20);

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

        JButton History = new JButton("History");
        JButton New = new JButton("New");
        JButton likes = new JButton("Likes");
        JButton Dislikes = new JButton("Dislikes");
        JTextField search = new JTextField("Search", 20);

        History.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel)gCurrentTable.getModel();
            model.setRowCount(0);

            ResultSet result = gDatabase.query("SELECT * FROM titles LIMIT 20;");

            String[] columns = { "originaltitle", "genres", "year", "runtimeminutes" };

            Vector<Vector<String>> rec = gDatabase.getTable(result, columns);

            for (Vector<String> row : rec) {
                model.addRow(row);
            }

            gCurrentTable.getColumnModel().getColumn(0).setPreferredWidth(300);

            gBody.revalidate();
            gBody.repaint();
        });

        New.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel)gCurrentTable.getModel();
            model.setRowCount(0);

            ResultSet result = gDatabase.query("SELECT * FROM titles OFFSET 20 LIMIT 20;");

            String[] columns = { "originaltitle", "genres", "year", "runtimeminutes" };

            Vector<Vector<String>> rec = gDatabase.getTable(result, columns);

            for (Vector<String> row : rec) {
                model.addRow(row);
            }

            gCurrentTable.getColumnModel().getColumn(0).setPreferredWidth(300);

            gBody.revalidate();
            gBody.repaint();
        });

        ResultSet result = gDatabase.query("SELECT * FROM titles OFFSET 20 LIMIT 20;");

        String[] columns = { "originaltitle", "genres", "year", "runtimeminutes" };

        Vector<Vector<String>> rec = gDatabase.getTable(result, columns);

        String[] header = { "Name", "Genre", "Year", "Runtime" };

        gCurrentTable = new JTable(rec, new Vector(Arrays.asList(header)));

        gCurrentTable.getColumnModel().getColumn(0).setPreferredWidth(300);

        //fifth row: table
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 4;
        page.add(new JScrollPane(gCurrentTable), gbcnt);

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
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 0;
        gbcnt.gridy = 1;
        page.add(History, gbcnt);
        gbcnt.gridwidth = 3;
        gbcnt.gridx = 3;
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

        gPages.put("viewerPage", page);
    }

    public static void generateAnalystPage()
    {
        //define font
        Font f1 = new Font(Font.SERIF, Font.PLAIN, 20);

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
        JTextField search = new JTextField("Search", 20);

        JLabel tabletitle = new JLabel("Popular Right Now");
        tabletitle.setFont(f1);

        String[][] rec1 = {
            { "1", "Steve", "AUS" },
            { "2", "Virat", "IND" },
            { "3", "Kane", "NZ" },
            { "4", "David", "AUS" },
            { "5", "Ben", "ENG" },
            { "6", "Eion", "ENG" },
        };
        String[] header1 = { "Title", "stuff", "Popularity" };
        JTable table1 = new JTable(rec1, header1);

        String[][] rec2 = {
            { "1", "Steve", "AUS" },
            { "2", "Virat", "IND" },
            { "3", "Kane", "NZ" },
            { "4", "David", "AUS" },
            { "5", "Ben", "ENG" },
            { "6", "Eion", "ENG" },
        };
        String[] header2 = { "Genre", "Directors", "Actors" };
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
        page.add(new JScrollPane(table1), gbcnt);

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
        page.add(new JScrollPane(table2), gbcnt);

        gPages.put("analystPage", page);
    }

    public static void generateLoginPage()
    {
        //define font
        Font f1 = new Font(Font.SERIF, Font.PLAIN, 20);

        JPanel page = new JPanel();

        //formatting the page using grid bag layout
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbcnt = new GridBagConstraints();
        page.setLayout(gbl);
        GridBagLayout layout = new GridBagLayout();
        page.setLayout(layout);

        // Page components
        JLabel title = new JLabel("Customer ID:");
        title.setFont(f1);

        JTextField text = new JTextField("1488844");

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            gUsername = text.getText();
            System.out.println(gUsername);

            setPage("viewerPage");
        });

        page.add(text);
        page.add(loginButton);

        gPages.put("loginPage", page);
    }

    // Generates the page which the user first encounters
    public static void generateLandingPage()
    {
        //define font
        Font f1 = new Font(Font.SERIF, Font.PLAIN, 20);

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

        viewer.addActionListener(e -> setPage("loginPage"));

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
