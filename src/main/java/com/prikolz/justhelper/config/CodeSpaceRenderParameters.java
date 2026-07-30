package com.prikolz.justhelper.config;

public class CodeSpaceRenderParameters extends ConfigObject {
    public BooleanParameter showPosition = boolParameter("show_position", true);
    public ObjectParameter<VerticalRenderLimit> verticalRenderLimit = objectParameter(
            "vertical_render_limit",
            VerticalRenderLimit::new
    );

    public static class VerticalRenderLimit extends ConfigObject {
        public BooleanParameter enabled = boolParameter("enabled", false);
        public IntParameter limit = intParameter("limit", 24, 0, 1024);
    }
}
