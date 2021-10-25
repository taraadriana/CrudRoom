package com.example.crudroom.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.crudroom.database.entitas.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("INSERT INTO user (nim, name, alamat, gender, usia) VALUES (:nim, :name, :alamat, :gender, :usia)")
    void insertAll(Integer nim, String name, String alamat, String gender, Integer usia);

    @Query("UPDATE user SET nim = :nim, name = :nama_mhs , alamat = :alamat, gender = :gender, usia = :usia WHERE uid=:uid")
    void update(int uid, int nim, String nama_mhs, String alamat, String gender, int usia );

    @Query("SELECT * FROM user WHERE uid=:uid")
    User get(int uid);

    @Delete
    void delete(User user);
}
