import java.awt.*;
import java.sql.*;
import javax.swing.*;

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
            JLabel yearT = new JLabel(year);
            JLabel genreT = new JLabel(genre);
            JLabel runtimeT = new JLabel(runtime);

            title.setFont(new Font("Calibri", Font.BOLD, 60));

            JButton play = new JButton("Play");
            JButton back = new JButton("Back");

            back.addActionListener(e -> TheSQL.setPage("viewer"));

            add(title, 40, 5, 10, 0);
            add(yearT, 20, 20, 12, 20);
            add(genreT, 20, 20, 12, 40);
            add(runtimeT, 20, 20, 12, 60);
            add(play, 40, 20, 12, 150);
            add(back, 20, 20, 12, 210);
        } catch (Exception e) {
        }
    }
}
