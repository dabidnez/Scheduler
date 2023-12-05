package dao;

import javafx.collections.ObservableList;
import model.Country;

/**
 * Country object related queries.
 */
public interface CountryDao {
    /**
     * Return a country object given its id.
     * @param id
     * @return
     */
    public Country getCountryByID(int id);

    /**
     * Get a list of all countries in the database.
     * @return
     */
    public ObservableList<Country> getAllCountries();
}
