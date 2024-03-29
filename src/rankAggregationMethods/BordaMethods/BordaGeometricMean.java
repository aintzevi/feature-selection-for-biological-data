package rankAggregationMethods.BordaMethods;

import java.util.List;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class BordaGeometricMean extends BordaMethod{

    /**
     * Aggregates the numbers of the input list using the geometric mean formula as its aggregation function
     * @param numbersToBeAggregated List containing the numbers to be aggregated by this function
     * @return a double type value containing the result of the aggregation
     */
    protected Double computeAggregation (List<Double> numbersToBeAggregated) {
        // Helper variable
        Double product = 1.0;

        // Iterate through the list of numbers
        for(Double currentNumber : numbersToBeAggregated)
            // Compute the product of the numbers
            product *= currentNumber;

        return Math.pow(product, 1.0/numbersToBeAggregated.size());        // Calculate and return the geometric mean
    }
}
