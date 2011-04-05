package com.cajhughes.ready;

import java.io.File;

public class OutputUtils {
    public static final String COMMA = ",";
    public static final String NEGATIVE = "Negative";
    public static final String POSITIVE = "Positive";
    public static final String ZERO = "Zero";

    public static File getOutputFile(final File file) {
        File output = null;
        if(file != null) {
            String filename = file.getAbsolutePath() + ".csv";
            output = new File(filename);
            while(output.exists()) {
                filename += ".csv";
                output = new File(filename);
            }
        }
        return output;
    }

    public static String toString(final Object quantity, final Object[] price, final Result[] results) {
        StringBuilder buffer = new StringBuilder();
        if(results != null) {
            int count = results.length;
            for(int i=0; i<count; i++) {
                if(price[i] != null && !price[i].equals("")) {
                    buffer.append(COMMA);
                    buffer.append(COMMA);
                    buffer.append(price[i].toString().toUpperCase());
                    buffer.append(COMMA);
                    buffer.append(COMMA);
                    buffer.append("\n");
                    buffer.append(COMMA);
                    buffer.append(COMMA);
                    buffer.append(POSITIVE);
                    buffer.append(COMMA);
                    buffer.append(ZERO);
                    buffer.append(COMMA);
                    buffer.append(NEGATIVE);
                    buffer.append("\n");
                    buffer.append(quantity.toString().toUpperCase());
                    buffer.append(COMMA);
                    buffer.append(POSITIVE);
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyNegPrice());
                    buffer.append("\n");
                    buffer.append(COMMA);
                    buffer.append(ZERO);
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyNegPrice());
                    buffer.append("\n");
                    buffer.append(COMMA);
                    buffer.append(NEGATIVE);
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyNegPrice());
                    buffer.append("\n");
                }
            }
        }
        return buffer.toString();
    }
}
