package ru.zoga_com.jmcd;

public class Messages {
    public static String BASE64_ENCODED = "<#9AFF1F>Закодированный текст base64:<white>\n{0}";
    public static String BASE64_DECODED = "<#9AFF1F>Декодированный текст base64:<white>\n{0}";
    public static String SHOW_FLOOR = "<yellow>{0}{2}<dark_gray> |> <click:suggest_command:'{3}'><hover:show_text:'{4}'><white>{1}";
    public static String SHOW_FLOOR_ALL_BEFORE = "<yellow>v <white>Описания этажей:";
    public static String SHOW_FLOOR_ALL_AFTER = "<yellow>^";
    public static String DESCRIBE = "<#9AFF1F>Установлено описание <white>{0} <#9AFF1F>этажа:<white> {1}";
    public static String ONLY_ON_DEV = "<sprite:items:item/command_block_minecart><#FF6467> Доступно только в мире кода!";
    public static String SHOW_FLOOR_DESCRIPTION_NOT_FOUND = "<#FF6467>Описание этажа {0} не найдено";
    public static String FIND_EMPTY = "\n<hover:show_text:'Параметры поиска:<yellow>\n{0}'><click:suggest_command:'{1}'><dark_gray> ♯ Ничего не найдено ♯";
    public static String FIND = "\n<hover:show_text:'Параметры поиска:<yellow>\n{0}'><click:suggest_command:'{1}'><yellow> ♯<white> Найдено {2} совпадений:\n";
    public static String GZIP_COMPRESS_ERROR = "<#FF6467>Ошибка сжатия: {0}";
    public static String GZIP_COMPRESS = "<#9AFF1F>Сжатый текст gzip:<white>\n{0}";
    public static String DECOMPRESS_ERROR = "<#FF6467>Ошибка распаковки: {0}";
    public static String GZIP_DECOMPRESS = "<#9AFF1F>Распакованный текст gzip:<white>\n{0}";
    public static String ITEM_EDITOR_COLOR_NOT_SET = "<#FF6467>Компонент цвета не установлен";
    public static String ITEM_EDITOR_COLOR_CURRENT = "Установленный цвет предмета:\n<reset>♯ HEX: <underlined><#{1}>{0}<reset>\n☰ RGB: <underlined><#{1}> {2}";
    public static String ITEM_EDITOR_COLOR_SET = "<#9AFF1F>Новый цвет предмета:\n<reset>♯ HEX: <underlined><#{1}>{0}<reset>\n☰ RGB: <underlined><#{1}> {2}";
    public static String ITEM_EDITOR_EQUIPMENT_SLOT = "<#9AFF1F>Слот: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_OVERLAY = "<#9AFF1F>Оверлей: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_ALLOWED_ENTITIES = "<#9AFF1F>Разрешенные сущности: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_DISPENSABLE = "<#9AFF1F>Выбрасывание раздатчиком: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_SWAPPABLE = "<#9AFF1F>Заменяемость: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_DAMAGE_ON_HURT = "<#9AFF1F>Разрушение от урона: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_EQUIP_ON_INTERACT = "<#9AFF1F>Экипировка взаимодействием: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_EQUIP_SOUND = "<#9AFF1F>Звук экипировки: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_GLIDER = "<#9AFF1F>Режим планирования: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_CAN_BE_SHEARED = "<#9AFF1F>Возможность снять ножницами: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_SHEARING_SOUND = "<#9AFF1F>Звук снятия ножницами: <white>{0}";
    public static String ITEM_EDITOR_EQUIPMENT_DELETED = "<yellow>Компонент брони был удален";
    public static String ITEM_EDITOR_EQUIPMENT_NOT_SET = "<#FF6467>Компонент экипировки не установлен";
    public static String ITEM_EDITOR_EQUIPMENT_CURRENT = """
                                    <#9AFF1F>Заданные параметры экипировки:
                                    <white> Слот: <#9AFF1F>{0}
                                    <white> Звук экипировки: <#9AFF1F>{1}
                                    <white> Ассет: <#9AFF1F>{2}
                                    <white> Оверлей: <#9AFF1F>{3}
                                    <white> Разрешенные сущности: <#9AFF1F>{4}
                                    <white> Выбрасывание раздатчиком: <#9AFF1F>{5}
                                    <white> Заменяемость: <#9AFF1F>{6}
                                    <white> Разрушение от урона: <#9AFF1F>{7}
                                    <white> Экипировка взаимодействием: <#9AFF1F>{8}
                                    <white> Возможность снять ножницами: <#9AFF1F>{9}
                                    <white> Звук снятия ножницами: <#9AFF1F>{10}
                                    <white> Режим планирования: <#9AFF1F>{11}
                                    """;
    public static String ITEM_EDITOR_PROFILE_NOT_SET = "<#FF6467>Профиль предмета не задан!";
    public static String ITEM_EDITOR_PROFILE = "<aqua>ⓘ<white> Профиль предмета:";
    public static String ITEM_EDITOR_PROFILE_FORMAT = " • <aqua>{0}<white> = <aqua>{1}";
    public static String ITEM_EDITOR_ATTRIBUTE_ADDED = "<<#9AFF1F>Добавлен атрибут <white><tr:'{0}'>/{1}";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIER_NOT_SET = "<yellow>Модификаторы атрибута <white><tr:'{0}'><yellow> не найдены!";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIER_DELETED = "<#9AFF1F>Удалено <white>{0}<#9AFF1F> модификаторов атрибута <white><tr:'{1}'>";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIER_FORMAT = "    - <yellow>{0} <white>{1} <gold>{2} {3}";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIER_MESSAGE_FORMAT = "  • <hover:show_text:'<tr:chat.copy> {1}'><click:copy_to_clipboard:'{1}'><aqua><tr:'{0}'>";
    public static String ITEM_EDITOR_ATTRIBUTE_NOT_SET = "<yellow>Атрибут <white><tr:'{0}'>/{1}<yellow> не найден!";
    public static String ITEM_EDITOR_ATTRIBUTE_DELETED = "<#9AFF1F>Удален атрибут <white><tr:'{0}'>/{1}";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIERS_NOT_SET = "<#FF6467>Модификаторы не найдены!";
    public static String ITEM_EDITOR_ATTRIBUTE_MODIFIERS = "<yellow>⏷ <white>Список модификаторов:";
    public static String ITEM_EDITOR_TAGS_ADDED = "<#9AFF1F>Добавлен тег <white>'{0}'";
    public static String ITEM_EDITOR_TAGS_NOT_SET = "<#FF6467>Тег <white>{0} <#FF6467>не найден!";
    public static String ITEM_EDITOR_TAGS_DELETED = "<#9AFF1F>Тег <white>{0} <#9AFF1F>удален!";
    public static String ITEM_EDITOR_TAGS = "\n<yellow>ⓘ<white> Установленные теги предмета:\n";
    public static String ITEM_EDITOR_TAGS_FORMAT = " <yellow>● <white>{0}<reset> <yellow>= <click:copy_to_clipboard:'{2}'><hover:show_text:'<tr:chat.copy>\n{2}'><white>{1}";
    public static String ITEM_EDITOR_TAGS_CLEARED = "<#9AFF1F>Очищено {0} тегов";
    public static String ITEM_EDITOR_TAGS_PASTE = "<#9AFF1F>>Скопировано <white>{0} <#9AFF1F>тегов в буфер. Для установки в предмет используйте /{1} tag paste";
    public static String ITEM_EDITOR_TAGS_SET = "<#9AFF1F>Установлено <white>{0} <#9AFF1F>тегов в предмет";
    public static String ITEM_EDITOR_ITEM_NEED_HOLD = "<#FF6467>Для редактирования предмета вы должны держать его в ведущей руке.";
    public static String ITEM_EDITOR_ITEM_ERROR = "<#FF6467>Ошибка выполнения: ";
    public static String LINE_COMMAND_ERROR = "<#FF6467>При выполнении команды произошла ошибка: {0}\nПодробнее /justhelper logs";
    public static String POS_SET = "<aqua>⧈<white> Точка возврата задана <aqua>⊻ <reset>{0} <gray>{2}<reset><aqua> ⊻";
    public static String POS_NOT_SET = "<yellow>⧈ Точка возврата не задана";
    public static String ZLIB_COMPRESS = "<#9AFF1F>Сжатый текст zlib:<white> {0}";
    public static String ZLIB_DECOMPRESS = "<#9AFF1F>Распакованный текст zlib:<white> {0}";
    public static String CONFIG_UPDATED = "<sprite:gui:icon/checkmark><#9AFF1F> Конфиг обновлен";
    public static String TEXT_UPDATED = "<sprite:gui:icon/checkmark><#9AFF1F> Текст обновлен";
}
