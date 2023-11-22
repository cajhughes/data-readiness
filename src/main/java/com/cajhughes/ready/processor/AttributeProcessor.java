package com.cajhughes.ready.processor;

import com.cajhughes.ready.model.Options;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class AttributeProcessor extends AbstractProcessor<String> {
    private Hashtable<Integer, String> numberToName = new Hashtable<Integer, String>();

    public AttributeProcessor(final Options options) {
        super(options);
        results = new HashMap<String, Set<String>>();
    }

    @Override
    protected void processHeader(final String line) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String token = tokens[i];
                numberToName.put(i, token);
                results.put(token, new HashSet<String>());
            }
        }
    }

    @Override
    protected void processLine(final String line, final int lineNumber) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String name = numberToName.get(i);
                Set<String> set = results.get(name);
                if(set != null) {
                    set.add(tokens[i]);
                }
                results.put(name, set);
            }
        }
    }
}
