package com.cajhughes.ready.util;

import com.cajhughes.ready.Options;
import java.io.IOException;
import java.util.StringTokenizer;
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
            StringTokenizer tokenizer = new StringTokenizer(header, options.getDelimiter());
            int tokenCount = tokenizer.countTokens();
            result = new String[tokenCount];
            for(int i=0; i<tokenCount; i++) {
                result[i] = tokenizer.nextToken();
            }
        }
        finally {
            LineIterator.closeQuietly(iterator);
        }
        return result;
    }
}
