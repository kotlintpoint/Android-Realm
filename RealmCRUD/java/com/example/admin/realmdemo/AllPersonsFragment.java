package com.example.admin.realmdemo;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPersonsFragment extends Fragment {

    ListView listView;
    List<Person> persons;
    PersonDAO personDAO;
    ArrayAdapter<Person> personArrayAdapter;

    public AllPersonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all_persons, container, false);
        // Inflate the layout for this fragment
        listView=view.findViewById(R.id.listview);
        personDAO=new PersonDAO(getActivity());
        setAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlertDialog(position);
            }
        });

        return view;
    }

    private void setAdapter() {
        persons=personDAO.getAllPerson();
        personArrayAdapter=new ArrayAdapter<Person>(getActivity(),android.R.layout.simple_list_item_1,persons);
        listView.setAdapter(personArrayAdapter);
    }

    private void showAlertDialog(final int position) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Select Option")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showEditDialog(position);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showConfirmationDialog(position);
                    }
                })
                .show();
    }

    private void showEditDialog(int position) {
        final Person person=persons.get(position);

        View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_new_person,null);
        final EditText etFirstName=view.findViewById(R.id.etFirstName);
        final EditText etLastName=view.findViewById(R.id.etLastName);
        final EditText etMobile=view.findViewById(R.id.etMobile);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnShow=view.findViewById(R.id.btnShow);
        etFirstName.setText(person.getFirstName());
        etLastName.setText(person.getLastName());
        etMobile.setText(person.getMobile());
        btnSave.setVisibility(View.GONE);
        btnShow.setVisibility(View.GONE);
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        person.setFirstName(etFirstName.getText().toString());
                        person.setLastName(etLastName.getText().toString());
                        person.setMobile(etMobile.getText().toString());
                        String msg=personDAO.updatePerson(person);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        setAdapter();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void showConfirmationDialog(final int position) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Do you really want to Delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Person person=persons.get(position);
                        String msg=personDAO.removePerson(person.getId());
                        persons.remove(person);
                        personArrayAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

}
