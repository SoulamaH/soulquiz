package com.app.quiz.activeandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "Difficultes")
public class Difficultes extends Model implements Serializable {

    //propriete
//    @Column(name="code", unique = true)
//    private int code;

    @SerializedName("niveauDifficultes")
    @Expose
    @Column(name = "niveauDifficulte")
    private String niveauDifficulte;


    public Difficultes() {
        super();
    }

    //
    public Difficultes(String niveauDifficulte) {
        super();
        this.niveauDifficulte = niveauDifficulte;
    }

    //relation de mapping entre la table Questions et la table Difficultes
    public List<Questions> listeQuestion() {
        return getMany(Questions.class, "difficulteId");
    }
}
