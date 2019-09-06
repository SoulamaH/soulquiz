package com.app.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.quiz.activeandroid.dao.rest.PartiesRestDao;
import com.app.quiz.activeandroid.model.Parties;

import app.com.quizapplication.R;

public class ChargementScoreActivity extends AppCompatActivity {

    private PartiesRestDao partiesRestDao;
    private Parties parties;
    private ImageView imageViewReload;
    private ProgressBar progressBarChargement;
    private TextView textViewChargement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement_score);

        imageViewReload = findViewById(R.id.reload);
        progressBarChargement = findViewById(R.id.chargement_score_progressbar);
        textViewChargement = findViewById(R.id.text_chargement);

        // instantiation
        partiesRestDao = new PartiesRestDao();

        parties = (Parties) getIntent().getSerializableExtra("partieTerminer");

        partiesRestDao. savePartie(parties,ChargementScoreActivity.this,this, imageViewReload, progressBarChargement, textViewChargement);

        // permet de relancer le chargement pour l'enregistrement
        imageViewReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partiesRestDao. savePartie(parties,ChargementScoreActivity.this,ChargementScoreActivity.this, imageViewReload, progressBarChargement, textViewChargement);
            }
        });


    }


}
