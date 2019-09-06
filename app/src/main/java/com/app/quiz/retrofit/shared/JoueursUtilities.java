package com.app.quiz.retrofit.shared;

import com.app.quiz.retrofit.repository.JoueursRepository;

public class JoueursUtilities {

    // constructeur
    private JoueursUtilities() {
    }

    // lien d'acces a l'API
    public static final String API_URL = "https://soulquiz.herokuapp.com/api/joueurs/";

    //
    public static JoueursRepository getJoueursService(){

        // retourne la fabrique de retrofit
        return RetrofitSingleton
                .getClient(API_URL)
                .create(JoueursRepository.class);
    }
}
