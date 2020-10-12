package platformpbp.uajy.quickresto.dabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import platformpbp.uajy.quickresto.model.User;
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabaseClient extends RoomDatabase {
    public abstract UserDAO userDAO();

}



