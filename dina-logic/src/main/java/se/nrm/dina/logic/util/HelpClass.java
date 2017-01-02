/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.logic.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author idali
 */
public class HelpClass {

    private static HelpClass instance = null;

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH.mm.ss");
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    public static synchronized HelpClass getInstance() {
        if (instance == null) {
            instance = new HelpClass();
        }
        return instance;
    }

    public String dateTimeToString(LocalDateTime date) {
        return date == null ? null : date.format(FORMATTER);
    }

    public String dateToString(Date date) {
        if (date != null) {
            return SIMPLE_DATE_FORMAT.format(date);
        } else {
            return null;
        }
    }

    public String dateToString(java.util.Date date) {
        if (date != null) {
            return SIMPLE_DATE_FORMAT.format(date);
        } else {
            return null;
        }
    }

    public java.sql.Date getTodaysDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }

    /**
     * Checks if the String is numeric
     *
     * @param s
     * @return
     */
    public boolean isNumric(String s) {
        return StringUtils.isNumeric(s);
    }
}
