package dao;

import javafx.collections.ObservableList;
import model.Appointment;

public interface AppointmentDao {
    public ObservableList<Appointment> getAllAppointments();
}
