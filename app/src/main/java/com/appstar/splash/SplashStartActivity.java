package com.appstar.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.common.GetSearchLocation;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.login.ChooseScreen;
import com.appstar.tutionportal.splash.SplashScreen;
import com.appstar.tutionportal.util.SharePreferenceData;

import java.util.ArrayList;
import java.util.List;

public class SplashStartActivity extends AppCompatActivity {
    public static ImageView imageView;
    private static SharePreferenceData sharePreferenceData;
    ViewPager viewPager;
    MyPageAdapter pageAdapter;
    TextView tvText;
    ImageView imgNext;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePreferenceData = new SharePreferenceData();
        if (!sharePreferenceData.isUserLogInFirst(getApplicationContext())) {
            Intent intent = new Intent(SplashStartActivity.this, SplashScreen.class);
            startActivity(intent);
            finish();
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.start_splash_screen);
            tvText = findViewById(R.id.tvSkip);
            imageView = findViewById(R.id.imageView);
            imgNext = findViewById(R.id.imgNext);
            imageView.setVisibility(View.VISIBLE);
            // getSupportActionBar().hide();
            viewPager = findViewById(R.id.viewPager);
            List<Fragment> fragments = getFragments();
            pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
            viewPager.setCurrentItem(0);
            viewPager.setAdapter(pageAdapter);
            viewPager.setPageTransformer(true, new ParallaxPageTransformer());
            viewPager.setOffscreenPageLimit(0);
            onClickListener();
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    tvText.setText("SKIP");
                    position = i;
                    imgNext.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                imageView.setImageResource(R.drawable.ic_logo);

                            }
                        }, 100);

                    } else if (i == 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageResource(R.drawable.ic_pin2);
                                // imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.location_find));
                            }
                        }, 10);

                    } else if (i == 3) {
                        tvText.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageDrawable(null);
                                imgNext.setVisibility(View.GONE);

                            }
                        }, 100);
                    } else {
                        imageView.setImageDrawable(null);

                    }

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }


    private void onClickListener() {
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(position + 1);

            }
        });
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePreferenceData.setUserLogInFirst(getApplicationContext(),false);
                Intent intent = new Intent(SplashStartActivity.this, ChooseScreen.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<>();
        fList.add(Fragment1.newInstance("Fragment 1"));
        fList.add(Fragment2.newInstance("Fragment 2"));
        fList.add(Fragment3.newInstance("Fragment 3"));
        fList.add(Fragment4.newInstance("Fragment 4"));
        return fList;
    }

    public class ParallaxPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {

            int pageWidth = view.getHeight();



           /* if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(1);

            } else if (position <= 1) { // [-1,1]

                imageView.setTranslationX(position * (pageWidth / 2)); //Half the normal speed

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(1);
            }*/


        }
    }

    public class FadeOutTransformation implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position * page.getWidth());

            page.setAlpha(1 - Math.abs(position));


        }
    }
}
