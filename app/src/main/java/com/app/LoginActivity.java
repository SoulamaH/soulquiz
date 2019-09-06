package com.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.quiz.activeandroid.dao.rest.JoueurRestDao;
import com.libizo.CustomEditText;


import app.com.quizapplication.R;

public class LoginActivity extends AppCompatActivity {

    // variables
    private CustomEditText editTextNumeroLogin;
    private CustomEditText editTextPasswordLogin;
    private TextView textViewViewInscription;

    private TextView editTextConnexion;
    private ProgressBar progressBarLogin;

    private ImageView btnValiderLogin;

    private Toolbar toolbarLogin;


    // objets
    private JoueurRestDao joueurRestDao;

    // log
    public static final String TAGLOG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // liaison
        toolbarLogin = findViewById(R.id.toolBarHeader);
        editTextNumeroLogin = findViewById(R.id.login_numero);
        editTextPasswordLogin = findViewById(R.id.login_password);
        btnValiderLogin = findViewById(R.id.login_valider_img);

        textViewViewInscription = findViewById(R.id.inscrire_register);

        editTextConnexion = findViewById(R.id.text_connexion);
        progressBarLogin = findViewById(R.id.login_progressbar);

        // visibilité false au premier lancement de la progressBar et du texte
        progressBarLogin.setVisibility(View.INVISIBLE);
        editTextConnexion.setVisibility(View.INVISIBLE);

        // appelle de la methode pour l'authentification
        clicBouton(this);


        // appelle de la methode pour aller sur la page de connexion au clique
        pageRegister();
    }


    private  void  clicBouton(final Context context){
        //action du bouton
        btnValiderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialision de l'objet
                joueurRestDao = new JoueurRestDao();
                //recuperation des info saisies
                joueurRestDao.login(editTextNumeroLogin.getText().toString(),editTextPasswordLogin.getText().toString(), context, progressBarLogin, editTextConnexion);


                //
//                initChamps();
            }
        });

    }

    // methode permettant d'aller sur la page de connexion
    public void pageRegister (){
        // clique pour l'inscription
        textViewViewInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAGLOG,"inscriptionOnClick");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                Log.i(TAGLOG,"inscription fin");
            }
        });
    }

    // methode pour initialisation des champs de saisie
    // apres clic sur boutonValider
    public void initChamps(){
        editTextNumeroLogin.getText().clear();
        editTextPasswordLogin.getText().clear();
    }
}
