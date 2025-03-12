package ubb.scs.map.ati.domain;

import java.util.Objects;

public class Patient extends Entity<Integer>{
    private String cnp;
    private Integer months;
    private PreBorn preBorn;
    private String diagnosis;
    private Integer gravity;

    public enum PreBorn{
        YES, NO
    }

    public Patient(String cnp, Integer months, String diagnosis, PreBorn preBorn, Integer gravity) {
        this.cnp = cnp;
        this.months = months;
        this.diagnosis = diagnosis;
        this.preBorn = preBorn;
        this.gravity = gravity;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public PreBorn getPreBorn() {
        return preBorn;
    }

    public void setPreBorn(PreBorn preBorn) {
        this.preBorn = preBorn;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getGravity() {
        return gravity;
    }

    public void setGravity(Integer gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(cnp, patient.cnp) && Objects.equals(months, patient.months) && preBorn == patient.preBorn && Objects.equals(diagnosis, patient.diagnosis) && Objects.equals(gravity, patient.gravity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnp, months, preBorn, diagnosis, gravity);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "cnp='" + cnp + '\'' +
                ", months=" + months +
                ", preBorn=" + preBorn +
                ", diagnosis='" + diagnosis + '\'' +
                ", gravity=" + gravity +
                '}';
    }
}
