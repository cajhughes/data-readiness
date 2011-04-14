package com.cajhughes.ready.util;

import com.cajhughes.ready.model.QuantityPriceResult;
import java.io.File;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

public class OutputUtils {
    public static final String COMMA = ",";
    public static final String CR = "\n";
    public static final String EXTENSION = ".csv";
    public static final String NEGATIVE = "Negative";
    public static final String POSITIVE = "Positive";
    public static final String QUOTE = "\"";
    public static final String ZERO = "Zero";

    public static File getOutputFile(final File[] files) {
        File output = null;
        if(files != null && files.length > 0) {
            StringBuilder buffer = new StringBuilder(files[0].getAbsolutePath());
            buffer.append(EXTENSION);
            output = new File(buffer.toString());
            while(output.exists()) {
                buffer.append(EXTENSION);
                output = new File(buffer.toString());
            }
        }
        return output;
    }

    public static void write(final PrintStream stream, final Object[] columnNames, final Map<Integer, Set<String>> results) {
        StringBuilder buffer = new StringBuilder();
        int columnNameCount = columnNames.length;
        int mapCount = results.size();
        Object[] resultsArray = new Object[mapCount];
        int rowCount = 0;
        int maxRowCount = 0;
        if(columnNameCount == mapCount) {
            for(int i=0; i<mapCount; i++) {
                buffer.append((String)columnNames[i]);
                Object[] columnArray = results.get(i).toArray();
                int arrayLength = columnArray.length;
                if(maxRowCount < arrayLength) {
                    maxRowCount = arrayLength;
                }
                resultsArray[i] = columnArray;
                if(i<mapCount-1) {
                    buffer.append(COMMA);
                }
            }
            stream.println(buffer.toString());
            while(rowCount < maxRowCount) {
                buffer = new StringBuilder();
                for(int i=0; i<mapCount; i++) {
                    Object[] columnArray = (Object[])resultsArray[i];
                    if(rowCount < columnArray.length) {
                        buffer.append(QUOTE);
                        buffer.append(columnArray[rowCount].toString());
                        buffer.append(QUOTE);
                    }
                    if(i<mapCount-1) {
                        buffer.append(COMMA);
                    }
                }
                stream.println(buffer.toString());
                rowCount++;
            }
        }
    }

    public static String toString(final Object quantity, final Object[] price, final QuantityPriceResult[] results) {
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
                    buffer.append(CR);
                    buffer.append(COMMA);
                    buffer.append(COMMA);
                    buffer.append(POSITIVE);
                    buffer.append(COMMA);
                    buffer.append(ZERO);
                    buffer.append(COMMA);
                    buffer.append(NEGATIVE);
                    buffer.append(CR);
                    buffer.append(quantity.toString().toUpperCase());
                    buffer.append(COMMA);
                    buffer.append(POSITIVE);
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getPosQtyNegPrice());
                    buffer.append(CR);
                    buffer.append(COMMA);
                    buffer.append(ZERO);
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getZeroQtyNegPrice());
                    buffer.append(CR);
                    buffer.append(COMMA);
                    buffer.append(NEGATIVE);
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyPosPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyZeroPrice());
                    buffer.append(COMMA);
                    buffer.append(results[i].getNegQtyNegPrice());
                    buffer.append(CR);
                    if(results[i].getQtyNotNumber() > 0) {
                        buffer.append("Quantity NaN");
                        buffer.append(COMMA);
                        buffer.append(results[i].getQtyNotNumber());
                        buffer.append(CR);
                    }
                    if(results[i].getPriceNotNumber() > 0) {
                        buffer.append("Price NaN");
                        buffer.append(COMMA);
                        buffer.append(results[i].getPriceNotNumber());
                        buffer.append(CR);
                    }
                }
            }
        }
        return buffer.toString();
    }
}
