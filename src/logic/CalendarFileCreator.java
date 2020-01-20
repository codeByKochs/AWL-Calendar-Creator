package logic;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.TimeZone;


import java.io.FileOutputStream;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFileCreator {

    private List<Event> eventsList;
    private Calendar icsCalendar;

    public CalendarFileCreator(List<Event> eventsList){
        this.eventsList = eventsList;
    }

    public Calendar getIcsCalendar() { return icsCalendar; }

    public void createCalendar(){

        // Create calendar
        icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        for (Event event : eventsList){

            //create start date
            java.util.Calendar startDate = new GregorianCalendar();
            startDate.set(java.util.Calendar.MONTH, event.getMonth());
            startDate.set(java.util.Calendar.DAY_OF_MONTH, event.getDay());
            startDate.set(java.util.Calendar.YEAR, event.getYear());
            startDate.set(java.util.Calendar.HOUR_OF_DAY, 18);
            startDate.set(java.util.Calendar.MINUTE, 0);
            startDate.set(java.util.Calendar.SECOND, 0);

            //create end date
            java.util.Calendar endDate = new GregorianCalendar();
            endDate.set(java.util.Calendar.MONTH, event.getMonth());
            endDate.set(java.util.Calendar.DAY_OF_MONTH, event.getDay());
            endDate.set(java.util.Calendar.YEAR, event.getYear());
            endDate.set(java.util.Calendar.HOUR_OF_DAY, 19);
            endDate.set(java.util.Calendar.MINUTE, 0);
            endDate.set(java.util.Calendar.SECOND, 0);

            // Create the event
            String eventName = event.getEvent();
            DateTime start = new DateTime(startDate.getTime());
            DateTime end = new DateTime(endDate.getTime());
            VEvent eventElement = new VEvent(start, end, eventName);

            //create a TimeZone and add it to eventElement
//            TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
//            TimeZone timezone = registry.getTimeZone("UTC");
//            VTimeZone tz = timezone.getVTimeZone();
//            eventElement.getProperties().add(tz.getTimeZoneId());

            //TODO add VAlarm to notify  a day before event occures

            //generate uid and add it to eventElement
            UidGenerator ug = new RandomUidGenerator();
            eventElement.getProperties().add(ug.generateUid());

            // add event to calendar
            icsCalendar.getComponents().add(eventElement);
        }
    }

    public void saveCalendar(){
        try{
            String desktopPath = System.getenv("homepath");
            desktopPath = "C:"+desktopPath+"\\Desktop\\AWL-Müllabholung.ics";

//            File file = new File(desktopPath);
//            FileWriter fileWriter = new FileWriter(file);

            FileOutputStream fout = new FileOutputStream(desktopPath);

//            System.out.println(icsCalendar);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(icsCalendar, fout);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
