package com.cajhughes.ready.model;

import java.io.File;

public class Options {
    private File file = null;
    private File[] files = null;
    private String delimiter = null;
    private String quantityColumn = null;
    private String priceColumn = null;
    private String validationError = null;

    public Options(final File[] files, final String delimiter) {
        this.files = files;
        this.delimiter = delimiter;
    }

    public Options(final File file, final String delimiter, final String quantity, final String price) {
        this.file = file;
        this.delimiter = delimiter;
        this.quantityColumn = quantity;
        this.priceColumn = price;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public File getFile() {
        File one = null;
        if(file != null) {
            one = file;
        }
        else if(files != null) {
            one = files[0];
        }
        return one;
    }

    public File[] getFiles() {
        File[] more = null;
        if(files != null) {
            more = files;
        }
        else if(file != null) {
            more = new File[] {file};
        }
        return more;
    }

    public String getPriceColumn() {
        return priceColumn;
    }

    public String getQuantityColumn() {
        return quantityColumn;
    }

    public String getValidationError() {
        return validationError;
    }

    public boolean isAttributeValid() {
        boolean result = true;
        if(files == null || files.length == 0) {
            result = false;
            validationError = "Please specify one, or more, files to be processed";
        }
        else {
            for(File file: files) {
                if(!file.exists()) {
                    result = false;
                    validationError = "File, " + file.getAbsolutePath() + ", does not exist - please select another";
                    break;
                }
            }
            if(result) {
                if(delimiter == null || delimiter.equals("")) {
                    result = false;
                    validationError = "Please specify a Delimiter";
                }
            }
        }
        return result;
    }

    public boolean isReadyValid() {
        boolean result = true;
        if(file == null) {
            result = false;
            validationError = "Please specify a file to be processed";
        }
        else {
            if(!file.exists()) {
                result = false;
                validationError = "File, " + file.getAbsolutePath() + ", does not exist - please select another";
            }
            if(result) {
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
                        if(priceColumn == null || priceColumn.equals("")) {
                            result = false;
                            validationError = "Please specify which contains Price data";
                        }
                    }
                }
            }
        }
        return result;
    }
}
