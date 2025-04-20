package fr.bts.sio.resasync.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Methods {

    // méthode de conversion string localdate

    public static java.sql.Date javaDateToSqlDate(Date date) {
        return (new java.sql.Date(date.getTime()));
    }

    public Date convertLocalDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
