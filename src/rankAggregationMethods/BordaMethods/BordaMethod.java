package rankAggregationMethods.BordaMethods;

import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 19-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public abstract class BordaMethod {
    /**
     * Creates a new ranking from the aggregation of the ranks of each element in the ranking system
     * Starts with a list of ranking systems, turns it into a map containing the id of every element
     * and all its rank values, then aggregates these values and returns a map with the id of the element
     * and its new rank value in the aggregated rank
     * @param listOfRankings list that contains maps with id (String) as key and the ranking (Double) as value
     *                       Every one of the maps is one ranking
     * @return Map containing the id as key and the aggregated result (new rank) as value
     */
    public Map<String, Double> doTheAggregation (List<Map<String, Double>> listOfRankings) {
        // Map to store the output ranking
        Map<String, Double> outputRanking = new LinkedHashMap<>();

        // From list of rankings to map of elements and all their ranking values
        Map<String, List<Double>> elementsAndRankingsMap = transformRankingsForAggregation(listOfRankings);

        // Iterating through map of elements and their rankings
        for(Map.Entry<String, List<Double>> currentElement : elementsAndRankingsMap.entrySet()) {
            // Add the current element id and the result of the aggregation of its rank values to the output map
            outputRanking.put(currentElement.getKey(), computeAggregation(currentElement.getValue()));
        }
        return outputRanking;
    }

    /**
     * Turns the list of ranking systems into a map that contains the ID of every element of the initial rankings and a list
     * with all its values from every ranking system
     * @param listOfRankings initial list containing maps that correspond to rankings
     * @return transformed data structure with ID as key and list of all rank values of this element as value
     */
    private Map<String, List<Double>> transformRankingsForAggregation(List<Map<String, Double>> listOfRankings) {
        List<String> ids = RankAggregationDataTransformation.getElementIds(listOfRankings);

        return RankAggregationDataTransformation.getRankingsOfAllElements(listOfRankings, ids);
    }

    /**
     * Computes the aggregation using a specific method
     * @param numbersToBeAggregated list containing the values to be aggregated
     * @return the result of the aggregation
     */
    protected abstract Double computeAggregation(List<Double> numbersToBeAggregated);
}
