package au.id.rlac.plainwall.ui.screen;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.wallpaper.Preferences;

public final class CustomColorFragment extends Fragment {

  public interface Listener {
    void onSelectionConfirmed(int selectedColor);
  }

  public static CustomColorFragment newInstance() {
    return new CustomColorFragment();
  }

  private static final String STATE_SELECTED_COLOR = "selected_color";

  Button btnConfirm;
  SeekBar seekRed, seekGreen, seekBlue;
  View background;
  int selectedColor;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      selectedColor = Preferences.getSelectedColor(getActivity());
    } else {
      selectedColor = savedInstanceState.getInt(STATE_SELECTED_COLOR);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setEnterTransition(new Fade());
      setReturnTransition(new Fade());
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_custom_color, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    background = view;
    btnConfirm = view.findViewById(R.id.btnConfirm);
    seekRed = view.findViewById(R.id.seekRed);
    seekGreen = view.findViewById(R.id.seekGreen);
    seekBlue = view.findViewById(R.id.seekBlue);
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateSelectedColour(seekRed.getProgress(), seekGreen.getProgress(),
            seekBlue.getProgress());
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {
      }
    };
    seekRed.setMax(255);
    seekGreen.setMax(255);
    seekBlue.setMax(255);
    seekRed.setProgress(Color.red(selectedColor));
    seekGreen.setProgress(Color.green(selectedColor));
    seekBlue.setProgress(Color.blue(selectedColor));
    background.setBackgroundColor(selectedColor);
    seekRed.setOnSeekBarChangeListener(seekBarChangeListener);
    seekGreen.setOnSeekBarChangeListener(seekBarChangeListener);
    seekBlue.setOnSeekBarChangeListener(seekBarChangeListener);
    btnConfirm.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Preferences.setSelectedColor(getActivity(), selectedColor);
        ((Listener) getActivity()).onSelectionConfirmed(selectedColor);
      }
    });
  }

  @Override public void onDestroyView() {
    background = null;
    btnConfirm = null;
    seekRed = seekGreen = seekBlue = null;
    super.onDestroyView();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STATE_SELECTED_COLOR, selectedColor);
  }

  void updateSelectedColour(int r, int g, int b) {
    selectedColor = Color.rgb(r, g, b);
    background.setBackgroundColor(selectedColor);
  }
}
