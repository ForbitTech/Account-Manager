package com.forbitbd.accountmanager.ui.layout_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.ChangeBounds;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.forbitbd.accountmanager.R;

public class LayoutTestActivity extends AppCompatActivity {

    private Scene mSceneA,mSceneB,mCurrentScene;
    private Transition mTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_test);
        ConstraintLayout root = findViewById(R.id.root);

        mSceneA = Scene.getSceneForLayout(root,R.layout.scene_a,this);
        mSceneB = Scene.getSceneForLayout(root,R.layout.scene_b,this);
        mSceneA.enter();
        mCurrentScene = mSceneA;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    LayoutTestActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chnageScene();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void chnageScene(){
        if(mCurrentScene==mSceneA){
            Transition transition = new ChangeBounds();
            transition.setDuration(700);
            transition.setInterpolator(new AccelerateDecelerateInterpolator());
            TransitionManager.go(mSceneB,transition);
            mCurrentScene = mSceneB;
        }else{
            Transition transition = new ChangeBounds();
            transition.setDuration(700);
            transition.setInterpolator(new AccelerateDecelerateInterpolator());
            TransitionManager.go(mSceneA,transition);
            mCurrentScene = mSceneA;
        }
    }
}