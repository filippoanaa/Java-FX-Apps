package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Need;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NeedRepository implements Repository<Long, Need> {
    private final String url;
    private final String username;
    private final String password;

    public NeedRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Need> findAll() {
        List<Need> needs = new ArrayList<>();
        String needQuery = "SELECT * FROM Need";

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement needStatement = connection.prepareStatement(needQuery);
             ResultSet needResultSet = needStatement.executeQuery()) {

            while (needResultSet.next()) {
                Long id = needResultSet.getLong("id");
                String title = needResultSet.getString("title");
                String description = needResultSet.getString("description");
                LocalDateTime deadline = needResultSet.getTimestamp("deadline").toLocalDateTime();
                Long savior = needResultSet.getLong("savior_id");
                Long manInNeed = needResultSet.getLong("man_in_need");
                String status = needResultSet.getString("status");

                Need need = new Need(title, description, deadline, manInNeed);
                need.setId(id);
                need.setSavior(savior);
                need.setStatus(status);

                needs.add(need);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return needs;
    }

    @Override
    public Need findById(Long aLong) {
        String query = "SELECT * FROM Need WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, aLong);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime deadline = rs.getTimestamp("deadline").toLocalDateTime();
                Long savior = rs.getLong("savior_id");
                Long manInNeed = rs.getLong("man_in_need");
                String status = rs.getString("status");
                Need need = new Need(title, description, deadline, manInNeed);
                need.setId(id);
                need.setSavior(savior);
                need.setStatus(status);
                return need;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public void add(Need need) {
        String needQuery = "INSERT INTO Need (title, description, deadline, man_in_need) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement needStatement = connection.prepareStatement(needQuery, Statement.RETURN_GENERATED_KEYS)) {

            needStatement.setString(1, need.getTitle());
            needStatement.setString(2, need.getDescription());
            needStatement.setTimestamp(3, Timestamp.valueOf(need.getDeadline()));
            needStatement.setObject(4, need.getManInNeed());

            needStatement.executeUpdate();

            try (ResultSet generatedKeys = needStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    need.setId(generatedKeys.getLong(1));
                }
            }

            String personNeedsQuery = "INSERT INTO person_need (person_id, need_id) VALUES (?, ?)";
            try (PreparedStatement personNeedsStatement = connection.prepareStatement(personNeedsQuery)) {
                personNeedsStatement.setLong(1, need.getManInNeed());
                personNeedsStatement.setLong(2, need.getId());
                personNeedsStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Need selectedNeed) {
        String sql = "UPDATE Need SET savior_id = ?, status = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, selectedNeed.getSavior());
            preparedStatement.setString(2, selectedNeed.getStatus());
            preparedStatement.setLong(3, selectedNeed.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}