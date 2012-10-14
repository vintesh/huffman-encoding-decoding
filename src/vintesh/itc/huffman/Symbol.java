/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.itc.huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author me12co18
 */
public class Symbol implements SymbolTemplate {

    private double probablityOfOccurance;
    private String identifier;
    private String code = "";

    public Symbol(double probablityOfOccurance, String identifier) {
        this.probablityOfOccurance = probablityOfOccurance;
        this.identifier = identifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return probablityOfOccurance;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int compareTo(SymbolTemplate o) {
        return this.getValue() > o.getValue() ? 1 : -1;
    }

    public final static String symbolListToString(ArrayList<Symbol> symbols) {
        String str = "";
        for (Symbol symbol : symbols) {
            str += symbol.getIdentifier() + ":" + symbol.getCode() + ",";
        }
        return str;
    }

    /*
     * Returns the Map of <Symbols,Code> sytle from the ArrayList<Symbols>
     */
    public final static Map<String, String> getMapOfCodes(ArrayList<Symbol> symbols) {
        Map<String, String> map = new HashMap<String, String>();
        for (Symbol symbol : symbols) {
            map.put(symbol.getIdentifier(), symbol.getCode());
        }
        return map;
    }
}
