package dao;


import helper.JDBC;
import helper.QueryMySQL;
import helper.TimeZoneConversions;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoMySQL implements UserDao {
    @Override
    public User getUser(String username) {
        try {
            ResultSet results = QueryMySQL.query("select * from users where User_Name='" + username + "';");

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
            ResultSet results = QueryMySQL.query("select * from users where id='" + id + "';");

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
}

