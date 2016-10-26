package Links;

import java.util.Random;

public class LinkList {
    public ListLink first;
    public ListLink last;
    private ListLink current;
    public int counter;

    public LinkList() {
        first = null;
        last = null;
        counter = -1;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void insertLast(StringList dData) {
        ListLink newLink = new ListLink(dData);
        if (isEmpty())
            first = newLink;
        else
            last.next = newLink;
        last = newLink;
        counter++;
    }

    public boolean find(String key) {
        this.current = first;

        while (!this.current.dData.first.dData.equals(key)) {
            if (this.current.next == null)
                return false;
            else
                this.current = this.current.next;
        }

        return true;
    }

    public ListLink getRandomWord() {
        Random rand = new Random();
        this.current = first;
        int randomNum = rand.nextInt((counter - 1) + 1) + 1;

        for (int i = 0; i <= counter; i++) {
            if (i == randomNum)
                return this.current;
            this.current = this.current.next;
        }
        return null;
    }

    public ListLink getLink(String key) {
        if(this.find(key))
            return this.current;
        else return null;
    }
}
