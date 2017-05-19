package rankAggregationMethods.BordaMethods;

import java.util.Collections;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class BordaMedian {

    // NOTE this list is NOT the one corresponding to the Map that comes as the output of the preprocessing stage.
    // It is actually a transformed view that contains the scores of different rank systems for ONE SNP/Element
    // TODO function that does this transformation -- See paper for details

    /**
     * Aggregates the numbers of the input list using median as its aggregation function
     * @param numbersToBeAggregated input list containing the numbers to be aggregated
     * @return a double value containing the result of the aggregation
     */
    private Double computeAggregation(List<Double> numbersToBeAggregated) {
        // Store list size in temporary variable (readability reasons)
        int size = numbersToBeAggregated.size();

        // Sort list
        Collections.sort(numbersToBeAggregated);

        // Check if odd or even
        if (size % 2 == 0)     // If even
            // Return the mean value of the two "central" elements
            return (numbersToBeAggregated.get(size/2 - 1) + numbersToBeAggregated.get(size/2))/2;
        else
            // Return the one central value
            return numbersToBeAggregated.get(size/2);
    }
}
