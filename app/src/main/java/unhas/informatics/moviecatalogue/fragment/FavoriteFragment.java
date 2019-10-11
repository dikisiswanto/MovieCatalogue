package unhas.informatics.moviecatalogue.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import unhas.informatics.moviecatalogue.R;
import unhas.informatics.moviecatalogue.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

	@BindView(R.id.viewpager)
	ViewPager viewPager;
	@BindView(R.id.tabs)
	TabLayout tabLayout;

	public FavoriteFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
		ButterKnife.bind(this, rootView);
		setupViewPager(viewPager);
		tabLayout.setupWithViewPager(viewPager);
		return rootView;
	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
		adapter.addFragment(new MovieFavFragment(), getResources().getString(R.string.title_tab1));
		adapter.addFragment(new TvShowFavFragment(), getResources().getString(R.string.title_tab2));
		viewPager.setAdapter(adapter);
	}

}
