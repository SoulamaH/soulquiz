package com.app.quiz.retrofit.shared;

import com.app.quiz.retrofit.repository.PartiesRepository;

public class PartiesUtilities {

    public PartiesUtilities() {
    }

    // lien d'acces an l'API
    public static final String API_URL = "https://soulquiz.herokuapp.com/api/parties/";

        public static PartiesRepository getPartiesService(){

            return RetrofitSingleton
                    .getClient(API_URL)
                    .create(PartiesRepository.class);
        }
}
