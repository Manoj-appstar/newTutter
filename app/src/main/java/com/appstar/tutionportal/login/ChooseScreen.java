package com.appstar.tutionportal.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.activities.AddInstituteClass;
import com.appstar.tutionportal.institute.activities.InstituteDashboard;

public class ChooseScreen extends AppCompatActivity {
    CardView cvStudent, cvTeacher, cvInstitute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen);
        findView();
        onClick();
    }

    private void findView() {
        cvInstitute = findViewById(R.id.cvInstitute);
        cvStudent = findViewById(R.id.cvStudent);
        cvTeacher = findViewById(R.id.cvTeacher);
    }

    private void onClick() {
        cvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScreen.this, NewLogInActivity.class);
                intent.putExtra("for", "student");
                startActivity(intent);
                finish();
            }
        });

        cvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScreen.this, NewLogInActivity.class);
                intent.putExtra("for", "teacher");
                startActivity(intent);
                finish();
            }
        });
        cvInstitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScreen.this, NewLogInActivity.class);
                intent.putExtra("for", "institute");
                startActivity(intent);
                finish();
            }
        });

    }


}
