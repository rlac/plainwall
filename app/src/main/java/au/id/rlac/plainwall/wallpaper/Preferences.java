package au.id.rlac.plainwall.wallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import au.id.rlac.plainwall.wallpaper.colors.Palette;

public class Preferences {
  public static final String KEY_COLOR = "color";
  public static final int DEFAULT_COLOR = Palette.Material.getColors()[0];

  public static SharedPreferences getSharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static int getSelectedColor(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_COLOR, DEFAULT_COLOR);
  }

  public static void setSelectedColor(Context context, int color) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(KEY_COLOR, color).apply();
  }

  private Preferences() {
  }
}
