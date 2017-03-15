import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 09-Mar-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class RankAggregationMethod {
    // TODO Consider making it more expandable (number of rankings)

    private List<SNP> SNPList;

    public RankAggregationMethod() {
        this.SNPList = new ArrayList<>();
    }

    public RankAggregationMethod(List<SNP> SNPList) {
        this.SNPList = SNPList;
    }

    // Borda Methods

    /**
     * Aggregates SNP rankings using the median of them as the new ranking
     * Changes the SNP list inside every SNP of the class input list
     */
    public void MedianRank() {
        // Temporary list to find median
        List<Double> tempList;
        // Temporary variable to save the median value
        Double median;
        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Copy its list of rankings to a temporary list - Copies only 3 first rankings (genetic rankings)
            tempList = new ArrayList<>(currentSNP.getSNPRank().subList(0, 3));

            // Sort list, lower to higher
            Collections.sort(tempList);

            // Choose median - is at index 1 from (0 1 2) in this implementation
            median = tempList.get(1);
            //TODO If expandable - odd or even number of elements

            // Add the median to the SNP's rank list, index 3
            currentSNP.addRank(3, median);
        } // end for - list of SNPs
    }

    /**
     * Aggregates SNP rankings using the geometric mean formula to calculate the aggregated rank
     * Changes the SNP list inside every SNP of the class input list
     */
    public void GeometricMeanRank() {
        // variables to help calculate geometric mean formula
        double rankProduct = 1.0;
        double geometricMean;

        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Read first 3 rankings
            for (int i = 0 ; i <= 2 ; ++i) {
                // Calculate the rankings product
                rankProduct *= currentSNP.getSNPRank().get(i);
            }

            // Calculate geometric mean

            // Geometric mean formula: value = L-root of product of L numbers
            geometricMean = Math.pow(rankProduct, 1/3);

            // Add geometric mean to the SNP's rank list, index 4
            currentSNP.getSNPRank().add(4, geometricMean);

        } // end for - list of SNPs
    }

    /**
     * Aggregates SNP rankings using the p-norm formula (Lin, 2010) to calculate the aggregated rank
     * @param p power to which every ranking will be raised to. Double value. p = 1 turns this method to the arithmetic mean
     */
    public void pNormRank(Double p) {
        double sum = 0.0;
        double pNorm;

        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Read first 3 rankings
            for (int i = 0 ; i <= 2 ; ++i) {
                // Calculate the sum of (rankings power of p)
                sum += Math.pow(currentSNP.getSNPRank().get(i), p);
            }

            // Calculate p-norm

            // p-norm formula (Lin, 2010): value = sum of (rankings power of p) divided by number of rankings
            // TODO Make number of rankings variable -- to be checked
            pNorm = sum/3;

            // Add p-norm to the SNP's rank list, index 5
            currentSNP.getSNPRank().add(5, pNorm);

        } // end for - list of SNPs
    }
}
