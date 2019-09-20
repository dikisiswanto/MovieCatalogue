package unhas.informatics.moviecatalogue.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie", indices = @Index(value = {"title"}, unique = true))
public class Movie implements Parcelable {
	@PrimaryKey(autoGenerate = true)
	private int uid;
	@SerializedName(value = "title", alternate = {"name"})
	private String title;
	@SerializedName(value = "release_date", alternate = {"first_air_date"})
	private String releaseDate;
	@SerializedName("overview")
	private String description;
	@SerializedName("vote_average")
	private String voteAverage;
	@SerializedName("original_language")
	private String originalLanguage;
	@SerializedName("poster_path")
	private String poster;
	private int movieType;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(String voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getMovieType() {
		return movieType;
	}

	public void setMovieType(int movieType) {
		this.movieType = movieType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.uid);
		dest.writeString(this.title);
		dest.writeString(this.releaseDate);
		dest.writeString(this.description);
		dest.writeString(this.voteAverage);
		dest.writeString(this.originalLanguage);
		dest.writeString(this.poster);
		dest.writeInt(this.movieType);
	}

	public Movie() {
	}

	protected Movie(Parcel in) {
		this.uid = in.readInt();
		this.title = in.readString();
		this.releaseDate = in.readString();
		this.description = in.readString();
		this.voteAverage = in.readString();
		this.originalLanguage = in.readString();
		this.poster = in.readString();
		this.movieType = in.readInt();
	}

	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}
