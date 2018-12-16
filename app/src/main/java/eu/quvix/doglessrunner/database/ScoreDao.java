package eu.quvix.doglessrunner;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Query("SELECT * FROM score where map_id = :mapId")
    List<Score> getAll(int mapId);

    @Query("SELECT * FROM score WHERE map_id = :mapId LIMIT :limit, :offset")
    List<Score> getAll(int mapId, int offset, int limit);

    @Insert
    void insert(Score score);
}
