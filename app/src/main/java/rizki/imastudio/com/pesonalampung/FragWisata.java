package rizki.imastudio.com.pesonalampung;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import static com.androidquery.util.AQUtility.getContext;


public class FragWisata extends AppCompatActivity {


    private ListView lvData;
    private ArrayList<ModelWisata> data;
    String idKatDiskon, status_ops, gambarMenuDiskon;
    private RestoranAdapter adapter;
    ImageView ivMenuKuImgResto;
//    String idKatDiskon;

    Context context = this;


    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_wisata);
        data = new ArrayList<>();
        lvData = (ListView) findViewById(R.id.lvRestoranFragment);
        Intent bundle = getIntent();
        idKatDiskon = bundle.getStringExtra("id_kat_wisata");
        gambarMenuDiskon = bundle.getStringExtra(Constant.GMB_KAT);
//        Toast.makeText(getContext(), "Gambar: " + gambarMenuDiskon  , Toast.LENGTH_LONG).show();
        ivMenuKuImgResto = (ImageView)findViewById(R.id.ivMenuKuImgResto);
        Picasso.with(getContext()).load(NurHelper.BASE_URL_IMAGE_DISKON+ gambarMenuDiskon).placeholder(R.drawable.no_image).
                into(ivMenuKuImgResto);
        aq = new AQuery(context);
        if (!NurHelper.isOnline(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), No_Internet.class));
            finish();

        } else {
            //ambil data booking
            getDataRestoran();

        }
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                ModelMeMnuDiskon b = data.get(position);
//                Intent a = new Intent(getContext(), ActivityProdukDiskon.class);
//                a.putExtra(ConstantDIkson.ID_MENU_DISKON, data.get(position).getId_hotel());
//                a.putExtra(ConstantDIkson.GBR_HOTEL, data.get(position).getGbr_hotel());
//                a.putExtra(ConstantDIkson.NAMA_HOTEL, data.get(position).getNama_hotel());
//                a.putExtra(ConstantDIkson.ALAMAT_HOTEL, data.get(position).getAlamat_hotel());
//                a.putExtra(ConstantDIkson.ID_KAT_DISKON, data.get(position).getId_kat_diskon());
//                a.putExtra(ConstantDIkson.id_produk_diskon, data.get(position).getId_produk_diskon());
//                a.putExtra(ConstantDIkson.nama_menu, data.get(position).getNama_menu());
//                a.putExtra(ConstantDIkson.GBR_MENU_DISKON, data.get(position).getGambar_menu());
//                a.putExtra(ConstantDIkson.nama_produk, data.get(position).getNama_produk());
//                a.putExtra(ConstantDIkson.gbr_produk, data.get(position).getGbr_produk());
//                a.putExtra(ConstantDIkson.hrg_produk, data.get(position).getHrg_produk());
//                a.putExtra(ConstantDIkson.deks_produk, data.get(position).getDeks_produk());
//                a.putExtra(ConstantDIkson.id_hotel, data.get(position).getId_hotel());
//                a.putExtra(ConstantDIkson.desk_hotel, data.get(position).getDesk_hotel());
//                a.putExtra(ConstantDIkson.ket_menu, data.get(position).getKet_menu());
//                a.putExtra(ConstantDIkson.gambar_menu, data.get(position).getGambar_menu());
//
//                getContext().startActivity(a);
            }
        });
    }





    private void getDataRestoran() {
        data.clear();
        //ambil data dari server
        String url = NurHelper.BASE_URL_DISKON + "wisataByKategori";
        Map<String, String> parampa = new HashMap<>();
        //parampa.put("t_device", NurHelper.getDeviceUUID(c));
        //parampa.put("t_token", sesi.getToken());
        parampa.put("id_kat_wisata", idKatDiskon);
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
                                            ModelWisata b = new ModelWisata();
                                            b.setId_kat_wisata(object.getString("id_kat_wisata"));
                                            b.setGbr_wisata(object.getString("gbr_wisata"));
                                            b.setKet_wisata(object.getString("ket_wisata"));
                                            b.setNama_wisata(object.getString("nama_wisata"));
                                            b.setId_obj_wisata(object.getString("id_obj_wisata"));



                                            //memasukkan data kedalam model booking
                                            data.add(b);
                                            //masukkan data arraylist kedalam custom adapter
                                            adapter = new RestoranAdapter(FragWisata.this, data);
                                            lvData.setAdapter(adapter);

                                        }
                                    } else {
                                        //  NurHelper.pesan(getActivity(), pesan);

                                        lvData.setVisibility(View.GONE);
                                        ivMenuKuImgResto.setVisibility(View.GONE);
//                                        RelativeLayout menuKosong = (RelativeLayout)getActivity(). findViewById(R.id.menuKosong);
//                                        menuKosong.setVisibility(View.VISIBLE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // NurHelper.pesan(getActivity(), "Error parsing data");
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            //  NurHelper.pesan(getActivity(), "Error get data");
            e.printStackTrace();
        }
    }

    private class RestoranAdapter extends BaseAdapter {
        private Activity c;
        private ArrayList<ModelWisata> datas;
        private LayoutInflater inflater = null;

        public RestoranAdapter(Activity c, ArrayList<ModelWisata> data) {
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
            TextView restoAlamat, restoNama;
            ImageView restoImg, restoInfo;
            CardView cardHome;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder = null;
            if (v == null) {
                inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_wisata, null);
                holder = new ViewHolder();
                holder.restoNama = (TextView) v.findViewById(R.id.listKateg);
                holder.restoAlamat = (TextView) v.findViewById(R.id.tvMenuKUDes);
                holder.restoImg = (ImageView) v.findViewById(R.id.katImage);
//                holder.restoInfo = (ImageView) v.findViewById(R.id.ivRestoFragInfo);
                holder.cardHome = (CardView)v.findViewById(R.id.cardHome);
                v.setTag(holder);

            } else {
                holder = (ViewHolder) v.getTag();
            }
            //masukkan data booking
            ModelWisata b = datas.get(position);

            holder.restoNama.setText(b.getNama_wisata());
            holder.restoAlamat.setText(b.getAlamat_wisata());
            holder.restoAlamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent a = new Intent(getApplicationContext(), ActDetailWisata.class);

                        a.putExtra(Constant.nama_wisata, datas.get(position).getNama_wisata());
                        a.putExtra(Constant.gbr_wisata, datas.get(position).getGbr_wisata());
                        a.putExtra(Constant.ket_wisata, datas.get(position).getKet_wisata());

                        startActivity(a);


                }
            });
            Picasso.with(getContext()).load(NurHelper.BASE_URL_IMAGE_DISKON+b.getGbr_wisata()).placeholder(R.drawable.no_image).
                    into(holder.restoImg);
            holder.restoNama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                      Intent a = new Intent(getApplicationContext(), ActDetailWisata.class);

                    a.putExtra(Constant.nama_wisata, datas.get(position).getNama_wisata());
                    a.putExtra(Constant.gbr_wisata, datas.get(position).getGbr_wisata());
                    a.putExtra(Constant.ket_wisata, datas.get(position).getKet_wisata());

                    startActivity(a);



                }
            });

            holder.cardHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
        Intent a = new Intent(getContext(), ActDetailWisata.class);

                    a.putExtra(Constant.nama_wisata, datas.get(position).getNama_wisata());
                    a.putExtra(Constant.gbr_wisata, datas.get(position).getGbr_wisata());
                    a.putExtra(Constant.ket_wisata, datas.get(position).getKet_wisata());

                    startActivity(a);

                }
            });
            holder.restoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(getApplicationContext(), ActDetailWisata.class);

                    a.putExtra(Constant.nama_wisata, datas.get(position).getNama_wisata());
                    a.putExtra(Constant.gbr_wisata, datas.get(position).getGbr_wisata());
                    a.putExtra(Constant.ket_wisata, datas.get(position).getKet_wisata());

                    startActivity(a);


                }
            });
//            imageLoader.DisplayImage(NurHelper.BASE_URL_IMAGE + b.getLogoResto(), holder.restoImg);
            return v;
        }
    }


}
