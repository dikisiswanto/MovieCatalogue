package unhas.informatics.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
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

  public Movie(String title, String releaseDate, String description, String voteAverage, String originalLanguage, String poster) {
	this.title = title;
	this.releaseDate = releaseDate;
	this.description = description;
	this.voteAverage = voteAverage;
	this.originalLanguage = originalLanguage;
	this.poster = poster;
  }

  @Override
  public int describeContents() {
	return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
	dest.writeString(this.title);
	dest.writeString(this.releaseDate);
	dest.writeString(this.description);
	dest.writeString(this.voteAverage);
	dest.writeString(this.originalLanguage);
	dest.writeString(this.poster);
  }

  protected Movie(Parcel in) {
	this.title = in.readString();
	this.releaseDate = in.readString();
	this.description = in.readString();
	this.voteAverage = in.readString();
	this.originalLanguage = in.readString();
	this.poster = in.readString();
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
