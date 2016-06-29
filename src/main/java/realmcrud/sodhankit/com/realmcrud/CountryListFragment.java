package realmcrud.sodhankit.com.realmcrud;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment {

    ListView countryListView;
    ArrayList<Country> countryList;
    RealmDataHandler realmDataHandler;
    Country country;

    public CountryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_city_list, container, false);
        countryListView=(ListView)view.findViewById(R.id.cityList);

        registerForContextMenu(countryListView);
        // Inflate the layout for this fragment
        return view;
    }
    private void loadData() {
        realmDataHandler=new RealmDataHandler(getActivity());
        countryList=realmDataHandler.getCountryList();

        ArrayAdapter<Country> adapter=new ArrayAdapter<Country>(getActivity(),
                android.R.layout.simple_list_item_1,countryList);
        countryListView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu,menu);
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        country = (Country) countryListView.getItemAtPosition(acmi.position);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId())
        {
            case R.id.action_edit:
                fragment=new InsertCountryFragment();
                Bundle args=new Bundle();
                args.putSerializable("COUNTRY",country);
                fragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .addToBackStack(CountryListFragment.class.getName())
                        .commit();
                break;
            case R.id.action_delete:
                RealmDataHandler realmDataHandler = new RealmDataHandler(getActivity());
                realmDataHandler.deleteCountry(country.getCode());
                loadData();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
