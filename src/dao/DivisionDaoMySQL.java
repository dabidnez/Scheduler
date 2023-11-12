package dao;

import helper.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static helper.QueryMySQL.query;

public class DivisionDaoMySQL implements DivisionDao {
    @Override
    public FirstLevelDivision getFirstLevelDivisionByID(int id) {
        try {
            ResultSet results = query("select * from first_level_divisions where Division_ID=" + id + ";");
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

    @Override
    public ObservableList<FirstLevelDivision> getAllDivisions() {
        //can optimize by grabbing all countries first, instead of one by one
        try {
            ResultSet results = query("select * from first_level_divisions;");
            ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
            while (results.next()) {
                FirstLevelDivision division = new FirstLevelDivision(results.getInt(1),
                        results.getString(2),
                        ZonedDateTime.of(results.getDate(3).toLocalDate(), results.getTime(3).toLocalTime(), ZoneId.of("UTC")),
                        results.getString(4),
                        ZonedDateTime.of(results.getDate(5).toLocalDate(), results.getTime(5).toLocalTime(), ZoneId.of("UTC")),
                        results.getString(6),
                        (new CountryDaoMySQL()).getCountryByID(results.getInt(7)));
                allDivisions.add(division);
            }
            return allDivisions;
        } catch (Exception e) {
            System.out.println(" -- getAllDivisions -- ");
            System.out.println(e);
        }
        return null;
    }
}
