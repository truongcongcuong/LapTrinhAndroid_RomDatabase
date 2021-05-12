package com.example.laptrinhandroid_romdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;

import com.example.laptrinhandroid_romdatabase.room.AppDatabase;
import com.example.laptrinhandroid_romdatabase.room.User;
import com.example.laptrinhandroid_romdatabase.room.UserDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,"user-database-test").allowMainThreadQueries()
                .build();
        UserDAO userDAO = db.userDAO();
        List<User> users = userDAO.getAll();
//        if(users.size()==0){
//            User[] users1 ={new User(1,"Truong","Cuong")
//            ,new User(2,"Cong","Cuong")};
//            userDAO.insertAll(users1);
//        }
    }
}