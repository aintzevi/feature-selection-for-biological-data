import preprocessing.*;
import rankAggregationMethods.BordaMethods.*;
import rankAggregationMethods.MarkovChainMethods.*;

import java.io.File;
import java.util.*;


/**
 * Created by Katerina Intzevidou on 24-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class Main {
    public static void main(String[] args) {
        // Object to handle file reading and writing
        FileOperations op = new FileOperations();

        // Map to store the initial output of the pre processing stage (list that contains all initial rankings to be aggregated)
        List<Map<String, Double>> inputListOfMaps = new ArrayList<>();
        // List of the SNPs contained in the initial rankings
        List<SNP> inputList;

        // Object to handle the input directory
        File folder = new File(".\\input\\");
        // Array with the contents of the directory above
        File[] listOfFiles = folder.listFiles();

        // Iterating through the files contained in the input directory
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                op.setInputFilename(".\\input\\" + listOfFiles[i].getName());
                inputList = op.fileLinesListToSNPList();
                inputListOfMaps.add(SNP.formatOutput(inputList,0));
            }
        }

        for (Map<String, Double> currentMap : inputListOfMaps) {
            for(Map.Entry<String, Double> currentEntry: currentMap.entrySet()) {
                System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
            }
        }

        /* ------------------- BORDA METHODS APPLICATION ------------------- */

        BordaMethod borda = new BordaMedian();

        Map<String, Double> outputMap = borda.getBordaMethodRanking(inputListOfMaps);

        // Print here
        System.out.println("Borda Median Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }
        op.setOutputFilename("BordaMedian" + outputMap.size() + "SNPs" + ".txt");
        // Write to file
        op.writeToFile(outputMap);

        borda = new BordaGeometricMean();

        outputMap = borda.getBordaMethodRanking(inputListOfMaps);
        // Print here
        System.out.println("Borda Geometric Mean Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }
        op.setOutputFilename("BordaGeometricMean" + outputMap.size() + "SNPs" + ".txt");

        // Write to file
        op.writeToFile(outputMap);
        borda = new BordaPNorm(0.5);
        outputMap = borda.getBordaMethodRanking(inputListOfMaps);

        // Print here
        System.out.println("Borda P-Norm Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }

        op.setOutputFilename("BordaPNorm" + outputMap.size() + "SNPs" + ".txt");
        // Write to file
        op.writeToFile(outputMap);

        /* ------------------- MARKOV CHAIN METHODS APPLICATION ------------------- */

        MarkovChain mc = new MC1();

        outputMap = mc.getMCMethodRanking(inputListOfMaps, 0.05);

        // Print here
        System.out.println("MC1 Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }
        op.setOutputFilename("MC1M" + outputMap.size() + "SNPs" + ".txt");

        // Write to file
        op.writeToFile(outputMap);

        mc = new MC2();

        outputMap = mc.getMCMethodRanking(inputListOfMaps, 0.05);

        // Print here
        System.out.println("MC2 Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }
        op.setOutputFilename("MC2M" + outputMap.size() + "SNPs" + ".txt");

        // Write to file
        op.writeToFile(outputMap);

        mc = new MC3();

        outputMap = mc.getMCMethodRanking(inputListOfMaps, 0.05);

        // Print here
        System.out.println("MC3 Method");
        for (Map.Entry<String, Double> currentEntry : outputMap.entrySet()) {
            System.out.println(currentEntry.getKey() + ", " + currentEntry.getValue());
        }
        op.setOutputFilename("MC3M" + outputMap.size() + "SNPs" + ".txt");

        // Write to file
        op.writeToFile(outputMap);
    }
}