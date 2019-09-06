package com.app.quiz.activeandroid.dao.rest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.LoginActivity;
import com.app.MainActivity;
import com.app.quiz.activeandroid.dao.JoueursDao;
import com.app.quiz.activeandroid.model.Joueurs;
import com.app.quiz.retrofit.repository.JoueursRepository;
import com.app.quiz.retrofit.shared.JoueursUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoueurRestDao {

    //objets
    private JoueursRepository joueursRepository;
    private Joueurs joueursRecup;

    private JoueursDao joueursDao;

    // variable pour le message de log
    private  static final String TAG ="JoueurRestDao";

    private String message;

    // constructeur
    public JoueurRestDao() {
        //
        joueursRepository = JoueursUtilities.getJoueursService();
        joueursDao = new JoueursDao();
    }

    // methode d'enregistrement
    public Joueurs save(Joueurs joueurs, final Context context){
        Call<Joueurs> call = joueursRepository.addJoueurs(joueurs);
        call.enqueue(new Callback<Joueurs>() {
            @Override
            public void onResponse(Call<Joueurs> call, Response<Joueurs> response) {
                if (response.isSuccessful()){
                    joueursRecup = response.body();
                    Log.i(TAG,"**** bien ajoutée ****");

                    // souvegarde des infos dans la BD lors de la connexion
                    if(joueursDao.add(response.body()) > 0){
//                    joueursDao.add(response.body());
                        Log.i(TAG,"**** save Historique *****");
                        //
                        Toast.makeText(context, "Enregistrement effectué avec succès", Toast.LENGTH_SHORT).show();
                    }

                    // appelle de la vue suivante apre enregistrement
                    Intent intent=new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Joueurs> call, Throwable t) {
                Log.i(TAG,t.getMessage());
            }
        });
        return joueursRecup;
    }

    public void login(String numero, String password, final Context context, final ProgressBar progressBarLogin, final TextView editTextConnexion ){
        Log.i(TAG,"****** debutLogin ******");
        Log.i(TAG,numero);
        Log.i(TAG,password);


        // visibilité false au premier lancement de la progressBar et du texte
        progressBarLogin.setVisibility(View.VISIBLE);
        editTextConnexion.setVisibility(View.VISIBLE);

        // condition en cas de nouveau clique sur le bouton connexion
        if (editTextConnexion.getText().toString().equals("Echec de connexion")){
            editTextConnexion.setText("Tentative de reconnexion ...");
        }

        Call<Joueurs>  call= joueursRepository.login(numero,password);
        call.enqueue(new Callback<Joueurs>() {
            @Override
            public void onResponse(Call<Joueurs> call, Response<Joueurs> response) {
                Log.i(TAG,"********* loginOnResponse *********");
                Log.i(TAG,response.code()+"");
                Log.i(TAG,response.toString());

                //condition pour savoir si la requete a été bien réalisée
                if (response.isSuccessful()){
                   // message =  response.body()+"";
                    Log.i(TAG,"succes");

                    Log.i(TAG,"***** succesOnResponse" + " " + response.body() );
                    joueursRecup = response.body();
                    Log.i(TAG,"****** succesOnResponse2" + " " + joueursRecup);

                    //condition de souvegarde des infos dans la BD lors de la connexion
                    if(joueursDao.add(joueursRecup) > 0){
//                        joueursDao.add(response.body());
                        Toast.makeText(context,"Connexion reussie ..", Toast.LENGTH_SHORT).show();
                    }
                    // appelle de l'activité
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    Log.i(TAG,"**** lanceMainActivityOnResponse *****");


                }
            }

            @Override
            public void onFailure(Call<Joueurs> call, Throwable t) {
                Log.i(TAG,"**** errorConexion ****");
                Log.i(TAG,t.getMessage());
                Log.i(TAG,t.toString());

                // visibilité false en cas d'erreur de la connexion et echangement du texte
                progressBarLogin.setVisibility(View.INVISIBLE);
                editTextConnexion.setText("Echec de connexion");
            }
        });
//        return message;
    }
}
