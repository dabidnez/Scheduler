package dao;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

/**
 * Division object related queries.
 */
public interface DivisionDao {
    /**
     * Get first level division object by its id.
     * @param id
     * @return
     */
    FirstLevelDivision getFirstLevelDivisionByID(int id);

    /**
     * Return a list of all first level divisions stored in the database.
     * @return
     */
    ObservableList<FirstLevelDivision> getAllDivisions();
}
