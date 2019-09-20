package unhas.informatics.moviecatalogue.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import unhas.informatics.moviecatalogue.MainViewModel;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.MovieFavAdapter;
import unhas.informatics.moviecatalogue.db.MovieDAO;
import unhas.informatics.moviecatalogue.db.MovieDatabase;
import unhas.informatics.moviecatalogue.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment {

	MovieFavAdapter adapter;

	public TvShowFavFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
		RecyclerView list_fav_mov = rootView.findViewById(R.id.list_fav_mov);
		list_fav_mov.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new MovieFavAdapter(getActivity());
		list_fav_mov.setAdapter(adapter);

		ArrayList<Movie> data = (ArrayList<Movie>) loadFavMovies();

		MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mainViewModel.setFavMovie(data);
		mainViewModel.getMovies().observe(this, getMovies);

		return rootView;
	}

	private List<Movie> loadFavMovies() {
		MovieDatabase database = Room.databaseBuilder(getActivity(), MovieDatabase.class, "db_movie")
				.allowMainThreadQueries()
				.build();
		MovieDAO movieDAO = database.getMovieDAO();
		return movieDAO.getMoviesByMovieType(2);
	}

	Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
		@Override
		public void onChanged(@Nullable ArrayList<Movie> movies) {
			if (movies != null)
				adapter.setMovies(movies);
		}
	};
}
