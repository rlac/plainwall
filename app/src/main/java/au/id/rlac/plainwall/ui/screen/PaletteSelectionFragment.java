package au.id.rlac.plainwall.ui.screen;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.wallpaper.colors.Palette;

/**
 * Displays the color palettes available for selection.
 */
public final class PaletteSelectionFragment extends Fragment {

  public interface Listener {
    void onCustomClicked();

    void onPaletteClicked(Palette palette);
  }

  public static PaletteSelectionFragment newInstance() {
    return new PaletteSelectionFragment();
  }

  Listener listener;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    listener = (Listener) getActivity();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setReenterTransition(new Fade());
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_palette_selection, container, false);
  }

  @Override public void onViewCreated(final View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.findViewById(R.id.btnCustomColor).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        listener.onCustomClicked();
      }
    });
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override public void onClick(View view) {
        listener.onPaletteClicked((Palette) view.getTag());
      }
    };
    TableLayout tablePalettes = view.findViewById(R.id.tablePalettes);
    TableRow currentRow = new TableRow(tablePalettes.getContext());
    tablePalettes.addView(currentRow);
    int rowElements = 0;
    final int numColumns = 2;
    for (Palette palette : Palette.values()) {
      if (rowElements == numColumns) {
        currentRow = new TableRow(tablePalettes.getContext());
        tablePalettes.addView(currentRow);
        rowElements = 0;
      }
      Button btnPalette = new Button(tablePalettes.getContext());
      btnPalette.setText(palette.getName());
      btnPalette.setTag(palette);
      btnPalette.setOnClickListener(onClickListener);
      currentRow.addView(btnPalette);
      rowElements++;
    }
  }
}
