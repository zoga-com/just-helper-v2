package ru.zoga_com.jmcd.utils;

@SuppressWarnings("unused")
public class SerializableConfig {
    public BlockNames block_names;
    public ChatParameters chat_parameters;
    public boolean show_position_in_code;
    public int command_sending_cooldown;
    public boolean enable_teleport_anchor;
    public boolean enable_each_find_list;


    public class ChatParameters {
        public boolean show_line_limit;
        public boolean enable_functional_markers;
    }

    public class BlockNames {
        public String bricks;
        public String prismarine;
        public String obsidian;
        public String oak_planks;
        public String emerald_block;
        public String purpur_block;
        public String diamond_block;
        public String iron_block;
        public String mossy_cobblestone;
        public String lapis_block;
        public String lapis_ore;
        public String dark_prismarine;
        public String cobblestone;
        public String gold_block;
        public String redstone_block;
        public String end_stone;
        public String netherrack;
        public String emerald_ore;
        public String red_nether_bricks;
        public String coal_block;
    }
}
