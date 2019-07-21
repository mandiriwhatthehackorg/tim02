package com.ainosi.iman.bankmandiri.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ainosi.iman.bankmandiri.R;
import com.ainosi.iman.bankmandiri.model.FinancialPlannerModel;
import com.ainosi.iman.bankmandiri.model.UserModel;
import com.ainosi.iman.bankmandiri.presenter.HomeInterface;
import com.ainosi.iman.bankmandiri.presenter.HomePresenter;
import com.ainosi.iman.bankmandiri.storage.Database;
import com.ainosi.iman.bankmandiri.util.Config;
import com.ainosi.iman.bankmandiri.util.JSONHandler;
import com.ainosi.iman.bankmandiri.util.Key;
import com.ainosi.iman.bankmandiri.util.SharedPref;
import com.ainosi.iman.bankmandiri.util.Util;

import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeInterface {

    private final String TAG = "HomeActivity";
    private TextView textBalance;
    private Database database;
    private HomePresenter presenter;
    private UserModel user;
    private String accountnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountnumber = SharedPref.get(HomeActivity.this, Config.ACCOUNTNUMBER, "not found");

        database = new Database(HomeActivity.this);
        database.getWritableDatabase();
        user = database.selectUser(accountnumber);

        textBalance = findViewById(R.id.textBalance);

        presenter = new HomePresenter(HomeActivity.this, this);
        presenter.getBalanceInfo();

        LinearLayout buttonMPlan = findViewById(R.id.buttonMPlan);
        buttonMPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MPlanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBalanceInfo();
    }

    @Override
    public void onGetBalanceInfoSuccess(JSONObject response) {
        String balance = JSONHandler.getStringFromJSONObject(response, Key.LEDGERBALANCE);
        if (!balance.equals("0.00")){
            presenter.splitBalance(balance, user.getSet_a(), user.getSet_b(), user.getSet_c());

        }else {
            Log.e(TAG, "onGetBalanceInfoSuccess: no need to split.");
        }
        long balance_before = Long.parseLong(this.user.getBalance());
        long balance_added = Util.extractAmount(balance);
        String balance_new = String.valueOf(balance_before + balance_added);

        UserModel user = new UserModel();
        user.setAccountnumber(accountnumber);
        user.setBalance(balance_new);
        database.updateUser(user);
        String idr_balance = "Rp. " + Util.formatIDRCurrency(balance_new);
        textBalance.setText(idr_balance);
    }

    @Override
    public void onGetBalanceInfoFailed(String message) {
        textBalance.setText(message);
    }

    @Override
    public void onSplitBalanceSuccess(long... saving) {
        FinancialPlannerModel fpm = new FinancialPlannerModel();
        fpm.setAccountnumber(accountnumber);
        fpm.setDate(Util.getDateNow("yyyy-MM-dd HH:mm:ss"));
        fpm.setSaving_a(String.valueOf(saving[0]));
        fpm.setSaving_b(String.valueOf(saving[1]));
        fpm.setSaving_c(String.valueOf(saving[2]));
        fpm.setTotal(String.valueOf(saving[3]));
        database.insertFinancialPlanner(fpm);
        Log.e(TAG, "onSplitBalanceSuccess: ==================================");
        List<FinancialPlannerModel> list = database.selectFinancialPlanner();
        for (FinancialPlannerModel model : list){
            Log.e(TAG, "onSplitBalanceSuccess: account " + model.getAccountnumber());
            Log.e(TAG, "onSplitBalanceSuccess: date    " + model.getDate());
            Log.e(TAG, "onSplitBalanceSuccess: save a  " + model.getSaving_a());
            Log.e(TAG, "onSplitBalanceSuccess: save b  " + model.getSaving_b());
            Log.e(TAG, "onSplitBalanceSuccess: save c  " + model.getSaving_c());
            Log.e(TAG, "onSplitBalanceSuccess: total   " + model.getTotal());
            Log.e(TAG, "onSplitBalanceSuccess: ==================================");
        }
    }
}
