package unhas.informatics.moviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_detail);

	Intent intent = getIntent();
	Movie movie = intent.getParcelableExtra("movie");

	showDetails(movie);


  }

  private void showDetails(Movie movie) {
    TextView title = findViewById(R.id.title);
    TextView originalLanguage = findViewById(R.id.original_language);
    TextView description = findViewById(R.id.overview);
    TextView releaseDate = findViewById(R.id.release_date);
    TextView runtime = findViewById(R.id.runtime);
    ImageView poster = findViewById(R.id.poster);
    ImageView backdrop = findViewById(R.id.backdrop);

    title.setText(movie.getTitle());
    originalLanguage.setText(movie.getOriginalLanguage());
    description.setText(movie.getDescription());
    releaseDate.setText(movie.getReleaseDate());
    runtime.setText(movie.getRuntime());
    poster.setImageResource(movie.getPoster());
    backdrop.setImageResource(movie.getPoster());

    Toolbar toolbar = findViewById(R.id.toolbar_detail);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null){
	  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
    setTitle(R.string.details);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	if (item.getItemId() == android.R.id.home){
	  this.finish();
	}
	return true;
  }
}
