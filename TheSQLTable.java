import javax.swing.*;

// Because JTable just isn't good enough for us
public class TheSQLTable extends JTable {
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
}
