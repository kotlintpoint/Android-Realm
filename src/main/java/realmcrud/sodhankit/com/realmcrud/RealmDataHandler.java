package realmcrud.sodhankit.com.realmcrud;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Admin on 6/29/2016.
 */
public class RealmDataHandler {

    Realm myRealm;

    public RealmDataHandler(Context context)
    {
        myRealm=Realm.getInstance(context);
    }

    public void insertCountry(final Country country)
    {
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Country newCountry = realm.createObject(Country.class);
                newCountry.setName(country.getName());
                newCountry.setPopulation(country.getPopulation());
                newCountry.setCode(country.getCode());
            }
        });
    }

    public void updateCountry(final Country country)
    {
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Country newCountry=myRealm.where(Country.class).equalTo("code",country.getCode()).findFirst();
                if(newCountry!=null) {
                    newCountry.setName(country.getName());
                    newCountry.setPopulation(country.getPopulation());
                    realm.copyToRealmOrUpdate(newCountry);
                }
            }
        });
    }

    public void deleteCountry(final String code)
    {
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Country> realmResults=myRealm.where(Country.class).findAll();
                Country country=realmResults.where().equalTo("code",code).findFirst();
                country.removeFromRealm();
            }
        });
    }

    public ArrayList<Country> getCountryList()
    {
        RealmResults<Country> countries=myRealm.where(Country.class).findAll();

        ArrayList<Country> countryList=new ArrayList<>();
        for (Country country : countries) {
            countryList.add(country);
        }
        return countryList;
    }

}
