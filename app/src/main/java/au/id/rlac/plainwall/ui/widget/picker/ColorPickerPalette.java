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
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import au.id.rlac.plainwall.R;
import au.id.rlac.plainwall.ui.widget.picker.ColorPickerSwatch.OnColorSelectedListener;

/**
 * A color picker custom view which creates an grid of color squares.  The number of squares per
 * row (and the padding between the squares) is determined by the user.
 */
public class ColorPickerPalette extends TableLayout {

  public static final int SIZE_SMALL = 1;
  public static final int SIZE_LARGE = 2;

  public OnColorSelectedListener mOnColorSelectedListener;

  private String mDescription;

  private int mSwatchLength;
  private int mMarginSize;
  private int mNumColumns;

  private SparseArray<ColorPickerSwatch> swatches = new SparseArray<>();

  public ColorPickerPalette(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ColorPickerPalette(Context context) {
    super(context);
  }

  /**
   * Initialize the size, columns, and listener.  Size should be a pre-defined size (SIZE_LARGE
   * or SIZE_SMALL).
   */
  public void init(int size, int columns, OnColorSelectedListener listener) {
    mNumColumns = columns;
    Resources res = getResources();
    if (size == SIZE_LARGE) {
      mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_large);
      mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_large);
    } else {
      mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_small);
      mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_small);
    }
    mOnColorSelectedListener = listener;

    mDescription = res.getString(R.string.color_swatch_description);
  }

  private TableRow createTableRow() {
    TableRow row = new TableRow(getContext());
    ViewGroup.LayoutParams params =
        new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    row.setLayoutParams(params);
    return row;
  }

  public ColorPickerSwatch getSwatchViewForColor(int color) {
    return swatches.get(color);
  }

  /**
   * Adds swatches to table in a serpentine format.
   */
  public void drawPalette(int[] colors) {
    if (colors == null) {
      return;
    }

    this.removeAllViews();
    swatches.clear();
    int tableElements = 0;
    int rowElements = 0;
    int rowNumber = 0;

    // Fills the table with swatches based on the array of colors.
    TableRow row = createTableRow();
    for (int color : colors) {
      tableElements++;

      ColorPickerSwatch colorSwatch = createColorSwatch(color);
      setSwatchDescription(tableElements, colorSwatch);

      addSwatchToRow(row, colorSwatch);

      swatches.put(color, colorSwatch);

      rowElements++;
      if (rowElements == mNumColumns) {
        addView(row);
        row = createTableRow();
        rowElements = 0;
        rowNumber++;
      }
    }

    // Create blank views to fill the row if the last row has not been filled.
    if (rowElements > 0) {
      while (rowElements != mNumColumns) {
        addSwatchToRow(row, createBlankSpace());
        rowElements++;
      }
      addView(row);
    }
  }

  private static void addSwatchToRow(TableRow row, View swatch) {
    row.addView(swatch);
  }

  /**
   * Add a content description to the specified swatch view.
   */
  private void setSwatchDescription(int index, View swatch) {
    String description = String.format(mDescription, index);
    swatch.setContentDescription(description);
  }

  /**
   * Creates a blank space to fill the row.
   */
  private ImageView createBlankSpace() {
    ImageView view = new ImageView(getContext());
    TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
    params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
    view.setLayoutParams(params);
    return view;
  }

  /**
   * Creates a color swatch.
   */
  private ColorPickerSwatch createColorSwatch(int color) {
    ColorPickerSwatch view = new ColorPickerSwatch(getContext());
    view.setColor(color);
    view.setOnColorSelectedListener(mOnColorSelectedListener);
    TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
    params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
    view.setLayoutParams(params);
    return view;
  }
}
