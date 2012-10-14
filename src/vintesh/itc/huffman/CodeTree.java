/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.itc.huffman;

import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
@Deprecated
public class CodeTree {

    public static class Node {

        public static final Node ROOT = new Node("", null, null, null, readData.Zero, readData.One);

        public static enum readData {

            Zero, One;
        }
        private String symbolString;
        private Node parent;
        private Node left;
        private Node right;
        private readData readLeft;
        private readData readRight;

        public Node(String symbolCode, Node parent, Node left, Node right, readData readLeft, readData readRight) {
            this.symbolString = symbolCode;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.readLeft = readLeft;
            this.readRight = readRight;
        }
    }

    public static Node makeCodeTree(ArrayList<Symbol> symbols) {
        for (Symbol symbol : symbols) {
            String code = symbol.getCode();
            Node nodePointer = Node.ROOT;
            int i = 0;
            while (true) {

                code.charAt(i);
                if (nodePointer == null) {
                    if (nodePointer.left == null && nodePointer.right == null) {
                        nodePointer.left = new Node("", nodePointer, null, null, Node.readData.Zero, Node.readData.One);
                        nodePointer.right = new Node("", nodePointer, null, null, Node.readData.Zero, Node.readData.One);
                    }
                    if (code.charAt(i) == 0) {
                        nodePointer = nodePointer.left;
                    } else {
                        nodePointer = nodePointer.right;
                    }
                }
                i++;
                if (i == code.length() - 1) {
                    nodePointer.symbolString = symbol.getIdentifier();
                    break;
                }
            }
        }
        return Node.ROOT;
    }

    public static String getCodeOfSymbol(Node nodePointer, String symbolString, String code) {
        if (nodePointer != null) {
            try {
                if (nodePointer.symbolString.equals(symbolString)) {
                    return code;
                } else {
                    code = code.substring(0, code.length() - 2);
                }

                code += "0";
                getCodeOfSymbol(nodePointer.left, symbolString, code);

                code += "1";
                getCodeOfSymbol(nodePointer.right, symbolString, code);

                return "NOT FOUND";
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                System.out.println(ex.toString());
            }
        }
        return null;
    }
}