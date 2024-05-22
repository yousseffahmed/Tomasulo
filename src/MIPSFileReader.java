import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MIPSFileReader {

    public List<String> readMIPSFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/programMIPS/"+filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
    
    public Instruction parseMIPSInstruction(String line) {
        String[] parts = line.split(" ");
        String operation = parts[0];
        String destRegister = parts[1];
        String src1Register = parts[2];
        String src2Register = parts[3];

        return new Instruction(operation, destRegister, src1Register, src2Register);
    }
}
