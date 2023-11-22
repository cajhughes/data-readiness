package com.cajhughes.ready.view;

import com.cajhughes.ready.controller.StatusAttributeProcessor;
import com.cajhughes.ready.model.AttributeTableModel;
import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.processor.AttributeProcessor;
import com.cajhughes.ready.util.InputUtils;
import com.cajhughes.ready.util.OutputUtils;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class AttributePanel extends JPanel {
    private static final String[] delimiters = new String[] {"", ",", "|"};

    private ReadyFrame parent;
    private File[] files;
    private JTextField filename = new JTextField();
    private JButton browse = new JButton();
    private JComboBox delimiter = new JComboBox(delimiters);
    private JButton start = new JButton();
    private JButton stop = new JButton();
    private JButton extract = new JButton();
    private JLabel filenameLabel = new JLabel();
    private JLabel delimiterLabel = new JLabel();
    private JLabel scrollLabel = new JLabel();
    private AttributeProcessor attributeProcessor = null;
    private Options options = null;
    private JTable table = new JTable();
    private JScrollPane scroll = new JScrollPane(table);
    private AttributeTableModel model;
    private Map<String, Set<String>> results;

    public AttributePanel(final ReadyFrame frame) {
        try {
            this.parent = frame;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setSize(new Dimension(638, 313));
        filename.setBounds(new Rectangle(80, 20, 300, 20));
        filename.setEnabled(false);
        browse.setText("Browse...");
        browse.setBounds(new Rectangle(400, 20, 100, 20));
        browse.setMnemonic('B');
        browse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    browse_actionPerformed();
                }
            });
        delimiter.setBounds(new Rectangle(80, 50, 300, 20));
        delimiter.setEnabled(false);
        delimiter.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    delimiter_itemStateChanged();
                }
            });
        start.setText("Start");
        start.setBounds(new Rectangle(520, 140, 100, 20));
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_actionPerformed();
            }
        });
        stop.setText("Stop");
        stop.setBounds(new Rectangle(520, 170, 100, 20));
        stop.setEnabled(false);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop_actionPerformed();
            }
        });
        extract.setText("Extract");
        extract.setBounds(new Rectangle(520, 200, 100, 20));
        extract.setEnabled(false);
        extract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                extract_actionPerformed();
            }
        });
        filenameLabel.setText("File");
        filenameLabel.setBounds(new Rectangle(10, 23, 70, 14));
        delimiterLabel.setText("Delimiter");
        delimiterLabel.setBounds(new Rectangle(10, 53, 70, 14));
        scrollLabel.setText("Results");
        scrollLabel.setBounds(new Rectangle(10, 143, 70, 14));
        scroll.setBounds(new Rectangle(80, 140, 420, 150));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        this.add(extract, null);
        this.add(scroll, null);
        this.add(stop, null);
        this.add(scrollLabel, null);
        this.add(delimiterLabel, null);
        this.add(filenameLabel, null);
        this.add(start, null);
        this.add(delimiter, null);
        this.add(browse, null);
        this.add(filename, null);
    }

    private void browse_actionPerformed() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setMultiSelectionEnabled(true);
        int selection = fileDialog.showOpenDialog(this);
        if(selection == JFileChooser.APPROVE_OPTION) {
            files = fileDialog.getSelectedFiles();
            try {
                if(!InputUtils.fileHeadersMatch(files)) {
                    files = null;
                    JOptionPane.showMessageDialog(
                        this, "Files do not have matching headers", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    filename.setText(null);
                    for(File file: files) {
                        filename.setText(filename.getText() + file.getAbsolutePath() + " ");
                    }
                    parent.setStatus("Select the column delimiter");
                    delimiter.setEnabled(true);
                    delimiter.setSelectedItem(InputUtils.getDelimiter(InputUtils.getHeader(files[0])));
                    delimiter.requestFocus();
                }
            }
            catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void delimiter_itemStateChanged() {
        String value = (String)delimiter.getSelectedItem();
        if(value != null && !value.equals("")) {
            Options options = new Options(files, value);
            try {
                InputUtils.getHeaderColumns(options);
            }
            catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void extract_actionPerformed() {
        PrintStream stream = null;
        try {
            File output = OutputUtils.getOutputFile(files);
            stream = new PrintStream(output, StandardCharsets.UTF_8.name());
            OutputUtils.writeAttributes(stream, getSelectedColumns(), results);
            stream.flush();
            stream.close();
            parent.setStatus("Output written to " + output.getAbsolutePath());
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            if(stream != null) {
                stream.close();
            }
        }
    }

    private String[] getSelectedColumns() {
        int selectionCount = table.getSelectedRowCount();
        String[] selected = new String[selectionCount];
        if(selectionCount > 0) {
            int[] selectedRows = table.getSelectedRows();
            for(int i=0; i<selectionCount; i++) {
                int selectedRow = selectedRows[i];
                selected[i] = (String)model.getValueAt(table.convertRowIndexToModel(selectedRow), 0);
            }
        }
        return selected;
    }

    private void start_actionPerformed() {
        options = new Options(files, (String)delimiter.getSelectedItem());
        if(options.isAttributeValid()) {
            attributeProcessor = new StatusAttributeProcessor(parent, options);
            attributeProcessor.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if("state".equals(event.getPropertyName())) {
                        if(event.getNewValue().equals(SwingWorker.StateValue.DONE)) {
                            tabulateResults();
                        }
                    }
                }
            });
            toggleControls(false);
            attributeProcessor.execute();
        }
        else {
            JOptionPane.showMessageDialog(this, options.getValidationError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stop_actionPerformed() {
        if(attributeProcessor != null) {
            attributeProcessor.cancel(true);
            attributeProcessor = null;
        }
        toggleControls(true);
        browse.requestFocus();
    }

    private void tabulateResults() {
        try {
            if(!attributeProcessor.isCancelled()) {
                extract.setEnabled(true);
                results = attributeProcessor.get();
                model = new AttributeTableModel(results);
                table.setModel(model);
                parent.setStatus("Select which rows you want to include in the extract");
            }
            toggleControls(true);
            attributeProcessor = null;
        }
        catch(CancellationException ce) {
            JOptionPane.showMessageDialog(this, "Operation Cancelled", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleControls(final boolean on) {
        delimiter.setEnabled(on);
        browse.setEnabled(on);
        start.setEnabled(on);
        stop.setEnabled(!on);
        extract.setEnabled(on);
    }
}
