package unhas.informatics.moviecatalogue.widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import unhas.informatics.moviecatalogue.BuildConfig;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.db.MovieDAO;
import unhas.informatics.moviecatalogue.db.MovieDatabase;
import unhas.informatics.moviecatalogue.model.Movie;

public class FavMovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
	private final ArrayList<Movie> mWidgetItems = new ArrayList<>();
	private MovieDatabase database;
	private final Context mContext;

	public FavMovieRemoteViewsFactory(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void onCreate() {
		final long identityToken = Binder.clearCallingIdentity();
		database = Room.databaseBuilder(mContext.getApplicationContext(), MovieDatabase.class, "db_movie")
				.allowMainThreadQueries()
				.build();
		Binder.restoreCallingIdentity(identityToken);
	}

	@Override
	public void onDataSetChanged() {
		try {
			MovieDAO movieDAO = database.getMovieDAO();
			mWidgetItems.clear();
			mWidgetItems.addAll(movieDAO.getAllFavMovies());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		database.close();
	}

	@Override
	public int getCount() {
		return mWidgetItems.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
		try {
			Bitmap bitmap = Glide.with(mContext)
					.asBitmap()
					.load(BuildConfig.IMG_URL + mWidgetItems.get(position).getPoster())
					.apply(new RequestOptions().fitCenter())
					.submit(800, 550)
					.get();

			rv.setImageViewBitmap(R.id.imageView, bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bundle extras = new Bundle();
		extras.putInt(FavMovieWidget.EXTRA_ITEM, position);
		Intent fillInIntent = new Intent();
		fillInIntent.putExtras(extras);

		rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
		return rv;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
}
