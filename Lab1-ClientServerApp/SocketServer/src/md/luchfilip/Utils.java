package md.luchfilip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by luchfilip on 11/14/15.
 *
 */
public class Utils {

    public static String getDateTime() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(cal.getTime());
    }

    public static String getSystemProperties() {
        String result = "";
        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)p.get(key);
            result += key + ": " + value;
        }
        return result;
    }

}
