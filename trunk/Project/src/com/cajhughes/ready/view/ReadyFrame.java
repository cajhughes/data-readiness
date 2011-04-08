package com.cajhughes.ready.view;

import com.cajhughes.ready.controller.StatusAttributeProcessor;
import com.cajhughes.ready.controller.StatusQuantityPriceProcessor;
import com.cajhughes.ready.model.Options;
import com.cajhughes.ready.processor.AttributeProcessor;
import com.cajhughes.ready.processor.QuantityPriceProcessor;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;

public class ReadyFrame extends JFrame {
    private static final String[] delimiters = new String[] {"", ",", "|"};
    private File file;
    private JTextField filename = new JTextField();
    private JButton browse = new JButton();
    private JComboBox delimiter = new JComboBox(delimiters);
    private JComboBox quantity = new JComboBox();
    private JComboBox price1 = new JComboBox();
    private JComboBox price2 = new JComboBox();
    private JComboBox price3 = new JComboBox();
    private JButton start = new JButton();
    private JButton stop = new JButton();
    private JButton attributes = new JButton();
    private JTextField status = new JTextField();
    private JLabel filenameLabel = new JLabel();
    private JLabel delimiterLabel = new JLabel();
    private JLabel quantityLabel = new JLabel();
    private JLabel priceLabel = new JLabel();
    private AttributeProcessor attributeProcessor = null;
    private QuantityPriceProcessor readyProcessor = null;
    private Options options = null;

