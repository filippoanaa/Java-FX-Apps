package ubb.scs.map.oferte.repository;

import ubb.scs.map.oferte.domain.SpecialOffer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialOfferRepository implements Repository<Double, SpecialOffer> {
    private final String url;
    private final String username;
    private final String password;

    public SpecialOfferRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<SpecialOffer> getAll() {
        List<SpecialOffer> specialOffers = new ArrayList<>();
        String sql = "SELECT * FROM special_offer";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("id");
                Double hotelId = resultSet.getDouble("hotel_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                int percent = resultSet.getInt("percent");

                SpecialOffer specialOffer = new SpecialOffer(hotelId, startDate.toLocalDate(), endDate.toLocalDate(), percent);
                specialOffer.setId(id);
                specialOffers.add(specialOffer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return specialOffers;
    }
}