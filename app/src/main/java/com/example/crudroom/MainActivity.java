package com.example.crudroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crudroom.adapter.UserAdapter;
import com.example.crudroom.database.AppDatabase;
import com.example.crudroom.database.entitas.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnCreate;
    private AppDatabase database;
    private UserAdapter userAdapter;
    private List<User> list = new ArrayList<>();
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnCreate = findViewById(R.id.btnNewList);

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter = new UserAdapter(getApplicationContext(), list);

        dialog = new AlertDialog.Builder(this);

        userAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(int position){
                final CharSequence[] dialogItem = {"edit", "hapus", "lihat"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(MainActivity.this,
                                        tambahActivity.class);
                                intent.putExtra("uid", list.get(position).uid);
                                startActivity(intent);
                                break;

                            case 1:
                                dialog.setMessage("do you want to delete this data?")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                User user = list.get(position);
                                                database.userDao().delete(user);
                                                onStart();
                                               finish();
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = dialog.create();
                                alertDialog.setTitle("Delete Data");
                                alertDialog.show();
                                onStart();
                                break;

                            case 2:
                                Intent intent2 = new Intent(MainActivity.this,
                                        showActivity.class);
                                intent2.putExtra("uid", list.get(position).uid);
                                startActivity(intent2);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager
                (getApplicationContext(), RecyclerView.VERTICAL, false);

        //memasukkan layout manager ke recycler view
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, tambahActivity.class));
            }
        });
    }

    //refresh data

        @Override
        protected void onStart(){
            super.onStart();
            list.clear();
            list.addAll(database.userDao().getAll());

            //kirim notif ke adapter
            userAdapter.notifyDataSetChanged();
        }
}
