package com.prikolz.justhelper.config;

import com.prikolz.justhelper.commands.Commands;

import java.util.HashMap;
import java.util.List;

public class CommandParameters extends ConfigObject {
    public final HashMap<String, ObjectParameter<Parameter>> commands = new HashMap<>();

    public CommandParameters() {
        for (var command : Commands.commands.values()) {
            var id = command.id;
            var parameter = objectParameter(id, () -> new Parameter(id, command.defaultAliases));
            commands.put(id, parameter);
        }
    }

    public Parameter get(String id) {
        var value = commands.get(id);
        if (value == null) return new Parameter(id);
        return value.value;
    }

    public static class Parameter extends ConfigObject {
        public final StringParameter name;
        public final BooleanParameter enabled = boolParameter("enabled", true);
        public final StringListParameter aliases;

        public Parameter(String id, String ... aliases) {
            this.name = stringParameter("name", id);
            this.aliases = stringListParameter("aliases", List.of(aliases));
        }

        public boolean isEnabled() { return enabled.value; }
        public String getName() { return name.value; }
    }
}
