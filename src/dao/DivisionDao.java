package dao;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

public interface DivisionDao {
    FirstLevelDivision getFirstLevelDivisionByID(int id);
    ObservableList<FirstLevelDivision> getAllDivisions();
}
