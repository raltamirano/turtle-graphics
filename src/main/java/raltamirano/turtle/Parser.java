package raltamirano.turtle;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Parser() {}

    public static List<Command> parse(final String input) {
        final List<Command> result = new ArrayList<>();
        final String[] tokens = input.split("\\s+");

        Instruction instruction = null;
        boolean expectingArgs = false;
        final List<String> args = new ArrayList<>();
        for(int i=0; i<tokens.length; i++) {
            final Instruction parsedInstruction = Instruction.of(tokens[i]);
            if (parsedInstruction != null) {
                if (expectingArgs && args.isEmpty())
                    throw new IllegalArgumentException("Expecting args for " + instruction);

                if (instruction != null) {
                    result.add(Command.of(instruction, args));
                    instruction = null;
                    args.clear();
                    expectingArgs = false;
                }

                if (parsedInstruction.needsParameters()) {
                    instruction = parsedInstruction;
                    expectingArgs = true;
                } else {
                    result.add(Command.of(parsedInstruction));
                }
            } else {
                if (!expectingArgs)
                    throw new IllegalArgumentException("Not expecting any args!");
                else
                    args.add(tokens[i]);
            }
        }

        if (expectingArgs && args.isEmpty())
            throw new IllegalArgumentException("Expecting args for " + instruction);

        if (instruction != null)
            result.add(Command.of(instruction, args));

        return result;
    }
}
