package dao;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Country;

public class CountryDaoMySQLTest {
    @Test
    void getUserByID() {
        CountryDao dao = new CountryDaoMySQL();
        assertEquals(dao.getCountryByID(1).getCountry(), "U.S");
        assertEquals(dao.getCountryByID(2).getCountry(), "UK");
    }
    @Test
    void getAllCountries() {
        CountryDao dao = new CountryDaoMySQL();
        ObservableList<Country> countries = dao.getAllCountries();
        assertEquals(countries.get(0).getCountry(), "U.S");
    }
}
