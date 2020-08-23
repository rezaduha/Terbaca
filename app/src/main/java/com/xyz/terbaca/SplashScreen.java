package com.xyz.terbaca;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        if (!isNetworkAvailable()){
            //Create an alertDialog
            AlertDialog.Builder checkBuilder = new AlertDialog.Builder(SplashScreen.this);
            checkBuilder.setIcon(R.drawable.error);
            checkBuilder.setTitle("Error");
            checkBuilder.setMessage("Check your internet connection!");

            //Builder retry button
            checkBuilder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Restart the activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            checkBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alert = checkBuilder.create();
            alert.show();
        }
        else {
            if (isNetworkAvailable()){
                Thread thread = new Thread(){
                    public void run(){
                        try {
                            sleep(4500);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            startActivity(new Intent(SplashScreen.this, WebsiteActivity.class));
                            finish();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo           = connectivityManager.getActiveNetworkInfo();
        return  activeNetworkInfo != null;
    }
}