package au.id.rlac.plainwall;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.id.rlac.plainwall.picker.ColorPickerPalette;
import au.id.rlac.plainwall.picker.ColorPickerSwatch;

/**
 * Displays a color picker palette. The activity is notified of any color selections and must
 * implement {@link ColorPickerSwatch.OnColorSelectedListener}.
 * 
 * Each color swatch in the picker is assigned a transition name for its color value. Duplicate
 * colors should not be used.
 */
public final class ColorPaletteFragment extends Fragment {

  private static final String ARGUMENT_COLORS = "colors";
  private static final String ARGUMENT_MODE = "mode";

  public static final int MODE_KEYS = 0;
  public static final int MODE_COLORS = 1;

  /**
   * Create a new instance.
   *
   * @param colors  The colors to display. A color swatch is created for each color.
   * @param mode    Tags the purpose of the fragment, accessible with {@link #getMode()}.
   *
   * @return a new instance.
   */
  public static ColorPaletteFragment newInstance(int[] colors, int mode) {
    ColorPaletteFragment instance = new ColorPaletteFragment();
    Bundle args = new Bundle();
    instance.setArguments(args);

    args.putIntArray(ARGUMENT_COLORS, colors);
    args.putInt(ARGUMENT_MODE, mode == MODE_KEYS ? MODE_KEYS : MODE_COLORS);
    return instance;
  }

  /**
   * @return the mode parameter originally set in {@link #newInstance(int[], int)}.
   */
  public int getMode() {
    return getArguments().getInt(ARGUMENT_MODE);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_color_palette, container, false);

    ColorPickerPalette colorPicker = (ColorPickerPalette) v.findViewById(R.id.color_picker);

    colorPicker.init(
        ColorPickerPalette.SIZE_LARGE,
        v.getResources().getInteger(R.integer.color_picker_columns),
        ((ColorPickerSwatch.OnColorSelectedListener) getActivity()));

    int[] colors = getArguments().getIntArray(ARGUMENT_COLORS);

    colorPicker.drawPalette(colors, 0);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      for (int color : colors) {
        colorPicker.getSwatchViewForColor(color).setTransitionName(String.valueOf(color));
      }
    }

    return v;
  }

  /**
   * Find the Color Swatch for a particular color, to be used as a transition shared element.
   *
   * @param color  The color to find.
   * @return the color swatch for the color, or null if not found.
   */
  public View getSharedElement(int color) {
    return ((ColorPickerPalette) getView().findViewById(R.id.color_picker)).getSwatchViewForColor(color);
  }
}
