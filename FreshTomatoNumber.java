import java.util.*;
import java.sql.*;
public class FreshTomatoNumber 
{
    Set<String> fans = new HashSet<String>();
    Set<String> movies = new HashSet<String>();
    // Each element is a layer, which is a hashmap of content to their fans
    ArrayList<HashMap<String, ArrayList<String>>> layers = new ArrayList<HashMap<String, ArrayList<String>>>();


    public ArrayList<ArrayList<String>> getPath(String targetMovie)
    {
        ArrayList<ArrayList<String>> path = new ArrayList<>();
        // For each layer
        for(int i = layers.size() -1; i > 0; i--){
            System.out.println(String.format("Tracing layer %d", i));
            Boolean foundFan = false;
            HashMap<String, ArrayList<String>> currentLayer = layers.get(i);
            HashMap<String, ArrayList<String>> prevLayer = layers.get(i-1);
            ArrayList<String> currentFans = currentLayer.get(targetMovie);
            System.out.println(String.format("Current fans: %s", currentFans));

            String commonFan;
            String commonMovie;

            // Find the first movie in the previous layer with a fan in common
            for (String movie : prevLayer.keySet()) {
                for(String fan : currentFans){
                    //System.out.println(String.format("Previous layer fans: %s", (prevLayer.get(movie))));
                    if(prevLayer.get(movie).contains(fan)){
                        System.out.println("Fan found.");
                        commonMovie = movie;
                        commonFan = fan;
                        ArrayList<String> pathNode = new ArrayList<>(List.of(commonMovie, commonFan));
                        path.add(0, pathNode);
                        foundFan = true;
                        targetMovie = commonMovie;
                        break;
                    }
                    else{
                        //System.out.println(String.format("%s %s", movie, prevLayer.get(movie)));
                    }
                }
                if(foundFan){
                    break;
                }
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
        if(!movies.contains(contentID)){
            movies.add(contentID);
        }
        System.out.println(contentID);
        HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();

        ResultSet layerInfo = TheSQL.gDatabase.query(
            String.format(
                "WITH Layer AS ( SELECT DISTINCT titleid AS id FROM customerratings WHERE EXISTS ( SELECT * FROM ( SELECT ARRAY_AGG(customerid) AS whitelist FROM customerratings WHERE titleid='%s' AND rating > 3 )Z WHERE customerratings.customerid=ANY(Z.whitelist) ) AND rating > 3 ) SELECT customerratings.customerid, customerratings.titleid FROM customerratings INNER JOIN Layer ON customerratings.titleid=Layer.id WHERE rating > 3;",
                contentID)
        ); 
        try {
            while (layerInfo.next()) {
                // For each title and and customerid in the layer
                String titleid = layerInfo.getString("titleid");
                String customerid = layerInfo.getString("customerid");

                // Add the customerid and movie to the layer if they don't exist already
                if(results.get(titleid) == null){
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

    public ArrayList<ArrayList<String>> findFreshTomatoNumber(String contentA, String contentB){
        layers.clear();
        fans.clear();
        movies.clear();

        // Generate layer 0
        System.out.println("Generating layer 0");
        String currMovie = contentA;
        HashMap<String, ArrayList<String>> layer0 = new HashMap<String, ArrayList<String>>();
        layer0.put(currMovie, getFans(currMovie));
        fans.addAll(getFans(currMovie));
        layers.add(layer0);

        // Generate all subsequent layers
        int index = 0;
        while(layers.get(layers.size()-1).get(contentB) == null){
            System.out.println(String.format("Generating layer %d", index+1));
            layers.add(new HashMap<String, ArrayList<String>>());
            // For each movie in a layer
            for (String movieString : layers.get(index).keySet()) {
                //System.out.println(String.format("Searching for %s", movieString));
                if(!movies.contains(movieString)){
                    HashMap<String, ArrayList<String>> currentGeneration = generateLayer(movieString);
                    if(currentGeneration.get("tt0304678") != null){
                        System.out.println(String.format("Actual parent fans: %s", getFans(movieString)));
                        System.out.println(String.format("parent fans: %s", layers.get(index).get(movieString)));

                    }
                    // For each generated movie in the next layer
                    for(String key : currentGeneration.keySet()){
                        if(layers.get(index+1).containsKey(key)){
                            ArrayList<String> temp = currentGeneration.get(key);
                            temp.addAll(layers.get(index+1).get(key));
                            layers.get(index+1).put(key, temp);
                        }
                        else{
                            layers.get(index+1).put(key, currentGeneration.get(key));
                        }

                    }
                    if(currentGeneration.containsKey(contentB)){
                        //System.out.println("contentB found");
                        break;
                    }
                }
            }
            index++;
        }
        System.out.println(String.format("%d layers generated", layers.size()));

        for(int layer = 0; layer < layers.size(); layer++){
            System.out.println(String.format("Layer %d contains %d movies", layer, layers.get(layer).keySet().size()));
        }

        // Find path
        ArrayList<ArrayList<String>> path = getPath(contentB);
        return path;
        //return new ArrayList<ArrayList<String>>();
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
