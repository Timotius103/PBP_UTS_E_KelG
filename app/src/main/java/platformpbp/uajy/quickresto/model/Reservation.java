package platformpbp.uajy.quickresto.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Reservation implements Serializable {

    public String nameRest;
    public String address;
    public int jmlhOrg;
    public String dateResrv;
    public String timeResrv;

    public Reservation(){

    }
    public Reservation(String nameRest, String address, int jmlhOrg, String dateResrv, String timeResrv) {
        this.nameRest = nameRest;
        this.address = address;
        this.jmlhOrg = jmlhOrg;
        this.dateResrv = dateResrv;
        this.timeResrv = timeResrv;
    }

    public String getNameRest() {
        return nameRest;
    }

    public void setNameRest(String nameRest) {
        this.nameRest = nameRest;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getJmlhOrg() {
        return jmlhOrg;
    }

    public void setJmlhOrg(int jmlhOrg) {
        this.jmlhOrg = jmlhOrg;
    }

    public String getDateResrv() {
        return dateResrv;
    }

    public void setDateResrv(String dateResrv) {
        this.dateResrv = dateResrv;
    }

    public String getTimeResrv() {
        return timeResrv;
    }

    public void setTimeResrv(String timeResrv) {
        this.timeResrv = timeResrv;
    }

}
