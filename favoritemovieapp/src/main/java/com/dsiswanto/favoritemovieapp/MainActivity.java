package com.dsiswanto.favoritemovieapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final int CODE_MOVIE = 1;
	private MovieListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final RecyclerView list = findViewById(R.id.list_fav_mov);
		list.setLayoutManager(new LinearLayoutManager(list.getContext()));
		adapter = new MovieListAdapter(getApplicationContext());
		list.setAdapter(adapter);
		getSupportLoaderManager().initLoader(CODE_MOVIE, null, this);
	}

	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
		switch (i) {
			case CODE_MOVIE:
				return new CursorLoader(getApplicationContext(), Utils.CONTENT_URI, null, null, null, null);
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
		if (loader.getId() == CODE_MOVIE) {
			try {
				adapter.setData(cursor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader) {
		if (loader.getId() == CODE_MOVIE) {
			adapter.setData(null);
		}
	}
}
