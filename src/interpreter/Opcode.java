package interpreter;

public enum Opcode {

    ADD("add"),
    TAIL("tail"),
    CLEAR("clr"),
    ASSIGN("assign"),
    GOTOA("gotoa"),
    GOTOB("gotob"),
    JMPA("jmpa"),
    JMPB("jmpb"),
    CONTINUE("continue");
    
    private String s;
    
    Opcode(String s) {
        this.s = s;
    }
    
    public String getText() {
        return this.s;
    }
    
    public static Opcode fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        for (Opcode oc : Opcode.values()) {
            if (s.equalsIgnoreCase(oc.toString())) {
                return oc;
            }
        }
        throw new IllegalArgumentException();
    }
    
    @Override
    public String toString() {
        return s;
    }
    
}
