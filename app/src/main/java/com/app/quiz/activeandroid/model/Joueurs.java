package com.app.quiz.activeandroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Joueurs extends Model implements Serializable {

//    @SerializedName("id")
//    @Expose
//    @Column(name = "code")
//    private Integer code;

    @SerializedName("nom")
    @Expose
    @Column(name = "nom")
    private String nom;

    @SerializedName("prenom")
    @Expose
    @Column(name = "prenom")
    private String prenom;

    @SerializedName("numero")
    @Expose
    @Column(name = "numero")
    private String numero;

    @SerializedName("email")
    @Expose
    @Column(name = "email")
    private String email;

    @SerializedName("password")
    @Expose
    @Column(name = "password")
    private String password;

    public Joueurs() {
        super();
    }

    // constructeur sans id
    public Joueurs(String nom, String prenom, String numero, String email, String password) {
       super();
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.email = email;
        this.password = password;
    }
}
