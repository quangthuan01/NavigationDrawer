package com.example.navigationdrawer.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.TabLayoutViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.mview_pager);

        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager());
        //add fragment
        adapter.AddFragment(new SignInFragment(), "Sign In");
        adapter.AddFragment(new SignUpFragment(), "Sign Up");
        //setAdapter
        viewPager.setAdapter(adapter);
        //connect tablayout to with viewpager
        tabLayout.setupWithViewPager(viewPager);

    }
//        @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(new Intent(Login.this, MainActivity.class));
//            finish();
//        }
//    }
}