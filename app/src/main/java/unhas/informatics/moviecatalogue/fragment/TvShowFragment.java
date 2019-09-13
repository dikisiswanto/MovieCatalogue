package unhas.informatics.moviecatalogue.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.MovieAdapter;
import unhas.informatics.moviecatalogue.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private String[] title;
    private String[] releaseDate;
    private String[] description;
    private String[] runtime;
    private String[] originalLanguage;
    private TypedArray poster;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private RecyclerView list_mov;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_show, container, false);
        list_mov = rootView.findViewById(R.id.list_mov);
        list_mov.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new MovieAdapter(getActivity());
        list_mov.setAdapter(adapter);

        prepare();
        addItem();

        return rootView;
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(title[i]);
            movie.setDescription(description[i]);
            movie.setReleaseDate(releaseDate[i]);
            movie.setRuntime(runtime[i]);
            movie.setOriginalLanguage(originalLanguage[i]);
            movie.setPoster(poster.getResourceId(i, -1));
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

    private void prepare() {
        title = getResources().getStringArray(R.array.title);
        description = getResources().getStringArray(R.array.description);
        releaseDate = getResources().getStringArray(R.array.release_date);
        runtime = getResources().getStringArray(R.array.runtime);
        originalLanguage  = getResources().getStringArray(R.array.original_language);
        poster = getResources().obtainTypedArray(R.array.poster);
    }

}
