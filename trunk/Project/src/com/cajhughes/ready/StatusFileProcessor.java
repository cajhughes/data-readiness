package com.cajhughes.ready;

import java.util.List;
import javax.swing.JTextField;

public class StatusFileProcessor extends FileProcessor {
    private JTextField status;

    public StatusFileProcessor(final JTextField status, final Options options, final int priceCount) {
        super(options, priceCount);
        this.status = status;
    }

    protected void process(List<Integer> lines) {
        Integer last = lines.get(lines.size()-1);
        status.setText("Processed " + last + " lines");
    }
}
