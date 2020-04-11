/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.kisoft.covid19.infra.core.service.rest;

import io.javalin.http.Context;
import me.kisoft.covid19.domain.auth.entity.User;
import me.kisoft.covid19.domain.auth.repo.UserRepository;
import me.kisoft.covid19.domain.core.service.PatientService;
import me.kisoft.covid19.domain.core.entity.MedicalProfile;
import me.kisoft.covid19.domain.core.entity.Question;
import me.kisoft.covid19.domain.core.entity.Symptom;
import me.kisoft.covid19.infra.auth.factory.UserRepositoryFactory;
import me.kisoft.covid19.infra.core.factory.PatientServiceFactory;

/**
 *
 * @author tareq
 */
public class PatientRestService {

    PatientService patientService = PatientServiceFactory.getInstance().get();

    public void signUp(Context ctx) throws Exception {
        try ( UserRepository repo = UserRepositoryFactory.getInstance().get()) {
            User toCreate = ctx.bodyAsClass(User.class);
            if (repo.getUserByUsername(toCreate.getUsername()) == null) {
                patientService.createPatient(toCreate);
                ctx.res.setStatus(200);
            } else {
                ctx.res.setStatus(400);
            }
        }
    }

    public void updateMedicalProfile(Context ctx) {
        User user = ctx.sessionAttribute("user");
        patientService.updatePatientMedicalProfile(user.getId(), ctx.bodyAsClass(MedicalProfile.class));
        ctx.res.setStatus(200);
    }

    public void getMedicalProfile(Context ctx) {
        User user = ctx.sessionAttribute("user");
        ctx.json(patientService.getPatientMedicalProfile(user.getId()));
        ctx.res.setStatus(200);
    }

    public void getUnansweredQuestions(Context ctx) {
        User user = ctx.sessionAttribute("user");
        ctx.json(patientService.getPatientUnansweredQuestions(user.getId()));
        ctx.res.setStatus(200);
    }

    public void getReccomendations(Context ctx) {
        User user = ctx.sessionAttribute("user");
        ctx.json(patientService.getPatientReccomendations(user.getId()));
        ctx.res.setStatus(200);
    }
    
    public void answerQuestion(Context ctx){
          User user = ctx.sessionAttribute("user");
          Long questionId = ctx.pathParam("id", Long.class).get();
          Question question = ctx.bodyAsClass(Question.class);
          patientService.answerPatientQuestion(user.getId(), questionId,question.getAnswer());
          ctx.status(200);
    }
    
    public void addSymptom(Context ctx){
          User user = ctx.sessionAttribute("user");
          patientService.addNewPatientSymptom(user.getId(), ctx.bodyAsClass(Symptom.class));
          ctx.status(200);
    }
}
