import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Page {
    public JPanel mPanel = new JPanel();
    public String mName = null;

    private GridBagConstraints mConstraints = new GridBagConstraints();

    public void add(JComponent component, int ipady, int gridWidth, int gridx, int gridy)
    {
        mConstraints.fill = GridBagConstraints.BOTH;
        mConstraints.ipady = ipady;
        mConstraints.gridwidth = gridWidth;
        mConstraints.gridx = gridx;
        mConstraints.gridy = gridy;

        mPanel.add(component, mConstraints);
    }
}

class ContentPage extends Page {
    public String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    public String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    public Vector<String> mPrevQueryResults = new Vector<String>();
    public TheSQLTable mTable = new TheSQLTable();
    public String mBegin = "1999-01-01";
    public String mEnd = "2020-12-31";

    interface ButtonQuery {
        String getQuery();
    }

    public JButton createButton(String text, ButtonQuery queryGenerator)
    {
        return createButton(text, queryGenerator, true);
    }

    public JButton createButton(String text, ButtonQuery queryGenerator, boolean cache)
    {
        JButton button = new JButton(text);

        button.addActionListener(e -> populateTable(queryGenerator.getQuery(), cache));

        return button;
    }

    public void populateTable(String query, boolean cache)
    {
        DefaultTableModel model = (DefaultTableModel)mTable.getModel();

        // Clear table
        model.setRowCount(0);

        if (query == null) {
            mTable.revalidate();
            mTable.repaint();

            return;
        }

        ResultSet results = TheSQL.gDatabase.query(query);

        // Populate table with results from query
        model.setDataVector(TheSQL.gDatabase.getTable(results, mColumns), new Vector<String>(Arrays.asList(mColumnNames)));

        try {
            results = TheSQL.gDatabase.query(query);
            mPrevQueryResults.removeAllElements();

            if (cache) {
                while (results.next())
                    mPrevQueryResults.add(results.getString("titleid"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        mTable.revalidate();
        mTable.repaint();
    }
}
