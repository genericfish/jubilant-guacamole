import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;

public class TheSQLAnalyst extends ContentPage {
    JLabel mError = new JLabel("");

    public TheSQLAnalyst()
    {
        mName = "analyst";
        mColumnNames = new String[] { "Title", "Reviews", "Genres", "Release", "Runtime" };
        mColumns = new String[] { "originaltitle", "count", "genres", "year", "runtimeminutes" };

        mPanel.setLayout(new GridBagLayout());

        // Page components
        JLabel title = new JLabel(TheSQL.gGroupName);
        JTabbedPane pane = new JTabbedPane();

        JComponent tab1 = makePopularPanel();
        JComponent tab2 = makeDirectorPanel();
        JComponent tab3 = makeTomatoPanel();
        JComponent tab4 = makePairsPanel();

        pane.addTab("Popular", null, tab1, "");
        pane.addTab("Indirect Director", null, tab2, "");
        pane.addTab("Fresh Tomato Number", null, tab3, "");
        pane.addTab("Hollywood Pairs", null, tab4, "");

        pane.addChangeListener(e -> {
            mError.setText("");
        });

        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mError.setHorizontalAlignment(SwingConstants.CENTER);
        mError.setForeground(Color.RED);

        //adding items to the page
        add(title, 20, 6, 0, 0);
        add(pane, 0, 0, 0, 1);
        add(mError, 0, 0, 0, 2);
        add(new JScrollPane(mTable), 0, 6, 0, 3);
    }

    void addError(String errorMessage)
    {
        mError.setText(errorMessage);
    }

    JComponent makePopularPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipady = 0;
        constraints.gridwidth = 12;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel title = new JLabel("Popular Titles");
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(title, constraints);

        JTextField begin = new JTextField(mBegin, 20);
        JTextField end = new JTextField(mEnd, 20);

        constraints.gridy = 1;
        constraints.gridwidth = 6;

        panel.add(begin, constraints);

        constraints.gridx = 6;

        panel.add(end, constraints);

        JButton refresh = createButton("Find Popular", () -> {
            ArrayList<String> startDate = new ArrayList<>(Arrays.asList(begin.getText().split("-")));
            ArrayList<String> endDate = new ArrayList<>(Arrays.asList(end.getText().split("-")));

            if (startDate.size() != 3 || begin.getText().matches(".*[a-z].*") || !TheSQL.validateDate(begin.getText())) {
                addError("Invalid start date.");
                return null;
            }

            if (endDate.size() != 3 || end.getText().matches(".*[a-z].*") || !TheSQL.validateDate(end.getText())) {
                addError("Invalid end date.");
                return null;
            }

            mError.setText("");

            mColumnNames = new String[] { "Title", "Reviews", "Genres", "Release", "Runtime" };
            mColumns = new String[] { "originaltitle", "count", "genres", "year", "runtimeminutes" };

            return String.format("SELECT * FROM (SELECT titleid, COUNT(*) FROM customerratings WHERE date BETWEEN '%s' AND '%s' GROUP BY titleid)T INNER JOIN titles ON T.titleid=titles.titleid ORDER BY count DESC, year DESC, T.titleid DESC;", begin.getText(), end.getText());
        }, false);

        constraints.gridx = 0;
        constraints.gridwidth = 12;
        constraints.gridy = 2;
        panel.add(refresh, constraints);

        return panel;
    }

    JComponent makeDirectorPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipady = 0;
        constraints.gridwidth = 12;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel title = new JLabel("Popular Titles");
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(title, constraints);

        JTextField actor = new JTextField("nm0000001", 20);

        constraints.gridy = 1;

        panel.add(actor, constraints);

        JButton button = createButton("Find Indirect Director", () -> {
            mColumnNames = new String[] { "Director", "Co-stars worked with" };
            mColumns = new String[] { "primaryname", "appearances" };

            ResultSet actorExists = TheSQL.gDatabase.query(
                String.format(
                    "SELECT * FROM names WHERE nconst='%s';",
                    actor.getText()));

            try {
                if (actorExists.next())
                    return IndirectDirector.getDirector(actor.getText());

            } catch (Exception e) {}

            addError("Invalid actor ID.");
            return null;
        }, false);
        constraints.gridy = 2;
        panel.add(button, constraints);

        return panel;
    }

    JComponent makeTomatoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints mConstraints = new GridBagConstraints();
        mConstraints.fill = GridBagConstraints.BOTH;
        mConstraints.ipady = 2;
        mConstraints.gridwidth = 2;
        mConstraints.gridx = 0;
        mConstraints.gridy = 0;
        JTextField input1 = new JTextField("Content ID A", 20);
        JTextField input2 = new JTextField("Content ID B", 20);
        JLabel output = new JLabel("Please Enter Values");
        JButton compute = new JButton("Find FTN");
        compute.addActionListener(event -> {
            if(input1.getText() == input2.getText()){
                output.setText("Error: Identical IDs Entered");
                output.setForeground(Color.RED);
            }
            else{
                String input1Trimmed = input1.getText().trim();
                String input2Trimmed = input2.getText().trim();
                ResultSet contentA = TheSQL.gDatabase.query(
                    String.format(
                        "SELECT titleid FROM customerratings WHERE titleid='%s';",
                        input1Trimmed));
                
                ResultSet contentB = TheSQL.gDatabase.query(
                    String.format(
                        "SELECT titleid FROM customerratings WHERE titleid='%s';",
                        input2Trimmed));

                try{
                    if (contentA.next() && contentB.next()) {
                        output.setForeground(Color.BLACK);
                        FreshTomatoNumber ftn = new FreshTomatoNumber();
                        ArrayList<ArrayList<String>> result = ftn.findFreshTomatoNumber(input1Trimmed, input2Trimmed);
                        output.setText(result.toString());
                    }
                    else{
                        output.setText("Error: Unrated/Invalid Content ID(s)");
                        output.setForeground(Color.RED);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        });
        panel.add(input1, mConstraints);
        mConstraints.gridx = 2;
        panel.add(input2, mConstraints);
        mConstraints.gridx = 4;
        mConstraints.gridwidth = 1;
        panel.add(compute, mConstraints);
        mConstraints.gridy = 1;
        mConstraints.gridx = 0;
        panel.add(output, mConstraints);

        return panel;
    }

    JComponent makePairsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipady = 0;
        constraints.gridwidth = 12;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel title = new JLabel("Hollywood Pairs");
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        panel.add(title, constraints);

        JButton button = createButton("Find Pairs", () -> {
            mColumnNames = new String[] { "Actor 1", "Actor 2", "Appearances", "Pair Rating" };
            mColumns = new String[] { "person1", "person2", "appearances", "rating" };

            return HollywoodPairs.getPairs();
        }, false);
        constraints.gridy = 1;
        panel.add(button, constraints);

        return panel;
    }
}
