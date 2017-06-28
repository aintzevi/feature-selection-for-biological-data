import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Katerina Intzevidou on 27-Jun-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class Evaluation {
    public static void main(String[] args) {
        // Object to handle the input directory
        File folder = new File("C:\\Users\\aintzevi\\Desktop\\proj\\Eval");

        computeEvaluationPerMethod(folder);
    }

    /**
     * Reads raw data from the input file
     * @param inputFilename filename of input file - absolute path - file coming from geneClass application. .csv
     * @return List containing the raw lines in String form in each of its entries
     */
    private static List<String> getRawLinesFromFile(String inputFilename) {
        File inputFile = new File(inputFilename);       // Create a file handle on the filename of the input
        String line;        // Line of file string
        List<String> listOfFileLines = new ArrayList<>();       // List to store raw data

        BufferedReader reader = null;       // Create buffered reader

        try {
            // Connect reader with input file
            reader = new BufferedReader(new FileReader(inputFile));

            // Init currentLine variable to empty string
            line = "";
            // While there still are lines in the file to read
            while (line != null) {
                // Add in list
                listOfFileLines.add(line);
                // Read the next currentLine, store in variable currentLine
                line = reader.readLine();
            }

            // Close reader after it has been read
            reader.close();
        } catch (IOException e) {
            System.out.println("Buffered reader init failed");
            e.printStackTrace();
        }
        finally {
            // Closing the reader
            try {
                // Checking if the reader is still open
                if (reader != null)
                    // If so, close it
                    reader.close();
            } catch (IOException ex) {
                System.out.println("Error closing buffered reader");
                ex.printStackTrace();
            }
        }
        return listOfFileLines;     // Return list with raw data
    }

    /**
     * Turns raw data to usable form - Creates pairs with the assumed group and the actual group of the SNPs
     * @param inputListOfLines List containing the raw data - first 14 lines are not useful here
     * @return List containing pairs with the assumed value as key and the actual value as value
     */
    private static List<Pair<String, String>> lineListToListOfPairs(List<String> inputListOfLines) {
        List<Pair<String, String>> pairArrayList = new ArrayList<>();       // List to store the pairs

        // Delete 14 first lines (empty and 13 first line of files that are not useful)
        for(int i = 0 ; i < 14 ; ++i) {
            // Data shifts to one index prior with every deletion, so always removing the first element
            inputListOfLines.remove(0);
        }

        // Iterate through the list containing the lines of the file
        for(String currentLine : inputListOfLines){
            // Pair to save values
            Pair<String, String> assumedAndProbablePair = null;

            // Tokenize each line
            StringTokenizer st = new StringTokenizer(currentLine, ";");
            if(st.hasMoreTokens()){
                // Creating a pair of values, using the first token (the assumed value) as key and
                // the second token (the -probably- actual value) as value
                assumedAndProbablePair = new Pair<>(st.nextToken().substring(1).replaceAll("\\d",""), st.nextToken().replaceAll("\\d",""));   // Removing an initial / from the key
            }
            pairArrayList.add(assumedAndProbablePair);      // Add current pair in the pairs list
        }

        return pairArrayList;       // Return the list of pairs
    }

    /**
     * Evaluates the efficiency of the method assuming the group in which an individual belongs.
     * @param listOfPairs list of pairs containing the assumed and actual group value for a number of individuals
     * @return A double value corresponding to the percentage of the individuals that were successfully put into the correct group
     */
    private static Double computeEvaluation (List<Pair<String, String>> listOfPairs) {
        // Number of pairs where the key and value values are the same (aka the individual was put in the probably correct group)
        int counter = 0;

        // Iterate through the list of pairs
        for (Pair<String, String> currentPair : listOfPairs) {
            // Count the number of pairs where the key and value pairs are the same
            if(currentPair.getKey().equals(currentPair.getValue()))
                counter++;      // Increase counter
        }

        // Compute and return efficiency (aka percentage of individuals put in the correct group)
        return (counter * 100.0) / listOfPairs.size();
    }

    /**
     * Evaluates methods based on some files with information about the assignment of individuals in groups
     * @param folder Directory in which .csv files exist. Those files contain the assigned and actual group values for every individual
     *               being examined - absolute path
     */
    public static void computeEvaluationForAllValues (File folder, String outFile) {
        // Array with the contents of the input directory
        File[] listOfFiles = folder.listFiles();

        File outputFile = new File(outFile);       // Handle to output file

        // Create buffered writer
        BufferedWriter writer = null;
        try {
            // Connecting writer to output file, using an appending option (no data overriding)
            writer = new BufferedWriter(new FileWriter(outputFile, true));
            // Iterating through the files contained in the input directory
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    // Write the name of the file and the efficiency of the assigning method in file
                    writer.write(listOfFiles[i].getName() + "\t" + computeEvaluation(lineListToListOfPairs(getRawLinesFromFile(folder.getAbsolutePath() + "\\" + listOfFiles[i].getName()))));
                    // Add a new line to the file, for the next entry to be written on next line
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Writer appending failed");
            e.printStackTrace();
        }
        // Closing buffered writer
        finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.println("Writer close problem");
                e.printStackTrace();
            }
        }
    }

    /**
     * Computes the evaluation of each method for different number of SNPs
     * @param folder directory containing subdirectories for each method. Each subdirectory contains csv files with the assignment results
     *               for the 10, 20,..., 100 first SNPs
     */
    public static void computeEvaluationPerMethod(File folder) {
        File[] listOfFiles = folder.listFiles();        // Files contained in initial folder

        // Iterate through files/folders in the folder
        for(File file : listOfFiles){
            // Compute the evaluation for every subfolder -- every method
            // Create an output file with the efficiency of each method for the different number of SNPs in a different file per method
            computeEvaluationForAllValues(file.getAbsoluteFile(), ".\\output\\" + file.getName() + "Result.txt");
        }
    }
}
