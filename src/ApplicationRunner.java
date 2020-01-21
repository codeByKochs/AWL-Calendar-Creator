import gui.MainWindow;
import logic.CalendarFileCreator;
import logic.HtmlEventsFinder;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRunner {
    public static void main(String[] args) {
        HtmlEventsFinder finder = new HtmlEventsFinder();
        CalendarFileCreator calendarFileCreator = new CalendarFileCreator();

        MainWindow window = new MainWindow(finder, calendarFileCreator);
    }
}