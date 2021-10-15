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
        JTextField begin = new JTextField(mBegin, 20);
        JTextField end = new JTextField(mEnd, 20);
        JTabbedPane pane = new JTabbedPane();
        JComponent tab1 = makeTextPanel("Popular");
        pane.addTab("Popular", null, tab1, "");
        JComponent tab2 = makeTextPanel("Indirect Director");
        pane.addTab("Indirect Director", null, tab2, "");
        JComponent tab3 = makeTextPanel("Fresh Tomato Number");
        pane.addTab("Fresh Tomato Number", null, tab3, "");
        JComponent tab4 = makeTextPanel("Hollywood Pairs");
        pane.addTab("Hollywood Pairs", null, tab4, "");

        
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton refresh = createButton("Update Table", () -> String.format(
            "SELECT * FROM (SELECT titleid, COUNT(*) FROM customerratings WHERE date BETWEEN '%s' AND '%s' GROUP BY titleid)T INNER JOIN titles ON T.titleid=titles.titleid ORDER BY count DESC, year DESC, T.titleid DESC;",
            begin.getText(), end.getText()
        ));

        JLabel tableTitle = new JLabel("Popular Titles");
        tableTitle.setFont(TheSQL.gHeaderFont);
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //adding items to the page
        //first row: title
        add(title, 20, 6, 0, 0);

        // graph (to be implemented)
        // add(graph, 0, 6, 00, 1)

        add(tableTitle, 10, 6, 0, 3);
        add(begin, 5, 3, 0, 4);
        add(end, 5, 3, 3, 4);
        add(refresh, 5, 6, 0, 5);

        add(new JScrollPane(mTable), 0, 6, 0, 6);
        add(pane,0,0,0,7);
    }

    JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
