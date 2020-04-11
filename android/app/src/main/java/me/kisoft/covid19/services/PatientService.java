package me.kisoft.covid19.services;

import java.util.List;

import me.kisoft.covid19.models.MedicalProfile;
import me.kisoft.covid19.models.Patient;
import me.kisoft.covid19.models.Question;
import me.kisoft.covid19.models.Symptom;

public interface PatientService {

    Patient login(String username, String password);

    Boolean register(Patient patient);

    List<Question> getQuestions();

    List<Symptom> getSymptoms();

    Boolean addSymptom(Symptom symptom);

    Boolean answerQuestion(Question question);

    Boolean createMedicalProfile(MedicalProfile profile);
}
