import java.sql.*;
import java.util.Vector;

public class TheSQLSQL {
    Connection mConnection = null;

    public TheSQLSQL()
    {
        try {
            Class.forName("org.postgresql.Driver");
            mConnection = DriverManager.getConnection(
                "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_913_4_db",
                "csce315_913_4_user", "tanzir");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public ResultSet query(String statement)
    {
        try {
            Statement stmt = mConnection.createStatement();
            ResultSet result = stmt.executeQuery(statement);

            return result;
        } catch (Exception e) {
        }

        return null;
    }

    public void close()
    {
        try {
            mConnection.close();
        } catch (Exception e) {
        }
    }

    public Vector<Vector<String>> getTable(ResultSet result, String[] columns)
    {
        Vector<Vector<String>> entries = new Vector<Vector<String>>();

        try {
            while (result.next()) {
                Vector<String> entry = new Vector<String>();

                for (String column : columns) {
                    entry.add(result.getString(column));
                }

                entries.add(entry);
            }
        } catch (Exception e) {
        }

        return entries;
    }
}
