package ubb.scs.map.ati.repository;

import ubb.scs.map.ati.domain.Bed;
import ubb.scs.map.ati.domain.Bed.BedType;
import ubb.scs.map.ati.domain.Bed.Ventilation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BedRepository implements Repository<Integer, Bed> {
    private final String url;
    private final String username;
    private final String password;

    public BedRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Bed> findAll() {
        List<Bed> beds = new ArrayList<>();
        String query = "SELECT * FROM beds";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bed bed = new Bed(
                        BedType.valueOf(resultSet.getString("bed_type")),
                        Ventilation.valueOf(resultSet.getString("ventilation")),
                        resultSet.getString("person_cnp")
                );
                bed.setId(resultSet.getInt("id"));
                beds.add(bed);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return beds;
    }

    @Override
    public Bed findById(Integer id) {
        String query = "SELECT * FROM beds WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Bed bed = new Bed(
                            BedType.valueOf(resultSet.getString("bed_type")),
                            Ventilation.valueOf(resultSet.getString("ventilation")),
                            resultSet.getString("person_cnp")
                    );
                    bed.setId(resultSet.getInt("id"));
                    return bed;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

//
//    public void save(Bed bed) {
//        String query = "INSERT INTO beds (bedype, ventilation, personCnp) VALUES (?, ?, ?)";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//
//            statement.setString(1, bed.getBedType().name());
//            statement.setString(2, bed.getVentilation().name());
//            statement.setString(3, bed.getPersonCnp());
//            statement.executeUpdate();
//
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    bed.setId(generatedKeys.getInt(1));
//                } else {
//                    throw new SQLException("Creating bed failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
    public void update(Bed bed) {
        String query = "UPDATE beds SET person_cnp = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bed.getPersonCnp());
            statement.setInt(2, bed.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//
//    public void deleteById(Integer id) {
//        String query = "DELETE FROM beds WHERE id = ?";
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}