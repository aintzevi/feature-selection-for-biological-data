package preprocessing;

import javafx.util.Pair;
import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.*;

/**
 * Contains all required SNP information, that being the SNP unique id, it's rank for some specific ranking and its respective score
 *
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP {
    // Unique id
    private String SNPid;
    // Rank of SNP for a specific ranking system
    private Double rank;
    // Score of SNP for a specific ranking system
    private Double score;
    // Normalised score - range in [0.0, 1.0]
    private Double normalizedScore;

    public SNP() {}

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

    // Methods

    /**
     * Helper function. Normalizes the SNP score values to the range [0,1]
     * @param SNPList List containing the SNPs whose scores we want to normalize
     */
    protected static void normalizeSNPScoreValues(List<SNP> SNPList) {
        // Creating a temporary list that contains all the scores of the SNPs in the list
        List<Double> scoresList = new ArrayList<>();
        // Pair of values to store the range of the score values
        Pair<Double, Double> rangePair;

        // Iterating through SNP List
        for(SNP currentSNP : SNPList) {
            // Adding score in the temporary score only list
            scoresList.add(currentSNP.getScore());
        }

        // Finding the range of values of the scores of the list
        rangePair = findRange(scoresList);

        // Iterating through SNP List
        for(SNP currentSNP : SNPList) {
            // Computing the normalized score for each SNP and storing it in the respective SNP field
            currentSNP.setNormalizedScore(normaliseAValue(currentSNP.getScore(),rangePair.getKey(), rangePair.getValue()));
        }
    }

    /**
     * Normalizes and reverses the score values of the input SNPs
     * @param SNPList is the list of SNPs whose normalized scores we want to compute and store
     */
    protected static void normalizeAndReverseSNPScoreValues(List<SNP> SNPList) {
        // Normalize using helper function
        normalizeSNPScoreValues(SNPList);

        // Iterate through the SNP List
        for(SNP currentSNP: SNPList) {
            // Reverse the score of every normalized score, and replace it with the new value
            currentSNP.setNormalizedScore(reverseScore(currentSNP.getNormalizedScore()));
        }
    }

    /**
     * Helper function that normalises double values of any range to a [0.0, 1.0] range
     * @param currentValue the value to be normalised
     * @param minValue Minimum value of the initial range of the currentValue
     * @param maxValue Maximum value of the initial range of the currentValue
     * @return The value of the currentValue in the range [0.0, 1.0]
     */
    private static Double normaliseAValue(Double currentValue, Double minValue, Double maxValue) {
        // Checking if the value given is in the initial range
        if(currentValue <= maxValue && currentValue >= minValue)
            // return the normalized value of the initial score
            return (currentValue - minValue)/(maxValue - minValue);     // formula x' = (x - xmin)/(xmax - xmin)
        System.out.println("Value to be normalised cannot be larger than max value!");
        // Exit with code equal to 1 if the value to be normalized is out of bounds
        System.exit(1);
        return null;
    }

    /**
     * Reverses a value in such way, that a higher value turns to a smaller one
     * @param value number to be reversed, must be in [0,1]
     * @return the reversed value. e.g. for an initial value of 0.7, return 0.3 etc.
     */
    private static Double reverseScore(Double value){
        return 1.0 - value;
    }

    /**
     * Helper function that finds the minimum and maximum value of a list and returns it as a Pair object
     * @param list is the list whose minimum and maximum value we're looking for
     * @return a Pair object Key: Minimum value
     *                       Value: Maximum value
     */
    private static Pair<Double, Double> findRange(List<Double> list) {
        // Using collections methods to get minimum and maximum values of the list
        return new Pair<>(Collections.min(list), Collections.max(list));
    }

    /**
     * Creates the proper output. That is a Map of pairs with the SNPid as the key and one of the other SNP fields as value,
     * depending on the parameter set by the user
     * @param SNPList is the initial SNPList from which the map will be created
     * @param parameter is the parameter based on which the field to serve as value is chosen
     *                  0 is for SNP rank
     *                  1 is for normalized SNP Score
     *                  Rank is also used as default
     * @return a map with the ranking of the initial list with the form of (SNPid -> value[depends on parameter])
     */
    public static Map<String, Double> formatOutput(List<SNP> SNPList, int parameter) {
        // Creating a linked hash map - output data structure
        Map<String, Double> outputSNPMap = new LinkedHashMap<>();

        // Choose which element of the SNP object to show in output
        switch(parameter){
            // SNP rank
            case 0:
                // Iterate through SNP list
                for (SNP currentSNP : SNPList) {
                    // Add SNP id and Rank in output Map (this map keeps the insertion order)
                    outputSNPMap.put(currentSNP.getSNPid(), currentSNP.getRank());
                }
                break;
            // Normalised score
            case 1:
                // Iterate through SNP list
                for (SNP currentSNP : SNPList) {
                    // Add SNP id and Rank in output Map (this map keeps the insertion order)
                    outputSNPMap.put(currentSNP.getSNPid(), currentSNP.getNormalizedScore());
                }
                break;
            // Using RANK as a default
            default:
                // Iterate through SNP list
                for (SNP currentSNP : SNPList) {
                    // Add SNP id and Rank in output Map (this map keeps the insertion order)
                    outputSNPMap.put(currentSNP.getSNPid(), currentSNP.getRank());
                }
                break;
        }
        return RankAggregationDataTransformation.createSortedOutput(outputSNPMap);      // Return sorted
    }
}
