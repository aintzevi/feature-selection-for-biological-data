package preprocessing;

/**
 * Contains all required preprocessing.SNP information, that being the preprocessing.SNP unique id, it's rank for some specific ranking and its respective score
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP /*implements Comparable<preprocessing.SNP> */{
    // Unique id
    private String SNPid;
    // Rank of preprocessing.SNP for a specific ranking system
    private Double rank;
    // Score of preprocessing.SNP for a specific ranking system
    private Double score;

    public SNP(){}
    /**
     * Creates an preprocessing.SNP object with specific values for the SNPid, the rank and the score of the preprocessing.SNP
     * @param SNPid is a string containing the unique name of the preprocessing.SNP
     * @param rank is the rank of this preprocessing.SNP in a specific ranking system
     * @param score is the score of this preprocessing.SNP in a specific ranking system
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

}
