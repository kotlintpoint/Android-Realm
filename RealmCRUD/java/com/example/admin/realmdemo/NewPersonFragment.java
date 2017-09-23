package com.example.admin.realmdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPersonFragment extends Fragment {

    EditText etFirstName, etLastName, etMobile;
    Button btnSave, btnShow;

    public NewPersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new_person, container, false);
        initView(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initView(View view) {
        etFirstName=view.findViewById(R.id.etFirstName);
        etLastName=view.findViewById(R.id.etLastName);
        etMobile=view.findViewById(R.id.etMobile);
        btnSave=view.findViewById(R.id.btnSave);
        btnShow=view.findViewById(R.id.btnShow);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonDAO personDAO=new PersonDAO(getActivity());
                Person person=new Person();
                person.setFirstName(etFirstName.getText().toString());
                person.setLastName(etLastName.getText().toString());
                person.setMobile(etMobile.getText().toString());
                String msg=personDAO.savePerson(person);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AllPersonsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout,fragment)
                        .addToBackStack(NewPersonFragment.class.getName())
                        .commit();
            }
        });
    }

}
