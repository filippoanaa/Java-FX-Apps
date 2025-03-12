package ubb.scs.map.adoptioncenter.domain;

public class Animal extends Entity<Integer> {
    private String name;
    private Integer centerId;
    private AnimalType type;

    public enum AnimalType {
        DOG, CAT, MONKEY, MOUSE, TURTLE
    }

    public Animal(String name, Integer centerId, AnimalType type) {
        this.name = name;
        this.centerId = centerId;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", centerId=" + centerId +
                ", type=" + type +
                '}';
    }
}
