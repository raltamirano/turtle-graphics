package raltamirano.turtle;

public enum Instruction {
    BYE("bye", false),
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

    Instruction(String mnemonic, boolean needsParameters) {
        this.mnemonic = mnemonic;
        this.needsParameters = needsParameters;
    }

    public static Instruction of(String token) {
        for(Instruction i : values())
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
