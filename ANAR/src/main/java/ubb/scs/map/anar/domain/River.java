package ubb.scs.map.anar.domain;

public class River extends Entity<Integer> {
    private String name;
    private Double averageLevel;

    public River(String name, Double averageLevel) {
        this.averageLevel = averageLevel;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverageLevel() {
        return averageLevel;
    }

    public void setAverageLevel(Double averageLevel) {
        this.averageLevel = averageLevel;
    }

    @Override
    public String toString() {
        return "River{" +
                ", name='" + name + '\'' +
                ", averageLevel=" + averageLevel +
                '}';
    }
}
