# Android-Realm
Android Realm

Realm is a mobile database and a replacement for SQLite

it has own C++ core and aims to provide a mobile-first alternative to SQLite. 
Realm store data in a universal, table-based format by a C++ core

**Advantages:**
* Faster than SQLite (up to 10x speed up over raw SQLite for normal operations)
* Easy to use
* Object conversion handled for you
* Convenient for creating and storing data on the fly
* Very responsive team

**Disadvantages:**
* No importing
* Still under active development
* Not a lot of content online
* Can’t access objects across threads


##Step 1 : Adding Realm to your project
```
compile 'io.realm:realm-android:0.84.1'
```

##Step 2 : Creating a Realm 
```
Realm myRealm = Realm.getInstance(getApplicationContext());
```
Will create Realm File named “default.realm”
If you want to add another Realm File then
```
Realm myOtherRealm = Realm.getInstance(new RealmConfiguration.Builder(context)
                        .name("myOtherRealm.realm")
                        .build()	);
```                        

##Step 3 : Creating Realm Object
```
public class Country extends RealmObject {
   private String name;
  
   private int population;

   @PrimaryKey
   private String code;

   public Country() { }

   public String getCode() { return code;   }

   public void setCode(String code) {   this.code = code;   }

   public String getName() {  return name;  }

   public void setName(String name) {   this.name = name;  }

   public int getPopulation() {   return population;  }

   public void setPopulation(int population) {  this.population = population;  }
}
```

##Step 4 : Creating Transaction

Realm forces you to write all transaction inside a transaction
```
// Option 1
myRealm.beginTransaction();
Country country=myRealm.createObject(Country.class);
country.setName("India");
country.setPopulation(10000000);
country.setCode("12");
myRealm.commitTransaction();


// Option 2
Country country2 = new Country();
country2.setName("India");
country2.setPopulation(25000000);
country2.setCode("IN");

myRealm.beginTransaction();
Country copyOfCountry = myRealm.copyToRealm(country2);
myRealm.commitTransaction();
```

##Transaction Blocks (no required beginTransaction or commitTransaction

```
myRealm.executeTransaction(new Realm.Transaction() {
   @Override
   public void execute(Realm realm) {
       Country country = realm.createObject(Country.class);
       country.setName("Sri Lanka");
       country.setPopulation(10000);
       country.setCode("SL");
   }
});
```

##Update Operation

```
myRealm.executeTransaction(new Realm.Transaction() {
   @Override
   public void execute(Realm realm) {
       Country country=myRealm.where(Country.class).equalTo("code","IN").findFirst();
       if(country!=null) {
   country.setName("New Country");
   realm.copyToRealmOrUpdate(country);
  }
   }
});
```

##Delete Operationmy

```
Realm.executeTransaction(new Realm.Transaction() {
   @Override
   public void execute(Realm realm) {
       RealmResults<Country> realmResults=myRealm.where(Country.class).findAll();
       Country country=realmResults.where().equalTo("code","SL").findFirst();
       country.removeFromRealm();
   }
});
```

##Step 5 : Writing Queries (fetching data)
```
RealmResults<Country> countries=myRealm.where(Country.class).findAll();

ArrayList<String> countryList=new ArrayList<>();
for (Country country : countries) {
   Log.i("Country",country.getName());
   countryList.add(country.getName());
}

ArrayAdapter<String> adapter=new ArrayAdapter<String>(RealmSampleActivity.this,
       android.R.layout.simple_list_item_1,countryList);

listview.setAdapter(adapter);
```


