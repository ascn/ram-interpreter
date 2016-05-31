package interpreter;

import java.io.IOException;

public class Interpreter {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: Interpreter file");
        }
        RAMIterator ri = new RAMIterator(args[0]);
        while (ri.hasNext()) {
            Instruction i = ri.next();
            ri.executeInstruction(i);
        }
        System.out.println("Result: " + ri.getResult());
    }

}
