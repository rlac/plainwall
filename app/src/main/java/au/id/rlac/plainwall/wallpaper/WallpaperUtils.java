package au.id.rlac.plainwall.wallpaper;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WallpaperUtils {
  public static int[] copyArray(int[] from) {
    int[] to = new int[from.length];
    for (int idx = 0; idx < from.length; idx++) {
      to[idx] = from[idx];
    }
    return to;
  }

  public static void acivate(Context context) {
    try {
      context.startActivity(new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).putExtra(
          WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
          new ComponentName(context, ColorWallpaperService.class))
          .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    } catch (ActivityNotFoundException e1) {
      try {
        context.startActivity(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER).addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK));
      } catch (ActivityNotFoundException e2) {
        Toast.makeText(context, "Error activating wallpaper", Toast.LENGTH_LONG).show();
      }
    }
  }

  public static boolean isActive(Context context) {
    WallpaperInfo info = WallpaperManager.getInstance(context).getWallpaperInfo();
    return info != null && info.getComponent().getPackageName().equals(context.getPackageName());
  }

  private WallpaperUtils() {
  }
}
