/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.itc.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vintesh
 */
public class FileDecompressor {

    private static final String ContentDivider = "|";
    private static final Logger logger = Logger.getLogger(FileDecompressor.class.getName());

    public void decodeFile(String encodedFileName, String decodedFileName) {
        try {
            byte[] encodedFileAsBytes = getFileAsByteArray(encodedFileName);

            // Generating the Symbol & Code ArrayList
            ArrayList<Symbol> symbols = getSymbolTable(encodedFileAsBytes);
            //            // Generating CodeTree
            //            Node codeTree = CodeTree.makeCodeTree(symbols);
            //            // Logging the CodeTree whether it have been generated correctly or not.
            //            String code = "";
            //            CodeTree.getCodeOfSymbol(CodeTree.Node.ROOT, symbols.get(2).getIdentifier(), code);
            //            System.out.println("Symbol: " + symbols.get(2) + " Code_Retrived: " + code);


            // Decoding the content
            int dividerIndex = getDividerIndexInArray(encodedFileAsBytes);

            String message = "";
            for (int j = 0, i = dividerIndex + 1; i < encodedFileAsBytes.length; i++) {
                message += String.valueOf((char) encodedFileAsBytes[i]);
            }

            System.out.println("Encoded Message In File: " + message);
            Symbol.symbolListToString(symbols);

            String decodedMessage = "";
            while (message.length() != 0) {
                System.out.println("");
                System.out.println("Meesage: " + message);
                System.out.println("Message Decoded: " + decodedMessage);

                for (Symbol symbol : symbols) {
                    if (message.startsWith(symbol.getCode())) {
                        decodedMessage += symbol.getIdentifier();
                        message = message.substring(symbol.getCode().length(), message.length());
                        break;
                    }
                }
            }
            System.out.println("Decoded Message: " + decodedMessage);

            // Writing the decoded Message in the Output File
            File file = new File(decodedFileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(decodedMessage.getBytes());

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString());
            ex.printStackTrace();
        }
    }

    private byte[] getFileAsByteArray(String encodedFileName) throws IOException, FileNotFoundException {
        // Reading the encoded file
        File encodedFile = new File(encodedFileName);
        byte encodedFileAsBytes[] = new byte[(int) encodedFile.length()];
        FileInputStream fis = new FileInputStream(encodedFile);
        fis.read(encodedFileAsBytes);
        return encodedFileAsBytes;
    }

    private ArrayList<Symbol> getSymbolTable(byte[] encodedFileAsBytes) {
        // Finding the Divider in the file & separate the SymbolTable
        System.out.println("\nFileContent: ");
        System.out.println(Arrays.toString(encodedFileAsBytes));
        int i = getDividerIndexInArray(encodedFileAsBytes);

        // Reading the String of symbol table from the byteArray
        String symbolTableString = "";
        for (int j = 0; j < i; j++) {
            symbolTableString += String.valueOf((char) encodedFileAsBytes[j]);
        }
        // Generating the ArrayList<Symbol>
        String[] splitSymbolsAndCodes = symbolTableString.split(",");
        ArrayList<Symbol> symbols = new ArrayList<Symbol>();
        for (int j = 0; j < splitSymbolsAndCodes.length; j++) {
            String[] symbolCodePair = splitSymbolsAndCodes[j].split(":");
            Symbol symbol = new Symbol(0, symbolCodePair[0]);
            symbol.setCode(symbolCodePair[1]);
            symbols.add(symbol);
        }
        // Logging the Symbol List
        System.out.println("\nSymbol Table Retrived from the File");
        System.out.println(Symbol.symbolListToString(symbols));
        return symbols;
    }

    private int getDividerIndexInArray(byte[] encodedFileAsBytes) {
        for (int i = 0; i < encodedFileAsBytes.length - 8; i++) {
            if (encodedFileAsBytes[i] == ContentDivider.getBytes()[0]) {
                return i;
            }
        }
        throw new IllegalStateException("SymbolTable cannot be retrived as the Divider is not found");
    }
}
