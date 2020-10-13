package platformpbp.uajy.quickresto;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import platformpbp.uajy.quickresto.model.Reservation;

public class SharePreferenceClass {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private static final String EMAIL="MAIL";
    private static final String PREFERENCE_NAME = "JSMPref";
    private static final String LOGIN = "Login";
    private static final String USERNAME = "Username";
    private static final String PHONE ="phone";
    private static final String PASS="pass";


    private static final String RESTNAME="revname";
    private static final String ADDRESS="address";
    private static final String JMLHORANG="jmlhorang";
    private static final String DATERESERV="reservdate";
    private static final String TIMERESERV="timereserv";

    public SharePreferenceClass(Context context){
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void createSession(UserClass user){
        editor.putBoolean(LOGIN,true);
        editor.putString(USERNAME,user.getFullName());
        editor.putString(EMAIL, user.getMail());
        editor.putString(PHONE,user.getPhone());
        editor.putString(PASS,user.getPass());
        editor.commit();
    }

    public void createReseravation(Reservation reserv){
        editor.putString(RESTNAME,reserv.getNameRest());
        editor.putString(ADDRESS,reserv.getAddress());
        editor.putString(JMLHORANG,String.valueOf(reserv.getJmlhOrg()));
        editor.putString(DATERESERV,reserv.getDateResrv());
        editor.putString(TIMERESERV,reserv.getTimeResrv());
        editor.commit();
    }

    public Reservation getReservation(){
        Reservation reserv= new Reservation();
        reserv.setNameRest(preferences.getString(RESTNAME,""));
        reserv.setAddress(preferences.getString(ADDRESS,""));
        reserv.setJmlhOrg(Integer.parseInt(preferences.getString(JMLHORANG,"")));
        reserv.setDateResrv(preferences.getString(DATERESERV,""));
        reserv.setTimeResrv(preferences.getString(TIMERESERV,""));
        return reserv;
    }

    public void closeSession(){
        if(this.loginS()){
            editor.clear();
            editor.commit();}
    }


    public UserClass getuser(){
        UserClass userClass=new UserClass();
        userClass.setFullName(preferences.getString(USERNAME,""));
        userClass.setPhone(preferences.getString(PHONE, ""));
        userClass.setMail(preferences.getString(EMAIL,""));
        userClass.setPass(preferences.getString(PASS,""));
        return userClass;
    }

    public String getUsernameS(){
        return preferences.getString(EMAIL,"");
    }
    public boolean loginS(){
        return preferences.getBoolean(LOGIN, false);
    }
}
