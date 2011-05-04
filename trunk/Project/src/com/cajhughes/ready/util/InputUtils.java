package com.cajhughes.ready.util;

import com.cajhughes.ready.model.Options;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class InputUtils {
    public static boolean fileHeadersMatch(final File[] files) throws IOException {
        boolean match = false;
        String header = null;
        for(File file: files) {
            if(header == null) {
                match = true;
                header = InputUtils.getHeader(file);
            }
            else {
                match = header.equals(InputUtils.getHeader(file));
            }
            if(!match) {
                break;
            }
        }
        return match;
    }

    public static String getDelimiter(final String header) {
        String delimiter = null;
        if(header != null && !header.equals("")) {
            String[] commaTokens = header.split("\\,");
            String[] pipeTokens = header.split("\\|");
            if(commaTokens.length >= pipeTokens.length) {
                delimiter = ",";
            }
            else {
                delimiter = "|";
            }
        }
        return delimiter;
    }

    public static String getHeader(final File file) throws IOException {
        String result = null;
        LineIterator iterator = null;
        try {
            iterator = FileUtils.lineIterator(file);
            result = iterator.nextLine();
        }
        finally {
            LineIterator.closeQuietly(iterator);
        }
        return result;
    }

    public static String[] getHeaderColumns(final Options options) throws IOException {
        String[] columnsOnly = getHeaderColumnsOnly(options);
        String[] result = new String[columnsOnly.length+1];
        result[0] = "";
        System.arraycopy(columnsOnly, 0, result, 1, columnsOnly.length);
        return result;
    }

    public static String[] getHeaderColumnsOnly(final Options options) throws IOException {
        String[] result = null;
        File[] files = options.getFiles();
        if(files != null && files.length > 0) {
            String header = getHeader(files[0]);
            String[] tokens = header.split("\\" + options.getDelimiter());
            int size = tokens.length;
            result = new String[size];
            for(int i=0; i<size; i++) {
                result[i] = tokens[i];
            }
        }
        return result;
    }
}
