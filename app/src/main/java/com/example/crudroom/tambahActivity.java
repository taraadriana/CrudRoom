package com.example.crudroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudroom.database.AppDatabase;
import com.example.crudroom.database.entitas.User;

public class tambahActivity extends AppCompatActivity {
    private EditText editNim, editName, editAlamat, editUsia, editGender;
    private Button btnTambah, btnKembali;
    private AppDatabase database;
    private int uid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        editNim = findViewById(R.id.editTextNim);
        editName = findViewById(R.id.editTextName);
        editAlamat = findViewById(R.id.editTextAlamat);
        editUsia = findViewById(R.id.editTextUsia);
        editGender = findViewById(R.id.editTextGender);
        btnTambah = findViewById(R.id.btnCreate);
        btnKembali = findViewById(R.id.btnCreateKembali);

        database = AppDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        if (uid>0){
            isEdit = true;
            User user = database.userDao().get(uid);
            editNim.setText(String.valueOf(user.nim));
            editName.setText(user.nama);
            editAlamat.setText(user.alamat);
            editGender.setText(user.gender);
            editUsia.setText(String.valueOf(user.usia));
        } else{
            isEdit = false;
        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ckNim = editNim.getText().toString();
                String ckName = editName.getText().toString();
                String ckAlamat = editAlamat.getText().toString();
                String ckUsia = editUsia.getText().toString();
                String ckGender = editGender.getText().toString();

                boolean check = validasi(ckNim, ckName, ckAlamat, ckUsia, ckGender);
                if(check == true) {
                    if (isEdit) {
                        database.userDao().update(uid, Integer.parseInt(editNim.getText().toString()),
                                editName.getText().toString(),
                                editAlamat.getText().toString(), editGender.getText().toString(),
                                Integer.parseInt(editUsia.getText().toString()));
                    } else {
                        database.userDao().insertAll(Integer.parseInt(editNim.getText().toString()),
                                editName.getText().toString(),
                                editAlamat.getText().toString(), editGender.getText().toString(),
                                Integer.parseInt(editUsia.getText().toString()));
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"isi semua data", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    public boolean validasi(String ckNim, String ckName, String ckAlamat, String ckUsia,
                            String ckGender) {
        if (ckNim.length() == 0) {
            editNim.requestFocus();
            editNim.setError("Nim tidak boleh kosong!");
            return false;
        } else if (ckName.length() == 0) {
            editName.requestFocus();
            editName.setError("Nama tidak boleh kosong!");
            return false;
        } else if (ckAlamat.length() == 0) {
            editAlamat.requestFocus();
            editAlamat.setError("Alamat tidak boleh kosong!");
            return false;
        } else if (ckUsia.length() == 0) {
            editUsia.requestFocus();
            editUsia.setError("Usia tidak boleh kosong!");
            return false;
        } else if (ckGender.length() == 0) {
            editGender.requestFocus();
            editGender.setError("Jenis kelamin tidak boleh kosong!");
            return false;
        }else{
            return true;
        }
    }

    public void quit(View view){
        Intent intent = new Intent(tambahActivity.this, MainActivity.class);
        startActivity(intent);
        tambahActivity.this.finish();
    }
}