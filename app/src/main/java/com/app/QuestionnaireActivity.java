package com.app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quiz.ChargementScoreActivity;
import com.app.quiz.activeandroid.dao.CategoriesDao;
import com.app.quiz.activeandroid.dao.DifficultesDao;
import com.app.quiz.activeandroid.dao.JoueursDao;
import com.app.quiz.activeandroid.dao.PartiesDao;
import com.app.quiz.activeandroid.dao.QuestionsDao;
import com.app.quiz.activeandroid.dao.rest.PartiesRestDao;
import com.app.quiz.activeandroid.model.Categories;
import com.app.quiz.activeandroid.model.Difficultes;
import com.app.quiz.activeandroid.model.Parties;
import com.app.quiz.activeandroid.model.Questions;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.com.quizapplication.R;

public class QuestionnaireActivity extends AppCompatActivity {

    // variable
    private Toolbar toolbar;
    private TextView textViewLangage;
    private TextView textViewNiveau;
    private TextView textViewQuestionnaire; // liaon avec le questionnaire
    private TextView textViewDecompteQuestionnaire;
    private TextView textViewScore;
    private Chronometer chronometerQuestion;

    private String selectLangage;
    private String selectNiveau;

    private ImageView imageViewChoixNiveau;
    private ImageView imageViewBoutonValider;

    private RadioGroup radioGroupOption;

    private RadioButton radioButtonOption1;
    private RadioButton radioButtonOption2;
    private RadioButton radioButtonOption3;

    // elle va permettre l'affichage de question aleatoir
    private int aleatoir;

    // compter du score
    private int compteurScore;

    // objet
    private QuestionsDao questionsDao;
    private List<Questions> questionsList;
    private Difficultes difficultes;
    private Categories categories;

    private CategoriesDao categoriesDao;
    private DifficultesDao difficultesDao;
    private PartiesDao partiesDao;
    private JoueursDao joueursDao;

    private Parties parties;

    private PartiesRestDao partiesRestDao;

    //declaration du tableau
    /** ce tableau contient la position des questions deja repondu
     * pour eviter quelles reviennent de nouveau lors du même questionnaire*/
    List<Integer> tablePosition = new ArrayList<>();

    // compteur
    private int cptNbQuestion = 0;
    // cette variable va nous pemettre d'arrêter le compte du score
    // apres la fin du chrono a la derniere question
    private boolean cptStopComptScore ;

    // couleur du text
    private ColorStateList textColorDefaultRB;

    // variable
    private long btnRetour;

    public static final String TAG = "QuestionnaireActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);


        // liaison
        toolbar = findViewById(R.id.toolBarHeader);
        setSupportActionBar(toolbar);
        textViewLangage = findViewById(R.id.select_langage);
        textViewNiveau = findViewById(R.id.select_niveau);
        imageViewChoixNiveau = findViewById(R.id.image_logo_questionnaire);
        textViewQuestionnaire = findViewById(R.id.liste_questionnaire);

        imageViewBoutonValider = findViewById(R.id.bouton_valider);

        textViewDecompteQuestionnaire = findViewById(R.id.decompte_question);

        textViewScore = findViewById(R.id.score_quiz);

        radioGroupOption = findViewById(R.id.bouton_groupe_radio);

        radioButtonOption1 = findViewById(R.id.bouton_radio_button1);
        radioButtonOption2 = findViewById(R.id.bouton_radio_button2);
        radioButtonOption3 = findViewById(R.id.bouton_radio_button3);

        chronometerQuestion = findViewById(R.id.chronometer_questionnaire);



        // initialisation du score
        compteurScore = 0;
        textViewScore.setText(String.valueOf(compteurScore));

        // recuperation
        selectLangage = getIntent().getStringExtra(MainActivity.LANGAGE_SELECTIONNEE);
        selectNiveau = getIntent().getStringExtra(ChoisirNiveauActivity.NIVEAU_SELECTIONNE);

        // affectation
        textViewLangage.setText(selectLangage);
        textViewNiveau.setText(selectNiveau);

