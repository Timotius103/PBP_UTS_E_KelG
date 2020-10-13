package platformpbp.uajy.quickresto.dabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ReservationDAO.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO ReservationDAO();
}
