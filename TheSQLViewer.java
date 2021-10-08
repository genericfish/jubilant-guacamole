import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TheSQLViewer extends Page {
    private final String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    private final String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    private JTable mTable = new JTable();

    public TheSQLViewer()
    {
        mName = "viewer";

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);

        JButton historyButton = createButton("History", "SELECT * FROM titles OFFSET 20 LIMIT 20;");
        JButton newButton = createButton("New", "SELECT * FROM titles OFFSET 40 LIMIT 20;");
        JButton likedButton = createButton("Liked", "SELECT * FROM titles OFFSET 60 LIMIT 20;");
        JButton dislikedButton = createButton("Disliked", "SELECT * FROM titles OFFSET 80 LIMIT 20;");

        JTextField search = new JTextField("Search", 20);

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

        mTable.revalidate();
        mTable.repaint();
    }
}
