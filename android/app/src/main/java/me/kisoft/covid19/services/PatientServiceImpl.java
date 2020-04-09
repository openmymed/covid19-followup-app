package me.kisoft.covid19.services;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import me.kisoft.covid19.models.Patient;
import me.kisoft.covid19.models.Question;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PatientServiceImpl implements PatientService {
    final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    final String LOGIN_URL = "https://covid19-staging.kisoft.me/login";
    final String REGISTER_URL = "https://covid19-staging.kisoft.me/patient/signup";
    OkHttpClient client = new OkHttpClient();

    @Override
    public Patient login(String username, String password) {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        try {
            Response response = post(LOGIN_URL, json);
            if (response.isSuccessful()) {
                String jsonRes = response.body().string();
                return gson.fromJson(jsonRes, Patient.class);
            } else {
                if (response.code() == 401 || response.code() == 403) {
                    return null;
                }
                return null;//what to return? if 500 or something else
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    @Override
    public Boolean register(Patient patient) {
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        try {
            Response response = post(REGISTER_URL, json);
            if (response.isSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }

    @Override
    public List<Question> getQuestions() {
        return null;
    }

    //from OKHttp
    private Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    //from OKHttp
    private Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }

}
