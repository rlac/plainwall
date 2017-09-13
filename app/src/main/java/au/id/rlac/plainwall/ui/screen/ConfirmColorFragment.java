package au.id.rlac.plainwall.ui.screen;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.wallpaper.Preferences;

public final class ConfirmColorFragment extends Fragment {
  private static final String ARGUMENT_COLOR = "color";
  private static final String ARGUMENT_TRANSITION_X = "transition_x";
  private static final String ARGUMENT_TRANSITION_Y = "transition_y";

  public interface Listener {
    void onSelectionConfirmed(int selectedColor);
  }

  boolean viewCreated = false;

  public static ConfirmColorFragment newInstance(int color, int transitionCenterX,
      int transitionCenterY) {
    ConfirmColorFragment fragment = new ConfirmColorFragment();
    Bundle args = new Bundle();
    args.putInt(ARGUMENT_COLOR, color);
    args.putInt(ARGUMENT_TRANSITION_X, transitionCenterX);
    args.putInt(ARGUMENT_TRANSITION_Y, transitionCenterY);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setReturnTransition(new Fade());
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_confirm_color, container, false);
  }

  @Override public void onViewCreated(final View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final View colorView = view.findViewById(R.id.color);
    colorView.setBackgroundColor(getArguments().getInt(ARGUMENT_COLOR));
    view.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Preferences.setSelectedColor(view.getContext(), getArguments().getInt(ARGUMENT_COLOR));
        ((Listener) getActivity()).onSelectionConfirmed(getArguments().getInt(ARGUMENT_COLOR));
      }
    });
    if (savedInstanceState == null
        && !viewCreated
        && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      colorView.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override public void onGlobalLayout() {
              int size = getResources().getDimensionPixelSize(R.dimen.color_swatch_large);
              int[] locationOnScreen = new int[2];
              colorView.getLocationOnScreen(locationOnScreen);
              int centerX = getArguments().getInt(ARGUMENT_TRANSITION_X) + (size / 2) - locationOnScreen[0];
              int centerY = getArguments().getInt(ARGUMENT_TRANSITION_Y) + (size / 2) - locationOnScreen[1];
              int height = colorView.getHeight();
              int width = colorView.getWidth();
              float startRadius = size / 2f;
              float endRadius = (float) Math.hypot(
                  Math.max(height - centerY, centerY),
                  Math.max(width - centerX, centerX)
              );
              Animator animator = ViewAnimationUtils.createCircularReveal(
                  colorView, centerX, centerY, startRadius, endRadius);
              animator.setInterpolator(new AccelerateInterpolator());
              animator.setDuration(
                  view.getResources().getInteger(android.R.integer.config_mediumAnimTime));
              animator.start();
              colorView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
          });
    }
    viewCreated = true;
  }
}
