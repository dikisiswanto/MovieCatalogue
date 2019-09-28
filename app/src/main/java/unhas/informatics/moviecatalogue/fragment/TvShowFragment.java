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

import unhas.informatics.moviecatalogue.MainViewModel;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.MovieAdapter;
import unhas.informatics.moviecatalogue.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

	private MovieAdapter adapter;
	private ShimmerFrameLayout mShimmerViewContainer;

	public TvShowFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tv_show, container, false);
		RecyclerView listTvShow = rootView.findViewById(R.id.list_mov);
		listTvShow.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		adapter = new MovieAdapter(getActivity());
		listTvShow.setAdapter(adapter);

		MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		mainViewModel.setMovies("tv");
		mainViewModel.getMovies().observe(this, getMovies);
		mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
		mShimmerViewContainer.startShimmer();

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
