package edu.dg202433.sabriter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.dg202433.android_projet.R;

public class StartActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ObjectAnimator rotateAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.rotation);
        ImageView house = findViewById(R.id.house);
        rotateAnimation.setTarget(house);

        ObjectAnimator slideUpAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.slide_up);
        TextView title = findViewById(R.id.title);
        slideUpAnimation.setTarget(title);

        rotateAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                slideUpAnimation.start();
                slideUpAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(StartActivity.this, edu.dg202433.sabriter.authentification.LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        rotateAnimation.start();
    }
}
