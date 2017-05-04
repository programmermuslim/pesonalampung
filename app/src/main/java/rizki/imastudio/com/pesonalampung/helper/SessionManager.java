package rizki.imastudio.com.pesonalampung.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {
    private static final String KEY_TOKEN = "tokenLogin";
    private static final String KEY_LOGIN = "isLogin";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE =0;    Context c;

    //0 agar cuma bsa dibaca hp itu sendiri
    String PREF_NAME="TransfoodPreff";

    //konstruktor
    public SessionManager(Context c){
        this.c = c;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //membuat session login
    public void cerateLoginSession(String token){
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
        //commit digunakan untuk menyimpan perubahan
    }
    //mendapatkan token
    public String getToken(){
        return pref.getString(KEY_TOKEN, "");
    }
    //cek login
    public boolean isLogin(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
    //logout user
    public void logout(){
        editor.clear();
        editor.commit();
    }

    public void setNama(String nama){
        editor.putString("user_name", nama);
        editor.commit();
    }
    public String getNama(){
        return pref.getString("user_name", "");
    }
    public void setEmail(String email){
        editor.putString("user_email", email);
        editor.commit();
    }
    public String getEmail(){
        return pref.getString("user_email", "");
    }
    public void setPhone(String phone){
        editor.putString("user_phone", phone);
        editor.commit();
    }
    public String getPhone(){
        return pref.getString("user_phone", "");
    }
    public void setAlamat(String user_alamat){
        editor.putString("user_alamat", user_alamat);
        editor.commit();
    }
    public String getAlamat(){
        return pref.getString("user_alamat", "");
    }
    public void setIduser(String id_user){
        editor.putString("id_user", id_user);
        editor.commit();
    }
    public String getIdUser(){
        return pref.getString("id_user", "");
    }

    public void setGcm(String gcm){
        editor.putString("gcm", gcm);
        editor.commit();
    }
    public String getGcm(){
        return pref.getString("gcm", "");
    }

}
