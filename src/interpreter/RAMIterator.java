package interpreter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class RAMIterator implements Iterator<Instruction> {

    private List<Instruction> instructions;
    private BufferedReader br;
    private int currIndex;
    private Map<Integer, String> intToChar;
    private List<Register> registers;
    private BufferedWriter bw;
    String newline = System.getProperty("line.separator");
    
    public RAMIterator() {
    }
    
    public RAMIterator(String path) {
        instructions = new ArrayList<Instruction>();
        intToChar = new HashMap<Integer, String>();
        registers = new ArrayList<Register>();
        try {
            bw = new BufferedWriter(new FileWriter(path + ".log"));
            br = new BufferedReader(new FileReader(path));
            // k line
            String line = br.readLine();
            int k = 0;
            k = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < k; ++i) {
                intToChar.put(i + 1, Character.toString((char) (i + 97)));
            }
            // n line
            line = br.readLine();
            int n = 0;
            n = Integer.parseInt(line.split(" ")[1]);
            // p line
            line = br.readLine();
            int p = 0;
            p = Integer.parseInt(line.split(" ")[1]);
            for (int i = 0; i < p; ++i) {
                registers.add(new Register(i + 1));
            }
            // i line
            line = br.readLine();
            int input = 0;
            String inputStrings[] = line.split(" ");
            input = inputStrings.length - 1;
            if (input != n) {
                throw new SyntaxErrorException();
            }
            for (int i = 0; i < input; ++i) {
                registers.get(i).setValue(inputStrings[i + 1]);
            }
            line = br.readLine();
            bw.write("Program to run:" + newline);
            while (line != null) {
                bw.write(line + newline);
                instructions.add(new Instruction(line));
                line = br.readLine();
            }
            br.close();
            bw.write(newline);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        currIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return !instructions.get(currIndex).getOpcode().toString().equals("continue");
    }

    @Override
    public Instruction next() {
        if (!hasNext()) {
            try {
                bw.write("No next instruction! Aborting..." + newline);
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new NoSuchElementException();
        }
        return instructions.get(currIndex);
    }
    
    public void executeInstruction(Instruction in) throws IOException {
        bw.write("Executing instruction:" + newline + in.toString() + newline);
        switch (in.getOpcode()) {
        case ADD:
            String charToAdd = intToChar.get(in.getJ());
            registers.get(in.getY() - 1).addRight(charToAdd);
            currIndex++;
            break;
        case TAIL:
            registers.get(in.getY() - 1).removeLeft();
            currIndex++;
            break;
        case CLEAR:
            registers.get(in.getY() - 1).clear();
            currIndex++;
            break;
        case ASSIGN:
            String tmp = registers.get(in.getY() - 1).getValue();
            registers.get(in.getR() - 1).setValue(tmp);
            currIndex++;
            break;
        case GOTOA:
            int i = currIndex - 1;
            while (i >= 0) {
                if (instructions.get(i).getN() == in.getY()) {
                    currIndex = i;
                    break;
                }
                i--;
            }
            break;
        case GOTOB:
            i = currIndex + 1;
            while (i < instructions.size()) {
                if (instructions.get(i).getN() == in.getY()) {
                    currIndex = i;
                    break;
                }
                i++;
            }
            break;
        case JMPA:
            String queryChar = intToChar.get(in.getJ());
            if (!registers.get(in.getR() - 1).getValue().equals("") && 
                    registers.get(in.getR() - 1).getLeftmost().equals(queryChar)) {
                i = currIndex - 1;
                while (i >= 0) {
                    if (instructions.get(i).getN() == in.getY()) {
                        currIndex = i;
                        break;
                    }
                    i--;
                }
            } else {
                currIndex++;
            }
            break;
        case JMPB:
            queryChar = intToChar.get(in.getJ());
            if (!registers.get(in.getR() - 1).getValue().equals("") && 
                    registers.get(in.getR() - 1).getLeftmost().equals(queryChar)) {
                i = currIndex + 1;
                while (i < instructions.size()) {
                    if (instructions.get(i).getN() == in.getY()) {
                        currIndex = i;
                        break;
                    }
                    i++;
                }
            } else {
                currIndex++;
            }
            break;
        case CONTINUE:
        default:
            break;
        }
        bw.write(this.toString() + newline);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Register r : registers) {
            sb.append("R" + r.getRegisterNum() + ": " + r.getValue() + newline);
        }
        return sb.toString();
    }
    
    public String getResult() {
        try {
            bw.write("Result: " + registers.get(0).getValue() + newline);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registers.get(0).getValue();
    }

}
