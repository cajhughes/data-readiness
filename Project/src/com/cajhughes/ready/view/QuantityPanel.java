package com.cajhughes.ready.view;

import com.cajhughes.ready.controller.StatusQuantityPriceProcessor;
import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.model.QuantityPriceTableModel;
import com.cajhughes.ready.processor.QuantityPriceProcessor;
import com.cajhughes.ready.util.InputUtils;
import com.cajhughes.ready.util.OutputUtils;
import java.awt.Cursor;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

public class QuantityPanel extends JPanel {
    private static final String[] delimiters = new String[] {"", ",", "|"};

    private ReadyFrame parent;
    private File file;
    private JTextField filename = new JTextField();
    private JButton browse = new JButton();
    private JComboBox delimiter = new JComboBox(delimiters);
    private JComboBox quantity = new JComboBox();
    private JComboBox price = new JComboBox();
    private JButton start = new JButton();
    private JButton stop = new JButton();
    private JButton extract = new JButton();
    private JLabel filenameLabel = new JLabel();
    private JLabel delimiterLabel = new JLabel();
    private JLabel quantityLabel = new JLabel();
    private JLabel priceLabel = new JLabel();
    private QuantityPriceProcessor readyProcessor = null;
    private Options options = null;
    private JTable table = new JTable();
    private JScrollPane scroll = new JScrollPane(table);
    private QuantityPriceTableModel model;
    private Map<String, Set<Integer>> results;

    public QuantityPanel(final ReadyFrame frame) {
        try {
            this.parent = frame;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setSize(new Dimension(673, 309));
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
        quantity.setBounds(new Rectangle(10, 130, 170, 20));
        quantity.setEnabled(false);
        price.setBounds(new Rectangle(80, 110, 300, 20));
        price.setEnabled(false);
        start.setText("Start");
        start.setBounds(new Rectangle(400, 50, 100, 20));
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_actionPerformed();
            }
        });
        stop.setText("Stop");
        stop.setBounds(new Rectangle(520, 50, 100, 20));
        stop.setEnabled(false);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop_actionPerformed();
            }
        });
        extract.setText("Extract");
        extract.setBounds(new Rectangle(520, 110, 100, 20));
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
        quantityLabel.setText("Quantity");
        quantityLabel.setBounds(new Rectangle(10, 113, 70, 14));
        priceLabel.setText("Price");
        priceLabel.setBounds(new Rectangle(203, 83, 50, 14));
        price.setBounds(new Rectangle(270, 80, 170, 20));
        price.setEnabled(false);
        scroll.setBounds(new Rectangle(200, 110, 300, 110));
        table.setFillsViewportHeight(true);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.add(extract, null);
        this.add(scroll, null);
        this.add(stop, null);
        this.add(price, null);
        this.add(priceLabel, null);
        this.add(quantityLabel, null);
        this.add(delimiterLabel, null);
        this.add(filenameLabel, null);
        this.add(start, null);
        this.add(quantity, null);
        this.add(delimiter, null);
        this.add(browse, null);
        this.add(filename, null);
    }

    private void browse_actionPerformed() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setMultiSelectionEnabled(false);
        int selection = fileDialog.showOpenDialog(this);
        if(selection == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
            try {
                filename.setText(null);
                filename.setText(filename.getText() + file.getAbsolutePath() + " ");
                parent.setStatus("Select the column delimiter");
                delimiter.setEnabled(true);
                delimiter.setSelectedItem(InputUtils.getDelimiter(InputUtils.getHeader(file)));
                delimiter.requestFocus();
            }
            catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void delimiter_itemStateChanged() {
        String value = (String)delimiter.getSelectedItem();
        if(value != null && !value.equals("")) {
            Options options = new Options(file, value, null, null);
            try {
                String[] columns = InputUtils.getHeaderColumns(options);
                if(columns != null && columns.length > 0) {
                    DefaultComboBoxModel quantityModel = new DefaultComboBoxModel(columns);
                    DefaultComboBoxModel priceModel = new DefaultComboBoxModel(columns);
                    parent.setStatus("Select the columns within the file that represent quantity and price(s)");
                    quantity.setModel(quantityModel);
                    quantity.setEnabled(true);
                    price.setModel(priceModel);
                    price.setEnabled(true);
                }
            }
            catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void extract_actionPerformed() {
        PrintStream stream = null;
        String category = model.getCategory(table.getSelectedRow(), table.getSelectedColumn());
        if(category != null && !category.startsWith("label")) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                File output = OutputUtils.getOutputFile(file);
                stream = new PrintStream(output);
                OutputUtils.writeLines(stream, results, model.getCategory(table.getSelectedRow(), table.getSelectedColumn()), file);
                stream.flush();
                stream.close();
                parent.setStatus("Output written to " + output.getAbsolutePath());
            }
            catch(IOException ioe) {
                this.setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
            }
            finally {
                this.setCursor(Cursor.getDefaultCursor());
                if(stream != null) {
                    stream.close();
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this,
                                          "Select the cell which represents the lines you wish to extract",
                                          "Information",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void start_actionPerformed() {
        options = new Options(file, (String)delimiter.getSelectedItem(),
                              (String)quantity.getSelectedItem(), (String)price.getSelectedItem());
        if(options.isReadyValid()) {
            readyProcessor = new StatusQuantityPriceProcessor(parent, options);
            readyProcessor.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if("state".equals(event.getPropertyName())) {
                        if(event.getNewValue().equals(SwingWorker.StateValue.DONE)) {
                            tabulateResults();
                        }
                    }
                }
            });
            toggleControls(false);
            readyProcessor.execute();
        }
        else {
            JOptionPane.showMessageDialog(this, options.getValidationError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stop_actionPerformed() {
        if(readyProcessor != null) {
            readyProcessor.cancel(true);
            readyProcessor = null;
        }
        toggleControls(true);
        browse.requestFocus();
    }

    private void toggleControls(final boolean on) {
        delimiter.setEnabled(on);
        quantity.setEnabled(on);
        price.setEnabled(on);
        browse.setEnabled(on);
        start.setEnabled(on);
        stop.setEnabled(!on);
    }

    private void tabulateResults() {
        try {
            if(!readyProcessor.isCancelled()) {
                extract.setEnabled(true);
                results = readyProcessor.get();
                model = new QuantityPriceTableModel(results);
                table.setModel(model);
                parent.setStatus("Select the cell which contains the results you wish to extract");
                /*
                File output = OutputUtils.getOutputFile(files);
                Object[] prices = {price.getSelectedItem()};
                FileUtils.writeStringToFile(
                    output, OutputUtils.toString(quantity.getSelectedItem(), prices, readyProcessor.get()));
                parent.setStatus("Output written to " + output.getAbsolutePath());
                */
            }
            toggleControls(true);
            readyProcessor = null;
        }
        catch(CancellationException ce) {
            JOptionPane.showMessageDialog(this, "Operation Cancelled", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
