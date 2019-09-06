package com.app.quiz.activeandroid.dao;

import android.util.Log;

import com.activeandroid.query.Select;
import com.app.quiz.activeandroid.model.Joueurs;

import java.util.List;

public class JoueursDao {

    public static final String TAG = "JoueursDao";

    /** les methodes CRUD*/

    // ajout d'un element (le void a ete mis pour un test de retour ajoutée1)
    public Long add(Joueurs joueurs){
        Joueurs joueurs1 = new Joueurs();

        joueurs1.setNom(joueurs.getNom());
        joueurs1.setPrenom(joueurs.getPrenom());
        joueurs1.setEmail(joueurs.getEmail());
        joueurs1.setNumero(joueurs.getNumero());
        joueurs1.setPassword(joueurs.getPassword());

        Log.i(TAG,"***** JoueursDao ajoutée2 :" + " "+joueurs1 );

        return joueurs1.save();
//        Log.i(TAG,"***** JoueursDao ajoutée1 :" + " " + getAll() );

    }

    //retourne un element
    public Joueurs getOne(Integer code){
        return new Select()
                .from(Joueurs.class)
                .where("code =?",code)
                .executeSingle();
    }

    // update
    public Long update(Joueurs joueurs){
        // si joueur different de zero on fait un save
//        if (getOne(joueurs.getCode()) != null){
//            return joueurs.save();
//        }
        return 0L;
    }

    //delete
    public void delete(Joueurs joueurs){
        joueurs.delete();
    }

    // retourn une liste de joueur
    public List<Joueurs> getAll(){
        return new Select()
                .from(Joueurs.class)
                .execute();
    }
}
