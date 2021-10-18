import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.Scanner;
import java.text.*;

public class TheSQL {
    static JPanel gBody = new JPanel();
    static final TheSQLSQL gDatabase = new TheSQLSQL();
    static final String gGroupName = "913 Group 4: The SQL";
    static final Font gHeaderFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    static HashMap<String, Page> gPages = new HashMap<String, Page>();
    static String gUsername = "1488844";
    static Page gCurrentPage = null;

    public static void main(String[] args)
    {
        // Initialize window
        JFrame frame = new JFrame(gGroupName);
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addPage(new TheSQLViewer());
        addPage(new TheSQLAnalyst());
        addPage(new TheSQLLanding());
        addPage(new TheSQLLogin());

        gBody.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(gBody);
        frame.setVisible(true);

        setPage("landing");
    }

    public static void addPage(Page page)
    {
        gPages.put(page.mName, page);
    }

    // Removes the old page from the body and adds the new one
    public static void setPage(String pageName)
    {
        setPage(gPages.get(pageName));
    }

    public static void setPage(Page page)
    {
        if (gCurrentPage != null)
            gBody.remove(gCurrentPage.mPanel);

        gCurrentPage = page;
        gBody.add(gCurrentPage.mPanel);

        gBody.revalidate();
        gBody.repaint();
    }

    public static String getQuery(String filename, String format) {
        String result = null;

        try {
            //constructor of file class having file as argument
            File file = new File(filename);
            Scanner sc = new Scanner(file); //file to be scanned
            String query = "";

            while (sc.hasNextLine()) //returns true if and only if scanner has another token
                query += sc.nextLine() + " ";

            result = (query.replace("%s", format));
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean validateDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);

        try {
            df.parse(date);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
