package com.example.asus.mynotebook.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.asus.mynotebook.R;

public class SplashActivity extends Activity {
    //闪频页面

    private ImageView splash;
    private ObjectAnimator animator;
    private AnimatorSet animatorSet;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = findViewById(R.id.iv_splash);
        startAnim();
    }

    private void startAnim() {
        animator = ObjectAnimator.ofFloat(splash, "rotation", 0f, 360f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(splash, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(splash, "scaleY", 0f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(splash, "alpha", 0.9f, 1f);

        animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(scaleX).with(scaleY).with(alpha);
        animatorSet.setDuration(2000);
        animatorSet.start();
        listener();
    }

    private void listener() {
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish(); // 销毁闪屏活动   而不是Destory()!!!!!
            }
        });
    }
}
