package com.app.quiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;

import app.com.quizapplication.R;

/**
 * Cette classe ne sert à rien elle peut même etre supprimée du projet
 * avec son son fichier XML
 */
public class JavaActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    // pour le compte en millis seconde
    public static final long DECOMPTE_EN_MILLIS = 20000; // countDowm;

    // variables static
    public static final String KEY_SCORE = "keyScore";
    public static final String KEY_QUESTION_COUNT = "keyQuestionCount"; // compteur de question
    public static final String KEY_MILLIS_TIME ="keyMillisTime"; //KEY_MILLIS_LEFT
    public static final String KEY_QUESTION_LIST = "keyQuestionList";
    public static final String KEY_REPONSE = "keyReponse";

    // variables
    private TextView txtScore;
    private TextView txtViewQuestion;
    private TextView txtQuestionCount; // compteur de question
    private TextView txtViewCountDown;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Button buttonConfirmAndNext;

    // counter = compter | cout = compter

    // couleur du text
    private ColorStateList textColorDefaultRB;

    // pour la couleur du chrono(decompte)
    private ColorStateList textColorDefaultCb; //chronoDefaultColor;

    // propriete de type decompte
    private CountDownTimer decompteTemps; // countDownTimer;
    private long tempsEnMillis; //timeLeftInMillis;


    // liste de question


    private int questionCounter;//questionCounter =compteurQuestion
    private int questionCountTotal; //questionCountTotal =nombreQuestion

    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        // liaisons
        txtScore = findViewById(R.id.txt_score);
        txtViewQuestion = findViewById(R.id.txt_questionaire);
        txtQuestionCount = findViewById(R.id.txt_question_count);
        txtViewCountDown = findViewById(R.id.txt_time);
        radioGroup = findViewById(R.id.btn_groupe_radio);
        radioButton1 = findViewById(R.id.btn_groupe_button1);
        radioButton2 = findViewById(R.id.btn_groupe_button2);
        radioButton3 = findViewById(R.id.btn_groupe_button3);
        buttonConfirmAndNext = findViewById(R.id.btn_valider);

        // prend la couleur actuelle des elements
        textColorDefaultRB = radioButton1.getTextColors();
        textColorDefaultCb = txtViewCountDown.getTextColors();

        //condition sur la sauvegarde de l'etat de l'ecran en portrait ou landscape
        if (savedInstanceState == null){


            //
            //       compteurQuestion; // qcounter
            //       private int nombreQuestion;// qcountTotal
            //       private JavaQuestion questionActuelle;// currentQuestion



            showNextquestion(); //showNextQuestion

            // si l'ecran change d'etat on sauvegarde les resultatet traitement continue
            // sans initialiser le jeu
        } else{
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            score = savedInstanceState.getInt(KEY_SCORE);
            tempsEnMillis = savedInstanceState.getLong(KEY_MILLIS_TIME);
            answered = savedInstanceState.getBoolean(KEY_REPONSE);

            // reponse = false debute du decompte
            if (!answered){
                starCountDowm();
            }else{
                updateCountDownText();
                attendreSolution();
            }
        }


        buttonConfirmAndNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //condition sur la repondse
                if(!answered){
                    if(radioButton1.isChecked() ||radioButton2.isChecked() || radioButton3.isChecked()){
                        checkReponse();
                        Toast.makeText(JavaActivity.this,"clic du bouton valider ", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(JavaActivity.this,"svp selectionnez une reponse", Toast.LENGTH_LONG).show();
                    }
                }else {
                    showNextquestion();
                }
            }
        });
    }

    // methode pour passer aux questions suivantes
    private void showNextquestion(){
        radioButton1.setTextColor(textColorDefaultRB);
        radioButton2.setTextColor(textColorDefaultRB);
        radioButton3.setTextColor(textColorDefaultRB);
        radioGroup.clearCheck();

        // condition
        if(questionCounter < questionCountTotal){
//            currentQuestion = questionList.get(questionCounter);
//
//            txtViewQuestion.setText(currentQuestion.getQuestion());
//            radioButton1.setText(currentQuestion.getOption1());
//            radioButton2.setText(currentQuestion.getOption2());
//            radioButton3.setText(currentQuestion.getOption3());

            questionCounter++;

            txtQuestionCount.setText("Question : " +questionCounter + "/"+ questionCountTotal);
            answered = false;
            buttonConfirmAndNext.setText("Confirm");

            // affectation des 20 secondes a la variable millis seconde
            tempsEnMillis = DECOMPTE_EN_MILLIS;

            starCountDowm(); // appel de la methode pour lancer le decompte
        } else{
            finishQuiz();
        }
    }

    // methode pour lancer le decompte
    private void starCountDowm() {
        // intervalle de decompte du temps en 1 = 1000 seconde par decompte
        decompteTemps = new CountDownTimer(tempsEnMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempsEnMillis = millisUntilFinished;

                // update sur le decompte...
                // methode de creation du decompte
                updateCountDownText();
            }

            // si le decompte passe a 0
            // on le reinitialise puis
            // on passe a la question suivante avec une reponse = 0 a la derniére question .
            @Override
            public void onFinish() {
                tempsEnMillis = 0;
                updateCountDownText();
                checkReponse();
            }
        }.start();
    }

    // methode update sur le compte ...
    // en realité on crée le decompte ici
    private void updateCountDownText() {
        int minutes = (int) ((tempsEnMillis/1000)/60);
        int seconds = (int) (tempsEnMillis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        txtViewCountDown.setText(timeFormatted);

        // timeLeftInMillis < 10 = 10000 second, le chrono passe au rouge
        // sinon il garde sa couleur initiale
        if (tempsEnMillis  < 10000){
            txtViewCountDown.setTextColor(Color.RED);
        }else{
            txtViewCountDown.setTextColor(textColorDefaultCb);
        }
    }

    // methode pour rechercher la reponse
    private  void checkReponse(){
        answered = true;
        //arrete du decompte
        decompteTemps.cancel();

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int reponse = radioGroup.indexOfChild(rbSelected) + 1;

//        // si la reponse est vrai les score augmente
//        if(reponse == currentQuestion.getReponse()){
//            score++;
//            txtScore.setText("Score : " +score);
//        }
        attendreSolution();
    }


    // solution
    /* change la couleur des boutons en cas de bonne ou mauvaise reponse
     * change aussi l'état du bouton de validation*/
    private void attendreSolution(){
        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);

//        switch (currentQuestion.getReponse()){
//            case 1:
//                radioButton1.setTextColor(Color.GREEN);
//                txtViewQuestion.setText("reponse 1 est correcte");
//                break;
//            case 2:
//                radioButton2.setTextColor(Color.GREEN);
//                txtViewQuestion.setText("reponse 2 est correcte");
//                break;
//            case 3:
//                radioButton3.setTextColor(Color.GREEN);
//                txtViewQuestion.setText("reponse 3 est correcte");
//                break;
//        }

        // si le compteur de question n'est pas egale a la question totale
        // le bouton de selection est a 'suivant' sinon "terminaer"
        if(questionCounter < questionCountTotal){
            buttonConfirmAndNext.setText("Suivant");
        }else{
            buttonConfirmAndNext.setText("Terminer");
        }
    }

    // finir
    public void finishQuiz(){

        Intent resultatIntent = new Intent();
        resultatIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultatIntent);
        finish();
    }

    // destruction
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // si decompte est different null
        if (decompteTemps != null){
            //arret du decompte
            decompteTemps.cancel();
        }
    }

    // l'etat de l'ecran en portrait ou lanscape
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt(KEY_SCORE,score);
//        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
//        outState.putLong(KEY_MILLIS_TIME,tempsEnMillis);
//        outState.putBoolean(KEY_REPONSE,answered);
//        // pour cette ligne le model doit implementé l'interface parcelable
//        // et la de question doit passer a ArrayList partout dans le code sinon error
//        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionList);
//    }
}
