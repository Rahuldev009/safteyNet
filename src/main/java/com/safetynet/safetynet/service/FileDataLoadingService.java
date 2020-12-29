package com.safetynet.safetynet.service;

import com.google.gson.Gson;
import com.safetynet.safetynet.constants.FileUrl;
import com.safetynet.safetynet.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Component
public class FileDataLoadingService {

    private List<PersonEntity> persons;
    private List<FirestationEntity> firestations;
    private List<MedicalRecordEntity> medicalRecords;
    private static final Logger logger = LogManager.getLogger(FileDataLoadingService.class);

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public List<FirestationEntity> getFirestations() {
        return firestations;
    }

    public List<MedicalRecordEntity> getMedicalRecords() {
        return medicalRecords;
    }

    @PostConstruct
    public void init() {
       // Properties properties = new Properties();
       // properties.load(new FileInputStream("src/main/resources/data/data.json"));

        //File reading logic
        String dataUrl = FileUrl.url;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(dataUrl));
        } catch (FileNotFoundException e) {
            logger.error("File cannot be found, check path of file");
        }
        //Json Parse the file inputs
        Gson gson = new Gson();
        InputFileDTO inputFileDTO = gson.fromJson(bufferedReader, InputFileDTO.class);

        //Populate DTOs as needed
        persons = inputFileDTO.getPersons();
        firestations = inputFileDTO.getFirestations();
        medicalRecords = inputFileDTO.getMedicalrecords();

    }

    public boolean updateDataFile(List<PersonEntity> persons, List<MedicalRecordEntity> medicalRecords,
                               List<FirestationEntity> firestations) {
        String dataUrl = FileUrl.url;
        InputFileDTO inputFileDTO = new InputFileDTO();
        if (persons != null){
            inputFileDTO.setPersons(persons);
            inputFileDTO.setFirestations(getFirestations());
            inputFileDTO.setMedicalrecords(getMedicalRecords());
        }
        else if (medicalRecords != null) {
            inputFileDTO.setMedicalrecords(medicalRecords);
            inputFileDTO.setPersons(getPersons());
            inputFileDTO.setFirestations(getFirestations());
        }
        else if (firestations != null){
            inputFileDTO.setFirestations(firestations);
            inputFileDTO.setPersons(getPersons());
            inputFileDTO.setMedicalrecords(getMedicalRecords());
        }
        Gson gson = new Gson();
        String outputFileData = gson.toJson(inputFileDTO);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(dataUrl));
            bufferedWriter.write(outputFileData);
            bufferedWriter.close();
        } catch (Exception ex) {
            logger.error("cannot write to file " + ex);
            return false;
        }
        logger.info("Data written to the file "+ outputFileData);
        return true;
    }

}
