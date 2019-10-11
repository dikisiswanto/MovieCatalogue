package unhas.informatics.moviecatalogue.activity;

import android.appwidget.AppWidgetManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.BuildConfig;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.db.MovieDAO;
import unhas.informatics.moviecatalogue.db.MovieDatabase;
import unhas.informatics.moviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {

	@BindView(R.id.toolbar_detail)
	Toolbar toolbar;
	@BindView(R.id.title)
	TextView title;
	@BindView(R.id.overview)
	TextView description;
	@BindView(R.id.original_language)
	TextView originalLanguage;
	@BindView(R.id.release_date)
	TextView releaseDate;
	@BindView(R.id.vote_average)
	TextView voteAverage;
	@BindView(R.id.poster)
	ImageView poster;
	@BindView(R.id.backdrop)
	ImageView backdrop;

	private Movie movie;
	private MovieDAO movieDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Intent intent = getIntent();
		movie = intent.getParcelableExtra("movie");

		ButterKnife.bind(this);
		initToolbar();
		showDetails(movie);

		movieDAO = Room.databaseBuilder(this, MovieDatabase.class, "db_movie")
				.allowMainThreadQueries()
				.build()
				.getMovieDAO();
	}

	private void initToolbar() {
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		setTitle(R.string.details);
	}

	private void showDetails(Movie movie) {
		title.setText(movie.getTitle());
		originalLanguage.setText(movie.getOriginalLanguage());
		description.setText(movie.getDescription());
		releaseDate.setText(movie.getReleaseDate());
		voteAverage.setText(movie.getVoteAverage());
		Glide.with(this)
				.load(BuildConfig.IMG_URL + movie.getPoster())
				.into(poster);
		Glide.with(this)
				.load(BuildConfig.IMG_URL + movie.getPoster())
				.into(backdrop);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.favorit_menu, menu);
		if (movieDAO.getMovieByTitle(movie.getTitle()) > 0) {
			setFavoriteSelected(menu.getItem(0));
		}
		return true;
	}

	private void setFavoriteSelected(MenuItem item) {
		item.setIcon(R.drawable.baseline_favorite_24);
		item.setEnabled(false);
	}

	private void markAsFavorite() {
		movieDAO.insert(movie);
		setResult(RESULT_OK);
		Intent brIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		sendBroadcast(brIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				break;
			case R.id.fav_btn:
				try {
					markAsFavorite();
					setFavoriteSelected(item);
					Toast.makeText(this, R.string.success_insert_fav, Toast.LENGTH_SHORT).show();
				} catch (SQLiteConstraintException e) {
					Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
		}
		return true;
	}
}
