package com.anjaniy.banglorehomepricepredictor;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anjaniy.banglorehomepricepredictor.fragments.About_App;
import com.anjaniy.banglorehomepricepredictor.fragments.Predictor;
import com.anjaniy.banglorehomepricepredictor.fragments.Saved_Predictions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar main_toolbar;
    private Toolbar nav_toolbar;
    private ProgressDialog dialog;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgetSetup();

        //WILL GET MESSAGE FRAGMENT AS DEFAULT FRAGMENT.
        if(savedInstanceState == null){
            main_toolbar.setTitle("Predictor");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Predictor()).commit();
            navigationView.setCheckedItem(R.id.predictor);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.predictor:
                    main_toolbar.setTitle("Predictor");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Predictor()).commit();
                    break;

                case R.id.saved_prediction:
                    main_toolbar.setTitle("Saved Predictions");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Saved_Predictions()).commit();
                    break;

                case R.id.about_app:
                    main_toolbar.setTitle("About App");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new About_App()).commit();
                    break;

                case R.id.logout:
                    AlertDialog.Builder builder_logout;
                    builder_logout = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

                    builder_logout.setMessage("Do you want to sign out??")

                            .setCancelable(false)

                            //CODE FOR POSITIVE(YES) BUTTON: -
                            .setPositiveButton("Yes", (dialog, which) -> {
                                //ACTION FOR "YES" BUTTON: -
                                showProgressDialog();
                                //ACTION FOR "YES" BUTTON: -
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(MainActivity.this, "Sign out has been successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                dismissDialog();
                            })

                            //CODE FOR NEGATIVE(NO) BUTTON: -
                            .setNegativeButton("No", (dialog, which) -> {
                                //ACTION FOR "NO" BUTTON: -
                                dialog.cancel();

                            });

                    //CREATING A DIALOG-BOX: -
                    AlertDialog alertDialog_signout = builder_logout.create();
                    //SET TITLE MAUALLY: -
                    alertDialog_signout.setTitle("Sign Out");
                    alertDialog_signout.show();
                    break;

                case R.id.delete_account:
                    AlertDialog.Builder builder_deleteAccount;
                    builder_deleteAccount = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

                    builder_deleteAccount.setMessage("Do you want to delete this account?")

                            .setCancelable(false)

                            //CODE FOR POSITIVE(YES) BUTTON: -
                            .setPositiveButton("Yes", (dialog, which) -> {
                                //ACTION FOR "YES" BUTTON: -
                                showProgressDialog();
                                //ACTION FOR "YES" BUTTON: -

                                FirebaseFirestore.getInstance().collection("Users")
                                        .whereEqualTo("emailAddress", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).get().addOnSuccessListener(queryDocumentSnapshotsUsers -> {
                                    WriteBatch writeBatchUser = FirebaseFirestore.getInstance().batch();
                                    List<DocumentSnapshot> snapshotsUsers = queryDocumentSnapshotsUsers.getDocuments();

                                    for(DocumentSnapshot snapshot: snapshotsUsers){
                                        writeBatchUser.delete(snapshot.getReference());
                                    }

                                    writeBatchUser.commit().addOnSuccessListener(unusedUsers -> Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete().addOnCompleteListener(taskUsers -> {
                                        if(taskUsers.isSuccessful()){
                                            FirebaseFirestore.getInstance().collection("Predictions")
                                                    .whereEqualTo("emailAddress", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()).get().addOnSuccessListener(queryDocumentSnapshotsPredictions -> {
                                                WriteBatch writeBatchPredictions = FirebaseFirestore.getInstance().batch();
                                                List<DocumentSnapshot> snapshotsPredictions = queryDocumentSnapshotsPredictions.getDocuments();

                                                for(DocumentSnapshot snapshot: snapshotsPredictions){
                                                    writeBatchPredictions.delete(snapshot.getReference());
                                                }

                                                writeBatchPredictions.commit().addOnSuccessListener(unusedPredictions -> Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete().addOnCompleteListener(taskPredictions -> {
                                                    if(taskPredictions.isSuccessful()){
                                                        Toast.makeText(MainActivity.this, "Account has been deleted successfully", Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(MainActivity.this, SplashScreen.class));
                                                        dismissDialog();
                                                    }
                                                    else{
                                                        dismissDialog();
                                                        Toast.makeText(MainActivity.this, Objects.requireNonNull(taskPredictions.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                })).addOnFailureListener(e -> {
                                                    dismissDialog();
                                                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                });
                                            });
                                        }
                                        else{
                                            dismissDialog();
                                            Toast.makeText(MainActivity.this, Objects.requireNonNull(taskUsers.getException()).getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    })).addOnFailureListener(e -> {
                                        dismissDialog();
                                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    });
                                });
                            })

                            //CODE FOR NEGATIVE(NO) BUTTON: -
                            .setNegativeButton("No", (dialog, which) -> {
                                //ACTION FOR "NO" BUTTON: -
                                dialog.cancel();

                            });

                    //CREATING A DIALOG-BOX: -
                    AlertDialog alertDialog_deleteAccount = builder_deleteAccount.create();
                    //SET TITLE MAUALLY: -
                    alertDialog_deleteAccount.setTitle("Delete Account");
                    alertDialog_deleteAccount.show();
                    break;

                case R.id.forgot_password:
                    AlertDialog.Builder builder_changePassword;
                    builder_changePassword = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

                    builder_changePassword.setMessage("Are you sure you forgot your password?")

                            .setCancelable(false)

                            //CODE FOR POSITIVE(YES) BUTTON: -
                            .setPositiveButton("Yes", (dialog, which) -> {
                                //ACTION FOR "YES" BUTTON: -
                                showProgressDialog();
                                //ACTION FOR "YES" BUTTON: -
                                FirebaseAuth.getInstance().sendPasswordResetEmail(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())).addOnSuccessListener(unused -> {
                                    dismissDialog();
                                    Toast.makeText(MainActivity.this, "Password reset link has been sent to your email.", Toast.LENGTH_LONG).show();
                                }).addOnFailureListener(e -> {
                                    dismissDialog();
                                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                });
                            })

                            //CODE FOR NEGATIVE(NO) BUTTON: -
                            .setNegativeButton("No", (dialog, which) -> {
                                //ACTION FOR "NO" BUTTON: -
                                dialog.cancel();

                            });

                    //CREATING A DIALOG-BOX: -
                    AlertDialog alertDialog_changePassword = builder_changePassword.create();
                    //SET TITLE MAUALLY: -
                    alertDialog_changePassword.setTitle("Forgot Password");
                    alertDialog_changePassword.show();
                    break;

            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void widgetSetup() {
        //CUSTOM - TOOLBAR: -
        main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        nav_toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(nav_toolbar);

        navigationView = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.Drawer_Layout);
        drawerLayout.setFitsSystemWindows(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,main_toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //ON_BACK_PRESSED() - METHOD: -
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            exitApp();
        }
    }

    private void exitApp() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);

        builder.setMessage("Do You Want To Close This App?")

                .setCancelable(false)

                //CODE FOR POSITIVE(YES) BUTTON: -
                .setPositiveButton("Yes", (dialog, which) -> {
                    //ACTION FOR "YES" BUTTON: -
                    finishAffinity();
                })

                //CODE FOR NEGATIVE(NO) BUTTON: -
                .setNegativeButton("No", (dialog, which) -> {
                    //ACTION FOR "NO" BUTTON: -
                    dialog.cancel();

                });

        //CREATING A DIALOG-BOX: -
        AlertDialog alertDialog = builder.create();
        //SET TITLE MAUALLY: -
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void dismissDialog() {
        dialog.dismiss();
    }
}