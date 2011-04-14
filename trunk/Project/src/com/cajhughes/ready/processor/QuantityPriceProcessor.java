package com.cajhughes.ready.processor;

import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.model.QuantityPriceResult;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class QuantityPriceProcessor extends SwingWorker<QuantityPriceResult[], Integer> {
    private Options options = null;
    private QuantityPriceResult[] results = null;
    private int price1Index = -1;
    private int price2Index = -1;
    private int price3Index = -1;
    private int quantityIndex;

    public QuantityPriceProcessor(final Options options, final int priceCount) {
        this.options = options;
        results = new QuantityPriceResult[priceCount];
        for(int i=0; i<priceCount; i++) {
            results[i] = new QuantityPriceResult();
        }
    }

    @Override
    public QuantityPriceResult[] doInBackground() throws IOException {
        File[] files = options.getFiles();
        for(File file: files) {
            int lineCounter = 0;
            LineIterator iterator = FileUtils.lineIterator(file);
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
        }
        QuantityPriceResult[] copy = new QuantityPriceResult[results.length];
        System.arraycopy(results, 0, copy, 0, results.length);
        return copy;        
    }

    private void processHeader(final String line) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String token = tokens[i];
                if(token.equals(options.getQuantityColumn())) {
                    quantityIndex = i;
                }
                else if(token.equals(options.getPrice1Column())) {
                    price1Index = i;
                }
                else if(token.equals(options.getPrice2Column())) {
                    price2Index = i;
                }
                else if(token.equals(options.getPrice3Column())) {
                    price3Index = i;
                }
            }
        }
    }

    private void processLine(final String line) {
        String quantity = null;
        String price1 = null;
        String price2 = null;
        String price3 = null;
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String token = tokens[i];
                if(quantityIndex == i) {
                    quantity = token;
                }
                else if(price1Index == i) {
                    price1 = token;
                }
                else if(price2Index == i) {
                    price2 = token;
                }
                else if(price3Index == i) {
                    price3 = token;
                }
                if(i >= quantityIndex && i >= price1Index && i >= price2Index && i >= price3Index) {
                    break;
                }
            }
            results[0].process(quantity, price1);
            if(price2Index != -1) {
                results[1].process(quantity, price2);
            }
            if(price3Index != -1) {
                results[2].process(quantity, price3);
            }
        }
    }
}
