package rankAggregationMethods.BordaMethods;

import java.util.Collections;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class BordaMedian extends BordaMethod {

    /**
     * Aggregates the numbers of the input list using median as its aggregation function
     * @param numbersToBeAggregated input list containing the numbers to be aggregated
     * @return a double value containing the result of the aggregation
     */
    protected Double computeAggregation(List<Double> numbersToBeAggregated) {
        // Store list size in temporary variable (readability reasons)
        int size = numbersToBeAggregated.size();

        // Sort list
        Collections.sort(numbersToBeAggregated);

        // Check if size is odd or even
        if (size % 2 == 0)     // If even
            // Return the mean value of the two "central" elements
            return (numbersToBeAggregated.get(size/2 - 1) + numbersToBeAggregated.get(size/2))/2;
        else
            // Return the one central value
            return numbersToBeAggregated.get(size/2);
    }
}
