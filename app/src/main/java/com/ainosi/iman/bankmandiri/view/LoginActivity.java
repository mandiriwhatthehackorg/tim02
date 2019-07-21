package com.ainosi.iman.bankmandiri.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ainosi.iman.bankmandiri.R;
import com.ainosi.iman.bankmandiri.model.FinancialPlannerModel;
import com.ainosi.iman.bankmandiri.model.UserModel;
import com.ainosi.iman.bankmandiri.storage.Database;
import com.ainosi.iman.bankmandiri.util.Config;
import com.ainosi.iman.bankmandiri.util.SharedPref;
import com.androidnetworking.AndroidNetworking;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private EditText editUsername, editPassword;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidNetworking.initialize(getApplicationContext());

        database = new Database(LoginActivity.this);
        database.getWritableDatabase();

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if (!username.equals("") && !password.equals("")){
                    UserModel user = database.selectUser(username,password);
                    if (user.getUsername() != null && user.getPassword() != null){
                        Log.e(TAG, "onClick: Cif  " + user.getCifnumber());
                        Log.e(TAG, "onClick: Acc  " + user.getAccountnumber());
                        Log.e(TAG, "onClick: Usr  " + user.getUsername());
                        Log.e(TAG, "onClick: Pass " + user.getPassword());
                        Log.e(TAG, "onClick: Name " + user.getName());
                        Log.e(TAG, "onClick: Bal  " + user.getBalance());
                        Log.e(TAG, "onClick: Sava " + user.getSet_a());
                        Log.e(TAG, "onClick: Savb " + user.getSet_b());
                        Log.e(TAG, "onClick: Savc " + user.getSet_c());

                        SharedPref.set(LoginActivity.this, Config.CIFNUMBER, user.getCifnumber());
                        SharedPref.set(LoginActivity.this, Config.ACCOUNTNUMBER, user.getAccountnumber());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Username atau password salah.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Username atau password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        UserModel user = new UserModel();
        user.setCifnumber("1000005085");
        user.setAccountnumber("1111006397571");
        user.setUsername("aditya");
        user.setPassword("123456");
        user.setName("Aditya Zulfahmi");
        user.setBalance("0");
        user.setSet_a(70);
        user.setSet_b(20);
        user.setSet_c(10);
//
//        database.insertUser(user);
//
//        FinancialPlannerModel fpm = new FinancialPlannerModel();
//        fpm.setAccountnumber("1111006397571");
//        fpm.setDate("2019-07-20 15:34:45");
//        fpm.setSaving_a("0");
//        fpm.setSaving_b("0");
//        fpm.setSaving_c("0");
//        fpm.setTotal("0");
//
//        database.insertFinancialPlanner(fpm);

    }
}

