package eu.quvix.doglessrunner.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM score where map_id = :mapId")
    List<Score> getAll(int mapId);

    @Query("SELECT * FROM score WHERE map_id = :mapId GROUP BY score ORDER BY score DESC LIMIT :limit OFFSET :offset")
    List<Score> getAll(int mapId, int offset, int limit);

    @Insert
    void insert(Score score);
}
