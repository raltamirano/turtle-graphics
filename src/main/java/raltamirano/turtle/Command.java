package raltamirano.turtle;

/**
 * http://www.mit.edu/~hlb/MA562/commands.html
 *
 * @implNote Extensions to the original command list: SHOW, HIDE, PUSH_*, POP_*
 */
public enum Command {
    BYE("bye", false),
    FORWARD("fw", true),
    BACK("bk", true),
    LEFT("lt", true),
    RIGHT("rt", true),
    CLEARSCREEN("cs", false),
    HOME("home", false),
    PENDOWN("pd", false),
    PENUP("pu", false),

    // Extensions
    SHOW("sw", false),
    HIDE("hd", false),
    PUSH_ANGLE("pu-a", false),
    PUSH_POSITION("pu-pos", false),
    POP_ANGLE("po-a", false),
    POP_POSITION("po-pos", false);

    private final String mnemonic;
    private final boolean needsParameters;

    Command(String mnemonic, boolean needsParameters) {
        this.mnemonic = mnemonic;
        this.needsParameters = needsParameters;
    }

    public static Command of(String token) {
        for(Command i : values())
            if (token.trim().equalsIgnoreCase(i.name()) || token.trim().equalsIgnoreCase(i.mnemonic()))
                return i;
        return null;
    }

    public String mnemonic() {
        return mnemonic;
    }

    public boolean needsParameters() {
        return needsParameters;
    }
}
