package helper;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

/**
 * Help with time zone conversions
 */
public class TimeZoneConversions {
    /**
     * Converts timestamp object to a ZonedDateTime.
     * @param timestamp
     * @return
     */
    public static ZonedDateTime timestampToZonedDateTime(Timestamp timestamp) {
        try {
            return timestamp.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Converts a SQL date object and a sql time object into a single zoned date time. Function assumes date and time
     * recieved are in UTC.
     * @param date
     * @param time
     * @return
     */
    public static ZonedDateTime sqlDateTimeToZoned(Date date, Time time) {
        LocalDate local_date = date.toLocalDate();
        LocalTime local_time = time.toLocalTime();
        LocalDateTime local_date_time = LocalDateTime.of(local_date, local_time);
        return ZonedDateTime.of(local_date_time, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
    }
}
