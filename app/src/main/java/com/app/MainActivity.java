package com.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quiz.JavaActivity;
import com.app.quiz.activeandroid.dao.CategoriesDao;
import com.app.quiz.activeandroid.dao.DifficultesDao;
import com.app.quiz.activeandroid.dao.JoueursDao;
import com.app.quiz.activeandroid.dao.QuestionsDao;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;


import app.com.quizapplication.R;

public class MainActivity extends AppCompatActivity {

    // proprietes pour faire la liaison avec notre xml

    private Toolbar toolbar;
    private CardView cardViewJava;
    private CardView cardViewSpring;
    private CardView cardViewAngular;
    private CardView cardViewAndroid;

    private TextView msgBouton;


    // faire des enregistrement dans la base de donnée
    private CategoriesDao categoriesDao;
    private DifficultesDao difficultesDao;
    private QuestionsDao questionsDao;


    //variables pour gerer le meilleurScore = highScore
    private static final int REQUEST_CODE_QUIZ = 1;
    public static  final  String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    // categorie
    public static final String EXTRA_CATEGORIE_ID = "extraCategorieId";
    public static final String EXTRA_CATEGORIE_NAME = "extraCategorieName";
    // difficulté
    public static final String EXTRA_DIFFICULTY = "extraDifficulte";

    // private TextView textViewHighScore;
    private int highScore;

    private LinearLayout lnNextLang;
    // variables etat bouton( pour jouer sur l'etat des boutons)
    private int etatBouton = 0; // initialisation


    // variable static pour la selection du du langage
    public static final String LANGAGE_SELECTIONNEE  = "langageSelectionnee";

    // variable
    private long btnRetour;

    // objets
    private JoueursDao joueursDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // liaisons des varibles cardView, linearLayout, textView avec le xml
        cardViewJava = findViewById(R.id.cardview_java);
        cardViewSpring = findViewById(R.id.cardview_spring);
        cardViewAngular = findViewById(R.id.cardview_angular);
        cardViewAndroid = findViewById(R.id.cardview_android);

        msgBouton = findViewById(R.id.msg_bouton);

        lnNextLang=findViewById(R.id.btn_nextwithlanguage);


        // appel de methode pour débuter l'activité
        debuterQuiz();


        loadHighScore(); // charge le score

        // toolbar
        toolbar = findViewById(R.id.toolBarHeader);
        toolbar.setTitle("Java Quiz");
        setSupportActionBar(toolbar);

        // besoin de l'instant pour l'enregistrement
        // sinon error
        categoriesDao = new CategoriesDao();
        difficultesDao = new DifficultesDao();
        questionsDao = new QuestionsDao();

        //condition pour faire les enregistrements
        // si categorie, difficulte et question sont null on fait un enregistrement
        if (categoriesDao.getAll().isEmpty()){
            categoriesDao.enregistrementParDefault();
        }

        if (difficultesDao.getAll().isEmpty()){
            difficultesDao.enregistrementParDefault();
        }

        if (questionsDao.getAll().isEmpty()){
            questionsDao.enregistrementParDefault();
        }
    }


    // methodes pour les actions lors du clic sur les images
    private void debuterQuiz() {

        // methode pour ecouter lors du clique sur les cardViews (Java, Spring, Angulr, Android)
        cardViewJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etatBouton = 1;
                msgBouton.setText("Continuer avec Java");

            }
        });

        cardViewSpring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etatBouton = 2;
                // change le message dans le layout en bas
                msgBouton.setText("Continuer avec Spring");
            }
        });

        cardViewAngular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etatBouton = 3;
                // change le message dans le layout en bas
                msgBouton.setText("Continuer avec Angular");
            }
        });

        cardViewAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etatBouton = 4;
                // change le message dans le layout en bas
                msgBouton.setText("Continuer avec Android");
            }
        });


        // ecoute sur msg_bouton
        lnNextLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChoisirNiveauActivity.class);
                /*startActivity(intent);*/

                //jouons sur la valeur etatBouton
                switch (etatBouton ){
                    case 0:
                        Snackbar.make(v,"Cliquez sur un langage avant", Snackbar.LENGTH_SHORT).show();
                        return ;

                    case 1:
                        intent.putExtra(LANGAGE_SELECTIONNEE,"java");
                        startActivity(intent);
                        return;

                    case 2:
                        intent.putExtra(LANGAGE_SELECTIONNEE,"spring");
                        startActivity(intent);
                        return;

                    case 3:
//                        intent.putExtra(LANGAGE_SELECTIONNEE,"angular");
//                        startActivity(intent);
                        Snackbar.make(v,"En cours de développement", Snackbar.LENGTH_LONG).show();
                        return;

                    case 4:
//                        intent.putExtra(LANGAGE_SELECTIONNEE,"android");
//                        startActivity(intent);
                        Snackbar.make(v,"En cours de développement", Snackbar.LENGTH_LONG).show();
                        return;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(JavaActivity.EXTRA_SCORE,0);

                // codition si le score > highScore (meilleur score)
                if(score > highScore){
                    updateHighScore(score);
                }
            }
        }
    }

    // charger le meilleur score
    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE,0);
       // textViewHighScore.setText("HighScore : "  + highScore);

    }

    // methode de modification du score par le meilleur
    private void updateHighScore(int highScoreNew) {
        highScore = highScoreNew;
      //  textViewHighScore.setText("HighScore : "  + highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highScore);
        editor.apply();
    }

    // fixe l'item deconnexion dans la barre
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            // appelle de la methode pour la deconnexion au clique
            case R.id.action_deconnexion:
                deconnexion();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    // methode pour sortir du jeu
    @Override
    public void onBackPressed() {


        // condition. Dans l'intervalle des 2 secondes appuyer deux fois pour sortir du jeu
        if (btnRetour + 2000 > System.currentTimeMillis()){
            finish();
        } else{
            Toast.makeText(this,"Veuillez appuyez de nauveau pour sortie du jeu", Toast.LENGTH_SHORT).show();
        }
        btnRetour = System.currentTimeMillis();
    }

    // methode pour la deconnexion
    public void deconnexion(){
        joueursDao = new JoueursDao();

        new TTFancyGifDialog.Builder(MainActivity.this)
                .setTitle("Deconnexion ...")
                .setMessage("Etes-vous sûre de vouloir vous deconnecter ?")
                .setPositiveBtnText("Oui")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Non")
                .setNegativeBtnBackground("#c1272d")
//                .setGifResource(R.drawable.)
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                      joueursDao.delete(joueursDao.getAll().get(0));
                        finish();
                        Toast.makeText(MainActivity.this,"Oui ...",Toast.LENGTH_SHORT).show();

                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(MainActivity.this,"Non...", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
}
