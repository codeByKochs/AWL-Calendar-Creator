package gui;

import listeners.exportButtonListener;
import logic.CalendarFileCreator;
import logic.HtmlEventsFinder;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private int width;
    private int height;
    private HtmlEventsFinder htmlEventsFinder;
    private CalendarFileCreator calendarFileCreator;


    public MainWindow(HtmlEventsFinder htmlEventsFinder, CalendarFileCreator calendarFileCreator) {
        this.htmlEventsFinder = htmlEventsFinder;
        this.calendarFileCreator = calendarFileCreator;
        this.width = 500;
        this.height = 200;
        run();
    }

    public void run(){
        setTitle("AWL Müllkalender Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(width, height));

        createComponents(this);
        pack();
        setVisible(true);
    }

    private void createComponents(Container container){

        // set up constraints for container components
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,1,1,1);

        //first row
        JLabel urlCaptionLabel = new JLabel("Url:");
        c.gridx = 0;
        c.gridy = 0;
        container.add(urlCaptionLabel, c);

        JTextArea descriptionTextArea = new JTextArea("Kalender unter https://www.awl-neuss.de/nc/abfallkalender.html auswählen und URL hier einfügen");
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setLineWrap(true);
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 2;
        container.add(descriptionTextArea, c);

        //second row
        JTextField urlTextField = new JTextField();
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 2;
        container.add(urlTextField, c);

        //third row
        JLabel targetPathCaptionLabel = new JLabel("Dateipfad:");
        c.gridx = 0;
        c.gridy = 2;
        container.add(targetPathCaptionLabel, c);

        //fourth row
        JTextField targetPathTextField = new JTextField();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        container.add(targetPathTextField, c);

        JButton exportButton = new JButton("exportieren");
        c.gridx = 3;
        c.gridy = 2;
        c.gridheight = 2;
        c.gridwidth = 1;
        container.add(exportButton, c);

        //set target path
        String desktopPath = System.getenv("homepath");
        targetPathTextField.setText("C:"+desktopPath+"\\Desktop\\AWL-Müllabholung.ics");

        //add action listeners
        exportButton.addActionListener(new exportButtonListener(htmlEventsFinder, calendarFileCreator, descriptionTextArea, urlTextField, targetPathTextField));
    }
}
