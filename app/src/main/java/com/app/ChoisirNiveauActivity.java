package com.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.quizapplication.R;

public class ChoisirNiveauActivity extends AppCompatActivity {

    //les proprietes
    private LinearLayout linearChoixDifficile;
    private TextView textViewChoixFacile;
    private TextView textViewChoixMoyen;
    private TextView selectDifficile;

    private ImageView imageViewChoixNiveau;


    // variable pour recuperer le langage
    private String recupLangage;

    // variable static pour le niveau selectionné
    public static final String NIVEAU_SELECTIONNE = "niveauSelectionne";

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choisir_niveau);

        // liaison
        linearChoixDifficile = findViewById(R.id.lnchoixdifficile);
        textViewChoixMoyen = findViewById(R.id.lnchoixmoyen);
        textViewChoixFacile = findViewById(R.id.lnchoixfacile);
        selectDifficile = findViewById(R.id.select_difficulte);

        imageViewChoixNiveau = findViewById(R.id.image_logo_choix_niveau);


        //recuperation de la valeur du langage selectionné
        recupLangage = getIntent().getStringExtra(MainActivity.LANGAGE_SELECTIONNEE);


        // appel de la methode pour debuter l'action
        choixNiveau();
        selectLogo();

    }


    // methode pour le logo
    private void selectLogo(){

        //changement du logo lors du toucher
        switch (recupLangage){
            case "java":
                imageViewChoixNiveau.setImageResource(R.mipmap.javarounded);
                return;
            case "spring":
                imageViewChoixNiveau.setImageResource(R.mipmap.springrounded);
                return;
            case "angular":
                imageViewChoixNiveau.setImageResource(R.mipmap.angularrounded);
                return;
            case "android":
                imageViewChoixNiveau.setImageResource(R.mipmap.androidrounded);
                return;
        }
    }

    // methode pour le niveau
    public void choixNiveau(){
        //methode pour ecouter lors du clique sur les
        linearChoixDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(ChoisirNiveauActivity.this,QuestionnaireActivity.class);
                // recuperation du niveau sectionné
                intent.putExtra(NIVEAU_SELECTIONNE,"difficile");
                // recuperation du langage sectionné depuis le MAinActivity
                intent.putExtra(MainActivity.LANGAGE_SELECTIONNEE, recupLangage);

                // changement de la couleur lors du toucher
                /**
                 * si clic sur difficile on passe a orenge et les autres passent a la couleur parDeault
                 */
                selectDifficile.setTextColor(Color.parseColor("#D97D54"));
                textViewChoixMoyen.setTextColor(Color.parseColor("#324755"));
                textViewChoixFacile.setTextColor(Color.parseColor("#324755"));

                // cette methode attend un thread et un delais
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);

                    }
                },1000);

            }
        });

        // methode pour ecouter lors du clique sur les
        textViewChoixMoyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent=new Intent(ChoisirNiveauActivity.this,QuestionnaireActivity.class);
                // recuperation du niveau sectionné
                intent.putExtra(NIVEAU_SELECTIONNE,"moyen");
                // recuperation du langage sectionné depuis le MAinActivity
                intent.putExtra(MainActivity.LANGAGE_SELECTIONNEE, recupLangage);

                // changement de la couleur lors du toucher
                /**
                 * si clic sur moyen on passe a orange et les autres passent a la couleur parDeault
                 */
                textViewChoixMoyen.setTextColor(Color.parseColor("#D97D54"));
                textViewChoixFacile.setTextColor(Color.parseColor("#324755"));
                selectDifficile.setTextColor(Color.parseColor("#324755"));

                // cette methode attend un thread et un delais
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);

                    }
                },1000);

            }
        });

        // methode pour ecouter lors du clique sur les
        textViewChoixFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent=new Intent(ChoisirNiveauActivity.this,QuestionnaireActivity.class);
                // recuperation du niveau sectionné
                intent.putExtra(NIVEAU_SELECTIONNE,"facile");
                // recuperation du langage sectionné depuis le MAinActivity
                intent.putExtra(MainActivity.LANGAGE_SELECTIONNEE, recupLangage);

                // changement de la couleur lors du toucher
                /**
                 * si clic sur facile on passe a orange et les autres passent a la couleur parDeault
                 */
                textViewChoixFacile.setTextColor(Color.parseColor("#D97D54"));
                selectDifficile.setTextColor(Color.parseColor("#324755"));
                textViewChoixMoyen.setTextColor(Color.parseColor("#324755"));

                // cette methode attend un thread et un delais pour executer l'activité
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);

                    }
                },1000);

            }
        });
    }

}
