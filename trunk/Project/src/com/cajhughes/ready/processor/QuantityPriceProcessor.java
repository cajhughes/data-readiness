package com.cajhughes.ready.processor;

import com.cajhughes.ready.model.Options;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class QuantityPriceProcessor extends AbstractProcessor<Integer> {
    private static final String QUOTE = "\"";
    private int priceIndex = -1;
    private int quantityIndex;

    public QuantityPriceProcessor(final Options options) {
        super(options);
        results = new HashMap<String, Set<Integer>>();
    }

    private BigDecimal getBigDecimal(final String decimal) throws NumberFormatException {
        BigDecimal value;
        if(decimal != null) {
            value = new BigDecimal(decimal);
        }
        else {
            throw new NumberFormatException();
        }
        return value;
    }

    private String getCategory(final String quantity, final String price) {
        BigDecimal qty;
        BigDecimal money;
        String category = null;
        try {
            qty = getBigDecimal(removeQuotes(quantity));
            try {
                money = getBigDecimal(removeQuotes(price));
                int quantityCompare = qty.compareTo(BigDecimal.ZERO);
                int priceCompare = money.compareTo(BigDecimal.ZERO);
                if(quantityCompare > 0) {
                    if(priceCompare > 0) {
                        category = "+ve+ve";
                    }
                    else if(priceCompare == 0) {
                        category = "+ve0";
                    }
                    else {
                        category = "+ve-ve";
                    }
                }
                else if(quantityCompare == 0) {
                    if(priceCompare > 0) {
                        category = "0+ve";
                    }
                    else if(priceCompare == 0) {
                        category = "00";
                    }
                    else {
                        category = "0-ve";
                    }
                }
                else {
                    if(priceCompare > 0) {
                        category = "-ve+ve";
                    }
                    else if(priceCompare == 0) {
                        category = "-ve0";
                    }
                    else {
                        category = "-ve-ve";
                    }
                }
            }
            catch(NumberFormatException nfe) {
                category = "PriceNaN";
            }
        }
        catch(NumberFormatException nfe) {
            category = "QuantityNaN";
        }
        return category;
    }

    @Override
    protected void processHeader(final String line) {
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String token = tokens[i];
                if(token.equals(options.getQuantityColumn())) {
                    quantityIndex = i;
                }
                else if(token.equals(options.getPriceColumn())) {
                    priceIndex = i;
                }
            }
        }
    }

    @Override
    protected void processLine(final String line, final int lineCount) {
        String quantity = null;
        String price = null;
        if(line != null) {
            String[] tokens = line.split("\\" + options.getDelimiter());
            int size = tokens.length;
            for(int i=0; i<size; i++) {
                String token = tokens[i];
                if(quantityIndex == i) {
                    quantity = token;
                }
                else if(priceIndex == i) {
                    price = token;
                }
                if(i >= quantityIndex && i >= priceIndex) {
                    break;
                }
            }
            String category = getCategory(quantity, price);
            Set<Integer> set = results.get(category);
            if(set == null) {
                set = new HashSet<Integer>();
            }
            set.add(Integer.valueOf(lineCount));
            results.put(category, set);
        }
    }

    private String removeQuotes(final String value) {
        String remove = null;
        if (value != null) {
            if(value.startsWith(QUOTE) && value.endsWith(QUOTE)) {
                remove = value.substring(1, value.length()-1);
            }
            else {
                remove = value;
            }
        }
        return remove;
    }
}
