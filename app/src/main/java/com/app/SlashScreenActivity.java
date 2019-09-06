package com.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.quiz.activeandroid.dao.JoueursDao;

import app.com.quizapplication.R;

public class SlashScreenActivity extends AppCompatActivity {

    // objects
    private Intent intent;

    private JoueursDao joueursDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // appelle de la methode
                historiqueConnexion();
                startActivity(intent);
                finish();
            }
        },1000);
    }

    //garde l'historique de la connexion
    private void historiqueConnexion(){
        //
        joueursDao = new JoueursDao();

        // si liste de joueurs vide redirection sur l'interface de connexion
        // ce qui veut dire que l'utilisateur s'est deconnecté
        // sinon dans le menu du jeu
        if (joueursDao.getAll().isEmpty()){
            intent = new Intent(SlashScreenActivity.this,LoginActivity.class);
        }else{
            intent = new Intent(SlashScreenActivity.this,MainActivity.class);
        }
    }
}
