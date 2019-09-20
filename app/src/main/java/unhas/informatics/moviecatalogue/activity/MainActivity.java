package unhas.informatics.moviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.fragment.FavoriteFragment;
import unhas.informatics.moviecatalogue.fragment.MovieFragment;
import unhas.informatics.moviecatalogue.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	Toolbar toolbar = findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle(R.string.title_tab1);
		if (savedInstanceState == null) {
			loadFragment(new MovieFragment());
		}
	}

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
			Fragment fragment;
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
	return true;
 }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
   if (item.getItemId() == R.id.localization_btn){
	 Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
	 startActivity(mIntent);
   }
   return super.onOptionsItemSelected(item);
 }

}
