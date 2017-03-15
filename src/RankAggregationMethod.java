import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 09-Mar-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class RankAggregationMethod {

    private List<SNP> SNPList;
    private int numberOfPrimaryRankings;

    public RankAggregationMethod() {
        this.SNPList = new ArrayList<>();
        numberOfPrimaryRankings = 3;
    }

    public RankAggregationMethod(List<SNP> SNPList, int numberOfPrimaryRankings) {
        this.SNPList = SNPList;
        this.numberOfPrimaryRankings = numberOfPrimaryRankings;
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
            // Copy its list of rankings to a temporary list - Copies only first rankings (genetic rankings)
            tempList = new ArrayList<>(currentSNP.getSNPRank().subList(0, numberOfPrimaryRankings));

            // Sort list, lower to higher
            Collections.sort(tempList);

            // Choose median
            // If the number of rankings is even
            if (numberOfPrimaryRankings %2 == 0) {
                // The median is the average value of the two central values
                median = (tempList.get(numberOfPrimaryRankings - 1) + tempList.get(numberOfPrimaryRankings))/2.0;
            }
            else { // If the number of rankings is odd
                // median is the central value of the list, whose index is the floor of the numberOfPrimaryRankings/2 value
                median = tempList.get(numberOfPrimaryRankings /2);
            }

            // Add the median to the SNP's rank list, index next to the last primary ranking (numberOfPrimaryRankings + 1)
            currentSNP.addRank(numberOfPrimaryRankings + 1, median);
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
            // Read primary rankings
            for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                // Calculate the rankings product
                rankProduct *= currentSNP.getSNPRank().get(i);
            }

            // Calculate geometric mean

            // Geometric mean formula: value = L-root of product of L numbers
            geometricMean = Math.pow(rankProduct, 1/numberOfPrimaryRankings);

            // Add geometric mean to the SNP's rank list, index next to the first aggregated rankink (numberOfPrimaryRankings + 2)
            currentSNP.getSNPRank().add(numberOfPrimaryRankings + 2, geometricMean);

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
            // Read primary rankings
            for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                // Calculate the sum of (rankings power of p)
                sum += Math.pow(currentSNP.getSNPRank().get(i), p);
            }

            // Calculate p-norm

            // p-norm formula (Lin, 2010): value = sum of (rankings power of p) divided by number of rankings
            pNorm = sum/numberOfPrimaryRankings;

            // Add p-norm to the SNP's rank list, index (numberOfPrimaryRankings + 3)
            currentSNP.getSNPRank().add(numberOfPrimaryRankings + 3, pNorm);

        } // end for - list of SNPs
    }
}
