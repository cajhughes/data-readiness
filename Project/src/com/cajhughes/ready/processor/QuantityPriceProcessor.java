package com.cajhughes.ready.processor;

import com.cajhughes.ready.Options;
import com.cajhughes.ready.QuantityPriceResult;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class QuantityPriceProcessor extends SwingWorker<QuantityPriceResult[], Integer> {
    private Options options = null;
    private QuantityPriceResult[] results = null;
    private int lineCounter;
    private int price1Index = -1;
    private int price2Index = -1;
    private int price3Index = -1;
    private int quantityIndex;

    public QuantityPriceProcessor(final Options options, final int priceCount) {
        lineCounter = 0;
        this.options = options;
        results = new QuantityPriceResult[priceCount];
        for(int i=0; i<priceCount; i++) {
            results[i] = new QuantityPriceResult();
        }
    }

    public QuantityPriceResult[] doInBackground() throws IOException {
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
        QuantityPriceResult[] copy = new QuantityPriceResult[results.length];
        System.arraycopy(results, 0, copy, 0, results.length);
        return copy;        
    }

    private void processHeader(final String line) {
        int count = 0;
        if(line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, options.getDelimiter());
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if(token.equals(options.getQuantityColumn())) {
                    quantityIndex = count;
                }
                else if(token.equals(options.getPrice1Column())) {
                    price1Index = count;
                }
                else if(token.equals(options.getPrice2Column())) {
                    price2Index = count;
                }
                else if(token.equals(options.getPrice3Column())) {
                    price3Index = count;
                }
                count++;
            }
        }
    }

    private void processLine(final String line) {
        int count = 0;
        String quantity = null;
        String price1 = null;
        String price2 = null;
        String price3 = null;
        if(line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, options.getDelimiter());
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if(quantityIndex == count) {
                    quantity = token;
                }
                else if(price1Index == count) {
                    price1 = token;
                }
                else if(price2Index == count) {
                    price2 = token;
                }
                else if(price3Index == count) {
                    price3 = token;
                }
                if(count >= quantityIndex && count >= price1Index && count >= price2Index && count >= price3Index) {
                    break;
                }
                count++;
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
