package com.cajhughes.ready.model;

import com.cajhughes.ready.util.InputUtils;
import java.io.File;

public class Options {
    private File[] files = null;
    private String delimiter = null;
    private String quantityColumn = null;
    private String price1Column = null;
    private String price2Column = null;
    private String price3Column = null;
    private String validationError = null;

    public Options(final File[] files, final String delimiter) {
        this(files, delimiter, null, null, null, null);
    }

    public Options(final File[] files, final String delimiter, final String quantity,
                   final String price1, final String price2, final String price3) {
        this.files = files;
        this.delimiter = delimiter;
        this.quantityColumn = quantity;
        this.price1Column = price1;
        this.price2Column = price2;
        this.price3Column = price3;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public File[] getFiles() {
        return files;
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
                else {
                    if(delimiter == null || delimiter.equals("")) {
                        result = false;
                        validationError = "Please specify a Delimiter";
                        break;
                    }
                }
            }
        }
        return result;
    }

    public boolean isReadyValid() {
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
                else {
                    if(delimiter == null || delimiter.equals("")) {
                        result = false;
                        validationError = "Please specify a Delimiter";
                        break;
                    }
                    else {
                        if(quantityColumn == null || quantityColumn.equals("")) {
                            result = false;
                            validationError = "Please specify which column contains Quantity data";
                            break;
                        }
                        else {
                            if(price1Column == null || price1Column.equals("")) {
                                result = false;
                                validationError = "Please specify at least one column which contains Price data";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
