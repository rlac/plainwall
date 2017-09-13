/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.id.rlac.plainwall.ui.widget.picker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import au.id.rlac.plainwall.R;

/**
 * Creates a circular swatch of a specified color.
 */
public class ColorPickerSwatch extends View implements View.OnClickListener {
  private int color;
  private OnColorSelectedListener onColorSelectedListener;

  /**
   * Interface for a callback when a color square is selected.
   */
  public interface OnColorSelectedListener {
    /**
     * Called when a specific color square has been selected.
     */
    void onColorSelected(ColorPickerSwatch sender, int color);
  }

  public ColorPickerSwatch(Context context) {
    this(context, null);
  }

  public ColorPickerSwatch(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ColorPickerSwatch(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOnClickListener(this);
  }

  public void setOnColorSelectedListener(OnColorSelectedListener listener) {
    onColorSelectedListener = listener;
  }

  public void setColor(int color) {
    this.color = color;
    Drawable[] colorDrawable =
        new Drawable[] { getContext().getResources().getDrawable(R.drawable.color_picker_swatch) };
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      setBackground(new ColorStateDrawable(colorDrawable, color));
    } else {
      setBackgroundDrawable(new ColorStateDrawable(colorDrawable, color));
    }
  }

  @Override public void onClick(View v) {
    if (onColorSelectedListener != null) {
      onColorSelectedListener.onColorSelected(this, color);
    }
  }
}
