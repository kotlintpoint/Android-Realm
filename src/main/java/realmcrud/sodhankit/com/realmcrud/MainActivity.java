package realmcrud.sodhankit.com.realmcrud;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment=new CountryListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_new:
                Fragment fragment=new InsertCountryFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .addToBackStack(CountryListFragment.class.getName())
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
