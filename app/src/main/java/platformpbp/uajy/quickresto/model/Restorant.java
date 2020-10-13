package platformpbp.uajy.quickresto.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Restorant {
    public String NamaResto;
    public String jeniResto;
    public String alamat;
    double jarak;
    public String tlp;
    public double longitude;
    public double latitude;
    public String urlfoto;

    public Restorant(){

    }

    public Restorant(String namaResto, String jeniResto, String alamat, double jarak, String tlp, double longitude, double latitude, String urlfoto) {
        NamaResto = namaResto;
        this.jeniResto = jeniResto;
        this.jarak = jarak;
        this.tlp = tlp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.urlfoto = urlfoto;
        this.alamat=alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaResto() {
        return NamaResto;
    }

    public void setNamaResto(String namaResto) {
        NamaResto = namaResto;
    }

    public String getJeniResto() {
        return jeniResto;
    }

    public void setJeniResto(String jeniResto) {
        this.jeniResto = jeniResto;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public String getTlp() {
        return tlp;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    @BindingAdapter("loadImage")
    public static void getGambar(ImageView imgView, String urlfoto){
        Glide.with(imgView).load(urlfoto).into(imgView);
    }
}
