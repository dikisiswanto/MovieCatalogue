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

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.MainViewModel;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.MovieAdapter;
import unhas.informatics.moviecatalogue.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

	@BindView(R.id.list_mov)
	RecyclerView listMov;
	@BindView(R.id.shimmer_view_container)
	ShimmerFrameLayout mShimmerViewContainer;

	private MovieAdapter adapter;

	public MovieFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
		ButterKnife.bind(this, rootView);
		listMov.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		adapter = new MovieAdapter(getActivity());
		listMov.setAdapter(adapter);
		mShimmerViewContainer.startShimmer();

		MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mainViewModel.setMovies("movie");
		mainViewModel.getMovies().observe(this, getMovies);
		return rootView;
	}

	private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
		@Override
		public void onChanged(@Nullable ArrayList<Movie> movies) {
			if (movies != null) {
				mShimmerViewContainer.stopShimmer();
				mShimmerViewContainer.setVisibility(View.GONE);
				adapter.setMovies(movies);
			}
		}
	};
}
