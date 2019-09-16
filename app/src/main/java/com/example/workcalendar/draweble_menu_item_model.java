package com.example.workcalendar;

public class draweble_menu_item_model {
    private int icon_code;
    private String item_text;

    public draweble_menu_item_model(int icon_code, String item_text) {
        this.icon_code = icon_code;
        this.item_text = item_text;
    }

    public int getIcon_code() {
        return icon_code;
    }

    public String getItem_text() {
        return item_text;
    }
}
