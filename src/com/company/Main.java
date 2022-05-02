package com.company;

import memory.Memory;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Memory memory;
    public static void main(String[] args) {
        /*memory = new Memory();
        wykonajAlgorytmy();*/
        sterowanieDanymi();
    }

    public static void sterowanieDanymi() {
        Supplier<Integer> changeTypCiagu = () -> {
            System.out.println("0: random");
            System.out.println("1: jednakowa random strona");
            System.out.println("2: w porządku wzrostu");
            System.out.println("3: w porządku wzrostu i na odwrót");
            System.out.println("4: mix poprzednich typów");
            System.out.print("Typ ciągu: ");
            int mode = sc.nextInt();
            return mode;
        };
        System.out.print("Ilość stron: ");
        int iloscStron = sc.nextInt();
        System.out.print("Ilość ramek: ");
        int iloscRamek = sc.nextInt();
        if (memory == null) {
            System.out.println("ciąg mode: ");
            int mode = changeTypCiagu.get();
            memory = new Memory(iloscStron, iloscRamek, mode);
        }
        else {
            memory.setIloscRamek(iloscRamek);
            memory.setIloscStron(iloscStron);
            System.out.println("Czy chcesz zmienić ciąg?\n1.Tak\n2.Nie");
            int opcja = sc.nextInt();
            if (opcja == 1) memory.setCiagMode(changeTypCiagu.get());
        }
        wykonajAlgorytmy();
        System.out.println("Czy chcesz kontynuować?\n1.Tak\n2.Nie");
        int opcja = sc.nextInt();
        if (opcja == 1) sterowanieDanymi();

    }

    private static void wykonajAlgorytmy() {
        System.out.println("FCFS: " + memory.startFCFS());
        System.out.println("OPT: " + memory.startOPT());
        System.out.println("LRU: " + memory.startLRU());
        System.out.println("ALRU: " + memory.startALRU());
        System.out.println("RAND: " + memory.startRAND());
    }
}
