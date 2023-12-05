package dao;


import helper.JDBC;
import helper.TimeZoneConversions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static helper.TimeZoneConversions.sqlDateTimeToZoned;

import static helper.QueryMySQL.query;

public class UserDaoMySQL implements UserDao {
    @Override
    public User getUser(String username) {
        try {
            ResultSet results = query("select * from users where User_Name='" + username + "';");

            results.next();

            User user = new User();
            user.setUser_id(results.getInt(1));
            //unique
            user.setUser_name(results.getString(2));
            user.setPassword(results.getString(3));
            user.setCreate_date(TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(4)));
            user.setCreated_by(results.getString(5));
            user.setLast_update(TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(6)));
            user.setLast_updated_by(results.getString(7));
            return user;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public User getUserByID(int id ) {
        try {
            ResultSet results = query("select * from users where id='" + id + "';");

            results.next();

            User user = new User();
            user.setUser_id(results.getInt(1));
            //unique
            user.setUser_name(results.getString(2));
            user.setPassword(results.getString(3));
            user.setCreate_date(TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(4)));
            user.setCreated_by(results.getString(5));
            user.setLast_update(TimeZoneConversions.timestampToZonedDateTime(results.getTimestamp(6)));
            user.setLast_updated_by(results.getString(7));
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            ResultSet results = query("select * from users;");
            while (results.next()) {
                User currentUser = new User(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        sqlDateTimeToZoned(results.getDate(4), results.getTime(4)),
                        results.getString(5),
                        sqlDateTimeToZoned(results.getDate(6), results.getTime(6)),
                        results.getString(7));
                allUsers.add(currentUser);
            }
            return allUsers;
        } catch (Exception e) {
            System.out.println("--getAllUsers() Exception--");
            System.out.println(e.getMessage());
            return null;
        }
    }
}

