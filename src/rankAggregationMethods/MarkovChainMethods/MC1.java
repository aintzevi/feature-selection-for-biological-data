package rankAggregationMethods.MarkovChainMethods;

import Jama.Matrix;
import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class MC1 extends MarkovChain{
    /**
     * Creates the transition probability matrix of the MC1 aggregation method [Lin, 2010]
     * @param listOfRankings list that contains maps with id (String) as key and the ranking (Double) as value.
     *                        Every one of the maps is one ranking
     * @return Matrix containing Double values between 0.0 and 1.0 - corresponding to probabilities
     */
    public Matrix createTransitionProbabilityMatrix(List<Map<String, Double>> listOfRankings) {
        // Get the element id of all elements in the input maps/rankings

        /*--------------------NOTE Elements are set with insertion order--------------------*/

        List<String> elementIds = RankAggregationDataTransformation.getElementIds(listOfRankings);
        // Save the size
        int tableSize = elementIds.size();

        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        // Helper variable
        double probabilitiesSum = 0.0;

        // FILLING THE TRANSITION PROBABILITY MATRIX

        // Iterate through the table list - row-wise (Can also be considered as iterating through the element ids list)
        for(int row = 0 ; row < tableSize ; ++row) {
            // Iterate through the table list - column-wise
            for(int column = 0 ; column < tableSize ; ++column) {
                // Iterate through list of maps
                for(Map<String, Double> currentMap : listOfRankings) {
                    // If the current row element exists in it
                    if(currentMap.containsKey(elementIds.get(row)) && currentMap.containsKey(elementIds.get(column))){
                        // If the column element has a value better than the row element
                        // (therefore row element's value is bigger than the one of column element - rankings!)
                        if(currentMap.get(elementIds.get(row)) > currentMap.get(elementIds.get(column))){
                            // Add the 1/S value in this cell (S being the table size/ number of all elements between which we create the new ranking)
                            transitionMatrix.set(row, column,1.0/tableSize);
                            // Add current cell value to the helper probability sum variable
                            probabilitiesSum += transitionMatrix.get(row, column);
                            break;      // Break map for loop - next column comparison
                        }
                        // If the column element has the same/worse value than the row one
                        else {
                            transitionMatrix.set(row, column, 0);       // Set cell value to zero
                        }
                    } // row and column element existence
                    else // at least one of the elements don't exist in current map/ranking
                        transitionMatrix.set(row, column, 0);       // Set cell value to zero
                } // End of list of Maps for-loop
            } // End of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - the sum of the probabilities to change to another state when in current state
            transitionMatrix.set(row, row, 1 - probabilitiesSum);
            // Resetting the probabilitiesSum value to evaluate the next row
            probabilitiesSum = 0.0;
        } // End of rows for-loop
        return transitionMatrix;    // Return the created transition probability matrix
    }
}
