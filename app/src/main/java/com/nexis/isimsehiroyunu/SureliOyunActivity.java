package com.nexis.isimsehiroyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SureliOyunActivity extends AppCompatActivity {

    private TextView txtIlBilgi, txtIl, txtToplamPuan, txtSure;
    private EditText editTxtTahmin;
    private Button btnHarfAl, btnTahminEt, btnTekrarOyna;
    private String[] iller = {"Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya",
            "Ankara", "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir",
            "Bartın", "Batman", "Bayburt", "Bilecik", "Bingöl", "Bitlis",
            "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum",
            "Denizli", "Diyarbakır", "Düzce", "Elazığ", "Edirne", "Erzincan",
            "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari",
            "Hatay", "Iğdır", "Isparta", "Istanbul", "Izmir", "Kahramanmaraş",
            "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kırıkkale",
            "Kırklareli", "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya",
            "Malatya", "Manisa", "Mardin", "Mersin", "Muğla", "Muş",
            "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Şanlıurfa", "Şırnak",
            "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Uşak", "Van",
            "Yalova", "Yozgat", "Zonguldak"};

    private Random rndIl, rndHarf;
    private int rndIlNumber, rndNumberHarf, baslangicHarfSayisi, toplamSure = 180000;
    private String gelenIl, ilBoyutu, editTxtGelenTahmin;
    private ArrayList<Character> ilHarfleri;
    private float maximumPuan = 100.0f, azaltilicakPuan, toplamPuan = 0, bolumToplamPuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sureli_oyun);
        txtIlBilgi = (TextView) findViewById(R.id.txtViewIlBilgiS);
        txtIl = (TextView) findViewById(R.id.txtViewIlS);
        editTxtTahmin = (EditText) findViewById(R.id.editTxtTahminS);
        txtToplamPuan = (TextView) findViewById(R.id.txtViewToplamPuanS);
        txtSure = (TextView)findViewById(R.id.txtViewSureS);
        btnHarfAl = (Button)findViewById(R.id.btnHarfAlS);
        btnTahminEt = (Button)findViewById(R.id.btnTahminEtS);
        btnTekrarOyna = (Button)findViewById(R.id.btnTekrarOynaS);

        //1000 = 1saniye
        //60000 = 60saniye
        //180000 = 180 saniye = 3 dakikaka

        new CountDownTimer(toplamSure, 1000) {
            @Override
            public void onTick(long l) {
                txtSure.setText((l / 60000) + ":" + ((l % 60000) / 1000));
            }

            @Override
            public void onFinish() {
                txtSure.setText("0:00");
                editTxtTahmin.setEnabled(false);
                btnHarfAl.setEnabled(false);
                btnTahminEt.setEnabled(false);
                btnTekrarOyna.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Oyun Bitti\nToplam Puanınız: " + bolumToplamPuan + "\nTekrar Oynamak İçin Butona Basın.", Toast.LENGTH_LONG).show();
            }
        }.start();

        rndHarf = new Random();
        randomDegerleriBelirle();
    }

    public void btnHarfAlS(View v){
        if (ilHarfleri.size() > 0) {
            randomHarfAl();
            toplamPuan -= azaltilicakPuan;
            Toast.makeText(getApplicationContext(), "Kalan Puan = " + toplamPuan, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(), "Alınabilecek Harf Kalmadı.", Toast.LENGTH_SHORT).show();
    }

    public void btnTekrarOynaS(View v){
        Intent tekrarOyna = new Intent(this, SureliOyunActivity.class);
        finish();
        startActivity(tekrarOyna);
    }

    public void btnTahminEtS(View v){
        editTxtGelenTahmin = editTxtTahmin.getText().toString();

        if (!TextUtils.isEmpty(editTxtGelenTahmin)) {
            if (editTxtGelenTahmin.equals(gelenIl)){
                bolumToplamPuan += toplamPuan;
                Toast.makeText(getApplicationContext(), "Tebrikler Doğru Tahminde Bulundunuz.", Toast.LENGTH_SHORT).show();
                txtToplamPuan.setText("Toplam Bölüm Puanı: " + bolumToplamPuan);

                editTxtTahmin.setText("");
                randomDegerleriBelirle();
            }
            else
                Toast.makeText(getApplicationContext(), "Yanlış Tahminde Bulundunuz.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Tahmin Değeri Boş Olamaz.", Toast.LENGTH_SHORT).show();
    }

    private void randomDegerleriBelirle(){
        ilBoyutu = "";

        rndIl = new Random();
        rndIlNumber = rndIl.nextInt(iller.length);
        gelenIl = iller[rndIlNumber];
        System.out.println(rndIlNumber + " = " + gelenIl);
        txtIlBilgi.setText(gelenIl.length() + " Harfli İlimiz");

        if (gelenIl.length() >= 5 && gelenIl.length() <= 7)
            baslangicHarfSayisi = 1;
        else if (gelenIl.length() >= 8 && gelenIl.length() < 10)
            baslangicHarfSayisi = 2;
        else if (gelenIl.length() >= 10)
            baslangicHarfSayisi = 3;
        else
            baslangicHarfSayisi = 0;

        for (int i = 0; i < gelenIl.length(); i++) {
            if (i < gelenIl.length() - 1)
                ilBoyutu += "_ ";
            else
                ilBoyutu += "_";
        }

        txtIl.setText(ilBoyutu);
        ilHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray())
            ilHarfleri.add(c);

        for (int c = 0; c < baslangicHarfSayisi; c++)
            randomHarfAl();

        azaltilicakPuan = maximumPuan / ilHarfleri.size();
        toplamPuan = maximumPuan;
    }

    private void randomHarfAl(){
        rndNumberHarf = rndHarf.nextInt(ilHarfleri.size());
        String[] txtHarfler = txtIl.getText().toString().split(" ");
        char[] gelenIlHarfler = gelenIl.toCharArray();

        for (int i = 0; i < gelenIl.length(); i++) {
            if (txtHarfler[i].equals("_") && gelenIlHarfler[i] == ilHarfleri.get(rndNumberHarf)) {
                txtHarfler[i] = String.valueOf(ilHarfleri.get(rndNumberHarf));
                ilBoyutu = "";

                for (int j = 0; j < gelenIl.length(); j++) {
                    if (j == i)
                        ilBoyutu += txtHarfler[j] + " ";
                    else if (j < gelenIl.length() - 1)
                        ilBoyutu += txtHarfler[j] + " ";
                    else
                        ilBoyutu += txtHarfler[j];
                }

                break;
            }
        }

        txtIl.setText(ilBoyutu);
        ilHarfleri.remove(rndNumberHarf);
    }
}