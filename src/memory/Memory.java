package memory;

import java.util.ArrayList;
import java.util.Random;

public class Memory {
    private ArrayList<Page> ramki = new ArrayList<>();
    private int iloscRamek;
    private int iloscStron;
    private Page[] ciag = new Page[10000];
    public Memory(int iloscStron, int iloscRamek, int mode) {
        this.iloscRamek = iloscRamek;
        this.iloscStron = iloscStron;
        generujCiag(mode);
    }
    public Memory() {
        iloscStron = 5;
        iloscRamek = 4;
        generujPrzyklad();
    }
    public int startFCFS() {
        int iloscBrakowStron = 0;
        int oldestPageIndex = 0;
        for (Page page : ciag) {
            if (!ramki.contains(page)) {
                if (ramki.size() < iloscRamek) {
                    ramki.add(page);
                }
                else {
                    if (oldestPageIndex == iloscRamek) oldestPageIndex = 0;
                    ramki.set(oldestPageIndex, page);
                    oldestPageIndex++;
                }
                iloscBrakowStron++;
            }
        }
        ramki.clear();
        return iloscBrakowStron;
    }

    public int startOPT() {
        int iloscBrakowStron = 0;
        for (int currPageIndex = 0; currPageIndex < ciag.length; currPageIndex++) {
            Page page = ciag[currPageIndex];
            if (!ramki.contains(page)) {
                if (ramki.size() < iloscRamek) {
                    ramki.add(page);
                }
                else {
                    int index = 0, maxDlugosc = 0;
                    for (int j = 0; j < iloscRamek; j++) {
                        int dlugosc = 0;
                        for (int futurePageIndex = currPageIndex + 1; futurePageIndex < ciag.length; futurePageIndex++) {
                            if (ramki.get(j).equals(ciag[futurePageIndex])) break;
                            dlugosc++;
                        }
                        if (maxDlugosc < dlugosc) {
                            index = j;
                            maxDlugosc = dlugosc;
                        }
                    }
                    ramki.set(index, page);
                }
                iloscBrakowStron++;
            }
        }
        ramki.clear();
        return iloscBrakowStron;
    }

    public int startLRU() {
        int iloscBrakowStron = 0;
        for (int currPageIndex = 0; currPageIndex < ciag.length; currPageIndex++) {
            Page page = ciag[currPageIndex];
            if (!ramki.contains(page)) {
                if (ramki.size() < iloscRamek) {
                    ramki.add(page);
                }
                else {
                    int index = 0, maxDlugosc = 0;
                    for (int j = 0; j < iloscRamek; j++) {
                        int dlugosc = 0;
                        for (int futurePageIndex = currPageIndex - 1; futurePageIndex >= 0; futurePageIndex--) {
                            if (ramki.get(j).equals(ciag[futurePageIndex])) break;
                            dlugosc++;
                        }
                        if (maxDlugosc < dlugosc) {
                            index = j;
                            maxDlugosc = dlugosc;
                        }
                    }
                    ramki.set(index, page);
                }
                iloscBrakowStron++;
            }
        }
        ramki.clear();
        return iloscBrakowStron;
    }

    public int startALRU() {
        int iloscBrakowStron = 0;
        int i = 1;
        for (Page page : ciag) {
            if (ramki.size() < iloscRamek) {
                iloscBrakowStron++;
                ramki.add(page);
            }
            else {
                boolean isAdded = false;
                for (Page page1 : ramki) {
                    if (page.equals(page1)) {
                        page1.bitOdwolania = 1;
                        isAdded = true;
                        break;
                    }
                    page1.bitOdwolania = 0;
                }
                if (!isAdded) {
                    ramki.remove(0);
                    ramki.add(page);
                    iloscBrakowStron++;
                }
            }
            i++;
        }
        ramki.clear();
        return iloscBrakowStron;
    }

