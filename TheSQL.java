import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

class Page {
    public JPanel mPanel = new JPanel();
    public String mName = null;

    private GridBagConstraints mConstraints = new GridBagConstraints();

    public void add(JComponent component, int ipady, int gridWidth, int gridx, int gridy)
    {
        mConstraints.fill = GridBagConstraints.BOTH;
        mConstraints.ipady = ipady;
        mConstraints.gridwidth = gridWidth;
        mConstraints.gridx = gridx;
        mConstraints.gridy = gridy;

        mPanel.add(component, mConstraints);
    }
}

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
        if (gCurrentPage != null)
            gBody.remove(gCurrentPage.mPanel);

        gCurrentPage = gPages.get(pageName);
        gBody.add(gCurrentPage.mPanel);

        gBody.revalidate();
        gBody.repaint();
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
}
