# [Android-Realm](https://realm.io/docs/java/latest/)
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


##Step 1 : Adding Realm to your project and Initialization
```
Project Gradle file : classpath "io.realm:realm-gradle-plugin:3.7.2"
Module Gradle File at Top : apply plugin: 'realm-android'
...
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
```

##Step 2 : Creating a Realm 
```
Realm realm = Realm.getDefaultInstance();
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
public class Person extends RealmObject{
    @PrimaryKey
    private int id;

    private String firstName, lastName, mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return id+">>"+firstName+">>"+lastName+">>"+mobile;
    }
}
```

##Step 4 : Creating Transaction

Realm forces you to write all transaction inside a transaction
```
// Option 1
public String savePerson(Person person)
{
    myRealm.beginTransaction();
    Person person=myRealm.createObject(Person.class);
    person.setId((int)getNewPersonId());
    person.setFirstName("Kotlin");
    person.setLastName("Tpoint");
    person.setMobile("1234567890");
    myRealm.commitTransaction();
}
....
public long getNewPersonId(){
        long id=(Long)realm.where(Person.class).max("id");
        return id+1;
}

// Option 2
 public String savePerson(Person person)
{
        person.setId((int)getNewPersonId());
        realm.beginTransaction();
        Person newPerson=realm.copyToRealm(person);
        realm.commitTransaction();
        return "Success";
}
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
```

##Delete Person from Database

```
public String removePerson(int id)
{
        realm.beginTransaction();
        Person person=realm.where(Person.class).equalTo("id",id).findFirst();
        person.deleteFromRealm();
        realm.commitTransaction();
        return "Success";
}
```

##Step 5 : Writing Queries to fetch All Persons
```
public List<Person> getAllPerson()
{
     RealmResults<Person> persons=realm.where(Person.class).findAll();
     return realm.copyFromRealm(persons);
}
```


