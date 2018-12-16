package eu.quvix.doglessrunner.database;

import android.arch.persistence.room.Room;
import android.view.View;

public class DatabaseManager {
    private static AppDatabase database = null;

    public static AppDatabase getDatabase(View view) {
        if(database == null) {
            database = Room.databaseBuilder(view.getContext(),
                    AppDatabase.class, "dogless-runner").build();
        }
        return database;
    }
}
