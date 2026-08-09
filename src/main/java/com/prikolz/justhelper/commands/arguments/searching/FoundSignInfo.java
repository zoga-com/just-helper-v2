package com.prikolz.justhelper.commands.arguments.searching;

import com.prikolz.justhelper.codespace.SignInfo;

public record FoundSignInfo(String[] lines, int mainLine, SignInfo sign) {

    public static FoundSignInfo create(SignInfo sign) {
        var lines = sign.getLines();
        return new FoundSignInfo(lines, 0, sign);
    }

    public String createHoverInfo(String prompt) {
        var hoverTextBuilder = new StringBuilder("<white>");
        var first = true;
        for (String line : lines) {
            if (first) {
                first = false;
                hoverTextBuilder.append(sign.getMiniBlockSprite(false)).append(" ")
                        .append(sign.codePos.getMiniBlockName()).append("\n<reset>");
                continue;
            }
            if (prompt == null)
                hoverTextBuilder.append(line).append('\n');
            else
                hoverTextBuilder.append(line.replace(prompt, "<yellow>" + prompt + "<white>")).append('\n');
        }
        hoverTextBuilder.append("<strikethrough:true><gray>                      \n<strikethrough:false>");
        hoverTextBuilder.append("<gray>").append(sign.codePos.floor).append(" э | ").append(sign.codePos.line).append(" л | ");
        hoverTextBuilder.append(sign.codePos.pos).append(" п\n").append("<dark_gray>(Нажмите для\n<dark_gray>телепортации)");
        return hoverTextBuilder.toString();
    }
}
