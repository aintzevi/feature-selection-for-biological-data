import java.util.List;

/**
 * Contains all required SNP information, that being the SNP unique id, it's rank for some specific ranking and its grading,
 * given that the ranking system uses grading, where the grade difference is meaningful, and ranking is not enough
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP {
    // Unique id
    private int SNPid;
    // Rank of SNP for a specific ranking system
    private List<Integer> SNPRank;
    // Grade of the SNP (in case we use grading to aggregate, rather than just ranking)
    private List<Float> SNPGrading;
    /*
    NOTED: Respective SNPRank and SNPGrading index refer to the results of one ranking system
    (index:0 (both lists) -> first ranking system, index:1 (both lists) -> second ranking system, etc.)
    */

    /**
     * Constructs a SNP with its unique id, a list of its rank positions and a list of its gradings
     * @param SNPid Unique SNP id
     * @param SNPRank List of the rank this SNP has in different ranking systems
     * @param SNPGrading List of the grading this SNP has in different ranking systems
     */
    public SNP(int SNPid, List<Integer> SNPRank, List<Float> SNPGrading) {
        this.SNPid = SNPid;
        this.SNPRank = SNPRank;
        this.SNPGrading = SNPGrading;
    }

/*    public SNP(int SNPid, int SNPRank, float SNPGrading) {
        this.SNPid = SNPid;
        this.SNPRank = new ArrayList<>();
        this.SNPGrading = new ArrayList<>();
    }*/

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
     * @param SNPid should be a unique, non-negative, integer value
     */
    public void setSNPid(int SNPid) {
        this.SNPid = SNPid;
    }

    /**
     * SNPRank list contains the ranking of this SNP for several ranking systems
     * @return SNPRank list
     */
    public List<Integer> getSNPRank() {
        return SNPRank;
    }

    /**
     *
     * @param SNPRank List containing positive integer values
     */
    public void setSNPRank(List<Integer> SNPRank) {
        this.SNPRank = SNPRank;
    }

    /**
     * SNPGrading list contains the grading of this SNP for several ranking systems - indices respective to the SNPRank list
     * @return SNPGrading list
     */
    public List<Float> getSNPGrading() {
        return SNPGrading;
    }

    /**
     *
     * @param SNPGrading List containing float values
     */
    public void setSNPGrading(List<Float> SNPGrading) {
        this.SNPGrading = SNPGrading;
    }

    // Methods

    /**
     * Adds a new ranking to the list with the SNP rankings, in a specific index
     * @param index position of the new ranking in the list
     * @param rank SNP rank to be added to the list of rankings
     */
    public void addRank (int index, int rank) {
        this.getSNPRank().add(index, rank);
    }

    /**
     * Adds a new grading to the list with the SNP gradings, in a specific index
     * @param index position of the new grading in the list
     * @param grading SNP grading to be added to the list of gradings
     */
    public void addGrading (int index, float grading) {
        this.getSNPGrading().add(index, grading);
    }

}
