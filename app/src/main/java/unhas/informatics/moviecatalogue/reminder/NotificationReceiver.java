package unhas.informatics.moviecatalogue.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.activity.MainActivity;
import unhas.informatics.moviecatalogue.api.ApiClient;
import unhas.informatics.moviecatalogue.api.ApiEndpoints;
import unhas.informatics.moviecatalogue.model.Movie;
import unhas.informatics.moviecatalogue.model.MovieResponse;

public class NotificationReceiver extends BroadcastReceiver {
	private static final String EXTRA_TYPE = "type";
	private static final String TYPE_DAILY = "daily_reminder";
	private static final String TYPE_RELEASE = "release_reminder";
	private static final int ID_DAILY_REMINDER = 1000;
	private static final int ID_RELEASE_TODAY = 1001;

	private Context context;

	public NotificationReceiver() {

	}

	public NotificationReceiver(Context context) {
		this.context = context;
	}


	@Override
	public void onReceive(Context context, Intent intent) {
		String type = intent.getStringExtra(EXTRA_TYPE);
		if (type.equals(TYPE_DAILY)) {
			showDailyReminder(context);
		} else if (type.equals(TYPE_RELEASE)) {
			getReleaseToday(context);
		}
	}

	private Calendar getReminderTime(String type) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, type.equals(TYPE_DAILY) ? 7 : 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		if (calendar.before(Calendar.getInstance())) {
			calendar.add(Calendar.DATE, 1);
		}

		return calendar;
	}

	private Intent getReminderIntent(String type) {
		Intent intent = new Intent(context, NotificationReceiver.class);
		intent.putExtra(EXTRA_TYPE, type);
		return intent;
	}

	public void setReleaseTodayReminder() {
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

	}

	public void setDailyReminder() {
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

	}

	private void getReleaseToday(final Context context) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		final String now = dateFormat.format(date);

		ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
		Call<MovieResponse> call = apiService.getReleasedMovies(now, now);

		call.enqueue(new Callback<MovieResponse>() {
			@Override
			public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
				if (response.isSuccessful()) {
					ArrayList<Movie> movies = response.body().getResults();
					int id = 2;
					for (Movie movie : movies) {
						String title = movie.getTitle();
						String desc = title + " " + context.getString(R.string.release_reminder_message);
						showReleaseToday(context, title, desc, id);
						id++;
					}
				}
			}

			@Override
			public void onFailure(Call<MovieResponse> call, Throwable t) {

			}
		});
	}

	private void showReleaseToday(Context context, String title, String desc, int id) {
		String CHANNEL_ID = "Channel_2";
		String CHANNEL_NAME = "Today release channel";

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.baseline_movie_24)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_movie_24))
				.setContentTitle(title)
				.setContentText(desc)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setSound(uriRingtone)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		Notification notification = mBuilder.build();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
			mBuilder.setChannelId(CHANNEL_ID);

			if (mNotificationManager != null) {
				mNotificationManager.createNotificationChannel(channel);
			}
		}
		if (mNotificationManager != null) {
			mNotificationManager.notify(id, notification);
		}
	}

	private void showDailyReminder(Context context) {
		int NOTIFICATION_ID = 1;
		String CHANNEL_ID = "Channel_1";
		String CHANNEL_NAME = "Daily Reminder channel";

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.baseline_movie_24)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_movie_24))
				.setContentTitle(context.getResources().getString(R.string.app_name))
				.setContentText(context.getResources().getString(R.string.daily_message_reminder))
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setSound(uriRingtone)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		Notification notification = mBuilder.build();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
			mBuilder.setChannelId(CHANNEL_ID);

			if (mNotificationManager != null) {
				mNotificationManager.createNotificationChannel(channel);
			}
		}
		if (mNotificationManager != null) {
			mNotificationManager.notify(NOTIFICATION_ID, notification);
		}
	}

	private void cancelReminder(Context context, String type) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, NotificationReceiver.class);
		int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
		pendingIntent.cancel();
		if (alarmManager != null) {
			alarmManager.cancel(pendingIntent);
		}
	}

	public void cancelDailyReminder(Context context) {
		cancelReminder(context, TYPE_DAILY);
	}

	public void cancelReleaseToday(Context context) {
		cancelReminder(context, TYPE_RELEASE);
	}
}
