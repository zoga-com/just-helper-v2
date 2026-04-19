package ru.zoga_com.jmcd.ui.widgets.config;

public class ConfigSetting<T> {
    public String title;
    public String description;
    public T value;

    public ConfigSetting(String title, String description, T value) {
        this.title = title;
        this.description = description;
    }
}
