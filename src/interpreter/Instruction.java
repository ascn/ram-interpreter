package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {

    private int n;
    private int r;
    private Opcode oc;
    private int j;
    private int y;
    
    Pattern p = Pattern.compile("N?(\\d+)");
    
    public Instruction() {
    }
    
    public Instruction(String s) {
        if (!s.matches(
            "N?\\d+ \\d+ (add|tail|clr|assign|gotoa|gotob|jmpa|jmpb|continue) \\d+ N?\\d+")) {
            throw new IllegalArgumentException();
        }
        if (s.matches("continue")) {
            oc = Opcode.fromString("continue");
            n = r = j = y = 0;
            return;
        }
        String tmp[] = s.split(" ");
        Matcher m = p.matcher(tmp[0]);
        if (m.find()) {
            n = Integer.parseInt(m.group(1));
        }
        r = Integer.parseInt(tmp[1]);
        oc = Opcode.fromString(tmp[2]);
        j = Integer.parseInt(tmp[3]);
        m = p.matcher(tmp[4]);
        if (m.find()) {
            y = Integer.parseInt(m.group(1));
        }
        
        //System.out.println(n + " " + r + " " + oc + " " + j + " " + y);
        
    }
    
    public int getN() {
        return n;
    }
    
    public int getR() {
        return r;
    }
    
    public Opcode getOpcode() {
        return oc;
    }
    
    public int getJ() {
        return j;
    }
    
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return (n + " " + r + " " + oc + " " + j + " " + y);
    }
    
}
