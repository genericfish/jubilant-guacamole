import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class TheSQLLogin extends Page {
    public TheSQLLogin()
    {
        mName = "login";

        mPanel.setLayout(new GridBagLayout());

        JLabel prompt = new JLabel("Please Enter Customer ID");
        prompt.setFont(TheSQL.gHeaderFont);

        JLabel title = new JLabel("  " + TheSQL.gGroupName);
        title.setFont(TheSQL.gHeaderFont);
        JTextField customerID = new JTextField("1488844");
        JButton loginButton = new JButton("Login");
        JLabel loginError = new JLabel("Username does not exist");
        loginError.setVisible(false);
        loginError.setForeground(Color.RED);

        loginButton.addActionListener(e -> {
            TheSQL.gUsername = customerID.getText();
            ResultSet userExists = TheSQL.gDatabase.query(
                String.format("SELECT customerid FROM customerratings WHERE customerid='%s';",TheSQL.gUsername)
            );

            try{
                if(userExists.next()){
                    TheSQL.setPage("viewer");
                }
                else{
                    System.out.println("Name doesn't work.");
                    loginError.setVisible(true);
                }
            }
            catch(Exception d){d.printStackTrace();}

        });

        add(title, 20, 2, 3, 0);
        add(prompt, 0, 5, 1, 1);
        add(customerID, 1, 10, 2, 2);
        add(loginButton, 2, 15, 3, 3);
        add(loginError, 3, 20, 4, 4);
    }
}
