import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 09-Mar-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class RankAggregationMethod {
    private List<SNP> SNPList;

    public RankAggregationMethod() {
        this.SNPList = new ArrayList<>();
    }

    public RankAggregationMethod(List<SNP> SNPList) {
        this.SNPList = SNPList;
    }

    // Borda Methods

    /**
     * Method that aggregates SNP rankings using the median of them as the new ranking
     * Changes the SNP list inside every SNP of the class input list
     */
    public void MedianRank() {
        // Temporary list to find median
        List<Integer> tempList;
        // Temporary variable to save the median value
        int median;
        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Copy its list of rankings to a temporary list
            tempList = new ArrayList<>(currentSNP.getSNPRank());

            // Sort list, lower to higher
            Collections.sort(tempList);

            // Choose median - is at index 1 from (0 1 2) in this implementation
            median = tempList.get(1);
            //TODO If expandable - odd or even number of elements

            // Add the median to the SNP's rank list, at index 3
            currentSNP.addRank(3, median);
        }
    }

    public void GeometricMeanRank() {

    }

    public void pNormRank() {

    }
}
