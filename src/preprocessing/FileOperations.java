package preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Katerina Intzevidou on 10-May-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class FileOperations {
    String inputFilename;
    String outputFilename;

    public FileOperations(String inputFilename, String outputFilename) {
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
    }

    public List<String> ReadFromFile() {
        // List to save the file contents
        List<String> fileLinesContents = new ArrayList<>();

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
            // While there still are lines to read
            while (line != null) {
                // Add line into the string array
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
        return fileLinesContents;
    }


    /**
     * Turns the list with the raw data of the input file (String) into SNP format (Check SNP doc)
     * @return List of SNPs
     */
    private List<SNP> ListToSNP() {
        // List to be transformed -- Contains raw input data
        List<String> fileLinesArrayList = new ArrayList<>(this.ReadFromFile());

        // Remove 11 first items from the list -- Useless text
        for (int i = 0; i < 11; ++i) {
            // Always removing the first item, since after every removal the contents are shifted one space back
            fileLinesArrayList.remove(0);
        }

        // Create SNP List
        List<SNP> SNPList = new ArrayList<>();

        // Iterating through each list string
        for (String currentLine : fileLinesArrayList) {
            // SNP that will be created by the current line of input
            SNP currentSNP = new SNP();

            // Tokenize current line using \t s
            StringTokenizer st = new StringTokenizer(currentLine, "\t");
            // While the line has still tokens
            while (st.hasMoreTokens()) {
                // Add rank, SNPid and Score values to the current SNP object
                currentSNP.setRank(Double.parseDouble(st.nextToken()));     // String to Double
                currentSNP.setSNPid(st.nextToken().trim());     // trim() to eliminate spaces
                currentSNP.setScore(Double.parseDouble(st.nextToken()));    // String to Double
                // Number of genotyped SNPs -- thrown away
                st.nextToken();

                // Add current SNP in SNP list
                SNPList.add(currentSNP);
            }
        }
        return SNPList;
    }

    // Function that takes the list of SNPs as input, and a parameter (indicating which of the elements of the SNP will be used at the output)
    // and returns a strusture with the ranking in form (SNPid, rank/score/etc)

    // Use in WriteToFile as well

}
