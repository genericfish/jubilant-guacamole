import java.io.File;   
import java.util.Scanner;   
import java.sql.*;

public class IndirectDirector  
{     
    public static ResultSet getDirector(String actor)  
    {  
        try  
        {  
            //constructor of file class having file as argument  
            File file=new File("Indirect_Director_SQL.sql");   
            Scanner sc = new Scanner(file);     //file to be scanned  
            String query = "";
            while (sc.hasNextLine()) {       //returns true if and only if scanner has another token  
                query += sc.nextLine();  
            }
            return TheSQL.gDatabase.query(String.format(query,actor)); 
        }  
        catch(Exception e)  
        {  
        e.printStackTrace();  
        }  
        return null;
    }  
}  