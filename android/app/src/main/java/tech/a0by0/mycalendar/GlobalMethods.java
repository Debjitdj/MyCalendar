package tech.a0by0.mycalendar;

/**
 * Created by Debjit on 23-Oct-17.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalMethods {

    public static String convertDate(String inputDate, SimpleDateFormat inputFormat, SimpleDateFormat outputFormat) {

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
