/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Information Theory & Coding
 * SCET, Surat
 */
package vintesh.itc.huffman;

import java.util.Arrays;

/**
 *
 * @author Vintesh
 */
public final class ByteUtils {

    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    public static byte[] fromBinary(String s) {
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for (int i = 0; i < sLen; i++) {
            if ((c = s.charAt(i)) == '1') {
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            } else if (c != '0') {
                throw new IllegalArgumentException();
            }
        }
        return toReturn;
    }

    /*
     * For testing the above methods.
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(fromBinary("0111110001111100")));
        byte test[] = new byte[1];
        test[0] = 124;
        System.out.println("toBinary: " + toBinary(test));
    }
}
