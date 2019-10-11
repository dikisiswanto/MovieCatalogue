package unhas.informatics.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.BuildConfig;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.activity.DetailActivity;
import unhas.informatics.moviecatalogue.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

	private Context context;
	private ArrayList<Movie> movies = new ArrayList<>();

	public MovieAdapter(Context context) {
		this.context = context;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
		return new MovieViewHolder(mView);
	}

	@Override
	public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
		movieViewHolder.bind(movies.get(i));
	}

	@Override
	public int getItemCount() {
		return movies.size();
	}


	public class MovieViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.title)
		TextView movieTitle;
		@BindView(R.id.poster)
		ImageView poster;

		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent details = new Intent(context, DetailActivity.class);
					details.putExtra("movie", movies.get(getAdapterPosition()));
					context.startActivity(details);
				}
			});
		}

		public void bind(Movie movie) {
			Glide.with(context)
					.load(BuildConfig.IMG_URL + movie.getPoster())
					.apply(new RequestOptions())
					.into(poster);
			movieTitle.setText(movie.getTitle());
		}
	}
}
