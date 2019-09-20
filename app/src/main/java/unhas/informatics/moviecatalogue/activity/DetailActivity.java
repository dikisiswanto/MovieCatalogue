package unhas.informatics.moviecatalogue.activity;

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

import unhas.informatics.moviecatalogue.BuildConfig;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.db.MovieDAO;
import unhas.informatics.moviecatalogue.db.MovieDatabase;
import unhas.informatics.moviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {

	Movie movie;
	MovieDAO movieDAO;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Intent intent = getIntent();
	  movie = intent.getParcelableExtra("movie");

		showDetails(movie);

	  movieDAO = Room.databaseBuilder(this, MovieDatabase.class, "db_movie")
			  .allowMainThreadQueries()
			  .build()
			  .getMovieDAO();
  }

  private void showDetails(Movie movie) {
    TextView title = findViewById(R.id.title);
    TextView originalLanguage = findViewById(R.id.original_language);
    TextView description = findViewById(R.id.overview);
    TextView releaseDate = findViewById(R.id.release_date);
    TextView voteAverage = findViewById(R.id.vote_average);
    ImageView poster = findViewById(R.id.poster);
    ImageView backdrop = findViewById(R.id.backdrop);

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

    Toolbar toolbar = findViewById(R.id.toolbar_detail);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
			setTitle(R.string.details);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.favorit_menu, menu);
		return true;
	}

	@Override
  public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				break;
			case R.id.fav_btn:
				try {
					if (movieDAO.getMovieByTitle(movie.getTitle()) > 0) {
						item.setEnabled(false);
					} else {
						movieDAO.insert(movie);
						setResult(RESULT_OK);
						Toast.makeText(this, R.string.success_insert_fav, Toast.LENGTH_SHORT).show();
					}
				} catch (SQLiteConstraintException e) {
					Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
		}
		return true;
  }
}
