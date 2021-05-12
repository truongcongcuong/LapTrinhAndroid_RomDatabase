package com.example.laptrinhandroid_romdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.laptrinhandroid_romdatabase.room.AppDatabase;
import com.example.laptrinhandroid_romdatabase.room.User;
import com.example.laptrinhandroid_romdatabase.room.UserDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvi_main_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa
        lvi_main_user = findViewById(R.id.lv_main_user);


        //build & connect room database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,"user-database-test").allowMainThreadQueries()
                .build();
        UserDAO userDAO = db.userDAO();
        List<User> users = userDAO.getAll();
        if(users.size()==0){
            User[] users1 ={new User(1,"Truong","Cuong")
            ,new User(2,"Cong","Cuong")};
            userDAO.insertAll(users1);
            users = userDAO.getAll();
        }

        //
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lvi_main_user.setAdapter(arrayAdapter);
    }
}