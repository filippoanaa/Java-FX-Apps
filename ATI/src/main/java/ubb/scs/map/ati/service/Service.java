package ubb.scs.map.ati.service;

import ubb.scs.map.ati.domain.Bed;
import ubb.scs.map.ati.domain.Patient;
import ubb.scs.map.ati.repository.BedRepository;
import ubb.scs.map.ati.repository.PatientRepository;
import ubb.scs.map.ati.utils.Observable;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Service extends Observable {
    private BedRepository bedRepository;
    private PatientRepository patientRepository;

    public Service(BedRepository bedRepository, PatientRepository patientRepository) {
        this.bedRepository = bedRepository;
        this.patientRepository = patientRepository;
    }

    public List<Bed> getAllBeds(){
        return bedRepository.findAll();
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Bed getBedById(int id){
        return bedRepository.findById(id);
    }

    public Patient getPatientById(int id){
        return patientRepository.findById(id);
    }

    public Patient getPatientByCnp(String cnp){
        return patientRepository.findByCnp(cnp);

    }

    public void updateBed(Bed availableBed) {
        bedRepository.update(availableBed);
        notifyObservers();
    }

    public List<Patient> getUnassignedPatients() {
        List<Patient> allPatients = patientRepository.findAll();
        List<String> assignedPatientCNPs = bedRepository.findAll().stream()
                .map(bed -> bed.getPersonCnp())
                .collect(Collectors.toList());
        return allPatients.stream()
                .filter(patient -> !assignedPatientCNPs.contains(patient.getCnp()))
                .collect(Collectors.toList());
    }
}
