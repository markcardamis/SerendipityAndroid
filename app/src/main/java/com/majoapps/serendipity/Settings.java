package com.majoapps.serendipity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Mark on 13/02/2016.
 */
public class Settings extends Activity{

    Switch bNextBase;
    Button bDefaultData;
    EditText etEditText;
    Button bEditText;
    SharedPreferences settingsValues;
    SharedPreferences.Editor editor;
    LinkedHashMap<String, List<String>> Levels_Category;
    List<String> Levels_List;
    ExpandableListView Exp_List;
    LevelsAdapter adapter;
    int array[] = {0,0};
    String etHelpString = "Click option, update text, then save!";
    Intent intent;
    Student student;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        bNextBase = findViewById(R.id.bNextBase);
        //bUpdateData = findViewById(R.id.bUpdateData);
        bDefaultData = findViewById(R.id.bDefaultData);
        Exp_List = findViewById(R.id.exp_list);
        bEditText = findViewById(R.id.bEditText);
        etEditText = findViewById(R.id.etEditText);

        etEditText.setText(etHelpString);
        intent = getIntent();

        settingsValues = PreferenceManager.getDefaultSharedPreferences(this);

        Student sHashmap = getIntent().getParcelableExtra("hashmapMain");
        if (sHashmap.msLinkedHashMap.size() > 0)
            Levels_Category = sHashmap.msLinkedHashMap;
        if (Levels_Category == null)
            Levels_Category = DataProvider.getInfo();

        Levels_List = new ArrayList<>(Levels_Category.keySet());
        adapter = new LevelsAdapter(this, Levels_Category, Levels_List);
        Exp_List.setAdapter(adapter);

        if (settingsValues.getBoolean("Settings", Boolean.TRUE))
            bNextBase.setChecked(Boolean.TRUE);
        else
            bNextBase.setChecked(Boolean.FALSE);


        Exp_List.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                array[0] = groupPosition;
                array[1] = childPosition;
                etEditText.setText(Levels_Category.get(Levels_List.get(array[0])).get(array[1]));
                return true;
            }
        });


        bEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                Levels_Category.get(Levels_List.get(array[0])).set(array[1],etEditText.getText().toString().trim());

                adapter = new LevelsAdapter(Settings.this, Levels_Category, Levels_List);
                Exp_List.setAdapter(adapter);
                Exp_List.expandGroup(array[0]);

                student = new Student(Levels_Category);
                intent.putExtra("hashmap",student);
                setResult(Activity.RESULT_OK, intent);
            }
        });


//        bUpdateData.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                new DataProviderUpdate(new DataProviderUpdate.AsyncResponse(){
//
//                    @Override
//                    public void processFinish(LinkedHashMap<String, List<String>> output) {
//                        //Here you will receive the result fired from async class
//                        //of onPostExecute(result) method.
//                        Levels_Category = output;
//                        Levels_List = new ArrayList<>(Levels_Category.keySet());
//                        adapter = new LevelsAdapter(Settings.this, Levels_Category, Levels_List);
//                        Exp_List.setAdapter(adapter);
//
//                        student = new Student(Levels_Category);
//                        intent.putExtra("hashmap",student);
//                        setResult(Activity.RESULT_OK, intent);
//                    }
//                }).execute();
//            }
//        });


        bDefaultData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Levels_Category = DataProvider.getInfo();
                Levels_List = new ArrayList<>(Levels_Category.keySet());
                adapter = new LevelsAdapter(Settings.this, Levels_Category, Levels_List);
                Exp_List.setAdapter(adapter);

                student = new Student(Levels_Category);
                intent.putExtra("hashmap",student);
                setResult(Activity.RESULT_OK, intent);
            }
        });

        bNextBase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor = settingsValues.edit();
                    editor.putBoolean("Settings", Boolean.TRUE);
                    editor.apply();
                } else {
                    editor = settingsValues.edit();
                    editor.putBoolean("Settings", Boolean.FALSE);
                    editor.apply();

                }
            }
        });
    }

}
