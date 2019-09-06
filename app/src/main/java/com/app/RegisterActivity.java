package com.app;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.quiz.activeandroid.dao.rest.JoueurRestDao;
import com.app.quiz.activeandroid.model.Joueurs;
import com.libizo.CustomEditText;

import app.com.quizapplication.R;

public class RegisterActivity extends AppCompatActivity {

  // variables
  private CustomEditText editTextNomRegister;
  private CustomEditText editTextPrenomRegister;
  private CustomEditText editTextEmailRegister;
  private CustomEditText editTextNumeroRegister;
  private CustomEditText editTextPasswordRegister;

  private ImageView btnValiderRegister;

  private Toolbar toolbarRegister;

  // objets
  private JoueurRestDao joueurRestDao;
  private Joueurs joueurs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);


    // liaison
    toolbarRegister = findViewById(R.id.toolBarHeader);

    editTextNomRegister = findViewById(R.id.register_nom);
    editTextPrenomRegister = findViewById(R.id.register_prenom);
    editTextEmailRegister = findViewById(R.id.register_email);
    editTextNumeroRegister = findViewById(R.id.register_numero);
    editTextPasswordRegister = findViewById(R.id.register_password);
    btnValiderRegister= findViewById(R.id.register_valider);


        //action du bouton
        btnValiderRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // appelle de la methode contrainte pour enregistrement
                contrainteEnregistrement(v);

            }
        });
  }


  // methode pour contrainte sur les champs de saisie
  // l'avant enregistement dans la BD
  public void contrainteEnregistrement(View v){
      if (TextUtils.isEmpty(editTextNomRegister.getText().toString())){
          Snackbar.make(v, "Veuillez entrer le nom avant de continuer s'il vous plaît", Snackbar.LENGTH_SHORT).show();
          return;

      } else if (TextUtils.isEmpty(editTextPrenomRegister.getText().toString())){
          Snackbar.make(v, "Veuillez entrer le prenom avant de continuer s'il vous plaît", Snackbar.LENGTH_SHORT).show();
          return;

      } else if (TextUtils.isEmpty(editTextNumeroRegister.getText().toString())){
          Snackbar.make(v, "Veuillez entrer le numero avant de continuer s'il vous plaît", Snackbar.LENGTH_SHORT).show();
          return;

      } else if (TextUtils.isEmpty(editTextPasswordRegister.getText().toString())){
          Snackbar.make(v, "Vous avez oublié de renseigner votre mot de passe", Snackbar.LENGTH_SHORT).show();
          return;

      } else {

          //initialisation
          joueurs = new Joueurs();
          //recuperation des infos saisies par l'utilisateur
          joueurs.setNom(editTextNomRegister.getText().toString());
          joueurs.setPrenom(editTextPrenomRegister.getText().toString());
          joueurs.setNumero(editTextNumeroRegister.getText().toString());
          joueurs.setEmail(editTextEmailRegister.getText().toString());
          joueurs.setPassword(editTextPasswordRegister.getText().toString());

          joueurRestDao = new JoueurRestDao();
          // enregistrement
          joueurRestDao.save(joueurs, this);
//          if (joueurRestDao.save(joueurs) != null){
//              Snackbar.make(v, "Enregistrement effectué avec succès", Snackbar.LENGTH_SHORT).show();
//
//              // appelle de la methode d'initialisation des champs apres la saisie
//              initChamps();
//
//              // appelle de la vue suivante apre enregistrement
//              Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
//              startActivity(intent);
//          }
      }
  }


  // methode pour initialisation des champs de saisie
  // apres clic sur boutonValider
    public void initChamps(){
      editTextNomRegister.getText().clear();
      editTextPrenomRegister.getText().clear();
      editTextEmailRegister.getText().clear();
      editTextNumeroRegister.getText().clear();
      editTextPasswordRegister.getText().clear();
    }
}
