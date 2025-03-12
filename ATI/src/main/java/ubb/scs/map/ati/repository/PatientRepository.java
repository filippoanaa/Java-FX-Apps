package ubb.scs.map.ati.repository;

import ubb.scs.map.ati.domain.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements PatientRepositoryInterface {
    private final String url;
    private final String username;
    private final String password;

    public PatientRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getString("cnp"),
                        resultSet.getInt("months"),
                        resultSet.getString("diagnosis"),
                        Patient.PreBorn.valueOf(resultSet.getString("pre_born")),
                        resultSet.getInt("gravity")
                );
                patient.setId(resultSet.getInt("id"));
                patients.add(patient);
            }

        } catch (SQLException e) {
            return null;
        }
        return patients;
    }

    @Override
    public Patient findById(Integer integer) {
        Patient patient = null;
        String query = "SELECT * FROM patients WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, integer);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient(
                            rs.getString("cnp"),
                            rs.getInt("months"),
                            rs.getString("diagnosis"),
                            Patient.PreBorn.valueOf(rs.getString("pre_born")),
                            rs.getInt("gravity")
                    );
                    patient.setId(rs.getInt("id"));
                }
            }

        } catch (SQLException ex) {
            return null;
        }
        return patient;
    }


    @Override
    public Patient findByCnp(String cnp) {
        Patient patient = null;
        String query = "SELECT * FROM patients WHERE cnp = ?";
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, cnp);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient(
                            rs.getString("cnp"),
                            rs.getInt("months"),
                            rs.getString("diagnosis"),
                            Patient.PreBorn.valueOf(rs.getString("pre_born")),
                            rs.getInt("gravity")
                    );
                    patient.setId(rs.getInt("id"));
                }
            }

        } catch (SQLException ex) {
            return null;
        }
        return patient;
    }
}
