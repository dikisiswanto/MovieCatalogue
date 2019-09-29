package com.dsiswanto.favoritemovieapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
	private Cursor mCursor;
	private Context context;

	public MovieListAdapter(Context context) {
		this.context = context;
	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list, viewGroup, false);
		return new MovieViewHolder(mView);
	}

	@Override
	public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
		movieViewHolder.bind(mCursor.moveToPosition(i));
	}

	@Override
	public int getItemCount() {
		return mCursor == null ? 0 : mCursor.getCount();
	}

	public void setData(Cursor cursor) {
		mCursor = cursor;
		notifyDataSetChanged();
	}

	public class MovieViewHolder extends RecyclerView.ViewHolder {
		private TextView title, description;
		private CircleImageView imageView;

		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title_fav);
			description = itemView.findViewById(R.id.description_fav);
			imageView = itemView.findViewById(R.id.poster_fav);
		}

		public void bind(boolean moveToPosition) {
			if (moveToPosition) {
				title.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
				description.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_DESCRIPTION)));
				Glide.with(context).load(Utils.POSTER_BASE_URL + mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER))).into(imageView);
			}
		}
	}
}
