
public class Cache {
    private double[] cacheArray;
    private int size;

    public Cache(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than 0");
        }

        this.size = size;
        this.cacheArray = new double[size];
        initializeCache();
    }

    private void initializeCache() {
        for (int i = 0; i < size; i++) {
            cacheArray[i] = 0;
        }
    }

    public Object read(int index) {
        validateIndex(index);
        return cacheArray[index];
    }

    public void write(int index, double data) {
        validateIndex(index);
        cacheArray[index] = data;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
    
    public void printCache() {
        System.out.println("Cache Content:");
        for (int i = 0; i < size; i++) {
            System.out.println("Index " + i + ": " + cacheArray[i]);
        }
    }
}
