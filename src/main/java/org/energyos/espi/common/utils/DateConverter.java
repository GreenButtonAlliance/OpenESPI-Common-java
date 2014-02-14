/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.models.atom.DateTimeType;
import org.joda.time.DateTime;

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
        if (gregorianCalendar == null) {
        	gregorianCalendar = new GregorianCalendar();
        }

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