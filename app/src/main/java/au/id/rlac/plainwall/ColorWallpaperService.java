package au.id.rlac.plainwall;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Draws the wallpaper as a flat color based on the user's color preference.
 */
public final class ColorWallpaperService extends WallpaperService {

  @Override public Engine onCreateEngine() {
    return new Engine();
  }

  class Engine extends WallpaperService.Engine
      implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences preferences;
    int selectedColor;

    @Override public void onCreate(SurfaceHolder surfaceHolder) {
      preferences = PreferenceManager.getDefaultSharedPreferences(ColorWallpaperService.this);
      selectedColor = preferences.getInt(
          SettingsActivity.KEY_SELECTED_COLOR, SettingsActivity.DEFAULT_COLOR);
      preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override public void onDestroy() {
      preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      if (SettingsActivity.KEY_SELECTED_COLOR.equals(key)) {
        selectedColor = preferences.getInt(
            SettingsActivity.KEY_SELECTED_COLOR, SettingsActivity.DEFAULT_COLOR);
        draw();
      }
    }

    @Override public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      draw();
    }

    void draw() {
      SurfaceHolder surfaceHolder = getSurfaceHolder();
      Canvas canvas = surfaceHolder.lockCanvas();

      if (canvas == null) return;

      try {
        canvas.drawColor(selectedColor);
      } finally {
        surfaceHolder.unlockCanvasAndPost(canvas);
      }
    }
  }
}
