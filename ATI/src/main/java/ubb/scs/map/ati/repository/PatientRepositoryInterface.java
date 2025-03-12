package ubb.scs.map.ati.repository;

import ubb.scs.map.ati.domain.Patient;

public interface PatientRepositoryInterface extends Repository<Integer, Patient> {
    Patient findByCnp(String cnp);
}
