package com.cajhughes.ready.processor;

import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.model.ProcessorProgress;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public abstract class AbstractProcessor<T> extends SwingWorker<Map<String, Set<T>>, ProcessorProgress> {
    protected Options options;
    protected Map<String, Set<T>> results = null;

    public AbstractProcessor(final Options options) {
        this.options = options;
    }

    @Override
    public Map<String, Set<T>> doInBackground() throws IOException {
        File[] files = options.getFiles();
        for(File file: files) {
            int lineCounter = 0;
            LineIterator iterator = FileUtils.lineIterator(file);
            try {
                while(!isCancelled() && iterator.hasNext()) {
                    lineCounter++;
                    String line = iterator.nextLine();
                    if(lineCounter == 1) {
                        processHeader(line);
                    }
                    else {
                        processLine(line, lineCounter);
                    }
                    if((lineCounter % 1000) == 0) {
                        publish(new ProcessorProgress(file, lineCounter));
                    }
                }
            }
            finally {
                LineIterator.closeQuietly(iterator);
            }
        }
        return results;
    }

    protected abstract void processHeader(final String line);

    protected abstract void processLine(final String line, final int lineNumber);
}
