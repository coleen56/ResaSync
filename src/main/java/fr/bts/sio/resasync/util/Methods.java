package fr.bts.sio.resasync.util;

import java.util.Date;

public class Methods {

    // méthode de conversion string localdate

    public static java.sql.Date javaDateToSqlDate(Date date) {
        return (new java.sql.Date(date.getTime()));
    }
}
