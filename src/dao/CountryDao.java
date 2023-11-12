package dao;

import javafx.collections.ObservableList;
import model.Country;

public interface CountryDao {
    public Country getCountryByID(int id);
    public ObservableList<Country> getAllCountries();
}