    public int startRAND() {
        int iloscBrakowStron = 0;
        for (Page page : ciag) {
            if (!ramki.contains(page)) {
                if (ramki.size() < iloscRamek) {
                    ramki.add(page);
                }
                else {
                    int pageIndexToRemove = new Random().nextInt(0, iloscRamek);
                    ramki.set(pageIndexToRemove, page);
                }
                iloscBrakowStron++;
            }
        }
        ramki.clear();
        return iloscBrakowStron;
    }

    private void generujPrzyklad() {
        ciag = new Page[12];
        for (int i = 0; i < 4; i++) {
            ciag[i] = new Page(i + 1);
        }
        ciag[4] = new Page(1);
        ciag[5] = new Page(2);
        ciag[6] = new Page(5);
        ciag[7] = new Page(1);
        ciag[8] = new Page(2);
        ciag[9] = new Page(3);
        ciag[10] = new Page(4);
        ciag[11] = new Page(5);
    }
    //надо как-то поумнее сделать
    private void generujCiag(int c) {
        int length = ciag.length;
        switch (c) {
            case 0: {
                //random
                for (int i = 0; i < length; i++) {
                    ciag[i] = new Page(new Random().nextInt(1,iloscStron + 1));
                }
                break;
            }
            case 1: {
                //x, x, x, x, x, x...
                int number = new Random().nextInt(1,iloscStron + 1);
                for (int i = 0; i < length; i++) {
                    ciag[i] = new Page(number);
                }
                break;
            }
            case 2: {
                //1, 2, 3, 4, 1, 2, 3, 4, 1...
                for (int i = 0, j = 1; i < length; i++, j++) {
                    ciag[i] = new Page(j);
                    if (j >= iloscStron) j = 0;
                }
                break;
            }
            case 3: {
                boolean isIncreasing = true;
                //1, 2, 3, 4, 4, 3, 2, 1, 1, 2...
                for (int i = 0, j = 1; i < length; i++) {
                    ciag[i] = new Page(j);
                    if (isIncreasing) {
                        if (j >= iloscStron) isIncreasing = false;
                        else j++;
                    }
                    else {
                        if (j <= 1) isIncreasing = true;
                        else j--;
                    }
                }
                break;
            }
            case 4: {
                //mix
                int a = ciag.length/4;
                for (int i = 0; i < a; i++) {
                    ciag[i] = new Page(new Random().nextInt(1,iloscStron + 1));
                }
                int number = new Random().nextInt(1,iloscStron + 1);
                for (int i = a; i < a*2; i++) {
                    ciag[i] = new Page(number);
                }
                for (int i = a*2; i < a*3; i++) {
                    ciag[i] = new Page(new Random().nextInt(1,iloscStron + 1));
                }
                for (int i = a*3, j = 0; i < ciag.length; i++, j++) {
                    ciag[i] = new Page(j);
                    if (j >= iloscStron) j = -1;
                }
                break;
            }
            case 5: {

            }
        }
    }
    /*private void generujCiag() {
        int a = ciag.length/4;
        for (int i = 0; i < a; i++) {
            ciag[i] = new Page(new Random().nextInt(1,iloscStron + 1));
        }
        int number = new Random().nextInt(1,iloscStron + 1);
        for (int i = a; i < a*2; i++) {
            ciag[i] = new Page(number);
        }
        for (int i = a*2; i < a*3; i++) {
            ciag[i] = new Page(new Random().nextInt(1,iloscStron + 1));
        }
        for (int i = a*3, j = 0; i < ciag.length; i++, j++) {
            ciag[i] = new Page(j);
            if (j >= iloscStron) j = -1;
        }
    }*/
    /*private void generujCiag() {
        for (int i = 0, j = 0; i < ciag.length; i++, j++) {
            ciag[i] = new Page(j);
            if (j >= iloscStron) j = -1;
        }
    }*/
    public void setIloscRamek(int iloscRamek) {
        this.iloscRamek = iloscRamek;
    }

    public void setIloscStron(int iloscStron) {
        this.iloscStron = iloscStron;
    }

    public void setCiagMode(int mode) {
        generujCiag(mode);
    }
}
