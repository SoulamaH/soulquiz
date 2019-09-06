package com.app.quiz.activeandroid.model;

import com.activeandroid.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Parties extends Model implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer code;

    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("joueurs")
    @Expose
    private Joueurs joueurs;

    @SerializedName("categories")
    @Expose
    private Categories categories;

    @SerializedName("difficultes")
    @Expose
    private Difficultes difficultes;

    // constructeurs

    public Parties() {
        super();
    }

    public Parties(Integer score, Joueurs joueurs, Categories categories, Difficultes difficultes) {
        super();
        this.score = score;
        this.joueurs = joueurs;
        this.categories = categories;
        this.difficultes = difficultes;
    }
}
