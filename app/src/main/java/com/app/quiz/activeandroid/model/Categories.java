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

@Table(name = "Categories")
@Getter
@Setter
@ToString
public class Categories extends Model implements Serializable {

    //propriete
    @SerializedName("id")
    @Expose
    @Column(name="code", unique = true)
    private Long code;

    // pour le nom des langages
    @SerializedName("name")
    @Expose
    @Column(name = "name")
    private String name;

    // constructeur
    public  Categories(){
        super();
    }

    // constructeur sans code
    public Categories(String name) {
        super();
        this.name = name;
    }

    //relation de mapping entre la table Questions et la table Categories
    public List<Questions> listeQuestion() {
        return getMany(Questions.class, "categorieId");
    }
}
