//package platformpbp.uajy.quickresto.dabase;
//
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//import platformpbp.uajy.quickresto.model.Reservation;
//
//public interface ReservationDAO {
//    @Query("SELECT * FROM reservation")
//    List<Reservation> getAll();
//
//    @Query("SELECT * FROM reservation WHERE email LIKE :cari")
//    List<Reservation> getCari(String cari);
//
//    @Insert
//    void insert(Reservation reservation);
//
////    @Update
////    void update(Reservation user);
////
////    @Delete
////    void delete(Reservation user);
//
//}
