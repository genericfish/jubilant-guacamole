import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class TheSQLLogin extends Page {
    public TheSQLLogin()
    {
        mName = "login";

        mPanel.setLayout(new GridBagLayout());

        JLabel prompt = new JLabel("Please Enter Customer ID");
        prompt.setFont(TheSQL.gHeaderFont);

        JLabel title = new JLabel(TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel loginError = new JLabel("Username does not exist");
        loginError.setVisible(false);
        loginError.setForeground(Color.RED);
        loginError.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField customerID = new JTextField("1488844");
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> {
            TheSQL.gUsername = customerID.getText();

            ResultSet userExists = TheSQL.gDatabase.query(
                String.format(
                    "SELECT customerid FROM customerratings WHERE customerid='%s';",
                    TheSQL.gUsername));

            try {
                if (userExists.next()) {
                    TheSQL.setPage("viewer");

                    return;
                }

                loginError.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        add(title, 20, 20, 0, 0);
        add(prompt, 0, 20, 0, 1);
        add(customerID, 10, 20, 0, 2);
        add(loginButton, 10, 20, 0, 3);
        add(loginError, 10, 20, 0, 4);
    }
}
