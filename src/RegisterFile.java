import java.util.ArrayList;
import java.util.List;

public class RegisterFile {

    public List<Register> intRegisters;
    public List<Register> floatRegisters;

    public RegisterFile() {
        this.intRegisters = new ArrayList<>();
        this.floatRegisters = new ArrayList<>();
        initializeIntRegisters();
        initializeFloatRegisters();
    }

    private void initializeIntRegisters() {
        intRegisters.add(new Register("zero", " ", 0.0, 0));

        for (int i = 1; i <= 31; i++) {
            intRegisters.add(new Register("T" + i, " ", 0.0, 0));
        }
    }

    private void initializeFloatRegisters() {
        for (int i = 0; i <= 31; i++) {
            floatRegisters.add(new Register("F" + i, " ", 0.0f, 0));
        }
    }

    public int readIntRegister(String registerName) {
        return findRegisterByName(intRegisters, registerName).contentINT;
    }

    public void writeIntRegister(String registerName, int value) {
        findRegisterByName(intRegisters, registerName).contentINT = value;
    }

    public double readFloatRegister(String registerName) {
        return findRegisterByName(floatRegisters, registerName).contentDOU;
    }

    public void writeFloatRegister(String registerName, double value) {
        findRegisterByName(floatRegisters, registerName).contentDOU = value;
    }

    public List<Register> getIntRegisterFileSnapshot() {
        return new ArrayList<>(intRegisters);
    }

    public List<Register> getFloatRegisterFileSnapshot() {
        return new ArrayList<>(floatRegisters);
    }

    private Register findRegisterByName(List<Register> registerList, String registerName) {
        for (Register register : registerList) {
            if (register.name.equals(registerName)) {
                return register;
            }
        }
        throw new IllegalArgumentException("Invalid register name: " + registerName);
    }
    
    public void outputRegisterFile() {

        System.out.println("\nFloating-Point Registers:");
        outputRegisters(floatRegisters);
    }

    private void outputRegisters(List<Register> registers) {
        for (Register register : registers) {
            System.out.println(register.name + ": " + "Qi=" + register.Qi + ", Content (int)=" + register.contentINT + ", Content (float)=" + register.contentDOU);
        }
    }
}
