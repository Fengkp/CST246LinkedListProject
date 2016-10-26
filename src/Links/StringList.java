package Links;

import java.util.Random;

public class StringList {
    public StringLink first;
    public StringLink last;
    public int counter;

    public StringList() {
        first = null;
        last = null;
        counter = -1;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void insertLast(String dData) {
        StringLink newLink = new StringLink(dData);
        if (isEmpty())
            first = newLink;
        else
            last.next = newLink;
        last = newLink;
        counter++;
    }

    public String generateParagraph(int paragraphLength) {
        Random rand = new Random();
        StringLink current;
        String paragraph = "";

        for (int k = 0; k < paragraphLength; k++) {
            int randomNum = rand.nextInt((counter - 1) + 1) + 1;
            current = first;

            for (int i = 0; i <= counter; i++) {
                if (i == randomNum) {
                    paragraph += current.dData;
                    paragraph += " ";
                    //System.out.print(current.dData + " ");
                    break;
                }
                current = current.next;
            }
        }
        return paragraph;
    }

    public void display() {
        StringLink current = first;
        while (current != null) {
            current.display();
            current = current.next;
        }
        System.out.println();
    }
}
