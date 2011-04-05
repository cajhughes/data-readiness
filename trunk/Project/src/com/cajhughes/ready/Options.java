package com.cajhughes.ready;

import java.io.File;

public class Options {
    private File file = null;
    private String delimiter = null;
    private String quantityColumn = null;
    private String price1Column = null;
    private String price2Column = null;
    private String price3Column = null;
    private String validationError = null;

    public Options(final File file, final String delimiter, final String quantity,
                   final String price1, final String price2, final String price3) {
        this.file = file;
        this.delimiter = delimiter;
        this.quantityColumn = quantity;
        this.price1Column = price1;
        this.price2Column = price2;
        this.price3Column = price3;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public File getFile() {
        return file;
    }

    public String getPrice1Column() {
        return price1Column;
    }

    public String getPrice2Column() {
        return price2Column;
    }

    public String getPrice3Column() {
        return price3Column;
    }

    public String getQuantityColumn() {
        return quantityColumn;
    }

    public String getValidationError() {
        return validationError;
    }

    public boolean valid() {
        boolean result = true;
        if(file == null || !file.exists()) {
            result = false;
            validationError = "Please specify the file to be processed";
        }
        else {
            if(!file.exists()) {
                result = false;
                validationError = "File, " + file.getAbsolutePath() + ", does not exist - please select another";
            }
            else {
                if(delimiter == null || delimiter.equals("")) {
                    result = false;
                    validationError = "Please specify a Delimiter";
                }
                else {
                    if(quantityColumn == null || quantityColumn.equals("")) {
                        result = false;
                        validationError = "Please specify which column contains Quantity data";
                    }
                    else {
                        if(price1Column == null || price1Column.equals("")) {
                            result = false;
                            validationError = "Please specify at least one column which contains Price data";
                        }
                    }
                }
            }
        }
        return result;
    }
}
