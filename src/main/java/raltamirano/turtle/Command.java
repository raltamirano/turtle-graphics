package raltamirano.turtle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Command {
    public static final Command CLEARSCREEN = new Command(Instruction.CLEARSCREEN, Collections.emptyList());
    public static final Command BYE = new Command(Instruction.BYE, Collections.emptyList());
    private final Instruction instruction;
    private final List<String> args;

    private Command(final Instruction instruction, final List<String> args) {
        this.instruction = instruction;
        this.args = args == null ? Collections.emptyList() : new ArrayList<>(args);
    }

    public static Command of(final Instruction instruction) {
        return new Command(instruction, Collections.emptyList());
    }

    public static Command of(final Instruction instruction, final List<String> args) {
        return new Command(instruction, args);
    }

    public static Command of(final Instruction instruction, final int value) {
        return new Command(instruction, asList(String.valueOf(value)));
    }

    public Instruction instruction() {
        return instruction;
    }

    public List<String> args() {
        return Collections.unmodifiableList(args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return instruction == command.instruction && Objects.equals(args, command.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instruction, args);
    }
}
