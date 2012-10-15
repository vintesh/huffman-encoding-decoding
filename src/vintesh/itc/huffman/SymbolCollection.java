/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Information Theory & Coding
 * SCET, Surat
 */
package vintesh.itc.huffman;

import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
public class SymbolCollection implements SymbolTemplate {

    private String identifier;
    private ArrayList<Symbol> symbolCollection;
    private double value;

    public SymbolCollection(String identifier, double value) {
        this.identifier = identifier;
        this.value = value;
        symbolCollection = new ArrayList<Symbol>();
    }

    public boolean addSymbolToCollection(Symbol symbol) {
        return symbolCollection.add(symbol);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ArrayList<Symbol> getSymbolCollection() {
        return symbolCollection;
    }

    public int compareTo(SymbolTemplate o) {
        return this.getValue() > o.getValue() ? 1 : -1;
    }
}
