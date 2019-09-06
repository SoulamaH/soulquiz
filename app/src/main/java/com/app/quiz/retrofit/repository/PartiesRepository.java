package com.app.quiz.retrofit.repository;

import com.app.quiz.activeandroid.model.Parties;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PartiesRepository {

    //https://soulquiz.herokuapp.com/api/parties/

    @POST("./")
    Call<List<Parties>> addParties(@Body Parties parties);

    // retourner le meilleur score
    @GET("bestscore/")
    Call<Parties> getFirstScore(@Body Parties parties);

    // retoune les meilleurs score
    @GET("lesbestscore/")
    Call<Parties>   getBestScore();


}