    public ReadyFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setResizable(false);
        this.setSize(new Dimension(520, 280));
        this.setTitle("Data Readiness");
        filename.setBounds(new Rectangle(80, 20, 300, 20));
        filename.setEnabled(false);
        browse.setText("Browse...");
        browse.setBounds(new Rectangle(400, 20, 100, 20));
        browse.setMnemonic('B');
        browse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    browse_actionPerformed(e);
                }
            });
        delimiter.setBounds(new Rectangle(80, 50, 300, 20));
        delimiter.setEnabled(false);
        delimiter.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    delimiter_itemStateChanged(e);
                }
            });
        quantity.setBounds(new Rectangle(80, 80, 300, 20));
        quantity.setEnabled(false);
        price1.setBounds(new Rectangle(80, 110, 300, 20));
        price1.setEnabled(false);
        price2.setBounds(new Rectangle(80, 140, 300, 20));
        price2.setEnabled(false);
        price3.setBounds(new Rectangle(80, 170, 300, 20));
        price3.setEnabled(false);
        start.setText("Start");
        start.setBounds(new Rectangle(400, 110, 100, 20));
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_actionPerformed(e);
            }
        });
        stop.setText("Stop");
        stop.setBounds(new Rectangle(400, 140, 100, 20));
        stop.setEnabled(false);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop_actionPerformed(e);
            }
        });
        attributes.setText("Attributes");
        attributes.setBounds(new Rectangle(400, 170, 100, 20));
        attributes.setEnabled(false);
        attributes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attributes_actionPerformed(e);
            }
        });
        status.setBounds(new Rectangle(1, 234, 512, 20));
        status.setText("Please select the file to be processed");
        status.setEnabled(false);
        filenameLabel.setText("File");
        filenameLabel.setBounds(new Rectangle(10, 23, 70, 14));
        delimiterLabel.setText("Delimiter");
        delimiterLabel.setBounds(new Rectangle(10, 53, 70, 14));
        quantityLabel.setText("Quantity");
        quantityLabel.setBounds(new Rectangle(10, 83, 70, 14));
        priceLabel.setText("Price(s)");
        priceLabel.setBounds(new Rectangle(10, 113, 70, 14));
        price1.setBounds(new Rectangle(80, 110, 300, 20));
        price1.setEnabled(false);
        this.getContentPane().add(attributes, null);
        this.getContentPane().add(stop, null);
        this.getContentPane().add(status, null);
        this.getContentPane().add(price3, null);
        this.getContentPane().add(price2, null);
        this.getContentPane().add(price1, null);
        this.getContentPane().add(priceLabel, null);
        this.getContentPane().add(quantityLabel, null);
        this.getContentPane().add(delimiterLabel, null);
        this.getContentPane().add(filenameLabel, null);
        this.getContentPane().add(start, null);
        this.getContentPane().add(quantity, null);
        this.getContentPane().add(delimiter, null);
        this.getContentPane().add(browse, null);
        this.getContentPane().add(filename, null);
    }

    private void toggleControls(final boolean on) {
        delimiter.setEnabled(on);
        quantity.setEnabled(on);
        price1.setEnabled(on);
        price2.setEnabled(on);
        price3.setEnabled(on);
        browse.setEnabled(on);
        start.setEnabled(on);
        stop.setEnabled(!on);
        attributes.setEnabled(on);
    }

    private void browse_actionPerformed(ActionEvent e) {
        JFileChooser fileDialog = new JFileChooser();
        int selection = fileDialog.showOpenDialog(this);
        if(selection == JFileChooser.APPROVE_OPTION) {
            file = fileDialog.getSelectedFile();
            filename.setText(file.getAbsolutePath());
            status.setText("Select the column delimiter");
            delimiter.setEnabled(true);
            delimiter.requestFocus();
        }
    }

    private void start_actionPerformed(ActionEvent e) {
        options = new Options(file, (String)delimiter.getSelectedItem(),
                              (String)quantity.getSelectedItem(), (String)price1.getSelectedItem(),
                              (String)price2.getSelectedItem(), (String)price3.getSelectedItem());
        if(options.isReadyValid()) {
            readyProcessor = new StatusQuantityPriceProcessor(status, options, 3);
            readyProcessor.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if("state".equals(event.getPropertyName())) {
                        if(event.getNewValue().equals(SwingWorker.StateValue.DONE)) {
                            writeReadyResults();
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

    private void attributes_actionPerformed(ActionEvent e) {
        options = new Options(file, (String)delimiter.getSelectedItem());
        if(options.isAttributeValid()) {
            attributeProcessor = new StatusAttributeProcessor(status, options);
            attributeProcessor.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if("state".equals(event.getPropertyName())) {
                        if(event.getNewValue().equals(SwingWorker.StateValue.DONE)) {
                            writeAttributeResults(options);
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

    private void stop_actionPerformed(ActionEvent e) {
        if(readyProcessor != null) {
            readyProcessor.cancel(true);
            readyProcessor = null;
        }
        toggleControls(true);
        browse.requestFocus();
    }

    private void writeAttributeResults(final Options options) {
        PrintStream stream = null;
        try {
            if(!attributeProcessor.isCancelled()) {
                File output = OutputUtils.getOutputFile(file);
                Map<Integer, Set<String>> results = attributeProcessor.get();
                stream = new PrintStream(output);
                OutputUtils.write(stream, InputUtils.getHeaderColumnsOnly(options), results);
                stream.flush();
                stream.close();
                status.setText("Output written to " + output.getAbsolutePath());
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
        finally {
            if(stream != null) {
                stream.close();
            }
        }
    }

    private void writeReadyResults() {
        try {
            if(!readyProcessor.isCancelled()) {
                File output = OutputUtils.getOutputFile(file);
                Object[] prices = {price1.getSelectedItem(), price2.getSelectedItem(), price3.getSelectedItem()};
                FileUtils.writeStringToFile(
                    output, OutputUtils.toString(quantity.getSelectedItem(), prices, readyProcessor.get()));
                status.setText("Output written to " + output.getAbsolutePath());
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

    private void delimiter_itemStateChanged(ItemEvent e) {
        String value = (String)delimiter.getSelectedItem();
        if(value != null && !value.equals("")) {
            Options options = new Options(file, value);
            try {
                String[] columns = InputUtils.getHeaderColumns(options);
                if(columns != null && columns.length > 0) {
                    DefaultComboBoxModel quantityModel = new DefaultComboBoxModel(columns);
                    DefaultComboBoxModel price1Model = new DefaultComboBoxModel(columns);
                    DefaultComboBoxModel price2Model = new DefaultComboBoxModel(columns);
                    DefaultComboBoxModel price3Model = new DefaultComboBoxModel(columns);
                    status.setText("Select the columns within the file that represent quantity and price(s)");
                    quantity.setModel(quantityModel);
                    quantity.setEnabled(true);
                    price1.setModel(price1Model);
                    price1.setEnabled(true);
                    price2.setModel(price2Model);
                    price2.setEnabled(true);
                    price3.setModel(price3Model);
                    price3.setEnabled(true);
                    attributes.setEnabled(true);
                }
            }
            catch(IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
