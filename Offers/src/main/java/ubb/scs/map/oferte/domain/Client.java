package ubb.scs.map.oferte.domain;

import java.util.List;
import java.util.Objects;

public class Client extends Entity<Long> {
    private Long id;
    private String name;
    private Integer fidelityGrade;
    private Integer age;
    private Hobby hobby;

    // Constructor
    public Client(String name, Integer fidelityGrade, Integer age, Hobby hobby) {
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.age = age;
        this.hobby = hobby;
    }

    // Getters
    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getFidelityGrade() {
        return fidelityGrade;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobbies() {
        return hobby;
    }

    // Setters
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFidelityGrade(Integer fidelityGrade) {
        this.fidelityGrade = fidelityGrade;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setHobbies(Hobby hobby) {
        this.hobby = hobby;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(name, client.name) &&
                Objects.equals(fidelityGrade, client.fidelityGrade) &&
                Objects.equals(age, client.age) &&
                Objects.equals(hobby, client.hobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fidelityGrade, age, hobby);
    }

    // toString
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fidelityGrade=" + fidelityGrade +
                ", age=" + age +
                ", hobbies=" + hobby +
                '}';
    }
}