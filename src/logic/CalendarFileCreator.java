package logic;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;


import java.io.FileOutputStream;
import java.time.Duration;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFileCreator {

    private List<Event> eventsList;
    private Calendar icsCalendar;

    public Calendar getIcsCalendar() { return icsCalendar; }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }

    public void createCalendar(){

        // Create calendar
        icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        for (Event event : eventsList){

            //create start date
            java.util.Calendar startDate = new GregorianCalendar();
            startDate.clear();
            startDate.set(java.util.Calendar.MONTH, event.getMonth());
            startDate.set(java.util.Calendar.DAY_OF_MONTH, event.getDay()-1);
            startDate.set(java.util.Calendar.YEAR, event.getYear());
            startDate.set(java.util.Calendar.HOUR_OF_DAY, 18);
            startDate.set(java.util.Calendar.MINUTE, 0);
            startDate.set(java.util.Calendar.SECOND, 0);

            //create end date
            java.util.Calendar endDate = new GregorianCalendar();
            endDate.clear();
            endDate.set(java.util.Calendar.MONTH, event.getMonth());
            endDate.set(java.util.Calendar.DAY_OF_MONTH, event.getDay()-1);
            endDate.set(java.util.Calendar.YEAR, event.getYear());
            endDate.set(java.util.Calendar.HOUR_OF_DAY, 19);
            endDate.set(java.util.Calendar.MINUTE, 0);
            endDate.set(java.util.Calendar.SECOND, 0);

            // Create the event
            String eventName = event.getEvent();
            DateTime start = new DateTime(startDate.getTime());
            DateTime end = new DateTime(endDate.getTime());
            VEvent eventElement = new VEvent(start, end, eventName);

//            //generate Alarm a day before the event and add it to event
//            VAlarm reminder = new VAlarm(Duration.ofHours(24));
//            reminder.getProperties().add(new Repeat(1));
//            reminder.getProperties().add(Action.DISPLAY);
//            eventElement.getAlarms().add(reminder);

            //generate uid and add it to eventElement
            UidGenerator ug = new RandomUidGenerator();
            eventElement.getProperties().add(ug.generateUid());

            // add event to calendar
            icsCalendar.getComponents().add(eventElement);
        }
    }

    public void saveCalendar(String filePath){
        try{

            FileOutputStream fout = new FileOutputStream(filePath);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(icsCalendar, fout);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //TODO check valid input
}
