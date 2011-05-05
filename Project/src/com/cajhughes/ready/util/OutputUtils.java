package com.cajhughes.ready.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class OutputUtils {
    public static final String COMMA = ",";
    public static final String CR = "\n";
    public static final String EXTENSION = ".csv";
    public static final String NEGATIVE = "Negative";
    public static final String POSITIVE = "Positive";
    public static final String QUOTE = "\"";
    public static final String ZERO = "Zero";

    public static File getOutputFile(final File file) {
        return getOutputFile(file, null);
    }

    public static File getOutputFile(final File file, final String category) {
        File output = null;
        if(file != null) {
            StringBuilder buffer = new StringBuilder(file.getAbsolutePath());
            if(category != null) {
                buffer.append(".");
                buffer.append(category);
            }
            buffer.append(EXTENSION);
            output = new File(buffer.toString());
            while(output.exists()) {
                buffer.append(EXTENSION);
                output = new File(buffer.toString());
            }
        }
        return output;
    }

    public static File getOutputFile(final File[] files) {
        return getOutputFile(files[0]);
    }

    public static void writeAttributes(final PrintStream stream,
                                       final String[] columnNames,
                                       final Map<String, Set<String>> results) {
        StringBuilder buffer = new StringBuilder();
        int columnNameCount = columnNames.length;
        Object[] resultsArray = new Object[columnNameCount];
        int rowCount = 0;
        int maxRowCount = 0;
        for(int i=0; i<columnNameCount; i++) {
            buffer.append(columnNames[i]);
            Object[] valueArray = results.get(columnNames[i]).toArray();
            int arrayLength = valueArray.length;
            if(maxRowCount < arrayLength) {
                maxRowCount = arrayLength;
            }
            resultsArray[i] = valueArray;
            if(i<columnNameCount-1) {
                buffer.append(COMMA);
            }
        }
        stream.println(buffer.toString());
        while(rowCount < maxRowCount) {
            buffer = new StringBuilder();
            for(int i=0; i<columnNameCount; i++) {
                Object[] columnArray = (Object[])resultsArray[i];
                if(rowCount < columnArray.length) {
                    buffer.append(QUOTE);
                    buffer.append(columnArray[rowCount].toString());
                    buffer.append(QUOTE);
                }
                if(i<columnNameCount-1) {
                    buffer.append(COMMA);
                }
            }
            stream.println(buffer.toString());
            rowCount++;
        }
    }

    public static void writeLines(final PrintStream stream,
                                  final Map<String, Set<Integer>> results,
                                  final String category,
                                  final File incoming) throws IOException {
        Set<Integer> set = results.get(category);
        if(set != null && set.size() > 0) {
            int lineCounter = 0;
            LineIterator iterator = FileUtils.lineIterator(incoming);
            try {
                while(iterator.hasNext()) {
                    lineCounter++;
                    String line = iterator.nextLine();
                    if(lineCounter == 1 || set.contains(Integer.valueOf(lineCounter))) {
                        stream.println(line);
                    }
                }
            }
            finally {
                LineIterator.closeQuietly(iterator);
            }
        }
    }
}
