package listeners;

import logic.CalendarFileCreator;
import logic.HtmlEventsFinder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class exportButtonListener implements ActionListener {
    private HtmlEventsFinder htmlEventsFinder;
    private CalendarFileCreator calendarFileCreator;
    private JTextArea descriptionLabel;
    private JTextField urlTextField;
    private JTextField targetPathTextField;

    public exportButtonListener(HtmlEventsFinder htmlEventsFinder, CalendarFileCreator calendarFileCreator, JTextArea descriptionLabel, JTextField urlTextField, JTextField targetPathTextField) {
        this.htmlEventsFinder = htmlEventsFinder;
        this.calendarFileCreator = calendarFileCreator;
        this.descriptionLabel = descriptionLabel;
        this.urlTextField = urlTextField;
        this.targetPathTextField = targetPathTextField;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (validateInput()){
         htmlEventsFinder.setURL(urlTextField.getText());
         htmlEventsFinder.extractEvents();

         calendarFileCreator.setEventsList(htmlEventsFinder.getEventsList());
         calendarFileCreator.createCalendar();
         calendarFileCreator.saveCalendar(targetPathTextField.getText());
        }
    }
    private boolean validateInput(){
        if (urlTextField.getText().isEmpty() || urlTextField.getText().isBlank()){
            descriptionLabel.setText("URL fehlerhaft");
            return false;
        }
        return true;
    }
}
