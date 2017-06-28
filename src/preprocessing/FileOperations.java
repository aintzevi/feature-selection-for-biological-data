package preprocessing;

import java.io.*;
import java.util.*;

/**
 * Created by Katerina Intzevidou on 10-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class FileOperations {
    private String inputFilename;
    private String outputFilename;

    // Class accessors and mutators
    public String getInputFilename() {
        return inputFilename;
    }

    public void setInputFilename(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    // METHODS

    public FileOperations() {
        // Empty Constructor
    }

    /**
     * Constructs a FileOperator object with given input and output filenames
     * @param inputFilename (relative) path to input folder
     * @param outputFilename (relative) path to output file
     */
    public FileOperations(String inputFilename, String outputFilename) {
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
    }

    /**
     * Helper function that reads raw data from a file containing SNP information (like SNP id, score, rank, etc) (File generated by TRES)
     * and puts every line in a list
     * @return List with the raw data as a String
     */
    private List<String> readRawDataFromFile() {
        // List to save the file contents
        List<String> fileLinesContents = new ArrayList<>();
        // String to save each line
        String line;

        // Create a file handle on the filename of the input
        File inputFile = new File(inputFilename);
        // Create buffered reader
        BufferedReader reader = null;

        try {
            // Connect reader with input file
            reader = new BufferedReader(new FileReader(inputFile));

            // Init line variable to space
            line = "";
            // While there still are lines in the file to read
            while (line != null) {
                // Add line into the string array list
                fileLinesContents.add(line);
                // Read the next line, store in variable line
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
        // Return list with the raw file contents
        return fileLinesContents;
    }

    /**
     * Helper function, clears the raw input data, gets rid of extra lines that contain no information
     * @return a clear list of strings containing the SNP information
     */
    private List<String> clearInputList() {
        // List to be transformed -- Contains raw input data
        List<String> fileLinesArrayList = new ArrayList<>(this.readRawDataFromFile());

        // Remove 11 first items from the list -- Useless text
        for (int i = 0; i < 11; ++i) {
            // Always removing the first item, since after every removal the contents are shifted one space back
            fileLinesArrayList.remove(0);
        }
        return fileLinesArrayList;
    }

    /**
     * Reads the raw data from a list of Strings and turns them into SNP format (Check SNP doc)
     * @return List of SNPs
     */
    public List<SNP> fileLinesListToSNPList() {
        // List of clear input data
        List<String> fileLinesArrayList = new ArrayList<>(this.clearInputList());
        // Create SNP List
        List<SNP> SNPList = new ArrayList<>();

        // Iterating through each string
        for (String currentLine : fileLinesArrayList) {
            // SNP that will be created by the current line of input
            SNP currentSNP = new SNP();

            // Tokenize current line using \t s
            StringTokenizer st = new StringTokenizer(currentLine, "\t");
            // While the line still has tokens
            while (st.hasMoreTokens()) {
                // Add rank, SNPid and Score values to the current SNP object
                currentSNP.setRank(Double.parseDouble(st.nextToken()));     // String to Double
                currentSNP.setSNPid(st.nextToken().trim());     // trim() to eliminate spaces
                // Raw score - not normalized
                currentSNP.setScore(Double.parseDouble(st.nextToken()));    // String to Double
                // Number of genotyped SNPs -- thrown away
                st.nextToken();

                // Add current SNP in SNP list
                SNPList.add(currentSNP);
            }
        }
        // Adding the normalized and reversed scores in every SNP of the SNPList
        SNP.normalizeAndReverseSNPScoreValues(SNPList);
        return SNPList;
    }

    /**
     * Writing a ranking list in a file. Format key value, where key is a string and value a double value
     * @param map is the structure containing the key value pairs to be written in file
     */
    public void writeToFile(Map<String, Double> map) {
        // Create file with the specific filename, using relative path, under the directory named "output"
        File outputFile = new File(".\\output\\" + outputFilename);

        // Create buffered writer
        BufferedWriter writer = null;
        try {
            // Connect the writer to the output file
            writer = new BufferedWriter(new FileWriter(outputFile));
            // Iterate through the input map
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                // Write the key - value pair in the file with format: key value
                writer.write(entry.getKey().substring(3) /*+ " " + BigDecimal.valueOf(entry.getValue())
                        .setScale(5, RoundingMode.HALF_UP)      // 5 digit precision at the score value
                        .doubleValue()*/);
                // Add a new line to the file, for the next entry to be written on next line
                writer.newLine();
            }
            writer.newLine();
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
}
