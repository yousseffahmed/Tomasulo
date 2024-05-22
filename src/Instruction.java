public class Instruction {
    public String operation;
    public String destRegister;
    public String src1Register;
    public String src2Register;

    public Instruction(String operation, String destRegister, String src1Register, String src2Register) {
        this.operation = operation;
        this.destRegister = destRegister;
        this.src1Register = src1Register;
        this.src2Register = src2Register;
    }

	public Instruction() {
		
	}
}
