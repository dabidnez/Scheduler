package dao;

import helper.QueryMySQL;
import helper.TimeZoneConversions;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static helper.QueryMySQL.query;
import static helper.TimeZoneConversions.timestampToZonedDateTime;

public class CountryDaoMySQL implements CountryDao {
    // Assuming garbage collection takes care of resultset.
    @Override
    public Country getCountryByID(int id) {
        Country country = null;
        try {
            // Using string concantenation instead of prepared statements makes this query
            // vulnerable to SQL injection attacks.
            // Potentially ok because method requires an integer.
            ResultSet results = query("select * from countries where Country_ID = " + id + ";");
            results.next();
            country = new Country(results.getInt(1),
                    results.getString(2),
                    timestampToZonedDateTime(results.getTimestamp(3)),
                    results.getString(4),
                    timestampToZonedDateTime(results.getTimestamp(5)),
                    results.getString(6));

        } catch (Exception e) {
            System.out.println(e);
        }
        return country;
    }
}
