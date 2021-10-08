import java.awt.*;
import javax.swing.*;

public class TheSQLLogin extends Page {
    public TheSQLLogin()
    {
        mName = "login";

        mPanel.setLayout(new GridBagLayout());

        JLabel prompt = new JLabel("Customer ID: ");
        prompt.setFont(TheSQL.gHeaderFont);

        JTextField customerID = new JTextField("1488844");
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            TheSQL.gUsername = customerID.getText();

            TheSQL.setPage("viewer");
        });

        mPanel.add(prompt);
        mPanel.add(customerID);
        mPanel.add(loginButton);
    }
}
