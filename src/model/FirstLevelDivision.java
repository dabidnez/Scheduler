package model;

import java.time.ZonedDateTime;

public class FirstLevelDivision {
    private int division_id;
    private String divison;
    private ZonedDateTime create_date;
    private String created_by;
    private ZonedDateTime last_update;
    private String last_updated_by;
    private Country country;

    public FirstLevelDivision(int division_id, String divison, ZonedDateTime create_date, String created_by, ZonedDateTime last_update, String last_updated_by, Country country) {
        this.division_id = division_id;
        this.divison = divison;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.country = country;
    }

    public FirstLevelDivision() {}

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    public String getDivison() {
        return divison;
    }

    public void setDivison(String divison) {
        this.divison = divison;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String toString() { return getDivison(); }
}
