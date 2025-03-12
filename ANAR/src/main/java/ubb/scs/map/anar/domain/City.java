package ubb.scs.map.anar.domain;

public class City extends Entity<Integer> {
    private Integer riverId;
    private String name;
    private Double cmdr;
    private Double cma;

    public City(Integer riverId, String name, Double cmdr, Double cma) {
        this.riverId = riverId;
        this.name = name;
        this.cmdr = cmdr;
        this.cma = cma;
    }

    public Integer getRiverId() {
        return riverId;
    }

    public void setRiverId(Integer riverId) {
        this.riverId = riverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCmdr() {
        return cmdr;
    }

    public void setCmdr(Double cmdr) {
        this.cmdr = cmdr;
    }

    public Double getCma() {
        return cma;
    }

    public void setCma(Double cma) {
        this.cma = cma;
    }

    @Override
    public String toString() {
        return "City{" +
                "riverId=" + riverId +
                ", name='" + name + '\'' +
                ", cmdr=" + cmdr +
                ", cma=" + cma +
                '}';
    }
}
