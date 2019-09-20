package unhas.informatics.moviecatalogue.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import unhas.informatics.moviecatalogue.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
	public abstract MovieDAO getMovieDAO();
}
