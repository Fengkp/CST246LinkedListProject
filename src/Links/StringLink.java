package Links;

public class StringLink {
    public String dData;
    public StringLink next;

    public StringLink(String word) {
        dData = word;
    }

    public void display() {
        System.out.print(dData + " ");
    }

    public String toString() {
        return dData;
    }

}
