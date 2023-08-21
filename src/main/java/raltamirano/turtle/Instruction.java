package raltamirano.turtle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Instruction {
    public static final Instruction CLEARSCREEN = new Instruction(Command.CLEARSCREEN, Collections.emptyList());
    public static final Instruction BYE = new Instruction(Command.BYE, Collections.emptyList());
    private final Command command;
    private final List<String> args;

    private Instruction(final Command command, final List<String> args) {
        this.command = command;
        this.args = args == null ? Collections.emptyList() : new ArrayList<>(args);
    }

    public static Instruction of(final Command command) {
        return new Instruction(command, Collections.emptyList());
    }

    public static Instruction of(final Command command, final List<String> args) {
        return new Instruction(command, args);
    }

    public static Instruction of(final Command command, final int value) {
        return new Instruction(command, asList(String.valueOf(value)));
    }

    public Command command() {
        return command;
    }

    public List<String> args() {
        return Collections.unmodifiableList(args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction instruction = (Instruction) o;
        return command == instruction.command && Objects.equals(args, instruction.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, args);
    }
}
