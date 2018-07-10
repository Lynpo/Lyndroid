package com.lynpo.video;

/**
 * Create by fujw on 2018/6/12.
 * *
 * AutoPlayOption
 */
public enum AutoPlayOption {
    ONLY_WIFI(0),
    MOBILE_WIFI(1),
    OFF(2);

    public int option;

    AutoPlayOption(int option) {
        this.option = option;
    }

    public static AutoPlayOption getOption(int option) {
        for (AutoPlayOption o : AutoPlayOption.values()) {
            if (o.option == option) {
                return o;
            }
        }
        return AutoPlayOption.ONLY_WIFI;
    }
}
