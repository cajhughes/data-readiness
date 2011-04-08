package com.cajhughes.ready.util;

import com.cajhughes.ready.Options;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class InputUtils {
    public static String[] getHeaderColumns(final Options options) throws IOException {
        String[] columnsOnly = getHeaderColumnsOnly(options);
        String[] result = new String[columnsOnly.length+1];
        result[0] = "";
        System.arraycopy(columnsOnly, 0, result, 1, columnsOnly.length);
        return result;
    }

    public static String[] getHeaderColumnsOnly(final Options options) throws IOException {
        String[] result = null;
        LineIterator iterator = null;
        try {
            iterator = FileUtils.lineIterator(options.getFile());
            String header = iterator.nextLine();
            String[] tokens = header.split("\\" + options.getDelimiter());
            int size = tokens.length;
            result = new String[size];
            for(int i=0; i<size; i++) {
                result[i] = tokens[i];
            }
        }
        finally {
            LineIterator.closeQuietly(iterator);
        }
        return result;
    }
}
