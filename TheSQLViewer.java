import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TheSQLViewer extends ContentPage {
    JTextField mBeginField = new JTextField(mBegin, 20);
    JTextField mEndField = new JTextField(mEnd, 20);
    JLabel mError = new JLabel("");

    public JButton createButtonWithErrorValidation(String text, ButtonQuery generator)
    {
        return createButton(text, () -> {
            ArrayList<String> startDate = new ArrayList<>(Arrays.asList(mBeginField.getText().split("-")));
            ArrayList<String> endDate = new ArrayList<>(Arrays.asList(mEndField.getText().split("-")));

            if (startDate.size() != 3 || mBeginField.getText().matches(".*[a-z].*") || !TheSQL.validateDate(mBeginField.getText())) {
                addError("Invalid start date.");
                return null;
            }

            if (endDate.size() != 3 || mEndField.getText().matches(".*[a-z].*") || !TheSQL.validateDate(mEndField.getText())) {
                addError("Invalid end date.");
                return null;
            }

            mError.setText("");

            return generator.getQuery();
        }, false);
    }
    public TheSQLViewer()
    {
        mName = "viewer";

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton historyButton = createButtonWithErrorValidation("History", () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, mBeginField.getText(), mEndField.getText()));
        JButton likedButton
            = createButtonWithErrorValidation(
                "Liked",
                () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND rating > 3 AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, mBeginField.getText(), mEndField.getText()));
        JButton dislikedButton = createButtonWithErrorValidation(
            "Disliked",
            () -> String.format("SELECT * FROM customerratings INNER JOIN titles ON customerratings.titleid=titles.titleid WHERE customerid='%s' AND rating < 3 AND date BETWEEN '%s' AND '%s' ORDER BY date DESC, key DESC;", TheSQL.gUsername, mBeginField.getText(), mEndField.getText()));
        JButton recButton = createButton("Recommendations", () -> TheSQL.getQuery("recommendations.sql", TheSQL.gUsername));
        mTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1 || e.getClickCount() < 2 || mPrevQueryResults == null)
                    return;

                int row = mTable.getSelectedRow();
                TheSQL.setPage(new TheSQLContent(mPrevQueryResults.get(row)));
            }
        });

        mError.setHorizontalAlignment(SwingConstants.CENTER);
        mError.setForeground(Color.RED);

        add(title, 20, 6, 0, 0);
        add(historyButton, 5, 3, 0, 1);
        add(recButton, 5, 3, 3, 1);
        add(likedButton, 5, 3, 0, 2);
        add(dislikedButton, 5, 3, 3, 2);
        add(mBeginField, 5, 3, 0, 3);
        add(mEndField, 5, 3, 3, 3);
        add(mError, 5, 6, 0, 4);
        add(new JScrollPane(mTable), 0, 6, 0, 5);
    }

    void addError(String errorMessage)
    {
        mError.setText(errorMessage);
    }
}
