package com.app.quiz.activeandroid.dao;

import com.app.quiz.activeandroid.model.Parties;

public class PartiesDao {

    // ajoute dun element
    public Long add(Parties parties){
        return parties.save();
    }
}
