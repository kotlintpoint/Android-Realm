package com.example.admin.realmdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Admin on 9/20/2017.
 */

public class PersonDAO {

    Realm realm;
    public PersonDAO(Context context)
    {
        realm = Realm.getDefaultInstance();
    }

    public String savePerson(Person person)
    {
        person.setId((int)getNewPersonId());
        realm.beginTransaction();
        Person newPerson=realm.copyToRealm(person);
        realm.commitTransaction();
        return "Success";
    }

    public long getNewPersonId(){
        long id=(Long)realm.where(Person.class).max("id");
        return id+1;
    }

    public String removePerson(int id)
    {
        realm.beginTransaction();
        Person person=realm.where(Person.class).equalTo("id",id).findFirst();
        person.deleteFromRealm();
        realm.commitTransaction();
        return "Success";
    }
    public String updatePerson(Person person)
    {
        realm.beginTransaction();
        Person NewPerson=realm.where(Person.class).equalTo("id",person.getId()).findFirst();
        NewPerson.setFirstName(person.getFirstName());
        NewPerson.setLastName(person.getLastName());
        NewPerson.setMobile(person.getMobile());
        realm.commitTransaction();
        return "Success";
    }
    public List<Person> getAllPerson()
    {
        RealmResults<Person> persons=realm.where(Person.class).findAll();
        return realm.copyFromRealm(persons);
    }

}
