import java.io.File;
import java.util.Scanner;

public class IndirectDirector {
    public static String getDirector(String actor)
    {
        String result = null;
        try {
            //constructor of file class having file as argument
            File file = new File("Indirect_Director_SQL.sql");
            Scanner sc = new Scanner(file); //file to be scanned
            String query = "";

            while (sc.hasNextLine()) { //returns true if and only if scanner has another token
                query += sc.nextLine() + ' ';
            }

            result = String.format(query, actor);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
