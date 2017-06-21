package rankAggregationMethods.MarkovChainMethods;

import Jama.Matrix;
import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class MC3 extends MarkovChain {

    /**
     * Creates the transition probability matrix of the MC3 aggregation method [Lin, 2010]
     * @param listOfRankings list that contains maps with id (String) as key and the ranking (Double) as value.
     *                        Every one of the maps is one ranking
     * @return Matrix containing Double values between 0.0 and 1.0 - corresponding to probabilities
     */
    public Matrix createTransitionProbabilityMatrix(List<Map<String, Double>> listOfRankings) {
        // Get the element id of all elements in the input maps/rankings
        List<String> elementIds = RankAggregationDataTransformation.getElementIds(listOfRankings);
        // Save the size
        int tableSize = elementIds.size();

        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        // Define helper variables
        double probabilitiesSum = 0.0;
        int majorityCounter = 0;    // counter to check the number of wins of element against the other at the ranking systems
        int commonRankingSystemsCounter = 0;    // number of rankings in which both elements exist

        // FILLING THE TRANSITION PROBABILITY MATRIX

        // Iterate through the table list - row-wise (Can also be considered as iterating through the element ids list)
        for (int row = 0; row < tableSize; ++row) {
            // Iterate through the table list - column-wise
            for (int column = 0; column < tableSize; ++column) {
                // Iterate through list of maps
                for (Map<String, Double> currentMap : listOfRankings) {
                    // If both current row and column elements exists in the current map/ranking
                    if (currentMap.containsKey(elementIds.get(row)) && currentMap.containsKey(elementIds.get(column))) {
                        commonRankingSystemsCounter++;      // Increment common rankings variable

                        // If the column element has a value better than the row element - better = smaller - rankings!
                        if (currentMap.get(elementIds.get(row)) > currentMap.get(elementIds.get(column)))
                            majorityCounter++;      // Increment the majorityCounter
                    }
                } // End of list of Maps for-loop

                transitionMatrix.set(row, column, (majorityCounter * 1.0) / (commonRankingSystemsCounter * tableSize));
                probabilitiesSum += transitionMatrix.get(row, column);      // Add current cell value to the probability sum

                // Reset helper variables for next comparison
                majorityCounter = 0;
                commonRankingSystemsCounter = 0;
            } // End of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - (sum of probabilities of moving to other states)
            transitionMatrix.set(row, row, 1 - probabilitiesSum);

            probabilitiesSum = 0.0;     // Reset probability sum for next row
        } // End of rows for-loop
        return transitionMatrix;    // Return the created transition probability matrix
    }
}
