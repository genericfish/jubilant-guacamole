import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TheSQLViewer extends Page {
    private final String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    private final String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    private Vector<String> mPrevQueryResults = new Vector<String>();
    private TheSQLTable mTable = new TheSQLTable();

    public TheSQLViewer()
    {
        mName = "viewer";

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);

        JButton historyButton = createButton("History", "SELECT * FROM titles OFFSET 20 LIMIT 20;");
        JButton newButton = createButton("New", "SELECT * FROM titles ORDER BY year DESC LIMIT 20;");
        JButton likedButton = createButton("Liked", "SELECT * FROM titles OFFSET 60 LIMIT 20;");
        JButton dislikedButton = createButton("Disliked", "SELECT * FROM titles OFFSET 80 LIMIT 20;");

        JTextField search = new JTextField("Search", 20);

        mTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1 || e.getClickCount() < 2 || mPrevQueryResults == null)
                    return;
                int row = mTable.getSelectedRow();
<<<<<<< Updated upstream

                TheSQL.gCurrentTitleID = mPrevQueryResults.get(row);
                TheSQL.setPage("content");
=======
                TheSQL.setPage(new TheSQLContent(mPrevQueryResults.get(row)));
>>>>>>> Stashed changes
            }
        });

        add(new JScrollPane(mTable), 0, 6, 0, 4);
        add(title, 20, 2, 3, 0);
        add(historyButton, 5, 3, 0, 1);
        add(newButton, 0, 3, 3, 1);
        add(likedButton, 0, 3, 0, 2);
        add(dislikedButton, 0, 3, 3, 2);
        add(search, 1, 6, 0, 3);
    }

    public JButton createButton(String text, String query)
    {
        JButton button = new JButton(text);

        button.addActionListener(e -> populateTable(query));

        return button;
    }

    public void populateTable(String query)
    {
        DefaultTableModel model = (DefaultTableModel)mTable.getModel();

        // Clear table
        model.setRowCount(0);

        ResultSet results = TheSQL.gDatabase.query(query);

        // Populate table with results from query
        model.setDataVector(TheSQL.gDatabase.getTable(results, mColumns), new Vector<String>(Arrays.asList(mColumnNames)));

        try {
            results = TheSQL.gDatabase.query(query);
            mPrevQueryResults.removeAllElements();

            while (results.next())
                mPrevQueryResults.add(results.getString("titleid"));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        mTable.revalidate();
        mTable.repaint();
    }
}
