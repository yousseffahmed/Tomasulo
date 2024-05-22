
public class Execute {
	public void execute(Instruction ins, RegisterFile reg, Cache cache) {
		ALU alu = new ALU();
		
		if (ins.operation.equals("ADD.D")) {
			double rs = reg.readFloatRegister(ins.src1Register);
			double rt = reg.readFloatRegister(ins.src2Register);
			reg.writeFloatRegister(ins.destRegister, alu.addFP(rs, rt));
		}
		
		else if (ins.operation.equals("ADD")) {
			int rs = reg.readIntRegister(ins.src1Register);
			int rt = reg.readIntRegister(ins.src2Register);
			reg.writeIntRegister(ins.destRegister, alu.add(rs, rt));
		}
		
		else if (ins.operation.equals("SUB.D")) {
			double rs = reg.readFloatRegister(ins.src1Register);
			double rt = reg.readFloatRegister(ins.src2Register);
			reg.writeFloatRegister(ins.destRegister, alu.subtractFP(rs, rt));
		}
		
		else if (ins.operation.equals("SUB")) {
			int rs = reg.readIntRegister(ins.src1Register);
			int rt = reg.readIntRegister(ins.src2Register);
			reg.writeIntRegister(ins.destRegister, alu.subtract(rs, rt));
		}
		
		else if (ins.operation.equals("MUL.D")) {
			double rs = reg.readFloatRegister(ins.src1Register);
			double rt = reg.readFloatRegister(ins.src2Register);
			reg.writeFloatRegister(ins.destRegister, alu.multiplyFP(rs, rt));
		}
		
		else if (ins.operation.equals("MUL")) {
			int rs = reg.readIntRegister(ins.src1Register);
			int rt = reg.readIntRegister(ins.src2Register);
			reg.writeIntRegister(ins.destRegister, alu.multiply(rs, rt));
		}
		
		else if (ins.operation.equals("DIV.D")) {
			double rs = reg.readFloatRegister(ins.src1Register);
			double rt = reg.readFloatRegister(ins.src2Register);
			reg.writeFloatRegister(ins.destRegister, alu.divideFP(rs, rt));
		}
		
		else if (ins.operation.equals("DIV")) {
			int rs = reg.readIntRegister(ins.src1Register);
			int rt = reg.readIntRegister(ins.src2Register);
			reg.writeIntRegister(ins.destRegister, alu.divide(rs, rt));
		}

		else if (ins.operation.equals("LD")) {
			double content = (double) cache.read(Integer.parseInt(ins.src1Register));
			reg.writeFloatRegister(ins.destRegister, content);
		}
		
		else if (ins.operation.equals("SD")) {
			double content = reg.readFloatRegister(ins.destRegister);
			cache.write(Integer.parseInt(ins.src1Register), content);
		}
	}
}
