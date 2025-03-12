package ubb.scs.map.faptebune.domain;

import java.util.List;
import java.util.Objects;

public class Person extends Entity<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private City city;
    private String street;
    private String streetNumber;
    private String number;
    private List<Need> needs;


    public Person(String firstName, String lastName, String username, String password, String number, String street, String streetNumber, City city, List<Need> needs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.number = number;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.needs = needs;
    }

    public List<Need> getNeeds() {
        return needs;
    }
    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getStreet() {
        return street;
    }

    public String getPassword() {
        return password;
    }

    public City getCity() {
        return city;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(username, person.username) && Objects.equals(password, person.password) && Objects.equals(city, person.city) && Objects.equals(street, person.street) && Objects.equals(streetNumber, person.streetNumber) && Objects.equals(number, person.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, city, street, streetNumber, number);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", city=" + city +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", number='" + number + '\'' +
                ", needs=" + needs +
                '}';
    }
}
