package realmcrud.sodhankit.com.realmcrud;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertCountryFragment extends Fragment {

    Button btnSave;
    EditText etCode, etName, etPopulation;
    Country country;

    public InsertCountryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        try {
            this.country = (Country) args.getSerializable("COUNTRY");

        } catch (Exception ex)
        {}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View view=inflater.inflate(R.layout.fragment_insert_country, container, false);
        // Inflate the layout for this fragment
        btnSave=(Button)view.findViewById(R.id.btnSave);
        etCode=(EditText)view.findViewById(R.id.etCode);
        etName=(EditText)view.findViewById(R.id.etName);
        etPopulation=(EditText)view.findViewById(R.id.etPopulation);

        if(country!=null)
        {
            etCode.setEnabled(false);
            etCode.setText(country.getCode());
            etName.setText(country.getName());
            etPopulation.setText(String.valueOf(country.getPopulation()));
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmDataHandler realmDataHandler = new RealmDataHandler(getActivity());
                if(country==null) {
                    country=new Country();
                    country.setCode(etCode.getText().toString());
                    country.setName(etName.getText().toString());
                    country.setPopulation(Integer.parseInt(etPopulation.getText().toString()));
                    realmDataHandler.insertCountry(country);
                    Toast.makeText(getActivity(), "Insert Success", Toast.LENGTH_SHORT).show();
                }else
                {
                    Country newCountry=new Country();
                    newCountry.setCode(country.getCode());
                    newCountry.setName(etName.getText().toString());
                    newCountry.setPopulation(Integer.parseInt(etPopulation.getText().toString()));
                    realmDataHandler.updateCountry(newCountry);
                    Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
