import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TheSQLViewer extends ContentPage {
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
        JButton recButton = createButton("Recommendations", () -> TheSQL.getQuery("recommendations.sql", TheSQL.gUsername));
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
        add(recButton, 5, 3, 3, 1);
        add(likedButton, 5, 3, 0, 2);
        add(dislikedButton, 5, 3, 3, 2);
        add(begin, 5, 3, 0, 3);
        add(end, 5, 3, 3, 3);
        add(new JScrollPane(mTable), 0, 6, 0, 4);

        historyButton.doClick();
    }
}
