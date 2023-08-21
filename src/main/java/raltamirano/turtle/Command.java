package raltamirano.turtle;

/**
 * http://www.mit.edu/~hlb/MA562/commands.html
 *
 * @implNote Extensions to the original command list: SHOW, HIDE.
 */
public enum Command {
    BYE("bye", false),
    SHOW("sw", false),
    HIDE("hd", false),
    FORWARD("FW", true),
    BACK("BK", true),
    LEFT("LT", true),
    RIGHT("RT", true),
    CLEARSCREEN("CS", false),
    HOME("HOME", false),
    PENDOWN("PD", false),
    PENUP("PU", false);

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
