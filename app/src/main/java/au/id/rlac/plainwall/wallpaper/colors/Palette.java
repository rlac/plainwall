package au.id.rlac.plainwall.wallpaper.colors;

import au.id.rlac.plainwall.R;

public enum Palette {
  Material {
    static final int RED_500 = 0xFFF44336;
    static final int PINK_500 = 0xFFE91E63;
    static final int PURPLE_500 = 0xFF9C27B0;
    static final int DEEP_PURPLE_500 = 0xFF673AB7;
    static final int INDIGO_500 = 0xFF3F51B5;
    static final int BLUE_500 = 0xFF2196F3;
    static final int LIGHT_BLUE_500 = 0xFF03A9F4;
    static final int CYAN_500 = 0xFF00BCD4;
    static final int TEAL_500 = 0xFF009688;
    static final int GREEN_500 = 0xFF4CAF50;
    static final int LIGHT_GREEN_500 = 0xFF8BC34A;
    static final int LIME_500 = 0xFFCDDC39;
    static final int YELLOW_500 = 0xFFFFEB3B;
    static final int AMBER_500 = 0xFFFFC107;
    static final int ORANGE_500 = 0xFFFF9800;
    static final int DEEP_ORANGE_500 = 0xFFFF5722;
    static final int BROWN_500 = 0xFF795548;
    static final int GREY_500 = 0xFF9E9E9E;
    static final int BLUE_GREY_500 = 0xFF607D8B;

    @Override public int getName() {
      return R.string.palette_material;
    }

    @Override public int[] getColors() {
      return new int[] {
          RED_500, PINK_500, PURPLE_500, DEEP_PURPLE_500, INDIGO_500, BLUE_500, LIGHT_BLUE_500,
          CYAN_500, TEAL_500, GREEN_500, LIGHT_GREEN_500, LIME_500, YELLOW_500, AMBER_500,
          ORANGE_500, DEEP_ORANGE_500, BROWN_500, GREY_500, BLUE_GREY_500
      };
    }
  }, Forest {
    @Override public int getName() {
      return R.string.palette_forest;
    }

    @Override public int[] getColors() {
      return new int[] {
          0xFF181907, 0xFF2C3E14, 0xFF2D541D, 0xFF507127, 0xFF7C9D39, 0xFF89B537, 0xFF95A566,
          0xFFB6CB77, 0xFFC5CDA6
      };
    }
  }, Sky {
    @Override public int getName() {
      return R.string.palette_sky;
    }

    @Override public int[] getColors() {
      return new int[] {
          0xFF476CA1, 0xFF6281A8, 0xFF82A9D1, 0xFF3A8DE0, 0xFF5097D9, 0xFFB8CFE6, 0xFFE4F1F7,
          0xFFF2FCFB, 0xFFFCFCF7
      };
    }
  }, Sunset {
    @Override public int getName() {
      return R.string.palette_sunset;
    }

    @Override public int[] getColors() {
      return new int[] {
          0xFF081838, 0xFF132854, 0xFF623D63, 0xFF41426B, 0xFF994259, 0xFFD14F58, 0xFFF7773B,
          0xFFF26e57, 0xFFFCD162
      };
    }
  }, City {
    @Override public int getName() {
      return R.string.palette_city;
    }

    @Override public int[] getColors() {
      return new int[] {
          0xFF393F45, 0xFF68737A, 0xFF7A8689, 0xFF605D5A, 0xFFA29E9C, 0xFFC5CBC6
      };
    }
  }, Reef {
    @Override public int getName() {
      return R.string.palette_reef;
    }

    @Override public int[] getColors() {
      return new int[] {
          0xFF132129, 0XFF482f4B, 0xFF037ACC, 0xFF497565, 0xFF5288A0, 0xFF724E29, 0xFF6E6F42,
          0XFF588A34, 0XFFA1DD1F, 0XFFDE3515, 0xFFCBAA58, 0xFFBA9FA2
      };
    }
  };

  public abstract int getName();

  public abstract int[] getColors();

}
