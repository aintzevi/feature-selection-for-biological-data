import java.util.Comparator;
import java.util.List;

/**
 * Contains all required SNP information, that being the SNP unique id, it's rank for some specific ranking and its respective score
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP /*implements Comparable<SNP> */{
    // Unique id
    private String SNPid;
    // Rank of SNP for a specific ranking system
    private Double rank;
    // Score of SNP for a specific ranking system
    private Double score;

    /**
     * Creates an SNP object with specific values for the SNPid, the rank and the score of the SNP
     * @param SNPid is a string containing the unique name of the SNP
     * @param rank is the rank of this SNP in a specific ranking system
     * @param score is the score of this SNP in a specific ranking system
     */
    public SNP(String SNPid, Double rank, Double score) {
        this.SNPid = SNPid;
        this.rank = rank;
        this.score = score;
    }

    // Class Accessors and Mutators
    public String getSNPid() {
        return SNPid;
    }

    public void setSNPid(String SNPid) {
        this.SNPid = SNPid;
    }

    public Double getRank() {
        return rank;
    }

    public void setRank(Double rank) {
        this.rank = rank;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    // Methods
/*
    @Override
    public int compareTo(SNP obj) {
        // Compare according to SNP rank using a Comparator
        return Comparators.RANK.compare(this, obj);
    }


    *//**
     * Comparators Class used to compare SNPs according to their rank (ascending sorting) and their score (descending sorting)
     *//*
    public static class Comparators {
        *//**
         * Comparator to sort depending on the Rank of the SNPs
         * Ascending order
         *//*
        public static Comparator<SNP> RANK = new Comparator<SNP>() {
            @Override
            public int compare(SNP obj1, SNP obj2) {
                return obj1.getSNPRank().get(numberOfPrimaryRankings).compareTo(obj2.getSNPRank().get(numberOfPrimaryRankings));
            }
        };

        *//**
         * Comparator to sort depending on the Score of the SNPs
         * Descending order
         *//*
        public static Comparator<SNP> SCORE = new Comparator<SNP>() {
            @Override
            public int compare(SNP obj1, SNP obj2) {
                // Reversing obj1 and obj2 for descending order
                return obj2.getSNPScore().get(numberOfPrimaryRankings).compareTo(obj1.getSNPScore().get(numberOfPrimaryRankings));
            }
        };
    }
*/
}
