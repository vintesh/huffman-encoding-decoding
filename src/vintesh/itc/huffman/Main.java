/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Information Theory & Coding
 * SCET, Surat
 */
package vintesh.itc.huffman;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Vintesh
 */
public class Main {

    public static String INPUT_FILE_NAME = "files/inputFile.txt";
    public static String ENCODED_FILE_NAME = "files/outputFile.txt";
    public static String DECODED_FILE_NAME = "files/decodedFile.txt";

    public static void main(String[] args) {
        // Generating the codes for the symbols with probablity given.
        calculateSymbolsCodes();

        // Reading the file & Compression with adding the Source table with the file.
        encodeAndDecodeFile();

        // Logging
        // System.out.println("BYTE: " + Arrays.toString("|".getBytes()));
    }

    private static void calculateSymbolsCodes() {
        ArrayList<Symbol> symbols = new ArrayList<Symbol>();
        symbols.add(new Symbol(0.7, "S1"));
        symbols.add(new Symbol(0.3, "S2"));
        symbols.add(new Symbol(0.25, "S3"));
        symbols.add(new Symbol(0.25, "S4"));
        symbols.add(new Symbol(0.1, "S5"));
        symbols.add(new Symbol(0.05, "S6"));
        symbols.add(new Symbol(0.05, "S7"));

        CodeGenerator calc = new CodeGenerator();
        calc.calculateSymbols(symbols);

        for (Symbol symbol : symbols) {
            System.out.println(symbol.getIdentifier() + ": " + symbol.getCode());
        }
    }

    private static void encodeAndDecodeFile() {
        FileCompressor fileCompressor = new FileCompressor();
        fileCompressor.compressFile(INPUT_FILE_NAME, ENCODED_FILE_NAME);

        FileDecompressor fileDecompressor = new FileDecompressor();
        fileDecompressor.decodeFile(ENCODED_FILE_NAME, DECODED_FILE_NAME);

        JOptionPane.showMessageDialog(null, "Compression Completed check the file with name: "
                + DECODED_FILE_NAME + " in your project Root");

    }
}
