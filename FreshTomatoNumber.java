import java.util.*;
import java.sql.*;
public class FreshTomatoNumber 
{
    Set<String> fans = new HashSet<String>();
    Set<String> movies = new HashSet<String>();
    // Each element is a layer, which is a hashmap of content to their fans
    ArrayList<HashMap<String, ArrayList<String>>> layers = new ArrayList<HashMap<String, ArrayList<String>>>();


    public void generateLayer(String contentID)
    {
        HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();

        ResultSet layerInfo = TheSQL.gDatabase.query(
            String.format(
                "SELECT users.customerid,titleid FROM ( SELECT customerid FROM customerratings WHERE titleid='%s' AND rating > 3 )users INNER JOIN customerratings ON users.customerid=customerratings.customerid WHERE rating > 3",
                contentID)
        ); 
        try {
            while (layerInfo.next()) {
                String titleid = layerInfo.getString("titleid");
                String customerid = layerInfo.getString("customerid");

                if(results.get(titleid) == null){
                    results.put(titleid, new ArrayList<String>());
                    movies.add(titleid);
                }
                results.get(titleid).add(customerid);
                fans.add(customerid);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        layers.add(results);
    }

    public Set<String> getFans(String contentID, int layer)
    {   
        Set<String> results = new HashSet<String>();
        ResultSet contentFans = TheSQL.gDatabase.query(
                String.format(
                    "SELECT customerid FROM customerratings WHERE titleid='%s' AND rating>=4;",
                    contentID));

        try {
            while (contentFans.next()) {
                String customerid = contentFans.getString("customerid");
                results.add(customerid);
                fans.add(contentFans.getString(customerid));
                getLikedContent(customerid);
            }
        } catch (Exception e) {
        }

        return results;
    }

    public Set<String> getLikedContent(String user){
        Set<String> results = new HashSet<String>();
        ResultSet contentFans = TheSQL.gDatabase.query(
                String.format(
                    "SELECT titleid FROM customerratings WHERE customerid='%s' AND rating>=4;",
                    user));

        try {
            while (contentFans.next()) {
                results.add(contentFans.getString("customerid"));
                movies.add(contentFans.getString("customerid"));
            }
        } catch (Exception e) {
        }
        return results;
    }
}
