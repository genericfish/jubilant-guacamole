import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TheSQLViewer extends Page {
    private final String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    private final String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    private Vector<String> mPrevQueryResults = new Vector<String>();
    private TheSQLTable mTable = new TheSQLTable();
    private String mBegin = "1999-12-30";
    private String mEnd = "2005-12-31";

    public TheSQLViewer()
    {
        mName = "viewer";

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField begin = new JTextField(mBegin, 20);
        JTextField end = new JTextField(mEnd, 20);

        JButton historyButton = createButton("History", () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, begin.getText(), end.getText()));
        JButton newButton = createButton("New", () -> "SELECT * FROM titles ORDER BY year DESC, titleid DESC LIMIT 2500;");
        JButton likedButton = createButton(
            "Liked",
            () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND rating > 3 AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, begin.getText(), end.getText()));
        JButton dislikedButton = createButton(
            "Disliked",
            () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND rating < 3 AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, begin.getText(), end.getText()));
        mTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1 || e.getClickCount() < 2 || mPrevQueryResults == null)
                    return;

                int row = mTable.getSelectedRow();
                TheSQL.setPage(new TheSQLContent(mPrevQueryResults.get(row)));
            }
        });

        add(title, 20, 6, 0, 0);
        add(historyButton, 5, 3, 0, 1);
        add(newButton, 5, 3, 3, 1);
        add(likedButton, 5, 3, 0, 2);
        add(dislikedButton, 5, 3, 3, 2);
        add(begin, 5, 3, 0, 3);
        add(end, 5, 3, 3, 3);
        add(new JScrollPane(mTable), 0, 6, 0, 4);

        historyButton.doClick();
    }

    public JButton createButton(String text, ButtonQuery queryGenerator)
    {
        JButton button = new JButton(text);

        button.addActionListener(e -> populateTable(queryGenerator.getQuery()));

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
