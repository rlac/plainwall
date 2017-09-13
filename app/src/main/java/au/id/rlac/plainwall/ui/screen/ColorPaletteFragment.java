package au.id.rlac.plainwall.ui.screen;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.ui.widget.picker.ColorPickerPalette;
import au.id.rlac.plainwall.ui.widget.picker.ColorPickerSwatch;
import au.id.rlac.plainwall.wallpaper.colors.Palette;

/**
 * Displays a color picker palette. The activity is notified of any color selections and must
 * implement {@link ColorPickerSwatch.OnColorSelectedListener}.
 *
 * Each color swatch in the picker is assigned a transition name for its color value. Duplicate
 * colors should not be used.
 */
public final class ColorPaletteFragment extends Fragment {

  private static final String ARGUMENT_PALETTE = "palette";

  public interface Listener {
    void onColorSelected(ColorPickerSwatch sender, int color);
  }

  /**
   * Create a new instance.
   *
   * @param palette The palette to display.
   * @return a new instance.
   */
  public static ColorPaletteFragment newInstance(Palette palette) {
    ColorPaletteFragment instance = new ColorPaletteFragment();
    Bundle args = new Bundle();
    instance.setArguments(args);
    args.putString(ARGUMENT_PALETTE, palette.name());
    return instance;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setEnterTransition(new Fade());
      setReturnTransition(new Slide());
      setExitTransition(new Fade());
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_color_palette, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ColorPickerPalette colorPicker = view.findViewById(R.id.color_picker);
    colorPicker.init(ColorPickerPalette.SIZE_LARGE,
        view.getResources().getInteger(R.integer.color_picker_columns),
        new ColorPickerSwatch.OnColorSelectedListener() {
          @Override public void onColorSelected(ColorPickerSwatch sender, int color) {
            ((Listener) getActivity()).onColorSelected(sender, color);
          }
        });
    Palette palette = Palette.valueOf(getArguments().getString(ARGUMENT_PALETTE));
    colorPicker.drawPalette(palette.getColors());
  }
}