        // appelle de la methode
        selectLogo();

        /*pour le questionnaire*/
        // instantiation de l'objet sinon erreor (null pointException)
        questionsDao = new QuestionsDao();
        difficultesDao = new DifficultesDao();
        categoriesDao = new CategoriesDao();

        joueursDao = new JoueursDao();
        parties =new Parties();
        partiesDao = new PartiesDao();

        partiesRestDao = new PartiesRestDao();

        // recuperation de la difficulté et de categories
        difficultes = difficultesDao.getOneByName(selectNiveau);
        categories = categoriesDao.getOneByName(selectLangage);
        // affectation
        questionsList = questionsDao.readAllByLanguageAndLevel(difficultes, categories);


        // affectation de la question a la propriété textView pour affichage de maniere aleatoire
        aleatoir = new Random().nextInt(questionsList.size());
        // affichage de la premiere question et suivante
        textViewQuestionnaire.setText(questionsList.get(aleatoir).getQuestion());
        // affichage des reponses dans la vue
        radioButtonOption1.setText(questionsList.get(aleatoir).getOption1());
        radioButtonOption2.setText(questionsList.get(aleatoir).getOption2());
        radioButtonOption3.setText(questionsList.get(aleatoir).getOption3());

        // appelle de la methode
        initialiserRadioButton();

        //incremantation
        cptNbQuestion++;
        // recuperation de la position de la question au premier lancement
        tablePosition.add(aleatoir);

        // affichage du decompte
        textViewDecompteQuestionnaire.setText(tablePosition.size() + "/" + questionsList.size());

        /**
         * ************************
         */
        startChronometer();

        // initialisation
        cptStopComptScore = false;

