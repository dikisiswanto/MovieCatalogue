package unhas.informatics.moviecatalogue.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {

  private TextView title, originalLanguage, description, releaseDate, runtime;
  private ImageView poster, backdrop;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_detail);

	Intent intent = getIntent();
	Movie movie = intent.getParcelableExtra("movie");

	showDetails(movie);


  }

  private void showDetails(Movie movie) {
    title = findViewById(R.id.title);
    originalLanguage = findViewById(R.id.original_language);
    description = findViewById(R.id.overview);
    releaseDate = findViewById(R.id.release_date);
    runtime = findViewById(R.id.runtime);
    poster = findViewById(R.id.poster);
    backdrop = findViewById(R.id.backdrop);

    title.setText(movie.getTitle());
    originalLanguage.setText(movie.getOriginalLanguage());
    description.setText(movie.getDescription());
    releaseDate.setText(movie.getReleaseDate());
    runtime.setText(movie.getRuntime());
    poster.setImageResource(movie.getPoster());
    backdrop.setImageResource(movie.getPoster());

    toolbar = findViewById(R.id.toolbar_detail);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle(R.string.details);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	  case android.R.id.home:
		this.finish();
		return true;
	  default:
		return super.onOptionsItemSelected(item);
	}
  }
}
