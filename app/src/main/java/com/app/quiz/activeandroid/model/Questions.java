package com.app.quiz.activeandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "Questions")
public class Questions extends Model implements Serializable {

    // proprietes
    @SerializedName("id")
    @Expose
    @Column(name = "code")
    private int code;

    @SerializedName("questions")
    @Expose
    @Column(name = "question")
    private  String question;

    @SerializedName("option1")
    @Expose
    @Column(name = "option1")
    private  String option1;

    @SerializedName("option2")
    @Expose
    @Column(name = "option2")
    private  String option2;

    @SerializedName("option3")
    @Expose
    @Column(name = "option3")
    private  String option3;

    @SerializedName("reponses") // cette ecriture doit identiquea celle du modele de mon api
    @Expose
    @Column(name = "reponse")
    private  int reponse;

    // mes Clés étrangère
    @SerializedName("difficultes")
    @Expose
    @Column(name = "difficulteId") // utiliser ce meme nom lors du mapping
    private Difficultes difficulteId;

    @SerializedName("categories")
    @Expose
    @Column(name = "categorieId") // utiliser ce meme nom lors du mapping
    private Categories categorieId;


    public Questions() {
        super();
    }

    // Constructeur sans code = id
       public Questions(String question, String option1, String option2, String option3, int reponse, Difficultes difficulteId, Categories categorieId) {
        super();
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.reponse = reponse;
        this.difficulteId = difficulteId;
        this.categorieId = categorieId;
    }

}
