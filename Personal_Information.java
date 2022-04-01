package com.example.mahmoudnahedashour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Personal_Information extends AppCompatActivity {

    Button next;
    EditText person_name, person_age;
    RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_activity);

        next = findViewById(R.id.next);
        person_name = findViewById(R.id.person_name);
        person_age = findViewById(R.id.person_age);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);


        String isDone = getData(Const.FILE_NAME, "firstActivity");

        if (isDone.equalsIgnoreCase("done")) {
            Intent intent = new Intent(getApplicationContext(), Financial_Educational.class);
            startActivity(intent);
            finish();
        }

        String gend = getData(Const.FILE_NAME, "gender");
        if (gend.equalsIgnoreCase("female"))
            female.setChecked(true);

        String txt1 = getData(Const.FILE_NAME, "person_name");
        if (!txt1.isEmpty())
            person_name.setText(txt1);

        String txt2 = getData(Const.FILE_NAME, "person_age");
        if (!txt2.isEmpty())
            person_age.setText(txt2);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (person_name.getText().toString().isEmpty()) {
                    Toast.makeText(Personal_Information.this, "person name is Empty", Toast.LENGTH_SHORT).show();
                } else if (person_age.getText().toString().isEmpty()) {
                    Toast.makeText(Personal_Information.this, "person age is Empty", Toast.LENGTH_SHORT).show();
                } else if (!male.isChecked() && !female.isChecked()) {
                    Toast.makeText(Personal_Information.this, "person gender is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (male.isChecked()) {
                        save(Const.FILE_NAME, "gender", "male");
                    } else if (female.isChecked()) {
                        save(Const.FILE_NAME, "gender", "female");
                    }
                    save(Const.FILE_NAME, "person_name", person_name.getText().toString());
                    save(Const.FILE_NAME, "person_age", person_age.getText().toString());

                    save(Const.FILE_NAME, "firstActivity", "done");
                    Intent intent = new Intent(getApplicationContext(), Financial_Educational.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (male.isChecked()) {
            save(Const.FILE_NAME, "gender", "male");
        } else if (female.isChecked())
            save(Const.FILE_NAME, "gender", "female");


        if (!person_name.getText().toString().isEmpty())
            save(Const.FILE_NAME, "person_name", person_name.getText().toString());
        if (!person_age.getText().toString().isEmpty())
            save(Const.FILE_NAME, "person_age", person_age.getText().toString());
    }

    public void save(String fileName, String key, String value) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String fileName, String key) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(fileName, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


}