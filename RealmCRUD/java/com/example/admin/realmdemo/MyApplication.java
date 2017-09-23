package com.example.admin.realmdemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Admin on 9/23/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config=new RealmConfiguration.Builder()
                .name("myRealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
