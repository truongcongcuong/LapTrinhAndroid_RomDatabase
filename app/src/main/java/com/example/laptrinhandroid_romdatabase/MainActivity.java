package com.example.laptrinhandroid_romdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.laptrinhandroid_romdatabase.room.AppDatabase;
import com.example.laptrinhandroid_romdatabase.room.User;
import com.example.laptrinhandroid_romdatabase.room.UserDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvi_main_user;
    Button btt_main_insert,btt_main_delete,btt_main_getAll,btt_main_findById;
    EditText edt_id,edt_fname,edt_lname;
    List<User> users;
    int index=-1;
    @SuppressLint("SetTextI18n")
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
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);

        //
        edt_id.setHint("id");
        edt_fname.setHint("First Name");
        edt_lname.setHint("Last Name");

        btt_main_findById.setText("Find By Id");
        btt_main_getAll.setText("Get All");
        btt_main_insert.setText("Insert");
        btt_main_delete.setText("Delete");




        //build & connect room database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,"user-database-test").allowMainThreadQueries()
                .build();
        UserDAO userDAO = db.userDAO();
        users = userDAO.getAll();
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

            if(!edt_id.getText().toString().equalsIgnoreCase("")) {
                int[] ints = {Integer.parseInt(edt_id.getText().toString())};
                List<User> usersChange = userDAO.loadAllByIds(ints);
                dataChange(usersChange);
                emptyData();
            }else {
                Toast.makeText(MainActivity.this,"Id is empty",Toast.LENGTH_SHORT).show();
            }
        });

        btt_main_getAll.setOnClickListener(v->{
            dataChange(userDAO.getAll());
            emptyData();
        });

        btt_main_insert.setOnClickListener(v->{
            if(!isEmptyData()){
                User user = new User(Integer.parseInt(edt_id.getText().toString()),edt_fname.getText().toString(),edt_lname.getText().toString());
                userDAO.insertAll(user);
                dataChange(userDAO.getAll());
                emptyData();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Empty Data",Toast.LENGTH_SHORT).show();
            }
        });

        lvi_main_user.setOnItemClickListener((parent, view, position, id) -> {
            index = position;
            edt_id.setText(String.valueOf(userDAO.getAll().get(position).getId()));
            edt_fname.setText(userDAO.getAll().get(position).getFirstName());
            edt_lname.setText(userDAO.getAll().get(position).getLastName());
            Toast.makeText(MainActivity.this,index+" " ,Toast.LENGTH_SHORT).show();
        });

        btt_main_delete.setOnClickListener(v->{
            if(index>=0){
                userDAO.delete(userDAO.getAll().get(index));
                dataChange(userDAO.getAll());
                emptyData();
                index=-1;
            }else {
                Toast.makeText(MainActivity.this,"Invalid Object",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dataChange(List<User> userList) {
        ArrayAdapter<User> arrayAdapterChange = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        lvi_main_user.setAdapter(arrayAdapterChange);
    }

    private void emptyData(){
        edt_id.setText("");
        edt_fname.setText("");
        edt_lname.setText("");
    }
    private boolean isEmptyData(){
        if (edt_id.getText().toString().equalsIgnoreCase(""))
            return true;
        if (edt_fname.getText().toString().equalsIgnoreCase(""))
            return true;
        if (edt_lname.getText().toString().equalsIgnoreCase(""))
            return true;
        return false;
    }

}