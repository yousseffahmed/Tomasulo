
public class ReservationStation {
	public String name;
	public String operation;
	public boolean busy;
	public double Vj;
	public double Vk;
	public String Qj;
    public String Qk;
    public int result;
    public int executionTime;
    public Instruction ins;

    public ReservationStation(String name, String operation, boolean busy, double Vj, double Vk, String Qj, String Qk, int executionTime) {
    	this.name = name;
        this.operation = operation;
        this.busy = busy;
        this.Vk = Vk;
        this.Vj = Vj;
        this.Qk = Qk;
        this.Qj = Qj;
        this.executionTime = executionTime;
    }
     
}
