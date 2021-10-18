import java.sql.*;
import java.util.*;
public class FreshTomatoNumber {
    Set<String> fans = new HashSet<String>();
    Set<String> movies = new HashSet<String>();
    // Each element is a layer, which is a hashmap of content to their fans
    ArrayList<HashMap<String, ArrayList<String>>> layers = new ArrayList<HashMap<String, ArrayList<String>>>();

    public ArrayList<ArrayList<String>> getPath(String targetMovie)
    {
        ArrayList<ArrayList<String>> path = new ArrayList<>();
        // For each layer
        for (int i = layers.size() - 1; i > 0; i--) {
            Boolean foundFan = false;
            HashMap<String, ArrayList<String>> currentLayer = layers.get(i);
            HashMap<String, ArrayList<String>> prevLayer = layers.get(i - 1);
            ArrayList<String> currentFans = currentLayer.get(targetMovie);

            String commonFan;
            String commonMovie;

            // Find the first movie in the previous layer with a fan in common
            for (String movie : prevLayer.keySet()) {
                for (String fan : currentFans) {
                    if (prevLayer.get(movie).contains(fan)) {
                        commonMovie = movie;
                        commonFan = fan;
                        ArrayList<String> pathNode = new ArrayList<>(List.of(commonMovie, commonFan));
                        path.add(0, pathNode);
                        foundFan = true;
                        targetMovie = commonMovie;
                        break;
                    }
                }
                if (foundFan)
                    break;
            }
        }

        return path;
    }
    public boolean layerHasMovie(int layer, String movie)
    {
        return layers.get(layer).containsKey(movie);
    }

    public HashMap<String, ArrayList<String>> generateLayer(String contentID)
    {
        if (!movies.contains(contentID))
            movies.add(contentID);

        HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();

        ResultSet layerInfo = TheSQL.gDatabase.query(
            String.format(
                "WITH Layer AS ( SELECT DISTINCT titleid AS id FROM customerratings WHERE EXISTS ( SELECT * FROM ( SELECT ARRAY_AGG(customerid) AS whitelist FROM customerratings WHERE titleid='%s' AND rating > 3 )Z WHERE customerratings.customerid=ANY(Z.whitelist) ) AND rating > 3 ) SELECT customerratings.customerid, customerratings.titleid FROM customerratings INNER JOIN Layer ON customerratings.titleid=Layer.id WHERE rating > 3;",
                contentID));
        try {
            while (layerInfo.next()) {
                // For each title and and customerid in the layer
                String titleid = layerInfo.getString("titleid");
                String customerid = layerInfo.getString("customerid");

                // Add the customerid and movie to the layer if they don't exist already
                if (results.get(titleid) == null) {
                    results.put(titleid, new ArrayList<String>());
                }
                results.get(titleid).add(customerid);
                fans.add(customerid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public ArrayList<ArrayList<String>> findFreshTomatoNumber(String contentA, String contentB)
    {
        layers.clear();
        fans.clear();
        movies.clear();

        // Generate layer 0
        String currMovie = contentA;
        HashMap<String, ArrayList<String>> layer0 = new HashMap<String, ArrayList<String>>();
        layer0.put(currMovie, getFans(currMovie));
        fans.addAll(getFans(currMovie));
        layers.add(layer0);

        // Generate all subsequent layers
        int index = 0;
        while (layers.get(layers.size() - 1).get(contentB) == null) {
            layers.add(new HashMap<String, ArrayList<String>>());
            // For each movie in a layer
            for (String movieString : layers.get(index).keySet()) {
                if (!movies.contains(movieString)) {
                    HashMap<String, ArrayList<String>> currentGeneration = generateLayer(movieString);

                    // For each generated movie in the next layer
                    for (String key : currentGeneration.keySet()) {
                        if (layers.get(index + 1).containsKey(key)) {
                            ArrayList<String> temp = currentGeneration.get(key);
                            temp.addAll(layers.get(index + 1).get(key));
                            layers.get(index + 1).put(key, temp);
                        } else {
                            layers.get(index + 1).put(key, currentGeneration.get(key));
                        }
                    }
                    if (currentGeneration.containsKey(contentB))
                        break;
                }
            }
            index++;
        }

        // Find path
        return getPath(contentB);
    }

    public ArrayList<String> getFans(String contentID)
    {
        ArrayList<String> results = new ArrayList<String>();
        ResultSet contentFans = TheSQL.gDatabase.query(
            String.format(
                "SELECT DISTINCT customerid FROM customerratings WHERE titleid='%s' AND rating>=4;",
                contentID));

        try {
            while (contentFans.next()) {
                String customerid = contentFans.getString("customerid");
                results.add(customerid);
                fans.add(contentFans.getString("customerid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
