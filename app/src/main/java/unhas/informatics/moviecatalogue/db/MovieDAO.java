package unhas.informatics.moviecatalogue.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

import unhas.informatics.moviecatalogue.model.Movie;

@Dao
public interface MovieDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Movie... movies);

	@Query("SELECT * FROM movie")
	Cursor selectAll();

	@Query("SELECT * FROM movie where uid = :uid")
	Cursor selectById(long uid);

	@Query("SELECT * FROM movie")
	List<Movie> getAllFavMovies();

	@Query("DELETE FROM movie WHERE uid = :uid")
	void deleteByUid(int uid);

	@Query("SELECT COUNT(uid) FROM movie WHERE title = :title")
	int getMovieByTitle(String title);

	@Query("SELECT * FROM movie WHERE movieType = :movieType")
	List<Movie> getMoviesByMovieType(String movieType);

}
