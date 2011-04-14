package com.cajhughes.ready.controller;

import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.model.ProcessorProgress;
import com.cajhughes.ready.processor.QuantityPriceProcessor;
import java.util.List;
import javax.swing.JTextField;

public class StatusQuantityPriceProcessor extends QuantityPriceProcessor {
    private JTextField status;

    public StatusQuantityPriceProcessor(final JTextField status, final Options options, final int priceCount) {
        super(options, priceCount);
        this.status = status;
    }

    @Override
    protected void process(List<ProcessorProgress> lines) {
        ProcessorProgress last = lines.get(lines.size()-1);
        status.setText("Processed " + last.getLinesProcessed() + " lines of " + last.getFile().getAbsolutePath());
    }
}
