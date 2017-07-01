package rankAggregationMethods;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by Katerina Intzevidou on 22-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class RankAggregationDataTransformation {

    // Methods

    /**
     * Finds and returns all the elements that exist in all the maps of the input list. The maps don't necessarily contain the same elements,
     * or the same number of elements.
     * @return A list (of String) of the unique ids existing in all the maps of the input list
     */
    public static List<String> getElementIds (List<Map<String, Double>> inputListOfMaps) {
        // List to store the ids
        List<String> ids = new ArrayList<>();
        // Iterate through the list maps
        for(Map<String, Double> currentMap : inputListOfMaps) {
            // Iterate through the current map
            for(Map.Entry<String, Double> entry : currentMap.entrySet()) {
                // If the key of the entry (id : type String) does not already exist in the ids array list, add it
                if(!ids.contains(entry.getKey()))
                    ids.add(entry.getKey());        // Adding key in the list
            }
        }
        // Return the list with all the ids contained in the maps (unique ids, only appearing once in the final list)
        return ids;
    }

    /**
     * Creates a map of all the rankings of a specific element, based on its id
     * @param element the specific element whose rankings we want to get
     * @return A list of the double values corresponding to the element rankings
     */
    public static List<Double> getRankingsOfAnElement (List<Map<String, Double>> inputListOfMaps, String element) {
        // List to store the rankings of the element
        List<Double> rankings = new ArrayList<>();

        // Iterate through the list maps
        for(Map<String, Double> currentMap : inputListOfMaps) {
            // If the element exists in the current map (is one of the keys)
            if(currentMap.keySet().contains(element))
                // Add the respective value in the rankings list
                rankings.add(currentMap.get(element));
        }
        return rankings; // Return all the ranking values associated with this element
    }

    /**
     * Returns all the rankings for all the elements given as input
     * @param idList List of the ids of the elements that we want to get the rankings of
     * @return Map containing all the items by id and their respective rankings
     */
    public static Map<String, List<Double>> getRankingsOfAllElements(List<Map<String, Double>> inputListOfMaps, List<String> idList) {
        // Map to store the rankings corresponding to one specific element
        Map<String, List<Double>> rankingsOfAllElements = new HashMap<>();
        // Iterate through the element id list
        for (String currentId : idList) {
            // Get all the rankings of the current element and save this list in the rankingsOfAllElements map, along with the id as key
            rankingsOfAllElements.put(currentId, getRankingsOfAnElement(inputListOfMaps, currentId));
        }
        return rankingsOfAllElements;   // Return the map with the rankings
    }

    /**
     * Creates and returns the sorted ranking result
     * @param unsortedRankingMap Map containing entries of an element ID(Key - String) and its respective score (Value - Double) in a ranking
     * @return A Map with the values of the input Map sorted
     */
    public static Map<String, Double> createSortedOutput (Map<String, Double> unsortedRankingMap) {
        // Turn Map into list of pairs to do the sorting
        List<Pair<String, Double>> listOfElementsAndRanks = new ArrayList<>();

        for(Map.Entry<String, Double> currentEntry : unsortedRankingMap.entrySet()) {
            listOfElementsAndRanks.add(new Pair<>(currentEntry.getKey(), currentEntry.getValue()));     // Add the map values into pairs list
        }

        // Sorting the list of pairs based on the value (rank, score)
        Collections.sort(listOfElementsAndRanks, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(final Pair<String, Double> o1, final Pair<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });

        // Map to save the final, sorted ranking
        Map <String, Double> sortedRankingMap = new LinkedHashMap<>();      // linked hash maps keep the insertion order

        // Iterating through the list of pairs and adding the values in the map in the proper ascending order.
        for (Pair<String, Double> currentPair : listOfElementsAndRanks) {
            sortedRankingMap.put(currentPair.getKey(), currentPair.getValue());
        }

        return sortedRankingMap;
    }
}
