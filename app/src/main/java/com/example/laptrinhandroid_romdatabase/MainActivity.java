package com.example.laptrinhandroid_romdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptrinhandroid_romdatabase.room.AppDatabase;
import com.example.laptrinhandroid_romdatabase.room.User;
import com.example.laptrinhandroid_romdatabase.room.UserDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvi_main_user;
    Button btt_main_insert,btt_main_delete,btt_main_getAll,btt_main_findById;
    EditText edt_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa
        lvi_main_user = findViewById(R.id.lv_main_user);
        btt_main_delete = findViewById(R.id.btt_main_delete);
        btt_main_findById = findViewById(R.id.btt_main_find_by_id);
        btt_main_getAll = findViewById(R.id.btt_main_get_all);
        btt_main_insert = findViewById(R.id.btt_main_insert);
        edt_id = findViewById(R.id.edt_id);

        //
        edt_id.setHint("id");

        btt_main_findById.setText("Find By Id");
        btt_main_getAll.setText("Get All");




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

        //action
        btt_main_findById.setOnClickListener(v->{
            int[] ints= {Integer.parseInt(edt_id.getText().toString())};
            List<User> usersChange = userDAO.loadAllByIds(ints);
            ArrayAdapter<User> arrayAdapterChange = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersChange);
            lvi_main_user.setAdapter(arrayAdapterChange);
        });

        btt_main_getAll.setOnClickListener(v->{
            lvi_main_user.setAdapter(arrayAdapter);
        });
    }
}