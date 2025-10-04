package org.csystem.android.util.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;

/**
 * Utility class for handling map-related operations.
 */
public final class MapUtil {
    private MapUtil()
    {
        throw new UnsupportedOperationException("Cannot create an instance of MapUtil");
    }

    /**
     * Opens a map application to show the specified location.
     *
     * @param context  the context to use for starting the activity
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     */
    public static void showOnMap(Context context, double latitude, double longitude)
    {
        var intent = new Intent(Intent.ACTION_VIEW);
        var uriStr = String.format(Locale.getDefault(), "geo:%f, %f", latitude, longitude);

        intent.setData(Uri.parse(uriStr));
        context.startActivity(intent);
    }

    /**
     * Opens a map application to show the specified location with a zoom level.
     *
     * @param context  the context to use for starting the activity
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     * @param zoom     the zoom level for the map
     */
    public static void showOnMap(Context context, double latitude, double longitude, int zoom)
    {
        var intent = new Intent(Intent.ACTION_VIEW);
        var uriStr = String.format(Locale.getDefault(), "geo:%f, %f?z=%d", latitude, longitude, zoom);

        intent.setData(Uri.parse(uriStr));
        context.startActivity(intent);
    }

    /**
     * Opens a chooser dialog to select a map application and show the specified location.
     *
     * @param context  the context to use for starting the activity
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     * @param title    the title for the chooser dialog
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, String title)
    {
        var intent = new Intent(Intent.ACTION_VIEW);
        var uriStr = String.format(Locale.getDefault(), "geo:%f, %f", latitude, longitude);

        intent.setData(Uri.parse(uriStr));
        context.startActivity(Intent.createChooser(intent, title));
    }

   /**
     * Opens a chooser dialog to select a map application and show the specified location.
     *
     * @param context   the context to use for starting the activity
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param titleResId the resource ID of the title for the chooser dialog
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, int titleResId)
    {
        chooseAndShowOnMap(context, latitude, longitude, context.getString(titleResId));
    }

    /**
     * Opens a chooser dialog to select a map application and show the specified location with a zoom level.
     *
     * @param context  the context to use for starting the activity
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     * @param zoom     the zoom level for the map
     * @param title    the title for the chooser dialog
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, int zoom, String title)
    {
        var intent = new Intent(Intent.ACTION_VIEW);
        var uriStr = String.format(Locale.getDefault(), "geo:%f, %f?z=%d", latitude, longitude, zoom);

        intent.setData(Uri.parse(uriStr));
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * Opens a chooser dialog to select a map application and show the specified location with a zoom level.
     *
     * @param context   the context to use for starting the activity
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param zoom      the zoom level for the map
     * @param titleResId the resource ID of the title for the chooser dialog
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, int zoom, int titleResId)
    {
        chooseAndShowOnMap(context, latitude, longitude, zoom, context.getString(titleResId));
    }

    /**
     * Opens a chooser dialog to select a map application and show the specified location with a zoom level.
     *
     * @param context   the context to use for starting the activity
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param zoom      the zoom level for the map
     * @param title     the title for the chooser dialog
     * @param isNewTask a boolean indicating whether the chooser activity should be started in a new task
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, int zoom, String title, boolean isNewTask)
    {
        var intent = new Intent(Intent.ACTION_VIEW);
        var uriStr = String.format(Locale.getDefault(), "geo:%f, %f?z=%d", latitude, longitude, zoom);

        intent.setData(Uri.parse(uriStr));
        var chooserIntent = Intent.createChooser(intent, title);

        if (isNewTask)
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(chooserIntent);
    }

    /**
     * Opens a chooser dialog to select a map application and show the specified location with a zoom level.
     *
     * @param context   the context to use for starting the activity
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param zoom      the zoom level for the map
     * @param titleResId the resource ID of the title for the chooser dialog
     * @param isNewTask a boolean indicating whether the chooser activity should be started in a new task
     */
    public static void chooseAndShowOnMap(Context context, double latitude, double longitude, int zoom, int titleResId, boolean isNewTask)
    {
        chooseAndShowOnMap(context, latitude, longitude, zoom, context.getString(titleResId), isNewTask);
    }
}