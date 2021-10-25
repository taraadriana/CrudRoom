package com.example.crudroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crudroom.database.AppDatabase;
import com.example.crudroom.database.entitas.User;

public class showActivity extends AppCompatActivity {
    private TextView shNim, shName, shAlamat, shUsia, shGender;
    private Button btnKembali;
    private AppDatabase database;
    private int uid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        shNim = findViewById(R.id.shNim);
        shName = findViewById(R.id.shName);
        shAlamat = findViewById(R.id.shAlamat);
        shUsia = findViewById(R.id.shUsia);
        shGender = findViewById(R.id.shGender);
        btnKembali = findViewById(R.id.btnKembali);

        database = AppDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        if (uid>0){
            User user = database.userDao().get(uid);
            shNim.setText(String.valueOf(user.nim));
            shName.setText(user.nama);
            shAlamat.setText(user.alamat);
            shGender.setText(user.gender);
            shUsia.setText(String.valueOf(user.usia));
        }
    }

    public void showQuit(View view){
        Intent intent = new Intent(showActivity.this, MainActivity.class);
        startActivity(intent);
        showActivity.this.finish();
    }
}