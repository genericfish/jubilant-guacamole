import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TheSQLContent extends Page {
    private final String[] mColumnNames = { "Title", "Genres", "Release", "Runtime" };
    private final String[] mColumns = { "originaltitle", "genres", "year", "runtimeminutes" };
    private JTable mTable = new JTable();

    public TheSQLContent()
    {
        mName = "content";
        
        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);

        add(new JScrollPane(mTable), 0, 6, 0, 4);
        add(title, 20, 2, 3, 0);
    }
}

