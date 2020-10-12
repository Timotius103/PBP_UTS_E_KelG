package platformpbp.uajy.quickresto.dabase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient databaseClient;

    private AppDatabaseClient database;

    private DatabaseClient(Context context){
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabaseClient.class, "reservation").build();
    }

    public static synchronized DatabaseClient getInstance2(Context context){
        if (databaseClient==null){
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public AppDatabaseClient getDatabase(){
        return database;
    }
}
