import java.awt.*;
import javax.swing.*;

public class TheSQLLanding extends Page {
    public TheSQLLanding()
    {
        mName = "landing";

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);

        JLabel prompt = new JLabel("I am a? ");
        prompt.setFont(TheSQL.gHeaderFont);

        JButton viewerButton = new JButton("Viewer");
        JButton analystButton = new JButton("Analyst");

        viewerButton.addActionListener(e -> TheSQL.setPage("login"));
        analystButton.addActionListener(e -> TheSQL.setPage("analyst"));

        add(title, 20, 6, 0, 0);

        add(prompt, 20, 6, 0, 1);
        add(viewerButton, 0, 3, 0, 2);
        add(analystButton, 0, 3, 3, 2);
    }
}
