package com.cajhughes.ready.controller;

import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.model.ProcessorProgress;
import com.cajhughes.ready.processor.QuantityPriceProcessor;
import com.cajhughes.ready.view.ReadyFrame;
import java.util.List;

public class StatusQuantityPriceProcessor extends QuantityPriceProcessor {
    private ReadyFrame frame;

    public StatusQuantityPriceProcessor(final ReadyFrame frame, final Options options) {
        super(options);
        this.frame = frame;
    }

    @Override
    protected void process(List<ProcessorProgress> lines) {
        ProcessorProgress last = lines.get(lines.size()-1);
        frame.setStatus("Processed " + last.getLinesProcessed() + " lines of " + last.getFile().getAbsolutePath());
    }
}
