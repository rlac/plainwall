package au.id.rlac.plainwall;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.View;

import au.id.rlac.plainwall.picker.ColorPickerSwatch;

/**
 * Displays a two level color palette and sets the selected color preference on selection. After
 * the color is selected the activity finishes.
 */
public final class SettingsActivity extends Activity
    implements ColorPickerSwatch.OnColorSelectedListener {

  public static final String KEY_SELECTED_COLOR = "color";
  public static final int DEFAULT_COLOR = Colors.RED_500;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      ColorPaletteFragment keyColorsPalette =
          ColorPaletteFragment.newInstance(Colors.keyPalette(), ColorPaletteFragment.MODE_KEYS);

      getFragmentManager().beginTransaction().add(android.R.id.content, keyColorsPalette).commit();
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

  @Override public void onColorSelected(int color) {
    ColorPaletteFragment currentPalette =
        (ColorPaletteFragment) getFragmentManager().findFragmentById(android.R.id.content);
    
    if (currentPalette.getMode() == ColorPaletteFragment.MODE_KEYS) {
      // display the full palette for the selected color
      
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      
      ColorPaletteFragment colorsFragment = ColorPaletteFragment.newInstance(
          Colors.paletteFor(color), ColorPaletteFragment.MODE_COLORS);
      
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // setup transitions
        // note - transitions not retained on fragment recreation, but it doesn't really matter
        View sharedElement = currentPalette.getSharedElement(color);
        if (sharedElement != null) {
          currentPalette.setReenterTransition(new Fade());
          currentPalette.setExitTransition(new Fade());

          TransitionSet move = new TransitionSet();
          move.addTransition(new ChangeBounds());
          move.addTransition(new ChangeTransform());
          move.addTransition(new ChangeClipBounds());

          colorsFragment.setSharedElementEnterTransition(move);
          colorsFragment.setSharedElementReturnTransition(move);
          colorsFragment.setEnterTransition(new Explode());
          colorsFragment.setReturnTransition(new Slide());
        
          ft.addSharedElement(sharedElement, sharedElement.getTransitionName());
        }
      }
      
      ft.replace(android.R.id.content, colorsFragment).addToBackStack(null).commit();

    } else {
      PreferenceManager.getDefaultSharedPreferences(this)
          .edit()
          .putInt(KEY_SELECTED_COLOR, color)
          .apply();

      finish();
    }
  }
}
