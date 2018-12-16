package eu.quvix.doglessrunner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "map_id")
    public String mapId;
    @ColumnInfo(name = "score")
    public double score;
}
