package unhas.informatics.moviecatalogue.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.BuildConfig;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.reminder.NotificationReceiver;

public class NotificationSettingActivity extends AppCompatActivity {
	@BindView(R.id.daily_switch)
	SwitchCompat dailyReminderSwitch;
	@BindView(R.id.release_switch)
	SwitchCompat releaseReminderSwitch;
	@BindView(R.id.toolbar_notif)
	Toolbar toolbar;

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor sharedPreferenceEdit;
	private NotificationReceiver notificationReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_setting);

		sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
		notificationReceiver = new NotificationReceiver(this);

		ButterKnife.bind(this);
		initToolbar();
		listenSwitchChanged();
		setPreferences();
	}

	private void initToolbar() {
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		setTitle(R.string.notification_setting_title);
	}

	private void listenSwitchChanged() {
		dailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sharedPreferenceEdit = sharedPreferences.edit();
				sharedPreferenceEdit.putBoolean("daily_reminder", isChecked);
				sharedPreferenceEdit.apply();
				if (isChecked) {
					notificationReceiver.setDailyReminder();
				} else {
					notificationReceiver.cancelDailyReminder(getApplicationContext());
				}
			}
		});
		releaseReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sharedPreferenceEdit = sharedPreferences.edit();
				sharedPreferenceEdit.putBoolean("release_reminder", isChecked);
				sharedPreferenceEdit.apply();
				if (isChecked) {
					notificationReceiver.setReleaseTodayReminder();
				} else {
					notificationReceiver.cancelReleaseToday(getApplicationContext());
				}
			}
		});
	}

	private void setPreferences() {
		boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
		boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

		dailyReminderSwitch.setChecked(dailyReminder);
		releaseReminderSwitch.setChecked(releaseReminder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			finish();
		return true;
	}
}
