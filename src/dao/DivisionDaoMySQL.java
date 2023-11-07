package dao;

import helper.QueryMySQL;
import helper.TimeZoneConversions;
import model.Country;
import model.FirstLevelDivision;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DivisionDaoMySQL implements DivisionDao {
    @Override
    public FirstLevelDivision getFirstLevelDivisionByID(int id) {
        try {
            ResultSet results = QueryMySQL.query("select * from first_level_divisions where Division_ID=" + id + ";");
            results.next();
            FirstLevelDivision division = new FirstLevelDivision(results.getInt(1),
                    results.getString(2),
                    ZonedDateTime.of(results.getDate(3).toLocalDate(), results.getTime(3).toLocalTime(), ZoneId.of("UTC")),
                    results.getString(4),
                    ZonedDateTime.of(results.getDate(5).toLocalDate(), results.getTime(5).toLocalTime(), ZoneId.of("UTC")),
                    results.getString(6),
                    (new CountryDaoMySQL()).getCountryByID(results.getInt(7)));

            return division;


        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
