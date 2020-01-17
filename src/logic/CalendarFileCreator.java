package logic;

import net.fortuna.ical4j.model.Calendar;

import java.util.List;

public class CalendarFileCreator {

    private List<String> eventsList;
    private Calendar calendar;

    public CalendarFileCreator(List<String> eventsList){
        this.eventsList = eventsList;
    }

    public void createCalendar(){
        this.calendar = new Calendar();
    }

}
