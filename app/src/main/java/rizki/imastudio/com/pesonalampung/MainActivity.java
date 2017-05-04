package rizki.imastudio.com.pesonalampung;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

import rizki.imastudio.com.pesonalampung.ModelWisata.ModelKategori;
import rizki.imastudio.com.pesonalampung.helper.Constant;
import rizki.imastudio.com.pesonalampung.helper.No_Internet;
import rizki.imastudio.com.pesonalampung.helper.NurHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    public ViewFlipper viewFlipper;
    public ListView ls;
    public FrameLayout container;
    TextView tvJoin;

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mRelativeLayout;
    private PopupWindow mPopupWindow;


    ImageView imgPopUpIklan;
    TextView txtHello;

    ImageView closeButton;

    private ProgressDialog progressDialog;
    JSONArray jsonArray = null;
    private CustomAdapter adapter;
    private String imgMenu, nmMenu, desMenu, hargaMenu,totalDiskon,  teksRunning, idMenu, statusOps, gbrIklan;
    private boolean mIsAppFirstLaunched = true;
    private ArrayList<ModelKategori> data;

    TextView txtWilayah, txt1, text2, text3;
    AQuery aq;
    Context contect = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        aq = new AQuery(contect);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        ls = (ListView) findViewById(R.id.ls);
        this.container = (FrameLayout)findViewById(R.id.container);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.push_left_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.push_left_out));
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();

        data = new ArrayList<>();



        // Get the widgets reference from XML layout
        mRelativeLayout = (LinearLayout)findViewById(R.id.fragmentHome);


        if(!NurHelper.isOnline(getApplicationContext())){
            startActivity(new Intent(MainActivity.this, No_Internet.class));
            finish();
        }else{

            getSliderDiskon();
            getMenuDiskon();


        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ls.setVisibility(View.GONE);
                viewFlipper.setVisibility(View.GONE);
//
////
                Intent a = new Intent(getApplicationContext(), FragWisata.class);
                a.putExtra(Constant.ID_KAT, data.get(position).getId_kat_wisata());
                a.putExtra(Constant.NAMA_KAT, data.get(position).getNama_kat());
                a.putExtra(Constant.GMB_KAT, data.get(position).getGbr_kategori());
                startActivity(a);
//                  Global.id_kat_diskon = data.get(position).getId_menu_diskon();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent a = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(a);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent a = new Intent(getApplicationContext(), Contact.class);
            startActivity(a);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getSliderDiskon() {
        // clear data sebelumnya
        data.clear();
        //ambil data dari server
        String url = NurHelper.BASE_URL_DISKON + "sliderWisata";
        Map<String, String> parampa = new HashMap<>();
        try {
            //mencari url dan parameter yang dikirimkan
            NurHelper.pre("Url : " + url + ", params " + parampa.toString());
            //koneksi ke server meggunakan aquery
            aq.ajax(url, parampa, String.class,
                    new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String hasil, AjaxStatus status) {
                            //cek apakah hasilnya null atau tidak
                            if (hasil != null) {
                                NurHelper.pre("Respon : " + hasil);
                                //merubah string menjadi json
                                try {
                                    JSONObject json = new JSONObject(hasil);
                                    String result = json.getString("result");
                                    String pesan = json.getString("msg");
                                    // NurHelper.pesan(getActivity(), pesan);
                                    if (result.equalsIgnoreCase("true")) {
                                        JSONArray jsonArray = json.getJSONArray("data");
                                        for (int a = 0; a < jsonArray.length(); a++) {
                                            HashMap<String, String> dataMap = new HashMap<>();
                                            JSONObject object = jsonArray.getJSONObject(a);
                                            idMenu = object.getString("id_slider");
                                            nmMenu = object.getString("slider");
//                                            hargaMenu = object.getString("harga_menu");
//                                            desMenu = object.getString("sts_batam_dikkon");
                                            imgMenu = object.getString("foto_slider");
//                                            Nmresto = object.getString("resto_nama");


                                            if ((a < Constant.VALUE_SLIDESHOW) && mIsAppFirstLaunched) {
                                                createSlideshow(idMenu, nmMenu, desMenu,
                                                        imgMenu);
                                            }
                                        }
                                    } else {
                                        NurHelper.pesan(getApplicationContext(), pesan);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    NurHelper.pesan(getApplicationContext(), "Error parsing data");
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            NurHelper.pesan(getApplicationContext(), "Error get data");
            e.printStackTrace();
        }
    }

    private void createSlideshow(String idMenu, String nmMenu, String desMenu, String imgMenu) {
        // Get layout
        View view = getLayoutInflater().inflate(R.layout.layout_slideshow, null);

        // Create view objects
        ImageView menuImg = (ImageView) view.findViewById(R.id.imgMenu);
        TextView namaMenu = (TextView) view.findViewById(R.id.tvNamaMenu);
        TextView judulResto = (TextView) view.findViewById(R.id.tvNamaResto);
        TextView deskripsiMenu = (TextView) view.findViewById(R.id.tvDesMenu);

        menuImg.setId(Integer.parseInt(idMenu));
//        menuImg.setOnClickListener(getApplicationContext());
        Picasso.with(getApplicationContext()).load(NurHelper.BASE_URL_IMAGE_DISKON+ imgMenu).placeholder(R.drawable.no_image).
                into(menuImg);

        viewFlipper.addView(view);
    }

    private void getMenuDiskon() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            data.clear();
            //ambil data dari server
            String url = NurHelper.BASE_URL_DISKON + "get_kategoriWisata";
            Map<String, String> parampa = new HashMap<>();
            //menambahkan progres dialog loading
            ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setInverseBackgroundForced(false);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setMessage("Loading..");
            try {
                //mencari url dan parameter yang dikirimkan
                NurHelper.pre("Url : " + url + ", params " + parampa.toString());
                //koneksi ke server meggunakan aquery
                aq.progress(progressDialog).ajax(url, parampa, String.class,
                        new AjaxCallback<String>() {
                            @Override
                            public void callback(String url, String hasil, AjaxStatus status) {
                                //cek apakah hasilnya null atau tidak
                                if (hasil != null) {
                                    NurHelper.pre("Respon : " + hasil);
                                    //merubah string menjadi json
                                    try {
                                        JSONObject json = new JSONObject(hasil);
                                        String result = json.getString("result");
                                        String pesan = json.getString("msg");
                                        // NurHelper.pesan(getActivity(), pesan);
                                        if (result.equalsIgnoreCase("true")) {
                                            JSONArray jsonArray = json.getJSONArray("data");
                                            for (int a = 0; a < jsonArray.length(); a++) {
                                                JSONObject object = jsonArray.getJSONObject(a);
                                                //ambil data perbooking dan masukkan ke kelas object model
                                                ModelKategori b = new ModelKategori();
                                                b.setId_kat_wisata(object.getString("id_kat_wisata"));
                                                b.setNama_kat(object.getString("nama_kat"));
//                                                b.setKet_menu(object.getString("ket_menu"));
                                                b.setGbr_kategori(object.getString("gbr_kategori"));

                                                //memasukkan data kedalam model booking
                                                data.add(b);
                                                //masukkan data arraylist kedalam custom adapter
                                                adapter = new CustomAdapter(getApplicationContext(), data);
                                                ls.setAdapter(adapter);

                                            }
                                        } else {
                                            // NurHelper.pesan(getActivity(), pesan);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        // NurHelper.pesan(getActivity(), "Error parsing data");
                                    }
                                }
                            }
                        });

            } catch (Exception e) {
                NurHelper.pesan(getApplicationContext(), "Error get data");
                e.printStackTrace();
            }
        }
    }

    private class CustomAdapter extends BaseAdapter {
        private Context c;
        private ArrayList<ModelKategori> datas;
        private LayoutInflater inflater = null;

        public CustomAdapter(Context c, ArrayList<ModelKategori> data) {
            this.c = c;
            datas = new ArrayList<>();
            this.datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView nmWilayah;
            ImageView imgWilayah;
            TextView cpWilayah;
            CardView cardView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder = null;
            if (v == null) {
                inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_wisata, null);
                holder = new ViewHolder();
                holder.nmWilayah = (TextView) v.findViewById(R.id.listKateg);
                holder.cardView = (CardView) v.findViewById(R.id.cardHome);
                holder.imgWilayah = (ImageView) v.findViewById(R.id.katImage);


                v.setTag(holder);

            } else {
                holder = (ViewHolder) v.getTag();
            }
            //masukkan data booking
            final ModelKategori b = datas.get(position);
            //holder.tvTanggal.setText(NurHelper.tglJamToInd(b.getBookingTgl()));
            holder.nmWilayah.setText(b.getNama_kat());
            holder.nmWilayah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(getApplicationContext(), FragWisata.class);
                    a.putExtra(Constant.ID_KAT, data.get(position).getId_kat_wisata());
                    a.putExtra(Constant.NAMA_KAT, data.get(position).getNama_kat());
                    a.putExtra(Constant.GMB_KAT, data.get(position).getGbr_kategori());
                    startActivity(a);
                }
            });
            holder.imgWilayah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(getApplicationContext(), FragWisata.class);
                    a.putExtra(Constant.ID_KAT, data.get(position).getId_kat_wisata());
                    a.putExtra(Constant.NAMA_KAT, data.get(position).getNama_kat());
                    a.putExtra(Constant.GMB_KAT, data.get(position).getGbr_kategori());
                    startActivity(a);
                }
            });
            Picasso.with(getApplicationContext()).load(NurHelper.BASE_URL_IMAGE_DISKON+ imgMenu).placeholder(R.drawable.no_image).
                    into(holder.imgWilayah);

            return v;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
//                actionLogin();
                getSliderDiskon();


                //return true;
            } else {
                Toast.makeText(getApplicationContext(), "Until you grant the permission, we canot login", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
