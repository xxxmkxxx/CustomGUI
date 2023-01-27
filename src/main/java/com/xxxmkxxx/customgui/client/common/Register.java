package com.xxxmkxxx.customgui.client.common;

import com.xxxmkxxx.customgui.CustomGUI;

import java.util.HashMap;
import java.util.Map;

public class Register {
    private static final Map<String, CustomGUI> registerMap = new HashMap<>();

    public static CustomGUI getGUI(String name) {
        return registerMap.get(name);
    }

    public static void register(String id, CustomGUI customGUI) {
        registerMap.put(id, customGUI);
    }

    public static void init() {
        registerMap.forEach((key, value) -> {
            value.getHudStage().init();
            value.getScreenStage().init();
        });
    }
}
