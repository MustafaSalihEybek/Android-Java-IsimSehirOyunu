package com.nexis.isimsehiroyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class NormalOyunActivity extends AppCompatActivity {

    private TextView txtIlBilgi, txtIl, txtToplamPuan;
    private EditText editTxtTahmin;
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
    private int rndIlNumber, rndNumberHarf, baslangicHarfSayisi;
    private String gelenIl, ilBoyutu, editTxtGelenTahmin;
    private ArrayList<Character> ilHarfleri;
    private float maximumPuan = 100.0f, azaltilicakPuan, toplamPuan = 0, bolumToplamPuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_oyun);
        txtIlBilgi = (TextView) findViewById(R.id.txtViewIlBilgiN);
        txtIl = (TextView) findViewById(R.id.txtViewIlN);
        editTxtTahmin = (EditText) findViewById(R.id.editTxtTahminN);
        txtToplamPuan = (TextView) findViewById(R.id.txtViewToplamPuanN);

        rndHarf = new Random();
        randomDegerleriBelirle();
    }

    public void btnTahminEtN(View v) {
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
                System.out.println("Yanlış Tahminde Bulundunuz.");
        } else
            System.out.println("Tahmin Değeri Boş Olamaz.");
    }

    public void btnHarfAlN(View v) {
        if (ilHarfleri.size() > 0) {
            randomHarfAl();
            toplamPuan -= azaltilicakPuan;
            Toast.makeText(getApplicationContext(), "Kalan Puan = " + toplamPuan, Toast.LENGTH_SHORT).show();
        }else
            System.out.println("Harf Kalmadı.");
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