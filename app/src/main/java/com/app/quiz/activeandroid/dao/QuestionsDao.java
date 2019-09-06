package com.app.quiz.activeandroid.dao;

import android.util.Log;

import com.activeandroid.query.Select;
import com.app.quiz.activeandroid.model.Categories;
import com.app.quiz.activeandroid.model.Difficultes;
import com.app.quiz.activeandroid.model.Questions;

import java.util.List;

public class QuestionsDao {

  // objets.
  private CategoriesDao categoriesDao;
  private DifficultesDao difficultesDao;
  public static final String TAG = "QuestionsDao";

  // constructeur
  public QuestionsDao (){
    categoriesDao = new CategoriesDao();
    difficultesDao = new DifficultesDao();
  }

  // methode pour faire un ajout
  public  Long add(Questions questions){
    return questions.save();
  }

  //retourne la liste de tous les elements
  public List<Questions> getAll(){

    return new Select()
            .from(Questions.class)
            .execute();
  }


  // enregistrement des categories
  public void enregistrementParDefault(){

    // faccile
    Questions qJf1 = new Questions("Q'est-ce qu'un objet en java ?","Un objet est une classe"," Un Objet est une methode","Un objet est une instance d'une classe",3,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("java"));
    Questions qJf2 = new Questions("comment declare t-on un objet en java ?"," Voiture monAuto = new Voiture();"," monAuto Voiture = new Voiture();"," voiture new voiture() = monAuto; ",1,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("java"));
    Questions qJf3 = new Questions("Citez les 5 étapes clés dans l'ordre pour la création d’une appli ","  Cahier charges (= besoin), Conception, Développement = Réalisation = Codage,Test ,Déploiement","Cahier charges (= besoin), Conception, Développement = Réalisation = Codage,Déploiement,Test",",Déploiement, Cahier charges (= besoin), Conception, Développement = Réalisation = Codage,Test",1,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("java"));
    Questions qJf4 = new Questions("Citez 2 métiers dans le domaine informatique pour créer une application ?"," Chef de projet, developpeur","comptable,  Architecte","Développeur, concessionnaire",1,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("java"));
    Questions qJf5 = new Questions("Qu’est-ce qu'un développeur Back Office","Un développeur Back Office est celui qui ne fait pas la conception de l'appli ","Un développeur Back Office est celui qui se charge de coder le design"," Un développeur Back Office est celui qui se charge de coder les requêtes de l’utilisateur",3,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("java"));
    // faire les enregistrements
    add(qJf1);
    add(qJf2);
    add(qJf3);
    add(qJf4);
    add(qJf5);


    // moyen
    Questions qJM1 = new Questions("Comment créer un tableau en Java ?","TypeTableau  nomTableau [ ] = { contenuTableau } ; "," TypeTableau  { contenuTableau } =  nomTableau [ ]  ; "," nomTableau [ ] = TypeTableau { contenuTableau } ;",1,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("java"));
    Questions qJM2 = new Questions("Proposez code tableau 1 dimension ","{1,2, 3,4} = int chiffreEntier [] ","int {1,2, 3,4}= chiffreEntier [] ;","int chiffreEntier [] = {1,2, 3,4} ;",3,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("java"));
    Questions qJM3 = new Questions("Comment connaître la taille d'un vecteur ?  ","Le nom du tableau.size () ;","Le nom du tableau.set () ;","Le nom du tableau.length () ;",1,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("java"));
    Questions qJM4 = new Questions("Comment connaître la taille d'un tableau ?","Le nom du tableau.get","Le nom du tableau.length","Le nom du tableau.size()",2,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("java"));
    // faire les enregistrements
    add(qJM1);
    add(qJM2);
    add(qJM3);
    add(qJM4);

    // difficulte
    Questions qJd1 = new Questions("Qu'est-ce que la surcharge de méthode ?","c’est d’écrire une méthode qui a le même nom et  avec une signature identique","c’est d’écrire une méthode qui n'a pas le même nom","c’est d’écrire une méthode qui a le même nom mais  avec une signature différente.",3,difficultesDao.getOneByName("difficile"),categoriesDao.getOneByName("java"));
    Questions qJd2 = new Questions("Combien de constructeurs classe JFrame  ?","4","8","5",1,difficultesDao.getOneByName("difficile"),categoriesDao.getOneByName("java"));
    Questions qJd3 = new Questions("Citez 2 caractéristiques clés constructeur  ?","un nom different de la classe, pas de type de retour ni même void.","même nom que la classe, pas de type de retour ni même void.","même nom que la classe, avec un type de retour .",2,difficultesDao.getOneByName("difficile"),categoriesDao.getOneByName("java"));
    // faire les enregistrements
    add(qJd1);
    add(qJd2);
    add(qJd3);
    // fin questionnaire Java

    // sprig debuter
    Questions qSf1 =new Questions("Qu'est-ce que spring? ","Spring est un Framework java facilitant le développement d’applications d’entreprises","Spring est un Framework Angular facilitant le développement d’applications d’entreprises","Spring est un Framework java facilitant le développement d’applications SE",1,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("spring"));
    Questions qSf2 =new Questions("Citez une fonctionnalité offerte par spring? ","Faciliter le développement des DAO en utilisant les Setter","Faciliter le développement des DAO en utilisant JDBC, JPA ","Faciliter le développement des DAO en utilisant le constructeur",2,difficultesDao.getOneByName("facile"),categoriesDao.getOneByName("spring"));
    // faire les enregistrements
    add(qSf1);
    add(qSf2);

    // moyen
    Questions qSm1 =new Questions("Donner 2 différents projets du portfolio spring.  ","Spring Framework, android ","Spring Framework, Spring boot  ","Spring boot , Spring angular",2,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("spring"));
    Questions qSm2 =new Questions("Donner 2 différents projets du portfolio spring.  ","Spring Framework, android ","Spring Framework, Spring boot  ","Spring boot , Spring angular",2,difficultesDao.getOneByName("moyen"),categoriesDao.getOneByName("spring"));
    // faire les enregistrements
    add(qSm1);
    add(qSm2);

    // difficile
    Questions qSd1 =new Questions("Qu’est-ce que l’injection de dépendance? ","pattern permettant de fournir à une BD les dépendances dont elle a besoin ","pattern permettant de fournir à une methode les dépendances dont il a besoin ","pattern permettant de fournir à un composant les dépendances dont il a besoin ",3,difficultesDao.getOneByName("difficile"),categoriesDao.getOneByName("spring"));
    // faire les enregistrements
    add(qSd1);

  }

  // methode pour recupérer et retourner la liste des difficulté et catégorie.
  public List<Questions> readAllByLanguageAndLevel(Difficultes difficultes , Categories categories){

    return new Select()
            .from(Questions.class)
            .where("difficulteId = ? and categorieId = ?", difficultes.getId(), categories.getId())
            .execute();
  }

  // methode pour recupérer et retourner la difficulté, catégorie et la reponse.
  public Questions readOneByLanguageAndLevelReponse(Long id, int reponse){

    Questions questionsTest;
    questionsTest = new Select()
            .from(Questions.class)
            .where("id = ? and reponse = ?", id, reponse)
            .executeSingle();

    if (questionsTest != null){
      Log.i(TAG,questionsTest.toString());
    }

    return questionsTest;
  }


}
