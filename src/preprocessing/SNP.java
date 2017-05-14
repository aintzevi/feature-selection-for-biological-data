package preprocessing;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains all required preprocessing.SNP information, that being the preprocessing.SNP unique id, it's rank for some specific ranking and its respective score
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP /*implements Comparable<preprocessing.SNP> */{
    // Unique id
    private String SNPid;
    // Rank of SNP for a specific ranking system
    private Double rank;
    // Score of SNP for a specific ranking system
    private Double score;
    // Normalised score - range in [0.0, 1.0]
    private Double normalizedScore;


    public SNP(){}
    /**
     * Creates an SNP object with specific values for the SNPid, the rank and the score of the SNP
     * @param SNPid is a string containing the unique name of the SNP
     * @param rank is the rank of this SNP in a specific ranking system
     * @param score is the score of this SNP in a specific ranking system
     * @param normalizedScore is the normalized value of the score of this SNP, from its initial range to the [0.0, 1.0] range
     */
    public SNP(String SNPid, Double rank, Double score, Double normalizedScore) {
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

    public Double getNormalizedScore() {
        return normalizedScore;
    }

    public void setNormalizedScore(Double normalizedScore) {
        this.normalizedScore = normalizedScore;
    }

}
