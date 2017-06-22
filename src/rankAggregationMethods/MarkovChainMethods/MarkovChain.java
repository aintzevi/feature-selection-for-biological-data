package rankAggregationMethods.MarkovChainMethods;

import Jama.Matrix;
import javafx.util.Pair;
import rankAggregationMethods.RankAggregationDataTransformation;

import java.util.*;

/**
 * Created by Katerina Intzevidou on 25-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public abstract class MarkovChain {

    // Methods

    protected Matrix transitionMatrix;

    /**
     * Creates the transition probability matrix of the Markov Chain methods
     * @param listOfRankings list that contains maps with id (String) as key and the ranking (Double) as value.
     *                        Every one of the maps is one ranking
     * @return Matrix containing Double values between 0.0 and 1.0 - corresponding to probabilities,
     * or -1.0 if this element is not included in the same ranking system
     */
    protected abstract Matrix createTransitionProbabilityMatrix(List<Map<String, Double>> listOfRankings);

    /**
     * Transforms an MC transition probabilities matrix according to the formula
     * P'(u -> v) = (1 - a)P(u -> v) + a/|S|, where a is a (preferably small) parameter and |S| the number of elements/rows of the matrix
     * @param A The transition probabilities matrix, contains non-negative, double values
     * @param a Parameter a of the formula, non-negative Double value (if equal to 0, then the matrix stays the same)
     */
    private static void transformMCMatrix(Matrix A, Double a) {
        // Get the size of the matrix -- square matrix so #rows = #columns
        int matrixSize = A.getColumnDimension();
        // Create a same size matrix with the parameter in every cell
        Matrix B = new Matrix(matrixSize, matrixSize, a/matrixSize);

        // First multiply the basic matrix elements with the value (1 - a)
        A.timesEquals(1 - a);

        // Then add matrix B (constant value a/|S|) to matrix A
        A.plusEquals(B);
    }

    /**
     * Calculates the stationary distribution of a transition probability matrix
     * @param transitionMatrix The matrix whose stationary distribution we are looking for
     * @return a matrix containing the stationary distribution
     */
    private static Matrix stationaryDistribution(Matrix transitionMatrix) {
        // Creating a clone of the transition matrix, to calculate the stationary Distribution (no changes to the original transition matrix object)
        Matrix tempTransitionMatrix = (Matrix) transitionMatrix.clone();
        // Transpose the matrix
        tempTransitionMatrix = tempTransitionMatrix.transpose();
        // initial guess for the eigenvector
        Matrix stationaryDistr = new Matrix(tempTransitionMatrix.getColumnDimension(), 1, 1.0 / tempTransitionMatrix.getColumnDimension());

        // power method - 50 iterations
        for (int i = 0; i < 50; i++) {
            // Multiplying the matrix
            stationaryDistr = tempTransitionMatrix.times(stationaryDistr);
            // Rescaling matrix values
            stationaryDistr = stationaryDistr.times(1.0 / stationaryDistr.norm1());
        }
        return stationaryDistr;
    }

    /**
     * Creates a new ranking out of a list of rankings using the Markov Chain 1 method.
     * @param listOfRankings list containing the initial rankings
     * @param a non negative parameter, preferably small
     * @return an ascending sorted new ranking
     */
    public Map<String, Double> getMCMethodRanking(List<Map<String, Double>> listOfRankings, Double a) {
        // Creating the transition probability matrix out of the list of rankings
        // and transforming it according to the formula P'(u -> v) = (1 - a)P(u -> v) + a/|S|,
        // where a is a (preferably small) parameter and |S| the number of elements/rows of the matrix
        transformMCMatrix(createTransitionProbabilityMatrix(listOfRankings), a);

        // Computing the stationary distribution of the probability matrix
        Matrix stationaryDistribution = stationaryDistribution(createTransitionProbabilityMatrix(listOfRankings));

        // Using the list of element IDs and a list of pairs to sort the rankings
        List<String> elementIds = RankAggregationDataTransformation.getElementIds(listOfRankings);
        List<Pair<String, Double>> listOfElementsAndRanks = new ArrayList<>();

        // Creating a list of pairs with the ID (string) and its respective value at the stationary distribution (Double)
        for(int i = 0 ; i < stationaryDistribution.getRowDimension() ; ++i) {
            listOfElementsAndRanks.add(new Pair<>(elementIds.get(i), stationaryDistribution.get(i,0)));
        }

        // Sorting the list of pairs based on the value (rank, score)
        Collections.sort(listOfElementsAndRanks, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(final Pair<String, Double> o1, final Pair<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue());
            }
        });

        // Map to save the final, sorted ranking
        Map <String, Double> sortedRankingMap = new LinkedHashMap<>();

        // Iterating through the list of pairs and adding the values in the map in the proper ascending order.
        for (Pair<String, Double> currentPair : listOfElementsAndRanks) {
            sortedRankingMap.put(currentPair.getKey(), currentPair.getValue());
        }

        return sortedRankingMap;        // Return the sorted ranking
    }
}
