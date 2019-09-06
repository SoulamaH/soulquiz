package com.app.quiz.activeandroid.dao.rest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.MainActivity;
import com.app.PartiesActivity;
import com.app.quiz.ChargementScoreActivity;
import com.app.quiz.activeandroid.model.Parties;
import com.app.quiz.retrofit.repository.PartiesRepository;
import com.app.quiz.retrofit.shared.PartiesUtilities;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartiesRestDao {

    // objets
    private PartiesRepository partiesRepository;
    private Parties partiesLog;

    // variriable LOG
    public static final String TAG = "PartiesRestDao";

    private List<Parties>partiesList;


    // constructeur
    public PartiesRestDao() {
        partiesRepository = PartiesUtilities.getPartiesService();
    }

    // ajout de la partie
    public Parties savePartie(final Parties parties, final Activity activity, final Context context, final ImageView imageViewReload, final ProgressBar progressBar, final TextView textViewChargement){
        parties.setCode(0);
        parties.getCategories().setCode(0L);
        Log.i(TAG,"****** savePartie :" + " " + parties);

        Call<List<Parties>> call = partiesRepository.addParties(parties);
        Log.i(TAG,"****** bien ajoutée1 call***** : " + call);

        // invisibilité de l'image de chargement
        imageViewReload.setVisibility(View.INVISIBLE);

        // visibilite de la progressBar
        progressBar.setVisibility(View.VISIBLE);
        // changement du textVie
        textViewChargement.setText("Chargement ...");

        call.enqueue(new Callback<List<Parties>>() {
            @Override
            public void onResponse(Call<List<Parties>> call, Response<List<Parties>> response) {
                Log.i(TAG,"******* bien ajoutée2" +" "+ response.code());

                // condition sur la reponse
                if (response.isSuccessful()){
                    Log.i(TAG,"***** bien ajoutée3 ******" + response.body());
                   // partiesLog = response.body();
                    partiesList = response.body();

                    ///***************
                    // Dialogue pour l'afficharge des meilleurs score
                    // ou retoure sur la page d'acueil
                    new TTFancyGifDialog.Builder(activity)
                            .setTitle("Score ...")
                            .setMessage("Partie terminée, votre score : " +parties.getScore())
                            .setPositiveBtnText("Meilleurs Score")
                            .setPositiveBtnBackground("#D97D54")
                            .setNegativeBtnText("Accueil")
                            .setNegativeBtnBackground("#BAAEA0")
            //              .setGifResource(R.drawable.)
                            .isCancellable(true)
                            .OnPositiveClicked(new TTFancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    Intent intent = new Intent(activity, PartiesActivity.class);

                                    intent.putExtra("chargeGraphe", (Serializable) partiesList);

                                    Toast.makeText(activity,"Affichage des meilleurs scores ...",Toast.LENGTH_SHORT).show();
                                    context.startActivity(intent);
                                }
                            })
                            .OnNegativeClicked(new TTFancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    Intent intent = new Intent(activity, MainActivity.class);
                                    Toast.makeText(activity,"Retour sur l'accueil...", Toast.LENGTH_SHORT).show();
                                    context.startActivity(intent);
                                }
                            })
                            .build();
                    //*********
                }else{
                    Log.i(TAG,"****** savePartier else");
                    imageViewReload.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);// disparution de l'objet
                    textViewChargement.setText("Erreur de chargement !");
                }
            }

            @Override
            public void onFailure(Call<List<Parties>> call, Throwable t) {
                Log.i(TAG,t.getMessage());
                Log.i(TAG,"****** savePartier OnFailure");
                imageViewReload.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);// disparution de l'objet

            }
        });

        return partiesLog;
    }

    //
    public Parties getOneParties (){


        return null;
    }

    // ramene les 5 meilleurs score
    public List<Parties> getBestScore(){

        Log.i(TAG,"****** getBestScore Parties *****");
        Call<Parties>call = partiesRepository.getBestScore();
        call.enqueue(new Callback<Parties>() {
            @Override
            public void onResponse(Call<Parties> call, Response<Parties> response) {

                Log.i(TAG,"****** getBestScore partie onResponse *******" );
                if (response.isSuccessful()){
                    Log.i(TAG,"****** getBestScore Parties If : " + response.code());
                    response.code();
                    Log.i(TAG,"****** getBestScore Parties If : " + response.body() );
                }
            }

            @Override
            public void onFailure(Call<Parties> call, Throwable t) {
                Log.i(TAG,"****** getBestScore Parties onFailure ****** "  );


            }
        });


        return null;
    }

    public List<Parties> getBestScoreChargement(){
        return partiesList;
    }
}
