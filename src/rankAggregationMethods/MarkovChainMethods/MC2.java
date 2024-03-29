package rankAggregationMethods.MarkovChainMethods;

import Jama.Matrix;
import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class MC2 extends MarkovChain {

    /**
     * Creates the transition probability matrix of the MC2 aggregation method [Lin, 2010]
     * @param listOfRankings list that contains maps with id (String) as key and the ranking (Double) as value.
     *                        Every one of the maps is one ranking
     * @return Matrix containing Double values between 0.0 and 1.0 - corresponding to probabilities
     */
    protected Matrix createTransitionProbabilityMatrix(List<Map<String, Double>> listOfRankings) {
        // Get the element id of all elements in the input maps/rankings
        List<String> elementIds = RankAggregationDataTransformation.getElementIds(listOfRankings);
        // Save the size
        int tableSize = elementIds.size();

        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        // Helper variables
        double probabilitiesSum = 0.0;
        int counter = 0;    // counter to check if one element is better than the other in the majority of ranking systems

        // FILLING THE TRANSITION PROBABILITY MATRIX

        // Iterate through the table list - row-wise (Can also be considered as iterating through the element ids list)
        for(int row = 0 ; row < tableSize ; ++row) {
            // Iterate through the table list - column-wise
            for(int column = 0 ; column < tableSize ; ++column) {
                // Iterate through list of maps
                for(Map<String, Double> currentMap : listOfRankings) {
                    // If the current row and column elements exists in the current map
                    if(currentMap.containsKey(elementIds.get(row)) && currentMap.containsKey(elementIds.get(column))){
                        // If the column element has a value better than the row element
                        // (therefore row element's value is bigger than the one of column element - rankings!)
                        if(currentMap.get(elementIds.get(row)) > currentMap.get(elementIds.get(column)))
                            counter++;      // Increment the wins counter
                    }
                } // End of list of Maps for-loop

                // If the column element has better ranking in the majority of the ranking systems (more than half)
                if (counter >= Math.ceil(listOfRankings.size() / 2.0))
                    // Add the 1/S value in this cell (S being the table size/ number of all elements between which we create the new ranking)
                    transitionMatrix.set(row, column,1.0/tableSize);
                else
                    transitionMatrix.set(row, column, 0);       // Set the cell value to 0

                probabilitiesSum += transitionMatrix.get(row, column);      // Add current cell value to the helper sum variable

                counter = 0;        // Reset counter for next column element comparisons
            } // End of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - the sum of the probabilities to change to another state when in current state
            transitionMatrix.set(row, row, 1 - probabilitiesSum);

            probabilitiesSum = 0.0;     // Resetting probability sum for next row

        } // End of rows for-loop
        return transitionMatrix;    // Return the created transition probability matrix
    }
}
