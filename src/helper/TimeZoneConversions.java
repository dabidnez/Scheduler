package helper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConversions {
    public static ZonedDateTime timestampToZonedDateTime(Timestamp timestamp) {
        try {
            return timestamp.toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
