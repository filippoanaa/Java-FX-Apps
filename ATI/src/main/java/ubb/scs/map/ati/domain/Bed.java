package ubb.scs.map.ati.domain;

public class Bed extends Entity<Integer> {
    private Integer id;
    private BedType bedType;
    private Ventilation ventilation;
    private String personCnp;


    public enum BedType {
        TIC, TIM, TIIP
    }

    public enum Ventilation{
        YES, NO
    }

    public Bed(BedType bedType, Ventilation ventilation, String personCnp) {
        this.bedType = bedType;
        this.ventilation = ventilation;
        this.personCnp = personCnp;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Ventilation getVentilation() {
        return ventilation;
    }

    public void setVentilation(Ventilation ventilation) {
        this.ventilation = ventilation;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public String getPersonCnp() {
        return personCnp;
    }

    public void setPersonCnp(String personCnp) {
        this.personCnp = personCnp;
    }

    @Override
    public String toString() {
        return "Bed{" +
                "id=" + id +
                ", bedType=" + bedType +
                ", ventilation=" + ventilation +
                ", personCnp='" + personCnp + '\'' +
                '}';
    }
}
