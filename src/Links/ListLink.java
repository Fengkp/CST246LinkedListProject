package Links;

public class ListLink {
    public StringList dData;
    public ListLink next;

    public ListLink(StringList dData) {
        this.dData = dData;
    }

    public void display() {
        System.out.print(dData + " ");
    }

}