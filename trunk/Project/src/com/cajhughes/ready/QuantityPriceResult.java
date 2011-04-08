package com.cajhughes.ready;

import java.math.BigDecimal;

public class QuantityPriceResult {
    private static final String QUOTE = "\"";

    private int positiveQuantityPositivePrice = 0;
    private int positiveQuantityZeroPrice = 0;
    private int positiveQuantityNegativePrice = 0;

    private int zeroQuantityPositivePrice = 0;
    private int zeroQuantityZeroPrice = 0;
    private int zeroQuantityNegativePrice = 0;

    private int negativeQuantityPositivePrice = 0;
    private int negativeQuantityZeroPrice = 0;
    private int negativeQuantityNegativePrice = 0;

    private int quantityNotNumber = 0;
    private int priceNotNumber = 0;

    public int getNegQtyPosPrice() {
        return negativeQuantityPositivePrice;
    }

    public int getNegQtyZeroPrice() {
        return negativeQuantityZeroPrice;
    }

    public int getNegQtyNegPrice() {
        return negativeQuantityNegativePrice;
    }

    public int getPosQtyPosPrice() {
        return positiveQuantityPositivePrice;
    }

    public int getPosQtyZeroPrice() {
        return positiveQuantityZeroPrice;
    }

    public int getPosQtyNegPrice() {
        return positiveQuantityNegativePrice;
    }

    public int getPriceNotNumber() {
        return priceNotNumber;
    }

    public int getQtyNotNumber() {
        return quantityNotNumber;
    }

    public int getZeroQtyPosPrice() {
        return zeroQuantityPositivePrice;
    }

    public int getZeroQtyZeroPrice() {
        return zeroQuantityZeroPrice;
    }

    public int getZeroQtyNegPrice() {
        return zeroQuantityNegativePrice;
    }

    public void process(final String quantity, final String price) {
        BigDecimal qty;
        BigDecimal money;
        try {
            qty = getBigDecimal(removeQuotes(quantity));
            try {
                money = getBigDecimal(removeQuotes(price));
                setResult(qty, money);
            }
            catch(NumberFormatException nfe) {
                priceNotNumber++;
            }
        }
        catch(NumberFormatException nfe) {
            quantityNotNumber++;
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

    private void setResult(final BigDecimal quantity, final BigDecimal price) {
        int quantityCompare = quantity.compareTo(BigDecimal.ZERO);
        int priceCompare = price.compareTo(BigDecimal.ZERO);
        if(quantityCompare > 0) {
            if(priceCompare == 1) {
                positiveQuantityPositivePrice++;                
            }
            else if(priceCompare == 0) {
                positiveQuantityZeroPrice++;
            }
            else {
                positiveQuantityNegativePrice++;
            }
        }
        else if(quantityCompare == 0) {            
            if(priceCompare == 1) {
                zeroQuantityPositivePrice++;                
            }
            else if(priceCompare == 0) {
                zeroQuantityZeroPrice++;
            }
            else {
                zeroQuantityNegativePrice++;
            }
        }
        else {
            if(priceCompare == 1) {
                negativeQuantityPositivePrice++;                
            }
            else if(priceCompare == 0) {
                negativeQuantityZeroPrice++;
            }
            else {
                negativeQuantityNegativePrice++;
            }
        }
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
}
