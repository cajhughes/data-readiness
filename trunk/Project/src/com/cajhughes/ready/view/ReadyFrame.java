package com.cajhughes.ready.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class ReadyFrame extends JFrame {
    private JTextField status = new JTextField();
    private JTabbedPane jTabbedPane = new JTabbedPane();

    public ReadyFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatus(final String message) {
        status.setText(message);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        status.setBorder(null);
        status.setBounds(new Rectangle(2, 358, 641, 18));
        status.setText("Please select the file(s) to be processed");
        status.setEnabled(false);
        this.setResizable(false);
        this.setSize(new Dimension(650, 400));
        this.setTitle("Data Readiness");
        jTabbedPane.setBounds(new Rectangle(0, 0, 645, 355));
        jTabbedPane.addTab("Data Quality", new QuantityPanel(this));
        jTabbedPane.addTab("Distinct Attributes", new AttributePanel(this));
        this.getContentPane().add(jTabbedPane, null);
        this.getContentPane().add(status, null);
    }
}
