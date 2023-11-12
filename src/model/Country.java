package model;

import javafx.collections.ObservableList;

import java.time.ZonedDateTime;

public class Country {
    private int country_id;
    private String country;
    private ZonedDateTime create_date;
    private String created_by;
    private ZonedDateTime last_update;

    public Country(int country_id, String country, ZonedDateTime create_date, String created_by, ZonedDateTime last_update, String last_updated_by) {
        this.country_id = country_id;
        this.country = country;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
    }

    public Country() {}

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ZonedDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(ZonedDateTime create_date) {
        this.create_date = create_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getLast_update() {
        return last_update;
    }

    public void setLast_update(ZonedDateTime last_update) {
        this.last_update = last_update;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    private String last_updated_by;

}
