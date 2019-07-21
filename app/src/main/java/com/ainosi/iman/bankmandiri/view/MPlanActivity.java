package com.ainosi.iman.bankmandiri.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ainosi.iman.bankmandiri.R;
import com.ainosi.iman.bankmandiri.adapter.PageAdapter;
import com.ainosi.iman.bankmandiri.model.FinancialPlannerModel;
import com.ainosi.iman.bankmandiri.storage.Database;
import com.ainosi.iman.bankmandiri.util.Util;
import com.ainosi.iman.bankmandiri.widget.CustomViewPager;

import java.util.List;

public class MPlanActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private CustomViewPager viewPager;
    private List<FinancialPlannerModel> list;
    private long total_saving_a, total_saving_b, total_saving_c;
    private String total_saving_a_, total_saving_b_, total_saving_c_;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mplan);

        Database database = new Database(this);
        database.getWritableDatabase();

        textView = findViewById(R.id.saldo);

        total_saving_a = 0;
        total_saving_b = 0;
        total_saving_c = 0;
        list = database.selectFinancialPlanner();
        for (FinancialPlannerModel model : list){
            total_saving_a += Long.parseLong(model.getSaving_a());
            total_saving_b += Long.parseLong(model.getSaving_b());
            total_saving_c += Long.parseLong(model.getSaving_c());
        }

        total_saving_a_ = "M-Plan (Rp. " + Util.formatIDRCurrency(String.valueOf(total_saving_a)) + ")";
        total_saving_b_ = "M-Plan (Rp. " + Util.formatIDRCurrency(String.valueOf(total_saving_b)) + ")";
        total_saving_c_ = "M-Plan (Rp. " + Util.formatIDRCurrency(String.valueOf(total_saving_c)) + ")";

        //Initializing the tablayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("70"));
        tabLayout.addTab(tabLayout.newTab().setText("20"));
        tabLayout.addTab(tabLayout.newTab().setText("10"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Initializing viewPager
        viewPager = findViewById(R.id.pager);

        //Creating our pager adapter
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //disable swipe
        viewPager.setEnableSwipe(false);
        tabLayout.setOnTabSelectedListener(this);
        textView.setText(total_saving_a_);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
        switch (position){
            case 0:
                textView.setText(total_saving_a_);
                break;
            case 1:
                textView.setText(total_saving_b_);
                break;
            case 2:
                textView.setText(total_saving_c_);
                break;

                default:
                    textView.setText("M-Plan");
                    break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
