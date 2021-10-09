import java.awt.*;
import javax.swing.*;

// TODO: Finish this

public class TheSQLAnalyst extends Page {
    private final String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    private final String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    private JTable mAnalystTable = new JTable();
    private JTable mPopularTable = new JTable();

    public TheSQLAnalyst()
    {
        mName = "analyst";

        //formatting the page using grid bag layout
        GridBagConstraints gbcnt = new GridBagConstraints();
        mPanel.setLayout(new GridBagLayout());

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");
        title.setFont(TheSQL.gHeaderFont);

        JButton content = new JButton("Content");
        JButton director = new JButton("Directors");
        JButton actor = new JButton("Actors");
        JTextField search = new JTextField("Search", 20);

        JLabel tabletitle = new JLabel("Popular Right Now");
        tabletitle.setFont(TheSQL.gHeaderFont);

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
        mPanel.add(title, gbcnt);

        //second row: graph (to be implemented)
        // gbcnt.fill = GridBagConstraints.HORIZONTAL;
        // gbcnt.gridwidth = 6;
        // gbcnt.gridx = 0;
        // gbcnt.gridy = 1;
        // mPanel.add( , gbcnt);

        //third row: Content, Director, and Actors buttons
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 5;
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 0;
        gbcnt.gridy = 2;
        mPanel.add(content, gbcnt);
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 2;
        gbcnt.gridy = 2;
        mPanel.add(director, gbcnt);
        gbcnt.gridwidth = 2;
        gbcnt.gridx = 4;
        gbcnt.gridy = 2;
        mPanel.add(actor, gbcnt);

        //fourth row: search bar
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 1;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 3;
        mPanel.add(search, gbcnt);

        //fifth row: table 1
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 4;
        mPanel.add(new JScrollPane(table1), gbcnt);

        //sixth row: table title
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.ipady = 10;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 3;
        gbcnt.gridy = 5;
        mPanel.add(tabletitle, gbcnt);

        //seventh row: table2
        gbcnt.fill = GridBagConstraints.HORIZONTAL;
        gbcnt.gridwidth = 6;
        gbcnt.gridx = 0;
        gbcnt.gridy = 6;
        mPanel.add(new JScrollPane(table2), gbcnt);
    }

    public JButton createButton(String text, String query)
    {
        JButton button = new JButton(text);

        button.addActionListener(e -> populateTable(query));

        return button;
    }

    public void populateTable(String query)
    {
        // DefaultTableModel model = (DefaultTableModel)mTable.getModel();

        // // Clear table
        // model.setRowCount(0);

        // model.setColumnIdentifiers(mColumns);

        // ResultSet results = TheSQL.gDatabase.query(query);

        // // Populate table with results from query
        // for (Vector<String> row : TheSQL.gDatabase.getTable(results, mColumns))
        //     model.addRow(row);

        // mTable.revalidate();
        // mTable.repaint();
    }
}
