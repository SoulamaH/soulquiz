package com.app;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class StartQuiz extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ActiveAndroid.initialize(this);
  }
}
