package dao;

import helper.QueryMySQL;
import helper.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;


import static helper.QueryMySQL.query;
import static helper.QueryMySQL.queryUpdate;
import static helper.TimeZoneConversions.sqlDateTimeToZoned;

public class AppointmentDaoMySQL implements AppointmentDao {
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        try {
            ResultSet results = query("select * from appointments;");

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            while (results.next()) {
                Appointment appointment = new Appointment(results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                        sqlDateTimeToZoned(results.getDate(7), results.getTime(7)),
                        sqlDateTimeToZoned(results.getDate(8), results.getTime(8)),
                        results.getString(9),
                        sqlDateTimeToZoned(results.getDate(10), results.getTime(10)),
                        results.getString(11),
                        results.getInt(12),
                        results.getInt(13),
                        results.getInt(14));
                appointments.add(appointment);
            }
            return appointments;

        } catch (Exception e) {
            System.out.println("--getAllAppointments Exception--");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAssociatedAppointments(int customer_id) {
        //vulnerable to sql injection
        queryUpdate("delete from appointments where customer_id=" + customer_id + ";");
    }

    @Override
    public void addAppointment(String title,
                               String description,
                               String location,
                               String type,
                               ZonedDateTime start_date_time,
                               ZonedDateTime end_date_time,
                               String username,
                               int customer_id,
                               int user_id,
                               int contact_id) {
        //vulnerable to sql injection
        System.out.println("Initial start: " + start_date_time);
        System.out.println("Initial end: " + end_date_time);

        ZonedDateTime utc_start = start_date_time.withZoneSameInstant(java.time.ZoneOffset.UTC);
        ZonedDateTime utc_end = end_date_time.withZoneSameInstant(java.time.ZoneOffset.UTC);

        System.out.println("UTC start: " + utc_start);
        System.out.println("UTC end: " + utc_end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatted_start = utc_start.format(formatter);
        String formatted_end = utc_end.format(formatter);

        queryUpdate(String.format("insert into appointments " +
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                " values (\"%s\", \"%s\", \"%s\",\"%s\", \"%s\", \"%s\", now(), \"%s\", now(),\"%s\", \"%d\", \"%d\", \"%d\");",
                title, description, location, type,
                formatted_start, formatted_end, username, username,
                customer_id, user_id, contact_id));

        System.out.println("Query: " + String.format("insert into appointments " +
                        "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                        " values (\"%s\", \"%s\", \"%s\",\"%s\", \"%s\", \"%s\", now(), \"%s\", now(),\"%s\", \"%d\", \"%d\", \"%d\");",
                title, description, location, type,
                formatted_start, formatted_end, username, username,
                customer_id, user_id, contact_id));

    }

    @Override
    public void updateAppointment(int id, String title, String description, String location, String type, ZonedDateTime start_date_time, ZonedDateTime end_date_time, String username, int customer_id, int user_id, int contact_id) {
        //vulnerable to sql injection
        System.out.println("Initial start: " + start_date_time);
        System.out.println("Initial end: " + end_date_time);

        ZonedDateTime utc_start = start_date_time.withZoneSameInstant(java.time.ZoneOffset.UTC);
        ZonedDateTime utc_end = end_date_time.withZoneSameInstant(java.time.ZoneOffset.UTC);

        System.out.println("UTC start: " + utc_start);
        System.out.println("UTC end: " + utc_end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatted_start = utc_start.format(formatter);
        String formatted_end = utc_end.format(formatter);

        queryUpdate(String.format("update appointments\n" +
                        "set title=\"%s\", description=\"%s\", location=\"%s\", type=\"%s\", start=\"%s\", end=\"%s\", last_update=now(), last_updated_by=\"%s\", customer_id=%d, user_id=%d, contact_id=%d\n" +
                        "where appointment_id=%d;",
                title, description, location, type,
                formatted_start, formatted_end, username,
                customer_id, user_id, contact_id, id));

        System.out.println("Query: " + String.format("update appointments\n" +
                        "set title=\"%s\", description=\"%s\", location=\"%s\", type=\"%s\", start=\"%s\", end=\"%s\", last_update=now(), last_updated_by=\"%s\", customer_id=%d, user_id=%d, contact_id=%d\n" +
                        "where appointment_id=%d;",
                title, description, location, type,
                formatted_start, formatted_end, username,
                customer_id, user_id, contact_id, id));
    }

    @Override
    public void deleteAppointment(int appointment_id) {
       queryUpdate(String.format("delete from appointments where appointment_id=%d;", appointment_id));
    }

    @Override
    public ObservableList<Appointment> getAllAppointmentsThisMonth() {
        ObservableList<Appointment> appointments_this_month = FXCollections.observableArrayList();
        ResultSet results = query("select * from appointments where extract(MONTH from Start)=extract(MONTH from now());");

        try {
            while (results.next()) {
                Appointment current_appointment = new Appointment(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                        sqlDateTimeToZoned(results.getDate(7), results.getTime(7)),
                        sqlDateTimeToZoned(results.getDate(8), results.getTime(8)),
                        results.getString(9),
                        sqlDateTimeToZoned(results.getDate(10), results.getTime(10)),
                        results.getString(11),
                        results.getInt(12),
                        results.getInt(13),
                        results.getInt(14)
                );
                appointments_this_month.add(current_appointment);
            }
            return appointments_this_month;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ObservableList<Appointment> getAllAppointmentsThisWeek() {
        ObservableList<Appointment> appointments_this_week = FXCollections.observableArrayList();
        ResultSet results = query("select * from appointments where yearweek(start) = yearweek(now());");

        try {
            while (results.next()) {
                Appointment current_appointment = new Appointment(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                        sqlDateTimeToZoned(results.getDate(7), results.getTime(7)),
                        sqlDateTimeToZoned(results.getDate(8), results.getTime(8)),
                        results.getString(9),
                        sqlDateTimeToZoned(results.getDate(10), results.getTime(10)),
                        results.getString(11),
                        results.getInt(12),
                        results.getInt(13),
                        results.getInt(14)
                );
                appointments_this_week.add(current_appointment);
            }
            return appointments_this_week;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ObservableList<String> getAllTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();
        ResultSet results = query("select distinct type from appointments;");
        try {
            while (results.next()) {
                types.add(results.getString(1));
            }
            return types;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ObservableList<String> getAllYears() {
        ObservableList<String> years = FXCollections.observableArrayList();
        ResultSet results = query("select distinct year(start) from appointments;");
        try {
            while (results.next()) {
                years.add(results.getString(1));
            }
            return years;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ObservableList<String> getAllMonths() {
        ObservableList<String> months = FXCollections.observableArrayList();
        ResultSet results = query("select distinct month(start) from appointments;");
        try {
            while (results.next()) {
                months.add(results.getString(1));
            }
            return months;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int getCountOfFilteredAppointments(String month, String year, String type) throws SQLException {
        String queryString = "select count(*) from appointments";
        boolean added_filter = false;
        if (!(month.equals("All"))) {
            if (!added_filter) {
                queryString = queryString + String.format(" where month(start)=%s", month);
                added_filter = true;
            } else {
                queryString = queryString + String.format(" and month(start)=%s", month);
            }
        }

        if (!(year.equals("All"))) {
            if (!added_filter) {
                queryString = queryString + String.format(" where year(start)=%s", year);
                added_filter = true;
            } else {
                queryString = queryString + String.format(" and year(start)=%s", year);
            }
        }

        if (!(type.equals("All"))) {
            if (!added_filter) {
                queryString = queryString + String.format(" where type=\"%s\"", type);
            } else {
                queryString = queryString + String.format(" and type=\"%s\"", type);
            }
        }

        ResultSet results = query(queryString);
        results.next();
        return results.getInt(1);
    }

    @Override
    public ObservableList<Appointment> getFutureAppointmentsByContact(String contact) throws SQLException {
        ObservableList<Appointment> appointments_by_contact = FXCollections.observableArrayList();

        ResultSet contact_id_results = query(String.format("select contact_id from contacts where contact_name=\"%s\";", contact));
        contact_id_results.next();
        int contact_id = contact_id_results.getInt(1);

        ResultSet results = query(String.format("select * from appointments where contact_id=%d and start >= now();", contact_id));
        while (results.next()) {
            appointments_by_contact.add(new Appointment(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(7), results.getTime(7)),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(8), results.getTime(8)),
                    results.getString(9),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(10), results.getTime(10)),
                    results.getString(11),
                    results.getInt(12),
                    results.getInt(13),
                    results.getInt(14)
            ));
        }
        return appointments_by_contact;
    }

    @Override
    public ObservableList<String> getAllLocationNames() throws SQLException {
        ObservableList<String> locations = FXCollections.observableArrayList();
        ResultSet results = query("select distinct location from appointments;");
        while (results.next()) {
            locations.add(results.getString(1));
        }
        return locations;
    }

    @Override
    public ObservableList<Appointment> getFutureAppointmentsByLocation(String location) throws SQLException {
        ObservableList<Appointment> appointments_by_location = FXCollections.observableArrayList();

        ResultSet results = query(String.format("select * from appointments where location=\"%s\" and start >= now();", location));
        while (results.next()) {
            appointments_by_location.add(new Appointment(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(7), results.getTime(7)),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(8), results.getTime(8)),
                    results.getString(9),
                    TimeZoneConversions.sqlDateTimeToZoned(results.getDate(10), results.getTime(10)),
                    results.getString(11),
                    results.getInt(12),
                    results.getInt(13),
                    results.getInt(14)
            ));
        }
        return appointments_by_location;
    }

}
