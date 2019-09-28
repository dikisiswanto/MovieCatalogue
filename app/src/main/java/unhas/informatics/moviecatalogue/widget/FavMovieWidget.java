package unhas.informatics.moviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import unhas.informatics.moviecatalogue.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavMovieWidget extends AppWidgetProvider {

	private static final String TOAST_ACTION = "unhas.informatics.moviecatalogue.TOAST_ACTION";
	public static final String EXTRA_ITEM = "unhas.informatics.moviecatalogue.EXTRA_ITEM";

	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
	                            int appWidgetId) {

		Intent intent = new Intent(context, FavMovieService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fav_movie_widget);
		views.setRemoteAdapter(R.id.stack_view, intent);
		views.setEmptyView(R.id.stack_view, R.id.empty_view);


		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName thisWidget = new ComponentName(context, FavMovieWidget.class);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
		}
	}

}

