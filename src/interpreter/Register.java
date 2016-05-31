package interpreter;

public class Register {

    private int num;
    private String value;
    
    public Register() {
        value = "";
    }
    
    public Register(int i) {
        this.num = i;
        value = "";
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String s) {
        value = s;
    }

    public int getRegisterNum() {
        return num;
    }
    
    public void addRight(String s) {
        value += s;
    }
    
    public void removeLeft() {
        value = value.substring(1);
    }
    
    public String getLeftmost() {
        return value.substring(0, 1);
    }
    
    public void clear() {
        value = "";
    }
    
}
