package dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DivisionDaoMySQLTest {
    @Test
    void getDivisionByID() {
        DivisionDao dao = new DivisionDaoMySQL();
        assertEquals(dao.getFirstLevelDivisionByID(1).getDivison(), "Alabama");
    }
    @Test
    void getAllDivisions() {
        DivisionDao dao = new DivisionDaoMySQL();
        assertEquals(dao.getAllDivisions().get(0).getDivison(), "Alabama");
    }
}
