package com.example.crudroom.database.entitas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    public int nim;

    @ColumnInfo(name = "name")
    public String nama;

    public String alamat, gender;

    public int usia;
}
