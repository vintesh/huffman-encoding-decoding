/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.itc.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vintesh
 */
public class FileCompressor {

    private static final String ContentDivider = "|";
    private static final Logger logger = Logger.getLogger(FileCompressor.class.getName());

    private static class SymbolFrequencyMap {

        private static ArrayList<SymbolFrequencyMap> table = new ArrayList<SymbolFrequencyMap>();
        private char symbol;
        private int frequency;

        public SymbolFrequencyMap(char symbol, int frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        public SymbolFrequencyMap() {
        }

        public int getFrequency() {
            return frequency;
        }

        public char getSymbol() {
            return symbol;
        }

        public static boolean isSymbolPresent(char symbol) {
            for (SymbolFrequencyMap obj : table) {
                if (obj.getSymbol() == symbol) {
                    return true;
                }
            }
            return false;
        }

        private static void incrementFrequency(char c) {
            for (SymbolFrequencyMap obj : table) {
                if (obj.getSymbol() == c) {
                    obj.frequency++;
                    return;
                }
            }
        }

        public static void addSymbol(SymbolFrequencyMap object) {
            table.add(object);
        }

        public static void drawCurrentMap() {
            System.out.println("Symbol|Frequency");
            for (SymbolFrequencyMap obj : table) {
                System.out.println("" + obj.getSymbol() + "\t" + obj.getFrequency());
            }
        }

        public static ArrayList<Symbol> getSymbolsAsArrayList() {
            ArrayList<Symbol> symbolList = new ArrayList<Symbol>();
            int totalFreq = 0;
            for (SymbolFrequencyMap obj : table) {
                totalFreq += obj.getFrequency();
            }
//            logger.info("Getting the Total Frequencies: " + totalFreq);
            for (SymbolFrequencyMap obj : table) {
//                logger.info("Adding the Value: " + (double) (obj.getFrequency() / (double) totalFreq) + " & Symbol: " + obj.getSymbol());
                symbolList.add(new Symbol((double) obj.getFrequency() / (double) totalFreq, String.valueOf(obj.getSymbol())));

            }
            return symbolList;
        }
    }

    /*
     * Calculating the frequencies of the Symbols & Parsing the file
     */
    private void calculateFrequencies(String sourceFileName) {
        try {
            File sourceFile = new File(sourceFileName);
            byte inputFileAsBytes[] = new byte[(int) sourceFile.length()];
            FileInputStream fis = new FileInputStream(sourceFile);
            fis.read(inputFileAsBytes);

            System.out.println("\nFile Content:");
            for (int i = 0; i < inputFileAsBytes.length; i++) {
                System.out.print((char) inputFileAsBytes[i]);
                if (SymbolFrequencyMap.isSymbolPresent((char) inputFileAsBytes[i])) {
                    SymbolFrequencyMap.incrementFrequency((char) inputFileAsBytes[i]);
                } else {
                    SymbolFrequencyMap.addSymbol(new SymbolFrequencyMap((char) inputFileAsBytes[i], 1));
                }
            }
            System.out.println("\n-----------------");
//            SymbolFrequencyMap.drawCurrentMap();
        } catch (IOException ex) {
            logger.log(Level.WARNING, ex.toString());
            ex.printStackTrace();
        }
    }

    public void compressFile(String sourceFileName, String destinationFileName) {
        calculateFrequencies(sourceFileName);
        ArrayList<Symbol> symbols = SymbolFrequencyMap.getSymbolsAsArrayList();

//        ======================= Logging for checking
//        System.out.println("Symbols|Probablity");
//        for (Symbol symbol : symbols) {
//            System.out.println(symbol.getIdentifier() + "\t" + symbol.getValue());
//        }

        CodeGenerator calc = new CodeGenerator();
        calc.calculateSymbols(symbols);
        Symbol.symbolListToString(symbols);

        // Adding the SYMBOL TABLE & encoding the FILECONTENT
        encodeAndWriteFileContent(symbols, sourceFileName, destinationFileName);
    }

    private void encodeAndWriteFileContent(ArrayList<Symbol> symbols, String inputFileName, String outputFileName) {
        try {
            // Making the output file & writing the symbolTable in that.
            File outputFile = new File(outputFileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(Symbol.symbolListToString(symbols).getBytes());

            // Reading the inputFile & encoding part.
            File sourceFile = new File(inputFileName);
            byte inputFileAsBytes[] = new byte[(int) sourceFile.length()];
            FileInputStream fis = new FileInputStream(sourceFile);
            fis.read(inputFileAsBytes);

            // Write some Content to distinguous between symbolTable & file content
            fos.write(ContentDivider.getBytes());

            // Getting the codes & symbols as map from the Symbol Class
            Map<String, String> map = Symbol.getMapOfCodes(symbols);

            byte encodedContent[][] = new byte[inputFileAsBytes.length][];

            for (int i = 0; i < inputFileAsBytes.length; i++) {
                try {
                    byte[] byteChunk = map.get(String.valueOf((char) inputFileAsBytes[i])).getBytes();
                    encodedContent[i] = new byte[byteChunk.length];
                    encodedContent[i] = byteChunk;
                } catch (NullPointerException npe) {
                    logger.warning("Symbol cann't be found in map \nCheck for Symbol: " + inputFileAsBytes[i]);
                }
            }

            // TO Allocate memory as need only ... more will lead to add extra spaces in file
            int totalBytes = 0;
            for (int i = 0; i < encodedContent.length; i++) {
                totalBytes += encodedContent[i].length;
            }
            byte contentToWrite[] = new byte[totalBytes];
            int lastLength = 0, l = 0;


            // Appending all the array to one.
            for (int i = 0; i < encodedContent.length; i++) {
//                System.arraycopy(encodedContent, 0, contentToWrite, lastLength, encodedContent[i].length);
                for (int j = 0; j < encodedContent[i].length; j++) {
                    contentToWrite[l++] = encodedContent[i][j];
                }
//                lastLength += encodedContent[i].length;
            }

            // Writing the final content in to the file
            fos.write(contentToWrite);
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString());
            ex.printStackTrace();
        }
    }
}
