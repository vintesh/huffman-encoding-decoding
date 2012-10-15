/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Information Theory & Coding
 * SCET, Surat
 */
package vintesh.itc.huffman.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import vintesh.itc.huffman.Symbol;

/**
 *
 * @author Vintesh
 */
public class AlphabatesFreqCounter {

    private static final Logger logger = Logger.getLogger(AlphabatesFreqCounter.class.getName());
    private static byte inputFileAsBytes[];
    private static HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();

    public static ArrayList<Symbol> calculateFreq(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(inputFileAsBytes);
            for (int i = 0; i < inputFileAsBytes.length; i++) {
                // @TODO Have to complete the 
                Character ch = Character.valueOf((char) inputFileAsBytes[i]);
                // System.out.println("65: " + Character.valueOf((char) x));  -- Working.
                Integer value = frequencyMap.remove(ch);
                frequencyMap.put(ch, value + 1);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        calculateFreq(new File("files/inputFile.txt"));
        for (Character character : frequencyMap.keySet()) {
            System.out.println("CHAR: " + character + " FREQ: " + frequencyMap.get(character));
        }
    }
}
