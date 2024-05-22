import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InstructionUnit {

    public Queue<Instruction> instructionQueue;
    private MIPSFileReader FR = new MIPSFileReader();

    public InstructionUnit(Queue<Instruction> instructionQueue) {
        this.instructionQueue = instructionQueue;
    }

    public InstructionUnit() {
        this.instructionQueue = new LinkedList<>();
    }

    public void loadInstructionsFromFile(String filePath) {
        List<String> lines = FR.readMIPSFile(filePath);
        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++)
        	instructions.add(FR.parseMIPSInstruction(lines.get(i)));

        for (Instruction instruction : instructions) {
            instructionQueue.add(instruction);
        }
    }
    
    public void printContents() {
        System.out.println("Instructions in IU:");
        for (Instruction currentInstruction : instructionQueue) {
            System.out.println("Operation: " + currentInstruction.operation +
                    ", Dest: " + currentInstruction.destRegister +
                    ", Src1: " + currentInstruction.src1Register +
                    ", Src2: " + currentInstruction.src2Register);
        }
    }
}
