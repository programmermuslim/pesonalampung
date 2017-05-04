package rizki.imastudio.com.pesonalampung;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rizki.imastudio.com.pesonalampung.ModelWisata.ModelWisata;
import rizki.imastudio.com.pesonalampung.helper.Constant;
import rizki.imastudio.com.pesonalampung.helper.No_Internet;
import rizki.imastudio.com.pesonalampung.helper.NurHelper;
import rizki.imastudio.com.pesonalampung.helper.SessionManager;

import static com.androidquery.util.AQUtility.getContext;


public class ActDetailWisata extends BaseActivity {
    Context c = this;
    private boolean mIsAppFirstLaunched = true;


    SessionManager sesi;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public ViewFlipper viewFlipper;
    public ListView ls;
    public FrameLayout container;
    AQuery aq;
    String teksNmProduk;
    String teksDesProduk;
    String teksGbrProduk;
    String teksHrgProduk;
    String idKat;
    private ListView lvData;
//    String idMenu;
    String idMenuDiskon, nmHotel, alHotel, idDataProduk;
    String idKatDiskon, idProdukDiskon, nmMenu, namaKategori, statusOps;
    String nmProduk, gbrProduk, hrgProduk, deksProduk, deksHotel, ketMenu, gbrMenu;
    private ArrayList<ModelWisata> data;
    private String id_menu, id_detail_hotel, idHotel, teksRunning, idMenu, gbr_menu;
    String id_promo, kode_promo, id_menu_produk, persen_promo, status_promo;
    String dataKode;
    TextView detailNamaMenu, detailMenuDes, detailHarga, detailMenuTotal;
    EditText etDetailPesan;
    TextView detailMenuJumlah;
    ImageView gbrWisata;
    ImageView btnMin, btnPlus;
    int counter ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_hotel);

        counter = 0;

        sesi= new SessionManager(this);
//         text3 = (TextView)v.findViewById(R.id.txtTeks3);

        Intent bundle = getIntent();

        idMenuDiskon = bundle.getStringExtra("id_menu");
        idProdukDiskon = bundle.getStringExtra("id_produk");
        idKatDiskon = bundle.getStringExtra("id_kat");
        nmProduk = bundle.getStringExtra(Constant.nama_wisata);
        gbrProduk = bundle.getStringExtra(Constant.gbr_wisata);
        hrgProduk = bundle.getStringExtra("hrg_produk");
        deksProduk = bundle.getStringExtra(Constant.ket_wisata);



        aq = new AQuery(this);

        if(!NurHelper.isOnline(ActDetailWisata.this)){
            startActivity(new Intent(ActDetailWisata.this, No_Internet.class));
            finish();
        }else{

            detailNamaMenu = (TextView)findViewById(R.id.detailNamaMenu);
            gbrWisata = (ImageView) findViewById(R.id.ivMenuKuImgResto);
            detailMenuDes = (TextView)findViewById(R.id.detailMenuDes);
            detailMenuDes.setText(deksProduk);
            detailNamaMenu.setText(nmProduk);
            Picasso.with(getApplicationContext()).load(NurHelper.BASE_URL_IMAGE_DISKON+ gbrProduk).placeholder(R.drawable.no_image).
                    into(gbrWisata);

        }










    }






}
