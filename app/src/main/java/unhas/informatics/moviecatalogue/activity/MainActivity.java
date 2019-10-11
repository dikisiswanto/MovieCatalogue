package unhas.informatics.moviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.fragment.FavoriteFragment;
import unhas.informatics.moviecatalogue.fragment.MovieFragment;
import unhas.informatics.moviecatalogue.fragment.SearchFragment;
import unhas.informatics.moviecatalogue.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.navigation)
	BottomNavigationView navigation;
	private SearchView mSearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setElevation(0);
			getSupportActionBar().setTitle(R.string.title_tab1);
		}
		if (savedInstanceState == null) {
			loadFragment(new MovieFragment());
		}
	}

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
			Fragment fragment;
			toolbar.collapseActionView();
			switch (menuItem.getItemId()) {
				case R.id.nav_bottom_1:
					if (getSupportActionBar() != null)
						getSupportActionBar().setTitle(R.string.title_tab1);
					fragment = new MovieFragment();
					loadFragment(fragment);
					return true;
				case R.id.nav_bottom_2:
					if (getSupportActionBar() != null)
						getSupportActionBar().setTitle(R.string.title_tab2);
					fragment = new TvShowFragment();
					loadFragment(fragment);
					return true;
				case R.id.nav_bottom_3:
					if (getSupportActionBar() != null)
						getSupportActionBar().setTitle(R.string.title_tab3);
					fragment = new FavoriteFragment();
					loadFragment(fragment);
					return true;
			}
			return false;
		}
	};

	private void loadFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		setupSearchView();
		return super.onCreateOptionsMenu(menu);
	}

	private void setupSearchView() {
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				mSearchView.clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				if (s.length() > 0) {
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					Bundle bundle = new Bundle();
					bundle.putString("query", s);
					SearchFragment fragment = new SearchFragment();
					fragment.setArguments(bundle);
					transaction.replace(R.id.frame_container, fragment);
					transaction.commit();
					navigation.getMenu().setGroupCheckable(0, false, true);
				} else {
					navigation.getMenu().setGroupCheckable(0, true, true);
					navigation.getMenu().getItem(0).setChecked(true);
					loadFragment(new MovieFragment());
					if (getSupportActionBar() != null) {
						toolbar.setTitle(R.string.title_tab1);
					}
				}
				return false;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.localization_btn:
				Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
				startActivity(mIntent);
				break;
			case R.id.notification_btn:
				Intent intent = new Intent(MainActivity.this, NotificationSettingActivity.class);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
