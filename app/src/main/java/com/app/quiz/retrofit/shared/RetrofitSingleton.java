package com.app.quiz.retrofit.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    // variable
    private static Retrofit retrofit = null;

    //constructeur
    private RetrofitSingleton() {
    }

    // fabrique de retrofit
    public static Retrofit getClient(String url){

//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();


        retrofit = null;
        retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL,Modifier.TRANSIENT, Modifier.STATIC).serializeNulls().create()))
                .build();

//        if (retrofit == null){
//            retrofit = new Retrofit
//                    .Builder()
//                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
        //retourne le retrofit
        return retrofit;
    }
}
