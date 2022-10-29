package com.nexis.isimsehiroyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnAnasayfaAyar(View v){
        switch (v.getId()){
            case R.id.btnNormalOyun:
                aktiviteyeGec("NormalOyun");
                break;

            case R.id.btnSureliOyun:
                aktiviteyeGec("SureliOyun");
                break;

            case R.id.btnCikis:
                cikisYap();
                break;
        }
    }

    private void aktiviteyeGec(String aktiviteIsmi){
        if (aktiviteIsmi.equals("NormalOyun"))
            intent = new Intent(this, NormalOyunActivity.class);
        else
            intent = new Intent(this, SureliOyunActivity.class);

        startActivity(intent);
    }

    private void cikisYap(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        cikisYap();
    }
}