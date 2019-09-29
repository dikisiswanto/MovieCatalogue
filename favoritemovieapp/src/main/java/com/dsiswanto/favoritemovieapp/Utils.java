package com.dsiswanto.favoritemovieapp;

import android.net.Uri;

public class Utils {
	public static final String TABLE_NAME = "movie";
	public static final String AUTHORITY = "unhas.informatics.moviecatalogue.provider";
	public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
			.authority(AUTHORITY)
			.appendPath(TABLE_NAME)
			.build();
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_POSTER = "poster";
	public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
}
