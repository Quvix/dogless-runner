package eu.quvix.doglessrunner.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "map_id")
    public int mapId;
    @ColumnInfo(name = "score")
    public double score;

    public Score(int mapId, double score) {
        this.mapId = mapId;
        this.score = score;
    }
}
