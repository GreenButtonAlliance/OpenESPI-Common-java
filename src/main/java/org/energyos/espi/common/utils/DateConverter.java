package org.energyos.espi.common.utils;

import org.energyos.espi.common.models.atom.DateTimeType;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateConverter {
    public static DateTimeType toDateTimeType(Date date) {
        DateTimeType dateTimeType = new DateTimeType();
        GregorianCalendar gregorianCalendar = new DateTime(date).toGregorianCalendar();
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        DatatypeFactory datatypeFactory;

        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        xmlGregorianCalendar.setFractionalSecond(null);
        dateTimeType.setValue(xmlGregorianCalendar);

        return dateTimeType;
    }

    public static DateTimeType toDateTimeType(GregorianCalendar gregorianCalendar) {
        DateTimeType dateTimeType = new DateTimeType();
        DatatypeFactory datatypeFactory;

        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        xmlGregorianCalendar.setFractionalSecond(null);
        dateTimeType.setValue(xmlGregorianCalendar);

        return dateTimeType;
    }

    public static Calendar epoch() {
        Calendar epoch = Calendar.getInstance();
        epoch.setTimeInMillis(0L);
        return epoch;
    }
}