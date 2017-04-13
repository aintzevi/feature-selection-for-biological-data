import java.util.Comparator;
import java.util.List;

/**
 * Contains all required SNP information, that being the SNP unique id, it's rank for some specific ranking and its grading,
 * given that the ranking system uses grading, where the grade difference is meaningful, and ranking is not enough
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP implements Comparable<SNP> {
    // Unique id
    private int SNPid;
    // Rank of SNP for a specific ranking system
    private List<Double> SNPRank;
    // Grade of the SNP (in case we use grading to aggregate, rather than just ranking)
    private List<Double> SNPScore;
    // Number of rankings that come from genetic filtering (NOTE: Must be the same for the SNPs in the same list)
    private static int numberOfPrimaryRankings;

    //TODO Handle not existing rank values

    /*
    NOTE: Respective SNPRank and SNPScore index refer to the results of one ranking system
    */

    /**
     * Constructs a SNP with its unique id, a list of its rank positions and a list of its gradings
     * @param SNPid Unique SNP id
     * @param SNPRank List of the rank this SNP has in different ranking systems
     * @param SNPScore List of the grading this SNP has in different ranking systems
     * @param numberOfPrimaryRankings How many the rankings that come from genetic filtering are
     */
    public SNP(int SNPid, List<Double> SNPRank, List<Double> SNPScore, int numberOfPrimaryRankings) {
        this.SNPid = SNPid;
        this.SNPRank = SNPRank;
        this.SNPScore = SNPScore;
        this.numberOfPrimaryRankings = numberOfPrimaryRankings;
    }

    // Getters and setters

    /**
     * SNPid is the unique id of the SNP
     * @return SNPid
     */
    public int getSNPid() {
        return SNPid;
    }

    /**
     * Sets the SNPid value
     * @param SNPid should be a unique, non-negative, double value
     */
    public void setSNPid(int SNPid) {
        this.SNPid = SNPid;
    }

    /**
     * SNPRank list contains the ranking of this SNP for several ranking systems
     * @return SNPRank list
     */
    public List<Double> getSNPRank() {
        return SNPRank;
    }

    /**
     *
     * @param SNPRank List containing positive double values
     */
    public void setSNPRank(List<Double> SNPRank) {
        this.SNPRank = SNPRank;
    }

    /**
     * SNPScore list contains the grading of this SNP for several ranking systems - indices respective to the SNPRank list
     * @return SNPScore list
     */
    public List<Double> getSNPScore() {
        return SNPScore;
    }

    /**
     *
     * @param SNPScore List containing double values
     */
    public void setSNPScore(List<Double> SNPScore) {
        this.SNPScore = SNPScore;
    }

    /**
     * numberOfPrimaryRankings is the number of rankings that come from genetic filtering
     * @return numberOfPrimaryRankings positive integer
     */
    public static int getNumberOfPrimaryRankings() {
        return numberOfPrimaryRankings;
    }

    /**
     * @param numberOfPrimaryRankings Positive integer - number of rankings that come from genetic filtering
     *                                // TODO maybe set it as a greater than 2 number
     */
    public static void setNumberOfPrimaryRankings(int numberOfPrimaryRankings) {
        SNP.numberOfPrimaryRankings = numberOfPrimaryRankings;
    }

    // Methods

    /**
     * Adds a new ranking to the list with the SNP rankings, in a specific index
     * @param index position of the new ranking in the list
     * @param rank SNP rank to be added to the list of rankings
     */
    public void addRank (int index, Double rank) {
        this.getSNPRank().add(index, rank);
    }

    /**
     * Adds a new score to the list with the SNP gradings, in a specific index
     * @param index position of the new score in the list
     * @param score SNP score to be added to the list of gradings
     */
    public void addScore(int index, Double score) {
        this.getSNPScore().add(index, score);
    }

    @Override
    public int compareTo(SNP obj) {
        // Compare according to SNP rank using a Comparator
        return Comparators.RANK.compare(this, obj);
    }

    /*@Override
    public int compareTo(SNP obj) {
        // Compare according to SNP score using a Comparator
        return Comparators.SCORE.compare(this, obj);
    }*/

    /**
     * Comparators Class used to compare SNPs according to their rank (ascending sorting) and their score (descending sorting)
     */
    public static class Comparators {
        /**
         * Comparator to sort depending on the Rank of the SNPs
         * Ascending order
         */
        public static Comparator<SNP> RANK = new Comparator<SNP>() {
            @Override
            public int compare(SNP obj1, SNP obj2) {
                return obj1.getSNPRank().get(numberOfPrimaryRankings).compareTo(obj2.getSNPRank().get(numberOfPrimaryRankings));
            }
        };

        /**
         * Comparator to sort depending on the Score of the SNPs
         * Descending order
         */
        public static Comparator<SNP> SCORE = new Comparator<SNP>() {
            @Override
            public int compare(SNP obj1, SNP obj2) {
                // Reversing obj1 and obj2 for descending order
                return obj2.getSNPScore().get(numberOfPrimaryRankings).compareTo(obj1.getSNPScore().get(numberOfPrimaryRankings));
            }
        };
    }
}
