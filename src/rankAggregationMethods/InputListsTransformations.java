package rankAggregationMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 22-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class InputListsTransformations {
    /**
     * Finds and returns all the elements that exist in all the maps of the input list. The maps don't necessarily contain the same elements,
     * or the same number of elements.
     * @param inputListOfMaps List containing the maps that correspond to the initial rankings
     * @return A list (of String) of the unique ids existing in all the maps of the input list
     */
    private List<String> getElementIds (List<Map<String, Double>> inputListOfMaps) {
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
        // Return the list with the ids of all the maps
        return ids;
    }

    /**
     * Creates a map of all the rankings of a specific element, based on its id
     * @param inputMapList List with the initial rankings for all elements (contained in maps)
     * @param element the specific element whose rankings we want to get
     * @return A list of the double values corresponding to the element rankings
     */
    private List<Double> getRankingsOfAnElement (List<Map<String, Double>> inputMapList, String element) {
        // List to store the rankings of the element
        List<Double> rankings = new ArrayList<>();

        // Iterate through the list maps
        for(Map<String, Double> currentMap : inputMapList) {
            // If the element exists in the current map (is one of the keys)
            if(currentMap.keySet().contains(element))
                // Add the respective value in the rankings list
                rankings.add(currentMap.get(element));
        }
        return rankings; // Return all the ranking values associated with this element
    }

    // TODO Keep the id as well?
    /**
     * Returns all the rankings for all the elements given as input
     * @param inputList List containing the maps corresponding to the results of a ranking system
     * @param idList List of the ids of the elements that we want to get the rankings of
     * @return
     */
    private List<List<Double>> getElementRankings (List<Map<String, Double>> inputList, List<String> idList) {
        // List to store the rankings corresponding to one specific SNP
        List<List<Double>> rankingsOfAllElements = new ArrayList<>();
        // Iterate through the SNP id list
        for (String currentId : idList) {
            // Get all the rankings of the current element and save this list in the rankingsOfAllElements list
            rankingsOfAllElements.add(getRankingsOfAnElement(inputList, currentId));
        }
        return rankingsOfAllElements;   // Return the list with the rankings
    }
}
