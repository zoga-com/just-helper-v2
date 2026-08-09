package com.prikolz.justhelper.config;

public class TeleportAnchorParameters extends ConfigObject {
    public BooleanParameter enabled = boolParameter("enable", true);
    public BooleanParameter sendMessage = boolParameter("send_message", true);
    public StringParameter icon = stringParameter("icon", "<#0000FF><shadow:#FFFFFFFF>⚓");
    public BooleanParameter marker = boolParameter("3d_marker", true);
}
