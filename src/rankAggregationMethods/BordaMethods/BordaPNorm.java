package rankAggregationMethods.BordaMethods;

import java.util.List;

/**
 * Created by Katerina Intzevidou on 15-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class BordaPNorm extends BordaMethod {

    private Double p;

    /**
     * Class constructor
     * @param p double value corresponding to the parameter that will be used for the aggregation
     *               if p = 1.0, the formula is the arithmetic mean
     */
    public BordaPNorm(Double p) {
        this.p = p;
    }

    /**
     * Aggregates the numbers of the input list using p-Norm as its aggregation function
     * @param numbersToBeAggregated input list containing the numbers to be aggregated
     * @return a double value containing the result of the aggregation
     */
    protected Double computeAggregation(List<Double> numbersToBeAggregated) {
        // Helper variable
        double sum = 0.0;

        // Iterate through the list numbers
        for (Double currentNumber : numbersToBeAggregated)
            sum += Math.pow(currentNumber, p);      // Get the sum of the numbers on power p

        //p-norm formula (Lin, 2010): value = sum of (rankings power of p) divided by number of rankings
        return sum/numbersToBeAggregated.size();       // Calculate p-norm
    }
}
