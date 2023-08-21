package raltamirano.turtle;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Parser() {}

    public static List<Instruction> parse(final String input) {
        final List<Instruction> result = new ArrayList<>();
        final String[] tokens = input.split("\\s+");

        Command command = null;
        boolean expectingArgs = false;
        final List<String> args = new ArrayList<>();
        for(int i=0; i<tokens.length; i++) {
            final Command parsedCommand = Command.of(tokens[i]);
            if (parsedCommand != null) {
                if (expectingArgs && args.isEmpty())
                    throw new IllegalArgumentException("Expecting args for " + command);

                if (command != null) {
                    result.add(Instruction.of(command, args));
                    command = null;
                    args.clear();
                    expectingArgs = false;
                }

                if (parsedCommand.needsParameters()) {
                    command = parsedCommand;
                    expectingArgs = true;
                } else {
                    result.add(Instruction.of(parsedCommand));
                }
            } else {
                if (!expectingArgs)
                    throw new IllegalArgumentException("Not expecting any args!");
                else
                    args.add(tokens[i]);
            }
        }

        if (expectingArgs && args.isEmpty())
            throw new IllegalArgumentException("Expecting args for " + command);

        if (command != null)
            result.add(Instruction.of(command, args));

        return result;
    }
}
