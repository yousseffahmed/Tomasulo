import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Tomasulo {
    public static ReservationStation[] addSubStation;
    public static ReservationStation[] divMulStation;
    public static Buffer[] loadBuffer;
    public static Buffer[] storeBuffer;
    public static RegisterFile RF;
    public static InstructionUnit IU;
    public static Execute EX;
    public static Cache cache;
    public static int addSubTime;
    public static int divMulTime;

    public static void init() {
        Scanner scanner = new Scanner(System.in);
        
        Queue<Instruction> instructionQueue = new LinkedList<>();
        RF = new RegisterFile();
        IU = new InstructionUnit(instructionQueue);
        EX = new Execute(); 

        System.out.print("Select add/sub station size: ");
        int addSubSize = scanner.nextInt();
        System.out.print("Select add/sub execution time: ");
        addSubTime = scanner.nextInt();
        addSubStation = new ReservationStation[addSubSize];
        initializeReservationStations(addSubStation, "A", addSubTime);
        
        System.out.print("Select div/mul station size: ");
        int divMulSize = scanner.nextInt();
        System.out.print("Select div/mul execution time: ");
        divMulTime = scanner.nextInt();    
        divMulStation = new ReservationStation[divMulSize];
        initializeReservationStations(divMulStation, "M", divMulTime);
        
        System.out.print("Select load buffer size: ");
        int loadSize = scanner.nextInt();
        System.out.print("Select load execution time: ");
        int loadTime = scanner.nextInt();
        loadBuffer = new Buffer[loadSize];
        initializeBuffers(loadBuffer, "L", loadTime);
        
        System.out.print("Select store buffer size: ");
        int storeSize = scanner.nextInt();
        System.out.print("Select store execution time: ");
        int storeTime = scanner.nextInt();
        storeBuffer = new Buffer[storeSize];
        initializeBuffers(storeBuffer, "S", storeTime);
        
        System.out.print("Select number of register you want to preload: ");
        int noRegister = scanner.nextInt();
        
        for (int i = 0; i < noRegister; i++) {
        	System.out.print("Enter register name: ");
            String name = scanner.next();
            System.out.print("Enter value: ");
            if (scanner.hasNextInt()) {
                int intValue = scanner.nextInt();
                RF.writeIntRegister(name, intValue);
            } else if (scanner.hasNextDouble()) {
                double doubleValue = scanner.nextDouble();
                RF.writeFloatRegister(name, doubleValue);
            }
        }
        
        System.out.print("Enter file name: ");
        String fileName = scanner.next();
        IU.loadInstructionsFromFile(fileName);
        
        System.out.print("Select cache size: ");
        int cacheSize = scanner.nextInt();
        cache = new Cache(cacheSize);
        
        System.out.print("Select number blocks you want to input: ");
        int cacheMap = scanner.nextInt();
        for (int i = 0; i < cacheMap; i++) {
        	System.out.print("Enter location: ");
        	int location = scanner.nextInt();
        	System.out.print("Enter value: ");
        	if (scanner.hasNextInt()) {
                int intValue = scanner.nextInt();
                cache.write(location,intValue);
            } else if (scanner.hasNextDouble()) {
                double doubleValue = scanner.nextDouble();
                cache.write(location, doubleValue);
            }
        }
        
        scanner.close();
    }

    public static void initializeReservationStations(ReservationStation[] stationArray, String name, int time) {
        for (int i = 0; i < stationArray.length; i++) {
            stationArray[i] = new ReservationStation(name + i, "", false, 0, 0, " ", " ", time);
        }
    }
    
    public static void initializeBuffers(Buffer[] bufferArray, String name, int time) {
        for (int i = 0; i < bufferArray.length; i++) {
        	bufferArray[i] = new Buffer(name+i, false, " ", time);
        }
    }
    
    public boolean searchIntRF(String src) {
    	boolean flag = false;
    	for (int i = 0; i < 32; i++) {
    		if (RF.intRegisters.get(i).name.equals(src) && RF.intRegisters.get(i).Qi != " ") {
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    }
    
    public static boolean searchFloatRF(String src) {
    	boolean flag = false;
    	for (int i = 0; i < 32; i++) {
    		if (RF.floatRegisters.get(i).name.equals(src) && RF.floatRegisters.get(i).Qi.equals(" ")) {
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    }
    
    public static String getQiInt(String src) {
    	String result = "";
    	for (int i = 0; i < 32; i++) {
    		if (RF.intRegisters.get(i).name.equals(src)) {
    			result = RF.intRegisters.get(i).Qi;
    			break;
    		}
    	}
    	return result;
    }
    
    public static String getQiFloat(String src) {
    	String result = "";
    	for (int i = 0; i < 32; i++) {
    		if (RF.floatRegisters.get(i).name.equals(src)) {
    			result = RF.floatRegisters.get(i).Qi;
    			break;
    		}
    	}
    	return result;
    }
    
    public static void overRideFloatRF(String name, String des) {
    	for (int i = 0; i < 32; i++) {
    		if (RF.floatRegisters.get(i).name.equals(des))
    			RF.floatRegisters.get(i).Qi = name;
    	}
    }
    
    public static void overRideIntRF(String name, String des) {
    	for (int i = 0; i < 32; i++) {
    		if (RF.intRegisters.get(i).name.equals(des))
    			RF.intRegisters.get(i).Qi = name;
    	}
    }
    
    public static double getContentFloat(String des, String name) {
    	double result = 0;
    	if (doneEX(name)) {
	    	for (int i = 0; i < 32; i++) {
	    		if (RF.floatRegisters.get(i).Qi.equals(name)) {
	    			result = RF.floatRegisters.get(i).contentDOU;
	    			break;
	    		}
	    	}
    	}
    	System.out.println("RESULT: "+ result+"NAME: "+name);
    	return result;
    }
    
    public static double getContentInt(String des, String name) {
    	double result = 0;
    	if (doneEX(name)) {
	    	for (int i = 0; i < 32; i++) {
	    		if (RF.intRegisters.get(i).Qi.equals(name)) {
	    			result = RF.intRegisters.get(i).contentDOU;
	    			break;
	    		}
	    	}
    	}
    	return result;
    }
    
    public static boolean doneEX(String name) {
    	boolean flag = false;
    	if (name.contains("M")) {
    		for (int i = 0; i < divMulStation.length; i++) {
    			if (divMulStation[i].name.equals(name) && divMulStation[i].executionTime == 0)
    				flag = true;
    		}
    	}
    	if (name.contains("A")) {
    		for (int i = 0; i < addSubStation.length; i++) {
    			if (addSubStation[i].name.equals(name) && addSubStation[i].executionTime == 0)
    				flag = true;
    		}
    	}
    	if (name.contains("L")) {
    		for (int i = 0; i < loadBuffer.length; i++) {
    			if (loadBuffer[i].name.equals(name) && loadBuffer[i].executionTime == 0)
    				flag = true;
    		}
    	}
    	return flag;
    }
    
    public static void printCLK(int i) {
    	System.out.println("CLK: "+i);
    	System.out.println("Div/Mul Station:");
    	
        for (ReservationStation station : divMulStation) {
        	System.out.println("Name: " + station.name);
            System.out.println("Operation: " + station.operation);
            System.out.println("Busy: " + station.busy);
            System.out.println("Vj: " + station.Vj);
            System.out.println("Vk: " + station.Vk);
            System.out.println("Qj: " + station.Qj);
            System.out.println("Qk: " + station.Qk);
            System.out.println("Execution Time: " + station.executionTime);
        }
        
        System.out.println("*************************");
        System.out.println("Add/Sub Station:");
        for (ReservationStation station : addSubStation) {
        	System.out.println("Name: " + station.name);
            System.out.println("Operation: " + station.operation);
            System.out.println("Busy: " + station.busy);
            System.out.println("Vj: " + station.Vj);
            System.out.println("Vk: " + station.Vk);
            System.out.println("Qj: " + station.Qj);
            System.out.println("Qk: " + station.Qk);
            System.out.println("Execution Time: " + station.executionTime);
        }
        
        System.out.println("*************************");
        System.out.println("Load Buffer:");
        for (Buffer buffer : loadBuffer) {
            System.out.println("Name: " + buffer.name);
            System.out.println("Busy: " + buffer.busy);
            System.out.println("Address: " + buffer.address);
            System.out.println("Execution Time: " + buffer.executionTime);
        }
        
//        System.out.println("*************************");
//        System.out.println("Store Buffer:");
//        for (Buffer buffer : storeBuffer) {
//            System.out.println("Name: " + buffer.name);
//            System.out.println("Busy: " + buffer.busy);
//            System.out.println("Address: " + buffer.address);
//            System.out.println("V: " + buffer.v);
//            System.out.println("Q: " + buffer.q);
//            System.out.println("Execution Time: " + buffer.executionTime);
//        }
//       Print contents of RF
       RF.outputRegisterFile();
        
//      Print contents of IU
        //IU.printContents();
  
//      Print contents cache
        cache.printCache();
    }
    
    public static void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("--------------------------------------------------------------------");
            Instruction ins = IU.instructionQueue.poll();
            String op = " ";
            if (ins != null && ins.operation != null) 
                 op = ins.operation;
            if (op.equals("MUL.D") || op.equals("DIV.D")) {
                for (int j = 0; j < divMulStation.length; j++) {
                    if (divMulStation[j].busy == false) {
                        divMulStation[j].operation = ins.operation;
                        divMulStation[j].busy = true;
                        divMulStation[j].ins = ins;
                        divMulStation[j].executionTime = divMulTime;
                        overRideFloatRF(divMulStation[j].name,ins.destRegister);
                        
                        if (searchFloatRF(ins.src1Register)) {
                            divMulStation[j].Vj = RF.readFloatRegister(ins.src1Register);
                        } else {
                            divMulStation[j].Qj = getQiFloat(ins.src1Register);
                        }
                        if (searchFloatRF(ins.src2Register)) {
                            divMulStation[j].Vk = RF.readFloatRegister(ins.src2Register);
                        } else {
                            divMulStation[j].Qk = getQiFloat(ins.src2Register);
                        }
                        if (divMulStation[j].Qj.equals(" ") && divMulStation[j].Qk.equals(" ") && divMulStation[j].executionTime == 0) {
                            EX.execute(divMulStation[j].ins, RF, cache);
                            divMulStation[j].busy = false;
                            break;
                        }
                        else {
                        	if (!divMulStation[j].Qj.equals(" ")) {
                        		divMulStation[j].Vj = getContentFloat(ins.destRegister,divMulStation[j].Qj);
                        	}
                        	if (!divMulStation[j].Qk.equals(" ")) {
                        		divMulStation[j].Vk = getContentFloat(ins.destRegister,divMulStation[j].Qk);
                        	}
                        }
                        
                        if (doneEX(divMulStation[j].Qj)) {
                        	divMulStation[j].Qj = " ";
                        }
                        
                        if (doneEX(divMulStation[j].Qk)) {
                        	divMulStation[j].Qj = " ";
                        }
                        
                        break;
                    }
                    else if (divMulStation[j].busy == true) {
                    	divMulStation[j].executionTime = divMulStation[j].executionTime - 1;
                    }
                }
            }
            else if (op.equals("ADD.D") || op.equals("SUB.D")) {
            	for (int j = 0; j < addSubStation.length; j++) {
                    if (addSubStation[j].busy == false) {
                        addSubStation[j].operation = ins.operation;
                        addSubStation[j].busy = true;
                        addSubStation[j].ins = ins;
                        addSubStation[j].executionTime = divMulTime;
                        overRideFloatRF(addSubStation[j].name,ins.destRegister);
                        
                        if (searchFloatRF(ins.src1Register)) {
                            addSubStation[j].Vj = RF.readFloatRegister(ins.src1Register);
                        } else {
                            addSubStation[j].Qj = getQiFloat(ins.src1Register);
                        }
                        if (searchFloatRF(ins.src2Register)) {
                            addSubStation[j].Vk = RF.readFloatRegister(ins.src2Register);
                        } else {
                            addSubStation[j].Qk = getQiFloat(ins.src2Register);
                        }
                        if (addSubStation[j].Qj.equals(" ") && addSubStation[j].Qk.equals(" ") && addSubStation[j].executionTime == 0) {
                            EX.execute(addSubStation[j].ins, RF, cache);
                            addSubStation[j].busy = false;
                            break;
                        }
                        else {
                        	if (!addSubStation[j].Qj.equals(" ")) {
                        		addSubStation[j].Vj = getContentFloat(ins.destRegister,addSubStation[j].Qj);
                        	}
                        	if (!addSubStation[j].Qk.equals(" ")) {
                        		addSubStation[j].Vk = getContentFloat(ins.destRegister,addSubStation[j].Qk);
                        	}
                        }
                        
                        if (doneEX(addSubStation[j].Qj)) {
                        	addSubStation[j].Qj = " ";
                        }
                        
                        if (doneEX(addSubStation[j].Qk)) {
                        	addSubStation[j].Qj = " ";
                        }
                        
                        break;
                    }
                    else if (addSubStation[j].busy == true) {
                    	addSubStation[j].executionTime = addSubStation[j].executionTime - 1;
                    }
                }
            }
            else if (op.equals("LD")) {
            	for (int j = 0; j < loadBuffer.length; j++) {
            		if (loadBuffer[j].busy == false) {
            			loadBuffer[j].busy = true;
            			loadBuffer[j].ins = ins;
            			loadBuffer[j].address = ins.src1Register;
            			overRideFloatRF(loadBuffer[j].name,ins.destRegister);
            			break;
            		}
            	}
            }
            else if (op.equals("SD")) {
            	for (int j = 0; j < loadBuffer.length; j++) {
            		if (loadBuffer[j].busy == false) {
            			loadBuffer[j].busy = true;
            			loadBuffer[j].ins = ins;
            			loadBuffer[j].address = ins.src1Register;
            			if (!searchFloatRF(ins.src1Register)){
            				loadBuffer[j].v = RF.readFloatRegister(ins.destRegister);
            			}
            			else {
            				loadBuffer[j].q = getQiFloat(ins.src1Register);
            			}
            			break;
            		}
            	}
            }
            else {
                for (int j = 0; j < divMulStation.length; j++) {
                    if (divMulStation[j].busy == true && divMulStation[j].Qj.equals(" ") && divMulStation[j].Qk.equals(" ")) {
                        divMulStation[j].executionTime = divMulStation[j].executionTime - 1;
                        if (divMulStation[j].Qj.equals(" ") && divMulStation[j].Qk.equals(" ") && divMulStation[j].executionTime == 0) {
                            EX.execute(divMulStation[j].ins, RF, cache);
                            divMulStation[j].busy = false;
                        }
                    }
                    else {
                    	if (!divMulStation[j].Qj.equals(" ")) {
                    		divMulStation[j].Vj = getContentFloat(divMulStation[j].ins.destRegister,divMulStation[j].Qj);
                    	}
                    	if (!divMulStation[j].Qk.equals(" ")) {
                    		divMulStation[j].Vk = getContentFloat(divMulStation[j].ins.destRegister,divMulStation[j].Qk);
                    	}
                    }
                    
                    if (doneEX(divMulStation[j].Qj)) {
                    	divMulStation[j].Qj = " ";
                    }
                    
                    if (doneEX(divMulStation[j].Qk)) {
                    	divMulStation[j].Qk = " ";
                    }
                }
                for (int j = 0; j < addSubStation.length; j++) {
                    if (addSubStation[j].busy == true && addSubStation[j].Qj.equals(" ") && addSubStation[j].Qk.equals(" ")) {
                        addSubStation[j].executionTime = addSubStation[j].executionTime - 1;
                        if (addSubStation[j].Qj.equals(" ") && addSubStation[j].Qk.equals(" ") && addSubStation[j].executionTime == 0) {
                            EX.execute(addSubStation[j].ins, RF, cache);
                            addSubStation[j].busy = false;
                        }
                    }
                    else {
                    	if (!addSubStation[j].Qj.equals(" ")) {
                    		addSubStation[j].Vj = getContentFloat(addSubStation[j].ins.destRegister,addSubStation[j].Qj);
                    	}
                    	if (!addSubStation[j].Qk.equals(" ")) {
                    		addSubStation[j].Vk = getContentFloat(addSubStation[j].ins.destRegister,addSubStation[j].Qk);
                    	}
                    }
                    
                    if (doneEX(addSubStation[j].Qj)) {
                    	addSubStation[j].Qj = " ";
                    }
                    
                    if (doneEX(addSubStation[j].Qk)) {
                    	addSubStation[j].Qk = " ";
                    }
                }
                for (int j = 0; j < loadBuffer.length; j++) {
            		if (loadBuffer[j].busy == true) {
            			if (loadBuffer[j].executionTime == 0) {
            				EX.execute(loadBuffer[j].ins, RF, cache);
            				loadBuffer[j].busy = false;
            			}
            			else {
            				loadBuffer[j].executionTime = loadBuffer[j].executionTime - 1;
            			}
            		}
            	}
                for (int j = 0; j < storeBuffer.length; j++) {
                	if (storeBuffer[j].busy == true) {
                		if (storeBuffer[j].executionTime == 0 && storeBuffer[j].q.equals(" ")) {
                			EX.execute(loadBuffer[j].ins, RF, cache);
                		}
                		else if (!storeBuffer[j].q.equals(" ")) {
                			storeBuffer[j].v = getContentFloat(storeBuffer[j].ins.destRegister,storeBuffer[j].q);
                		}
                		loadBuffer[j].executionTime = loadBuffer[j].executionTime - 1;
                	}
                }
            }
            printCLK(i);
        }
    }


    public static void main(String[] args) {
        init();
        run();
    }
}