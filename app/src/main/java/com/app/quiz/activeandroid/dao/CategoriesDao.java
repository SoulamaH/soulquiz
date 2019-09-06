package com.app.quiz.activeandroid.dao;

import com.activeandroid.query.Select;
import com.app.quiz.activeandroid.model.Categories;

import java.util.Date;
import java.util.List;

public class CategoriesDao {


  // methode pour un ajout
  public  Long add(Categories categories){
    categories.setCode(new Date().getTime());
    return categories.save();
  }

  //retourne la liste de tous les elements
  public List<Categories> getAll(){

    return new Select()
            .from(Categories.class)
            .execute();
  }

  // Retourne un element par par nom
  public Categories getOneByName(String name){
    return new Select()
            .from(Categories.class)
            .where("name =?", name)
            .executeSingle();
  }


  // enregistrement des categories
  public void enregistrementParDefault(){

    Categories c1 = new Categories("java");
    Categories c2 = new Categories("spring");
    Categories c3 = new Categories("angular");
    Categories c4 = new Categories("android");

    // faire les enregistrement grace a la methode add plus haut

    add(c1);
    add(c2);
    add(c3);
    add(c4);
  }

}
