import java.io.Serializable;

public class SerializableClass implements Serializable {
    @Save
    private final int mask;
    private Character ch;
    @Save
    private String str;
    @Save
    private int i;
    private long l;

    public SerializableClass() {
        this.mask= 0b1101;
    }

    public SerializableClass(int mask, String str, int i, long l) {
        this.mask= mask;
        this.str = str;
        this.i = i;
        this.l = l;
    }

    @Override
    public String toString() {
        return "SerializableClass{" + "mask=" + mask + ", ch=" + ch + ", str='" + str + '\'' + ", i=" + i + ", l=" + l + '}';
    }
}
