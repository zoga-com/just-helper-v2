package com.prikolz.justhelper.config;

public class ChatParameters extends ConfigObject {
    public BooleanParameter showLineLimit = boolParameter("show_line_limit", true);
    public BooleanParameter enableMarkers = boolParameter("enable_functional_markers", true);
}
