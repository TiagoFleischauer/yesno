package com.example.yesno;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.yesno.Answer;

public interface RestService {
    @GET("api/")
    Call<Answer> getAnswer();

}