
public class Buffer {
	public String name;
    public boolean busy;
    public String address;
    public int executionTime;
    public Instruction ins;
    public double v;
    public String q;

    public Buffer(String name, boolean busy, String address, int executionTime) {
    	this.name = name;
        this.busy = busy;
        this.address = address;
        this.executionTime = executionTime;
    }
}