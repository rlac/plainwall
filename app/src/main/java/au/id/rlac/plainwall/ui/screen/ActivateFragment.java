package au.id.rlac.plainwall.ui.screen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.wallpaper.WallpaperUtils;

public final class ActivateFragment extends Fragment {

  public static ActivateFragment newInstance() {
    return new ActivateFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_activate, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.findViewById(R.id.btnActivate).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        WallpaperUtils.acivate(getActivity());
      }
    });
  }
}
