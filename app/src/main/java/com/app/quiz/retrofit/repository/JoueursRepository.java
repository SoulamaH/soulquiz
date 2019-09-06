package com.app.quiz.retrofit.repository;

import com.app.quiz.activeandroid.model.Joueurs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JoueursRepository {

    // methode de login
    @FormUrlEncoded
    @POST("./login")
    Call<Joueurs> login(@Field("numero") String numero, @Field("password") String password);

    // methde pour ajouter un joueur
    @POST("./")
    Call<Joueurs> addJoueurs(@Body Joueurs joueurs);

}
