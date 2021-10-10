import java.awt.*;
import javax.swing.*;

public class TheSQLAnalyst extends ContentPage {
    public TheSQLAnalyst()
    {
        mName = "analyst";
        mColumnNames = new String[] { "Title", "Reviews", "Genres", "Release", "Runtime" };
        mColumns = new String[] { "originaltitle", "count", "genres", "year", "runtimeminutes" };

        mPanel.setLayout(new GridBagLayout());

        // Page components
        JLabel title = new JLabel("Project 4: The SQL");
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton refresh = createButton("refresh", () -> "SELECT * FROM (SELECT titleid, COUNT(*) FROM customerratings WHERE date BETWEEN '2003-01-01' AND '2006-01-01' GROUP BY titleid)T INNER JOIN titles ON T.titleid=titles.titleid ORDER BY count DESC, year DESC, T.titleid DESC;");
        JButton director = new JButton("Directors");
        JButton actor = new JButton("Actors");

        JLabel tableTitle = new JLabel("Popular Titles");
        tableTitle.setFont(TheSQL.gHeaderFont);
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //adding items to the page
        //first row: title
        add(title, 20, 6, 0, 0);

        //second row: graph (to be implemented)
        // gbcnt.fill = GridBagConstraints.HORIZONTAL;
        // gbcnt.gridwidth = 6;
        // gbcnt.gridx = 0;
        // gbcnt.gridy = 1;
        // mPanel.add( , gbcnt);

        add(refresh, 5, 6, 0, 2);

        //sixth row: table title
        add(tableTitle, 10, 6, 0, 5);

        add(new JScrollPane(mTable), 0, 6, 0, 6);
    }
}
