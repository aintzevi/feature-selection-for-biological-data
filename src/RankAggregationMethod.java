import Jama.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Katerina Intzevidou on 09-Mar-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class RankAggregationMethod {

    private List<SNP> SNPList;
    // Number of rankings coming from genetic filtering (must be the same with the respective number of the SNPs)
    private int numberOfPrimaryRankings;
//    private int rankingParameter;

    public RankAggregationMethod(List<SNP> SNPList, int numberOfPrimaryRankings) {
        this.SNPList = SNPList;
        this.numberOfPrimaryRankings = numberOfPrimaryRankings;
    }

    /**
     * @return Positive integer - Number of rankings coming from genetic filtering
     */
    public int getNumberOfPrimaryRankings() {
        return numberOfPrimaryRankings;
    }

    /**
     *
     * @param numberOfPrimaryRankings Number of rankings coming from genetic filtering (must be the same with the respective number of the SNPs)
     */
    public void setNumberOfPrimaryRankings(int numberOfPrimaryRankings) {
        this.numberOfPrimaryRankings = numberOfPrimaryRankings;
    }

    // Borda Methods

    /**
     * Aggregates SNP rankings using the median of them as the new ranking
     * Changes the SNP list inside every SNP of the class input list
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         1 is for score and 0 is for everything else (default)
     */
    public void Median(int rankingParameter) {
        // Temporary list to find median
        List<Double> tempList;
        // Temporary variable to save the median value
        Double median;
        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Copy its list of primary rankings to a temporary list
            if (rankingParameter == 1)
                // sublist: [0, numberOfPrimaryRankings)
                tempList = new ArrayList<>(currentSNP.getSNPScore().subList(0, numberOfPrimaryRankings));
            else
                tempList = new ArrayList<>(currentSNP.getSNPRank().subList(0, numberOfPrimaryRankings));

            // Sort list, lower to higher
            Collections.sort(tempList);

            // Calculate median

            // If the number of rankings is even
            if (numberOfPrimaryRankings%2 == 0) {
                // The median is the average value of the two central values
                median = (tempList.get(numberOfPrimaryRankings / 2 - 1) + tempList.get(numberOfPrimaryRankings / 2)) / 2.0;
            }
            else {// If the number of rankings is odd
                // median is the central value of the list, whose index is the floor of the numberOfPrimaryRankings/2 value
                median = tempList.get(numberOfPrimaryRankings/2);
            }

            // Adding the aggregation method result to rank or score, depending on the parameter
            if (rankingParameter == 1)
                // Add the median to the SNP's score list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.addScore(numberOfPrimaryRankings, median);
            else
                // Add the median to the SNP's rank list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.addRank(numberOfPrimaryRankings, median);
        } // end for - list of SNPs
    }

    /**
     * Aggregates SNP rankings using the geometric mean formula to calculate the aggregated rank
     * Changes the SNP list inside every SNP of the class input list
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         1 is for score and 0 is for everything else (default)
     */
    public void GeometricMean(int rankingParameter) {
        // variables to help calculate geometric mean formula
        double product = 1.0;
        double geometricMean;

        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Read primary rankings
            for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                if (rankingParameter == 1) {
                    // Calculate the score product
                    product *= currentSNP.getSNPScore().get(i);

                }
                else {
                    // Calculate the rankings product
                    product *= currentSNP.getSNPRank().get(i);
                }
            }

            // Calculate geometric mean

            // Geometric mean formula: value = L-root of product of L numbers
            geometricMean = Math.pow(product, 1.0/numberOfPrimaryRankings);

            if (rankingParameter == 1) {
                // Add geometric mean to the SNP's score list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.getSNPScore().add(numberOfPrimaryRankings, geometricMean);
            }
            else {
                // Add geometric mean to the SNP's rank list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.getSNPRank().add(numberOfPrimaryRankings, geometricMean);
            }
            // Reset product value to 1.0 for the loop of the next SNP
            product = 1.0;
        } // end for - list of SNPs
    }

    /**
     * Aggregates SNP rankings using the p-norm formula (Lin, 2010) to calculate the aggregated rank
     * @param p power to which every ranking will be raised to. Double value. p = 1 turns this method to the arithmetic mean
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         0 is for rank and 1 is for score
     */
    public void pNormRank(Double p, int rankingParameter) {
        double sum = 0.0;
        double pNorm;

        // For every SNP in the input list
        for (SNP currentSNP : SNPList) {
            // Read primary rankings
            for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {

                if(rankingParameter == 0)
                    // Calculate the sum of (rankings power of p)
                    sum += Math.pow(currentSNP.getSNPRank().get(i), p);
                else
                    // Calculate the sum of (rankings power of p)
                    sum += Math.pow(currentSNP.getSNPScore().get(i), p);
            } // end-for of primary rankings loop

            // Calculate p-norm

            // p-norm formula (Lin, 2010): value = sum of (rankings power of p) divided by number of rankings
            pNorm = sum/numberOfPrimaryRankings;

            if (rankingParameter == 0)
                // Add p-norm to the SNP's rank list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.getSNPRank().add(numberOfPrimaryRankings, pNorm);
            else
                // Add p-norm to the SNP's score list, index next to the last primary ranking (numberOfPrimaryRankings)
                currentSNP.getSNPScore().add(numberOfPrimaryRankings, pNorm);
            // Reset sum value to 0 to calculate the value of the next SNP
            sum = 0.0;
        } // end for - list of SNPs
    }

    /**
     * Applies the Markov Chain 1 Method (Lin, 2010)
     * Calculates the probability of moving from an element of lower ranking to one of a higher ranking (pairwise comparison)
     * At least one lower ranking is enough to set the probability higher to move to the other element/state
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         1 is for score and 0 is for everything else (default)
     */
    public void MC1(int rankingParameter) {
        int tableSize = SNPList.size();
        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        double probabilitiesSum = 0.0;

        // Iterating through the elements to create the transition table
        for (int row = 0 ; row < tableSize ; ++row) {
            for (int column = 0 ; column < tableSize ; ++column) {
                // Iterating through the ranking lists of the current pair of SNPs (2 outer for-loops)
                for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                    // Aggregating based on score
                    if (rankingParameter == 1) {
                        // If (for the corresponding score) the score of the first element value is smaller than the second (aka is worse)
                        if (SNPList.get(row).getSNPScore().get(i) < SNPList.get(column).getSNPScore().get(i)) {
                            // Fill the transition matrix cell with the value 1/numberOfPrimaryRankings
                            transitionMatrix.set(row, column, 1.0/numberOfPrimaryRankings);
                            // This condition is true for at least one of the rankings, no need to check the rest rankings for this element pair
                            // Exit this loop
                            break;
                        }
                        // If all rankings of the first elements are bigger than or equal to the second one's (aka all rankings are at least equally good)
                        // NOTE this way every pair comparison of an element with itself gets a probability of 0
                        else
                            // Fill the corresponding cell of the transition matrix with a zero
                            transitionMatrix.set(row, column, 0);
                    }
                    // In all other rankingParameter values, we aggregate by rank by default
                    else {
                        // If (for the corresponding ranking) the ranking of the first element is bigger than the second (aka is worse)
                        if (SNPList.get(row).getSNPRank().get(i) > SNPList.get(column).getSNPRank().get(i)) {
                            // Fill the transition matrix cell with the value 1/numberOfPrimaryRankings
                            transitionMatrix.set(row, column, 1.0/numberOfPrimaryRankings);
                            // Since this condition is true for at least one of the rankings, no need to check the rest
                            // Exit this loop
                            break;
                        }
                        // If all rankings of the first elements are smaller or equal than the second one's (aka all rankings are at least equally good)
                        // NOTE this way every pair comparison of an element with itself gets a probability of 0
                        else
                            // Fill the corresponding cell of the transition matrix with a zero
                            transitionMatrix.set(row, column, 0);
                    }
                    // Add probability of each state transition to get the sum
                    probabilitiesSum += transitionMatrix.get(row, column);

                } // end of rankings for-loop
            } // end of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - the sum of the probabilities to change to another state when in current state
            transitionMatrix.set(row, row, 1 - probabilitiesSum);
            // Resetting the probabilitiesSum value to evaluate the next row
            probabilitiesSum = 0.0;
        } // end of rows for-loop
    }

    /**
     * Applies the Markov Chain 1 Method (Lin, 2010)
     * Calculates the probability of moving from an element of lower ranking to one of a higher ranking (pairwise comparison)
     * The element must have a better ranking for at least half of the rankings to have a transition probability higher than zero
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         1 is for score and 0 is for everything else (default)
     */
    public void MC2(int rankingParameter) {
        // Size of the transition matrix - also the number of elements/states
        int tableSize = SNPList.size();
        // Counter to check if the majority of ranks of one state is better than the current state
        int counter = 0;
        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);

        // Variable to keep the sum of the probabilities of moving from the current state to another, to ultimately calculate the probability of staying to the current state
        double probabilitiesSum = 0.0;

        // Iterating through the elements to create the transition table
        for (int row = 0 ; row < tableSize ; ++row) {
            for (int column = 0 ; column < tableSize ; ++column) {
                // Iterating through the ranking lists of the current pair of SNPs (2 outer for-loops)
                for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                    // Count the number of rankings for which the first element has worse ranking than the second element

                    if (rankingParameter == 1) {
                        // If (for the corresponding ranking) the ranking of the first element is bigger than the second (aka is worse)
                        if (SNPList.get(row).getSNPScore().get(i) > SNPList.get(column).getSNPScore().get(i))
                            // Increment the counter
                            counter++;
                    }
                    else {
                        // If (for the corresponding ranking) the ranking of the first element is bigger than the second (aka is worse)
                        if (SNPList.get(row).getSNPRank().get(i) > SNPList.get(column).getSNPRank().get(i))
                            // Increment the counter
                            counter++;
                    }
                } // end of rankings for-loop

                // If the rank of the first element is bigger than the second for more than half the rankings
                if (counter > numberOfPrimaryRankings/2) {
                    //Fill the transition matrix cell with the value 1/numberOfPrimaryRankings
                    transitionMatrix.set(row, column, 1.0/numberOfPrimaryRankings);
                }
                else {
                    // Fill the corresponding cell of the transition matrix with a zero
                    transitionMatrix.set(row, column, 0);
                }
                // Add probability of each state transition to get the sum
                probabilitiesSum += transitionMatrix.get(row, column);

                // Reset list majority counter to zero for next column element iteration
                counter = 0;
            } // end of columns for-loop

            // After one element is compared to all others, come back and change the (row, row) cell
            // to the value 1 - the sum of the probabilities to change to another state when in current state
            transitionMatrix.set(row, row, 1 - probabilitiesSum);;

            // Reset probability counter for next current element iteration
            probabilitiesSum = 0.0;
        } // end of rows for-loop
    }

    /**
     * Markov Chain 3 method (Lin, 2010)
     * Calculates the probability of moving from an element of lower ranking to one of a higher ranking (pairwise comparison)
     * The probability of moving to another state is proportional to the number of lists that rank the new state higher than the current one.
     * @param rankingParameter Parameter to choose if the ranking is based on the rank or the score of the SNPs.
     *                         1 is for score and 0 is for everything else (default)
     */
    public void MC3 (int rankingParameter) {
        // Size of the transition matrix - also the number of elements/states
        int tableSize = SNPList.size();
        // Counter to keep the number of rankings where the current state has worse ranking than the new state it's compared to
        int rankingsCounter = 0;
        // Create a 2D transition matrix, both dimension sizes equal to the number of SNPs
        Matrix transitionMatrix = new Matrix(tableSize, tableSize);
        // Variable to keep the sum of the probabilities of moving from the current state to another, to ultimately calculate the probability of staying to the current state
        double probabilitiesSum = 0.0;

        // Iterating through the elements to create the transition table
        for (int row = 0 ; row < tableSize ; ++row) {
            for (int column = 0; column < tableSize; ++column) {
                // Iterating through the ranking lists of the current pair of SNPs (2 outer for-loops)
                for (int i = 0 ; i < numberOfPrimaryRankings ; ++i) {
                    if (rankingParameter == 1)
                        if (SNPList.get(row).getSNPScore().get(i) > SNPList.get(column).getSNPScore().get(i))
                            // Increment the counter
                            rankingsCounter  += 1;
                    else
                        // If (for the corresponding ranking) the ranking of the first element is bigger than the second (aka is worse)
                        if (SNPList.get(row).getSNPRank().get(i) > SNPList.get(column).getSNPRank().get(i))
                            // Increment the counter
                            rankingsCounter  += 1;
                }
                // Filling the transition matrix space according to the MC3 formula
                transitionMatrix.set(row, column, rankingsCounter/numberOfPrimaryRankings*tableSize);
                // Adding calculated probability (from current to new state) to the probability sum (to help calculate the current -> current probability later)
                probabilitiesSum += rankingsCounter/numberOfPrimaryRankings*tableSize;

                // Reset rankings counter for next new element iteration
                rankingsCounter = 0;
            } // end of column for-loop (comparison of current element to all new elements)
            // Calculate the probability from the current first element to itself. Value = 1 - the sum of the probabilities to the other elements
            transitionMatrix.set(row,row, 1 - probabilitiesSum);

            // Reset probability counter for next current element iteration
            probabilitiesSum = 0.0;
        } // end of row for-loop
    }

    /**
     * Method that creates a ranked list out of an aggregation method results
     * @param rankingParameter Parameter to set if the sorting should be done according to the calculated rank or score value
     *                         0 - Rank (ascending order - smaller value is higher)
     *                         1 - Score (descending order - bigger value is higher)
     * @return Arraylist of SNPs sorted according to either rank or score (depending on the parameter)
     */
    public List<SNP> getAggregatedList(int rankingParameter) {
        // Output list to be returned
        List<SNP> outputList = new ArrayList<>(this.SNPList);

        if(rankingParameter == 0)
            // Sorting in ascending order
            Collections.sort(outputList, SNP.Comparators.RANK);
        else if (rankingParameter == 1)
            // Sorting in descending order
            Collections.sort(outputList, SNP.Comparators.SCORE);
        // Return sorted list
        return outputList;

        // TODO Check for breaking rank ties with scores
    }
}
