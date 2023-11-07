package dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountryDaoMySQLTest {
    @Test
    void getUserByID() {
        CountryDao dao = new CountryDaoMySQL();
        assertEquals(dao.getCountryByID(1).getCountry(), "U.S");
        assertEquals(dao.getCountryByID(2).getCountry(), "UK");
    }
}