        // Ecoute du bouton
        imageViewBoutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // si condition verifiée on execute validerQuestion
                if (contraintBtnValidation()){

                // thread d'execution
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        validerQuestion();
                    }
                });
                }
            }
        });

    }

    /********  methode pour valider les questions************/
    private void validerQuestion() {
        /**Traitement pour Eviter d'afficher les questions deux fois lors du même questionnaire*/
        if(questionsList.size() == cptNbQuestion){

            // cptStopComptScore = false au premier passage exécution du if,
            // au second passage la variable est a true d'où arret du compte du score à la fin du chrono
            if(!cptStopComptScore){
                // appelle de la methode pour execution de la tâche
                verifReponse(questionsList.get(aleatoir).getId(),recupReposeSelect());

                // arrete du chrono et initialisation apres la derniere question
                chronometerQuestion.setBase(SystemClock.elapsedRealtime());//iniatialisation chrono
                chronometerQuestion.stop(); // arrete
                cptStopComptScore = true;

                // enregistrement du score
                /** recuperation des info et affectation a la partie*/

                parties.setJoueurs(joueursDao.getAll().get(0));
                Log.i(TAG,"******* questionnnaire joueurs" + " "+ joueursDao.getAll().toString());

                parties.setDifficultes(difficultes);
                parties.setCategories(categories);
                parties.setScore(compteurScore);

                Log.i(TAG,"****** questionnnaire parties" + " "+ parties);
                // enregistrement des infos
                partiesDao.add(parties);
                //
//                Intent intent = new Intent(QuestionnaireActivity.this, PartiesActivity.class);
//                startActivity(intent);
                afficherScore();


            }

        }

        // la liste du questionnaire
        if (questionsList.size() > cptNbQuestion) {

            // appelle de la methode pour execution de la tâche
            verifReponse(questionsList.get(aleatoir).getId(),recupReposeSelect());

            // conditon tantque la position existe on reprend le traitement
            // de sorte a ne pas avoir la meme position d'où une question differente à l'affichage
            while (tablePosition.contains(aleatoir)) {
                aleatoir = new Random().nextInt(questionsList.size());
            }

            // appelle de la methode
            initialiserRadioButton();

            // on genere la question de nouveau
            textViewQuestionnaire.setText(questionsList.get(aleatoir).getQuestion());
            radioButtonOption1.setText(questionsList.get(aleatoir).getOption1());
            radioButtonOption2.setText(questionsList.get(aleatoir).getOption2());
            radioButtonOption3.setText(questionsList.get(aleatoir).getOption3());

            // ajout de la position au tableau
            tablePosition.add(aleatoir);

            // affichage du decompte
            textViewDecompteQuestionnaire.setText(tablePosition.size() + "/" + questionsList.size());

            // incrementation
            cptNbQuestion++;

            // reinitialise le compteur
            chronometerQuestion.setBase(SystemClock.elapsedRealtime());
        }
    }

    // recuperation de la rponse selectionnée
    private int recupReposeSelect() {
        if (radioButtonOption1.isChecked()){
            return 1;
        }else if (radioButtonOption2.isChecked()){
            return 2;
        }else if (radioButtonOption3.isChecked()){
            return 3;
        }
        return 0; // 0 si reponse non selectionnée
    }

    // methode pour verifier si la reponse est correcte ou non
    private boolean verifReponse(Long id, int reponse){

        // verifie existance objet
        /** s'il y a un objet on retourne vrai pour bonne reponse sinon faux pour mauvaise*/
        if(questionsDao.readOneByLanguageAndLevelReponse(id , reponse) != null){
            //
//            compteurScore+=2;
            compteurScore++;
            textViewScore.setText(String.valueOf(compteurScore)); //evolution du score
            return true;
        }
        return false;
    }

    // methode pour initialiser les radioButton en passant par le radioGroup
    // apres la validation de la reponse
    private void initialiserRadioButton(){
        radioGroupOption.clearCheck();
    }

    // methode pour le logo
    private void selectLogo() {

        //changement du logo lors du toucher
        switch (selectLangage) {
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

    // chronometre
    private void startChronometer(){
        // debut du chrono
       chronometerQuestion.start();

       chronometerQuestion.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
           @Override
           public void onChronometerTick(Chronometer chronometer) {
                // variable
               Long tempsEcroule = SystemClock.elapsedRealtime() - chronometerQuestion.getBase();

               // conditon pour reinitialiser le chrono apres 10 secodes
               if (tempsEcroule > 11000){
                   chronometerQuestion.setBase(SystemClock.elapsedRealtime());// reinitialiser le chrono

                   // appelle pour changement de question
                   validerQuestion();
               }
           }
       });
    }

    /**
     * Methode de contraint. L'utilisateur ne peut pas passer
     * à la question suivante tant qu'un radioButton n'est pas selectionné
     * @return
     */
    // contrainte bouton valider
    private boolean contraintBtnValidation(){

        // condition. return true si un radioButton est selectionné sinon false
        if (radioButtonOption1.isChecked() || radioButtonOption2.isChecked() || radioButtonOption3.isChecked()){
            return true;
        }
        Toast.makeText(this,"Veuillez sélectionner une réponse d'abord",Toast.LENGTH_SHORT).show();
        return false;
    }

    // fixe l'item deconnexion dans la toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            // pour la deconnexion au clique
            case R.id.action_deconnexion:
                deconnexion();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    // methode pour l'affichage du score
    public void afficherScore(){
        // enregistrement de la partie
      Intent intent = new Intent(QuestionnaireActivity.this, ChargementScoreActivity.class);
      intent.putExtra("partieTerminer", parties);
      startActivity(intent);





    }

    // methode pour la deconnexion
    public void deconnexion(){
        new TTFancyGifDialog.Builder(QuestionnaireActivity.this)
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
                        finish();
                        Toast.makeText(QuestionnaireActivity.this,"Oui ...",Toast.LENGTH_SHORT).show();

                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(QuestionnaireActivity.this,"Non...", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    // methode pour sortir du jeu
    @Override
    public void onBackPressed() {

        // condition. Dans l'intervalle des 2 secondes appuyer deux fois pour sortir du jeu
        if (btnRetour + 2000 > System.currentTimeMillis()){
            finish();
        } else{
            Toast.makeText(this,"appuyez encore pour terminer", Toast.LENGTH_SHORT).show();
        }
        btnRetour = System.currentTimeMillis();
    }


}
