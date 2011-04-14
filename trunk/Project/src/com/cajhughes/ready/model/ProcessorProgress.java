package com.cajhughes.ready.model;

import java.io.File;

public class ProcessorProgress {
    private File file;
    private int linesProcessed;

    public ProcessorProgress(final File file, final Integer linesProcessed) {
        this.file = file;
        this.linesProcessed = linesProcessed;
    }

    public File getFile() {
        return file;
    }

    public Integer getLinesProcessed() {
        return linesProcessed;
    }
}
