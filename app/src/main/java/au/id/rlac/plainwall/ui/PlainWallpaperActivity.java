package au.id.rlac.plainwall.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.ui.screen.ActivateFragment;
import au.id.rlac.plainwall.ui.screen.ColorPaletteFragment;
import au.id.rlac.plainwall.ui.screen.ConfirmColorFragment;
import au.id.rlac.plainwall.ui.screen.CustomColorFragment;
import au.id.rlac.plainwall.ui.screen.PaletteSelectionFragment;
import au.id.rlac.plainwall.ui.widget.picker.ColorPickerSwatch;
import au.id.rlac.plainwall.wallpaper.WallpaperUtils;
import au.id.rlac.plainwall.wallpaper.colors.Palette;

public abstract class PlainWallpaperActivity extends Activity
    implements PaletteSelectionFragment.Listener, ColorPaletteFragment.Listener,
    ConfirmColorFragment.Listener, CustomColorFragment.Listener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      if (!showActivate() || WallpaperUtils.isActive(this)) {
        getFragmentManager().beginTransaction()
            .add(android.R.id.content, PaletteSelectionFragment.newInstance())
            .commit();
      } else {
        getFragmentManager().beginTransaction()
            .add(android.R.id.content, ActivateFragment.newInstance())
            .commit();
      }
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (showActivate()) {
      Fragment current = getFragmentManager().findFragmentById(android.R.id.content);
      if (WallpaperUtils.isActive(this)) {
        if (current instanceof ActivateFragment) {
          getFragmentManager().beginTransaction()
              .replace(android.R.id.content, PaletteSelectionFragment.newInstance())
              .commit();
        }
      } else {
        if (!(current instanceof ActivateFragment)) {
          getFragmentManager().beginTransaction()
              .replace(android.R.id.content, ActivateFragment.newInstance())
              .commit();
        }
      }
    }
  }

  @Override public void onBackPressed() {
    FragmentManager fm = getFragmentManager();
    if (fm.getBackStackEntryCount() > 0) {
      fm.popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  @Override public void onCustomClicked() {
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, CustomColorFragment.newInstance())
        .addToBackStack(null)
        .commit();
  }

  @Override public void onPaletteClicked(Palette palette) {
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, ColorPaletteFragment.newInstance(palette))
        .addToBackStack(null)
        .commit();
  }

  @Override public void onColorSelected(ColorPickerSwatch sender, int color) {
    int[] location = new int[2];
    sender.getLocationOnScreen(location);
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content,
            ConfirmColorFragment.newInstance(color, location[0], location[1]))
        .addToBackStack(null)
        .commit();
  }

  protected abstract boolean showActivate();

  public static class LauncherActivity extends PlainWallpaperActivity {
    @Override protected boolean showActivate() {
      return true;
    }

    @Override public void onSelectionConfirmed(int selectedColor) {
      getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
      Toast.makeText(this, R.string.color_set, Toast.LENGTH_SHORT).show();
    }
  }

  public static class SettingsActivity extends PlainWallpaperActivity {
    @Override protected boolean showActivate() {
      return false;
    }

    @Override public void onSelectionConfirmed(int selectedColor) {
      Toast.makeText(this, R.string.color_set, Toast.LENGTH_SHORT).show();
      finish();
    }
  }
}
