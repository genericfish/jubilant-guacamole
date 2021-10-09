import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TheSQLContent extends Page {
    public TheSQLContent(String titleID)
    {
        mName = "content";

        ResultSet movie = TheSQL.gDatabase.query("SELECT * FROM titles WHERE titleid='" + titleID + "'");


        try {
        movie.next();
        String movieTitle = movie.getString("originaltitle");
        String year = movie.getString("year");
        String genre = movie.getString("genres");
        String runtime = movie.getString("runtimeminutes");

        mPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(movieTitle);
        title.setFont(new Font("Calibri", Font.BOLD, 60));
        JLabel yearT = new JLabel(year);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        JLabel genreT = new JLabel(genre);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        JLabel runtimeT = new JLabel(runtime);
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        add(title, 40, 5, 10, 0);
        add(yearT, 20, 20, 12, 20);
        add(genreT, 20, 20, 12, 40);
        add(runtimeT, 20, 20, 12, 60);
        JButton play = new JButton("Play");
        add(play, 40, 20, 12, 150);
        }
        catch(Exception e) {
        }
    }
}

