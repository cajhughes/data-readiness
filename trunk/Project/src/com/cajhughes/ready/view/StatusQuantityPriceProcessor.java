package com.cajhughes.ready.view;

import com.cajhughes.ready.Options;
import com.cajhughes.ready.processor.QuantityPriceProcessor;
import java.util.List;
import javax.swing.JTextField;

public class StatusQuantityPriceProcessor extends QuantityPriceProcessor {
    private JTextField status;

    public StatusQuantityPriceProcessor(final JTextField status, final Options options, final int priceCount) {
        super(options, priceCount);
        this.status = status;
    }

    protected void process(List<Integer> lines) {
        Integer last = lines.get(lines.size()-1);
        status.setText("Processed " + last + " lines");
    }
}
