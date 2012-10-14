/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.itc.huffman;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author me12co18
 */
public class CodeGenerator {

    public void calculateSymbols(ArrayList<Symbol> symbols) {
        Queue<SymbolTemplate> symbolQueue = new PriorityQueue<SymbolTemplate>();
        for (Symbol symbol : symbols) {
            if (!(symbolQueue.add(symbol))) {
                throw new IllegalStateException("Symbol is not added to List. Check the data.");
            }
        }

        while (symbolQueue.size() > 2) {
            SymbolTemplate node1 = symbolQueue.remove();
            SymbolTemplate node2 = symbolQueue.remove();
            SymbolCollection newNode = new SymbolCollection("X", node1.getValue() + node2.getValue());
            appendCodeValuesToSymbol(node1, 1);
            appendCodeValuesToSymbol(node2, 0);
            if (node1 instanceof Symbol) {
                newNode.addSymbolToCollection((Symbol) node1);
            } else if (node1 instanceof SymbolCollection) {
                SymbolCollection collection = (SymbolCollection) node1;
                for (Symbol obj : collection.getSymbolCollection()) {
                    newNode.addSymbolToCollection(obj);
                }
            }
            if (node2 instanceof Symbol) {
                newNode.addSymbolToCollection((Symbol) node2);
            } else if (node2 instanceof SymbolCollection) {
                SymbolCollection collection = (SymbolCollection) node2;
                for (Symbol obj : collection.getSymbolCollection()) {
                    newNode.addSymbolToCollection(obj);
                }
            }
            symbolQueue.add(newNode);
        }

        appendCodeValuesToSymbol(symbolQueue.remove(), 1);
        appendCodeValuesToSymbol(symbolQueue.remove(), 0);
    }

    private void appendCodeValuesToSymbol(SymbolTemplate node1, int i) {
        if (node1 instanceof Symbol) {
            Symbol symbol = (Symbol) node1;
            symbol.setCode(String.valueOf(i) + symbol.getCode());
        } else if (node1 instanceof SymbolCollection) {
            SymbolCollection collection = (SymbolCollection) node1;
            for (Symbol obj : collection.getSymbolCollection()) {
                obj.setCode(String.valueOf(i) + obj.getCode());
            }
        }
    }
}
