import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class TheSQLContent extends Page {
    class Component extends JComponent implements SwingConstants {
        public void setHorizontalAlignment(int alignment) {
        }
    }

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

            JLabel groupName = new JLabel(TheSQL.gGroupName);
            groupName.setFont(TheSQL.gHeaderFont);

            JLabel genreT = new JLabel("Genre: " + genre);
            if (genre.split("/").length > 1)
                genreT.setText("Genres: " + genre.replaceAll("/", ", "));

            if (runtime != null) {
                int hours = Integer.parseInt(runtime) / 60;
                int minutes = Integer.parseInt(runtime) % 60;

                JLabel runtimeT = new JLabel();
                runtimeT.setText(String.format("Runtime: %d:%d", hours, minutes));
                runtimeT.setHorizontalAlignment(SwingConstants.CENTER);

                add(runtimeT, 20, 9, 0, 4);
            }

            JLabel yearT = new JLabel("Released: " + year);
            JButton play = new JButton("Play");

            JButton back = new JButton("Back");
            back.addActionListener(e -> TheSQL.setPage("viewer"));

            groupName.setHorizontalAlignment(SwingConstants.CENTER);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            yearT.setHorizontalAlignment(SwingConstants.CENTER);
            genreT.setHorizontalAlignment(SwingConstants.CENTER);

            play.setHorizontalAlignment(SwingConstants.CENTER);
            back.setHorizontalAlignment(SwingConstants.CENTER);

            add(groupName, 20, 9, 0, 0);
            add(title, 20, 9, 0, 1);
            add(yearT, 20, 9, 0, 2);
            add(genreT, 20, 9, 0, 3);
            add(play, 20, 9, 0, 5);
            add(back, 20, 9, 0, 6);
        } catch (Exception e) {
        }
    }

    public void add(Component component, int ipady, int gridWidth, int gridx, int gridy)
    {
        component.setHorizontalAlignment(SwingConstants.CENTER);

        super.add(component, ipady, gridWidth, gridx, gridy);
    }
}
