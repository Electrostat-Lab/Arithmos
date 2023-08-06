package com.arithmos.util;

/**
 * Houses multiple hashing algorithms out-of-the-box.
 * 
 * @author pavl_g
 */
public final class HashingUtils {

    private HashingUtils() {
    }

    /**
     * Moves a number of the MSB [nBits] to the LSB.
     * 
     * @param code a hash code to perform shifting on
     * @param nBits the number of MSBs (most significant bits) to move to the LSB location
     * @return the new hashcode with the re-location applied
     */
    public static int shiftToLSB(int code, int nBits) {
        return (code << nBits) /* 1) shifts the code to the right by no. of bits 
                                 creating some new ZERO bits at the Least significant location */ 
                    | (code >>> (Integer.SIZE - nBits)) /* 2) Moves the MSBs to the LSBs location. 3) ORs the 2 components. */;
    }


    /**
     * Moves a number of the LSB [nBits] to the MSB.
     * 
     * @param code a hash code to perform shifting on
     * @param nBits the number of LSBs (least significant bits) to move to the MSB location
     * @return the new hashcode with the re-location applied
     */
    public static int shiftToMSB(int code, int nBits) {
        return (code >> nBits) /* 1) shifts the code to the left by no. of bits 
                                 creating some new ZERO bits at the Most significant location */ 
                    | (code << (Integer.SIZE - nBits)) /* 2) Moves the LSBs to the MSBs location. 3) ORs the 2 components. */;
    }

    /**
     * Spreads the most significant 16-bits in a hashcode into
     * the least significant locations by (1) shifting the [code]
     * by 16-bits to the right and (2) XORing it with the original value.
     *
     * @param code the original hashcode
     * @return a new code with the MSBs spread into the least significant locations
     */
    public static int spreadMSBtoLSB(int code) {
        return code ^ (code >>> (Integer.SIZE / 2));
    }

    /**
     * Spreads the least significant 16-bits in a hashcode into
     * the most significant locations by (1) shifting the [code]
     * by 16-bits to the left and (2) XORing it with the original value.
     *
     * @param code the original hashcode
     * @return a new code with the LSBs spread into the most significant locations
     */
    public static int spreadLSBtoMSB(int code) {
        return code ^ (code << (Integer.SIZE / 2));
    }

    /**
     * Compresses the hashcode to the size using a modular function.
     * 
     * @param code the hashcode to compress
     * @param size the compression value
     * @return a new compressed version of the input hashcode
     */
    public static int modCompress(int code, int size) {
        return code % size;
    }

    /**
     * Compresses the hashcode to the size using an AND function.
     * 
     * @param code the hashcode to compress
     * @param size the compression value
     * @return a new compressed version of the input hashcode
     */
    public static int andCompress(int code, int size) {
        return code & (size - 1);
    }
}
