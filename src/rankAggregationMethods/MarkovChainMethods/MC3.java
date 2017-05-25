package rankAggregationMethods.MarkovChainMethods;

import Jama.Matrix;
import rankAggregationMethods.InputListsTransformations;

import java.util.List;
import java.util.Map;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class MC3 {
    // List of the different rankings to aggregate
    private List<Map<String, Double>> inputListOfMaps;
    private InputListsTransformations ilt;

    public MC3(List<Map<String, Double>> inputListOfMaps) {
        this.inputListOfMaps = inputListOfMaps;
        ilt = new InputListsTransformations(inputListOfMaps);
    }

    /**
     * Creates the transition probability matrix of the MC3 rank aggregation method [Lin, 2010], for a list of elements that are ranked
     * using different ranking systems
     */
    private Matrix createTransitionProbabilityMatrix() {
        // Get the element id of all elements in the input maps/rankings
        List<String> elementIds = ilt.getElementIds();
        // Save the size
        int tableSize = elementIds.size();

        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        // Helper variables
        double probabilitiesSum = 0.0;
        int majorityCounter = 0;    // counter to check if one element is better than the other in the majority of ranking systems
        int commonRankingSystemsCounter = 0;

        // FILLING THE TRANSITION PROBABILITY MATRIX

        // Iterate through the table list - row-wise (Can also be considered as iterating through the element ids list)
        for (int row = 0; row < tableSize; ++row) {
            // Iterate through the table list - column-wise
            for (int column = 0; column < tableSize; ++column) {
                // Iterate through list of maps
                for (Map<String, Double> currentMap : inputListOfMaps) {
                    // If the current row element exists in it
                    if (currentMap.containsKey(elementIds.get(row))) {
                        // If the column element exists as well
                        if (currentMap.containsKey(elementIds.get(column))) {
                            // Increment the counter that stores the number of rankings in which both elements exist
                            commonRankingSystemsCounter++;

                            // If the column element has a value better than the row element
                            // (therefore row element's value is bigger than the one of column element - rankings!)
                            if (currentMap.get(elementIds.get(row)) > currentMap.get(elementIds.get(column)))
                                // Increment the majorityCounter
                                majorityCounter++;
                        } // Column
                        else
                            continue;   // Start looking in the next map
                    } // Row
                    else
                        continue;   // Start looking in the next map
                    // Add current cell value to the helper probability sum variable
                    probabilitiesSum += transitionMatrix.get(row, column);
                } // End of list of Maps for-loop

                // If the column element has better ranking in the majority of the ranking systems
                if (majorityCounter > inputListOfMaps.size())
                    // Add the 1/S value in this cell (S being the tablezize/ number of all elements between which we create the new ranking)
                    transitionMatrix.set(row, column, majorityCounter / commonRankingSystemsCounter);
                else
                    // Set the cell value to 0
                    transitionMatrix.set(row, column, 0);

                // If probabilities sum is equal to zero - means there was no map where both elements existed
                transitionMatrix.set(row, column, -1.0);      // Set cell value to -1.0 (Negative value to distinguish from 0s)
            } // End of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - the sum of the probabilities to change to another state when in current state
            transitionMatrix.set(row, row, 1 - probabilitiesSum);
            // Resetting the helper variables to evaluate the next row
            probabilitiesSum = 0.0;
            majorityCounter = 0;
            commonRankingSystemsCounter = 0;
        } // End of rows for-loop
        return transitionMatrix;    // Return the created transition probability matrix
    }
}
