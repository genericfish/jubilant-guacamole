import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class HollywoodPairs {
    public static ResultSet getPairs(String actor)
    {
        ResultSet result = null;
        try {
            //constructor of file class having file as argument
            File file = new File("HollywoodPairs.sql");
            Scanner sc = new Scanner(file); //file to be scanned
            String query = "";

            while (sc.hasNextLine()) //returns true if and only if scanner has another token
                query += sc.nextLine();

            result = TheSQL.gDatabase.query(String.format(query, actor));
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
