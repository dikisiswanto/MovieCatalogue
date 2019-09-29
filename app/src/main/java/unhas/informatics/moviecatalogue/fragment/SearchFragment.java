package unhas.informatics.moviecatalogue.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import unhas.informatics.moviecatalogue.MainViewModel;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.MovieAdapter;
import unhas.informatics.moviecatalogue.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

	private MovieAdapter adapter;
	private ShimmerFrameLayout mShimmerViewContainer;

	public SearchFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		RecyclerView searchResult = rootView.findViewById(R.id.list_mov_result);
		searchResult.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		adapter = new MovieAdapter(getActivity());
		searchResult.setAdapter(adapter);

		String query = getArguments().getString("query");

		MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mainViewModel.searchMovie(query);
		mainViewModel.getMovies().observe(this, getMovieResult);

		mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
		mShimmerViewContainer.startShimmer();

		return rootView;
	}


	Observer<ArrayList<Movie>> getMovieResult = new Observer<ArrayList<Movie>>() {
		@Override
		public void onChanged(@Nullable ArrayList<Movie> movies) {
			mShimmerViewContainer.stopShimmer();
			mShimmerViewContainer.setVisibility(View.GONE);
			if (movies != null && movies.size() > 0) {
				adapter.setMovies(movies);
			} else {
				Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_LONG).show();
			}
		}
	};
}
