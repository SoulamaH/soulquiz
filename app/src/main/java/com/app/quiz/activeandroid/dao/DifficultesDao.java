package com.app.quiz.activeandroid.dao;

import com.activeandroid.query.Select;
import com.app.quiz.activeandroid.model.Categories;
import com.app.quiz.activeandroid.model.Difficultes;

import java.util.List;

public class DifficultesDao {

    //CRUD
    // methode pour faire un ajout
    public  Long add(Difficultes difficultes){
        return difficultes.save();
    }

    //retourne la liste de tous les elements
    public List<Difficultes> getAll(){
        return new Select()
                .from(Difficultes.class)
                .execute();
    }

    // Retourne un element par nom
    public Difficultes getOneByName(String name){
        return new Select()
                .from(Difficultes.class)
                .where("niveauDifficulte = ?", name)
                .executeSingle();
    }


    // enregistrement des categories
    public void enregistrementParDefault(){
        Difficultes d1 = new Difficultes("facile");
        Difficultes d2 = new Difficultes("moyen");
        Difficultes d3 = new Difficultes("difficile");

        // faire les enregistrements grace a la methode add plus haut
        add(d1);
        add(d2);
        add(d3);
    }
}
