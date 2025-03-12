package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.City;
import ubb.scs.map.faptebune.domain.Need;
import ubb.scs.map.faptebune.domain.Person;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository implements Repository<Long, Person> {
    private final String url;
    private final String username;
    private final String password;
    private final NeedRepository needRepository;

    public PersonRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.needRepository = new NeedRepository(url, username, password);
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String personQuery = "SELECT * FROM Person";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement personStatement = connection.prepareStatement(personQuery);
             ResultSet personResultSet = personStatement.executeQuery()) {

            while (personResultSet.next()) {
                Long id = personResultSet.getLong("id");
                String firstName = personResultSet.getString("first_name");
                String lastName = personResultSet.getString("last_name");
                String username = personResultSet.getString("username");
                String password = personResultSet.getString("password");
                City city = City.valueOf(personResultSet.getString("city"));
                String street = personResultSet.getString("street");
                String streetNumber = personResultSet.getString("street_number");
                String number = personResultSet.getString("number");

                List<Need> needs = getNeedsForPerson(id, connection);
                Person person = new Person(firstName, lastName, username, password, number, street, streetNumber, city, needs);
                person.setId(id);
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    @Override
    public Person findById(Long aLong) {
        String sql = "SELECT * FROM Person where id = ?";
        Person person = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                City city = City.valueOf(rs.getString("city"));
                String street = rs.getString("street");
                String streetNumber = rs.getString("street_number");
                String number = rs.getString("number");
                List<Need> needs = getNeedsForPerson(id, connection);
                person = new Person(firstName, lastName, username, password, number, street, streetNumber, city, needs);
                person.setId(id);
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }


    private List<Need> getNeedsForPerson(Long personId, Connection connection) {
        List<Need> needs = new ArrayList<>();
        String needQuery = "SELECT * FROM Need n JOIN Person_Need pn ON n.id = pn.need_id WHERE pn.person_id = ?";

        try (PreparedStatement needStatement = connection.prepareStatement(needQuery)) {
            needStatement.setLong(1, personId);
            try (ResultSet needResultSet = needStatement.executeQuery()) {
                while (needResultSet.next()) {
                    Long id = needResultSet.getLong("id");
                    String title = needResultSet.getString("title");
                    String description = needResultSet.getString("description");
                    LocalDateTime deadline = needResultSet.getTimestamp("deadline").toLocalDateTime();
                    Long savior = needResultSet.getLong("savior_id");
                    String status = needResultSet.getString("status");

                    Need need = new Need(title, description, deadline, personId);
                    need.setId(id);
                    need.setSavior(savior);
                    need.setStatus(status);

                    needs.add(need);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return needs;
    }

    @Override
    public void add(Person person) {
        String personQuery = "INSERT INTO Person (first_name, last_name, username, password, city, street, street_number, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement personStatement = connection.prepareStatement(personQuery, Statement.RETURN_GENERATED_KEYS)) {

            personStatement.setString(1, person.getFirstName());
            personStatement.setString(2, person.getLastName());
            personStatement.setString(3, person.getUsername());
            personStatement.setString(4, person.getPassword());
            personStatement.setString(5, person.getCity().name());
            personStatement.setString(6, person.getStreet());
            personStatement.setString(7, person.getStreetNumber());
            personStatement.setString(8, person.getNumber());

            personStatement.executeUpdate();

            try (ResultSet generatedKeys = personStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getLong(1));
                }
            }

//            for (Need need : person.getNeeds()) {
//                needRepository.add(need);
//                addPersonNeedRelation(person.getId(), need.getId(), connection);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    private void addPersonNeedRelation(Long personId, Long needId, Connection connection) {
//        String personNeedQuery = "INSERT INTO Person_Need (person_id, need_id) VALUES (?, ?)";
//
//        try (PreparedStatement personNeedStatement = connection.prepareStatement(personNeedQuery)) {
//            personNeedStatement.setLong(1, personId);
//            personNeedStatement.setLong(2, needId);
//            personNeedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}