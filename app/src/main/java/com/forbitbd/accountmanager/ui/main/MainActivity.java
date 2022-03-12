package com.forbitbd.accountmanager.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.login.LoginActivity;
import com.forbitbd.accountmanager.utils.BaseActivity;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.User;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financel.FinanceActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.View,Communicator {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Scene mSceneHome,mSceneCreateProject,mCurrentScene,
            searchUserScene,shareScene,sharedUsersScene,profileScene,shareBusinessScene;
    private Transition mTransition;

    private User currentUser;

    private MainPresenter mPresenter;
    private List<Project> projectList;
    private Project updatedProject;
    private User searchedUser;

    private Bitmap userBitmap;

//    private final SCropImageContractJava.Presenter presenter = new SCropImagePresenterJava();

    private final ActivityResultLauncher<CropImageContractOptions> cropImage =
            registerForActivityResult(new CropImageContract(), new ActivityResultCallback<CropImageView.CropResult>() {
                @Override
                public void onActivityResult(CropImageView.CropResult result) {
                    if(result.isSuccessful()){
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), result.getUriContent());
                            userBitmap = bitmap;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                loadBannerAd(R.id.adView);
            }
        });

        ConstraintLayout container = findViewById(R.id.container);

        mSceneHome = Scene.getSceneForLayout(container,R.layout.scene_home,this);
        mSceneCreateProject = Scene.getSceneForLayout(container,R.layout.scene_create_project,this);
        searchUserScene = Scene.getSceneForLayout(container,R.layout.scene_search_user,this);
        shareScene = Scene.getSceneForLayout(container,R.layout.scene_share,this);
        sharedUsersScene = Scene.getSceneForLayout(container,R.layout.scene_shared_users,this);
        profileScene = Scene.getSceneForLayout(container,R.layout.scene_profile,this);
        shareBusinessScene = Scene.getSceneForLayout(container,R.layout.scene_share_business,this);



        mPresenter = new MainPresenter(this);

        mPresenter.getCurrentUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());



    }

    private void loadBannerAd(int id) {
        AdView mAdView = findViewById(id);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id =item.getItemId();
        String title = getString(R.string.businesslist);
        if (id == R.id.home){
            showHomeScene();
        }else if (id == R.id.logout){
            signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else if(id== R.id.profile){
            showProfileScene();
        }else if(id== R.id.shared_business){
            showShareBusinessScene();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(mCurrentScene!=mSceneHome){
                showHomeScene();
            }else{
                finish();
            }

        }
    }

    @Override
    public void saveUserAndShowHomeFragment(User user) {
        this.currentUser = user;
        mPresenter.getUserProjects(user);
    }

    private void updateNav() {
        ImageView ivImage = navigationView.findViewById(R.id.image);
        TextView tvName = navigationView.findViewById(R.id.name);
        TextView tvEmail = navigationView.findViewById(R.id.email);

        tvName.setText(currentUser.getName());
        tvEmail.setText(currentUser.getEmail());
        if(currentUser.getImage()!=null && !currentUser.getImage().equals("")){
            Picasso.get().load(currentUser.getImage()).into(ivImage);
        }


    }

    @Override
    public void startHomeScene(List<Project> projectList) {
        this.projectList = projectList;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);



        updateNav();
        mSceneHome.enter();
        mCurrentScene = mSceneHome;
    }

    @Override
    public void deleteProjectFromList(Project project) {
        this.projectList.remove(project);
        showHomeScene();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Bitmap getUserBitmap() {
        return userBitmap;
    }

    @Override
    public void openCropImageActivity() {
        CropImageContractOptions options = new CropImageContractOptions(null, new CropImageOptions())
                .setScaleType(CropImageView.ScaleType.CENTER)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4, 4);
//                .setMaxZoom(8)
//                .setAutoZoomEnabled(true)
//                .setMultiTouchEnabled(true)
//                .setCenterMoveEnabled(true)
//                .setShowCropOverlay(false)
//                .setAllowFlipping(false)
//                .setSnapRadius(10f)
//                .setTouchRadius(30f)
//                .setInitialCropWindowPaddingRatio(0.3f)
//                .setBorderLineThickness(5f)
//                .setBorderLineColor(R.color.black)
//                .setBorderCornerThickness(6f)
//                .setBorderCornerOffset(2f)
//                .setBorderCornerLength(20f)
//                //.setBorderCornerColor(RED)
//                .setGuidelinesThickness(5f)
//                //.setGuidelinesColor(RED)
//                .setBackgroundColor(Color.argb(119, 30, 60, 90))
//                .setMinCropWindowSize(20, 20)
//                .setMinCropResultSize(16, 16)
//                .setMaxCropResultSize(999, 999)
//                .setActivityTitle("CUSTOM title")
//                //.setActivityMenuIconColor(RED)
//                .setOutputUri(null)
//                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
//                .setOutputCompressQuality(50)
//                .setRequestedSize(100, 100)
//                .setRequestedSize(100, 100, CropImageView.RequestSizeOptions.RESIZE_FIT)
//                .setInitialCropWindowRectangle(null)
//                .setInitialRotation(180)
//                .setAllowCounterRotation(true)
//                .setFlipHorizontally(true)
//                .setFlipVertically(true)
//                .setCropMenuCropButtonTitle(getString(R.string.ok))
//                .setCropMenuCropButtonIcon(R.drawable.ac)
//                .setAllowRotation(true)
//                .setNoOutputImage(false)
//                .setFixAspectRatio(true);

        cropImage.launch(options);
    }

    @Override
    public void updateUserInServer(User user) {
        mPresenter.updateUser(user,getUserBytes());
    }

    private byte[] getUserBytes(){
        if(userBitmap!=null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            userBitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
            byte[] bytes = bos.toByteArray();
            return bytes;
        }

        return null;
    }

    @Override
    public void sharedProjectDone() {
        this.updatedProject = null;
        this.searchedUser = null;
        showToast("Share Project Done Successfully");
        showHomeScene();
    }

    @Override
    public void updateComplete(User user) {
        showToast("Update User Successfully");
        this.currentUser = user;
        this.userBitmap = null;
        updateNav();
        showHomeScene();
    }

    @Override
    public void startFinanceActivity(Project project) {
        SharedProject sp = new SharedProject(project);
        if(sp!=null){
            Intent intent = new Intent(this, FinanceActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.PROJECT,sp);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{

        }

    }

    @Override
    public void startFinanceActivity(SharedProject sharedProject) {
        Intent intent = new Intent(this, FinanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public void showCreateProjectScene(Project project) {
        this.updatedProject = project;
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(mSceneCreateProject,transition);
        mCurrentScene = mSceneCreateProject;

    }

    @Override
    public void showHomeScene() {
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(mSceneHome,transition);
        mCurrentScene = mSceneHome;
    }

    @Override
    public void showShareScene() {
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(shareScene,transition);
        mCurrentScene = shareScene;
    }

    @Override
    public void showProfileScene() {
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(profileScene,transition);
        mCurrentScene = profileScene;
    }

    @Override
    public void showShareBusinessScene() {
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(shareBusinessScene,transition);
        mCurrentScene = shareBusinessScene;
    }

    @Override
    public void addProjectTotheList(Project project) {
        this.projectList.add(project);
        showHomeScene();
    }

    @Override
    public void updateProjectInList(Project project) {
        this.projectList.set(projectList.indexOf(updatedProject),project);
        this.updatedProject = null;
        showHomeScene();
    }

    @Override
    public void deleteProject(Project project) {
        mPresenter.deleteProject(project);
    }

    @Override
    public void setSearchUser(User user) {
        this.searchedUser = user;
        // Open Shared User Scene Here;
        showShareScene();
    }

    @Override
    public void shareProject(SharedProject sharedProject) {
        mPresenter.shareProject(sharedProject);
    }

    @Override
    public void deleteSharedProject(SharedProject sharedProject) {
         mPresenter.deleteSharedProject(sharedProject);
    }

    @Override
    public User getSearchedUser() {
        return this.searchedUser;
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public List<Project> getProjects() {
        return this.projectList;
    }

    @Override
    public Project getUpdateProject() {
        return updatedProject;
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showSearchUserScene(Project project) {
        this.updatedProject = project;
        Transition transition = new ChangeBounds();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(searchUserScene,transition);
        mCurrentScene = searchUserScene;

    }

    @Override
    public void showSharedUsersScene(Project project) {
        this.updatedProject = project;
        Transition transition = new Fade();
        transition.setDuration(500);
        transition.setInterpolator(new OvershootInterpolator());
        TransitionManager.go(sharedUsersScene,transition);
        mCurrentScene = sharedUsersScene;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
//                    userBitmap = bitmap;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }
}