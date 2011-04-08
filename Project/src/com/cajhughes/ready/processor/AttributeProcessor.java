package com.cajhughes.ready.processor;

import com.cajhughes.ready.Options;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class AttributeProcessor extends SwingWorker<Map<Integer, Set<String>>, Integer> {
    private Options options = null;
    private HashMap<Integer, Set<String>> results = null;
    private int lineCounter;

    public AttributeProcessor(final Options options) {
        lineCounter = 0;
        this.options = options;
        results = new HashMap<Integer, Set<String>>();
    }

    public Map<Integer, Set<String>> doInBackground() throws IOException {
        LineIterator iterator = FileUtils.lineIterator(options.getFile());
        try {
            while(!isCancelled() && iterator.hasNext()) {
                lineCounter++;
                String line = iterator.nextLine();
                if(lineCounter == 1) {
                    processHeader(line);
                }
                else {
                    processLine(line);
                }
                if((lineCounter % 1000) == 0) {
                    publish(lineCounter);
                }
            }
        }
        finally {
            LineIterator.closeQuietly(iterator);
        }
        return results;
    }

    private void processHeader(final String line) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                results.put(i, new HashSet<String>());
            }
        }
    }

    private void processLine(final String line) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                Set<String> set = results.get(i);
                if(set != null) {
                    set.add(tokens[i]);
                }
                results.put(i, set);
            }
        }
    }
}
