package com.example.spordiklubifx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Spordivahendid {
    private static List<Spordivahend> spordivahendList = new ArrayList<>();


    public static List<Spordivahend> getSpordivahendList() {
        return spordivahendList;
    }
    public static void loeSpordivahendid(String file) throws Exception {
        spordivahendList = new ArrayList<>();
        File fail = new File(file);
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            while (sc.hasNext()) {
                String[] soned = sc.nextLine().trim().split(", ");
                Spordivahend spordivahend = new Spordivahend(Boolean.parseBoolean(soned[0]), Integer.parseInt(soned[1]), soned[2], Integer.parseInt(soned[3]), Integer.parseInt(soned[4]));
                spordivahendList.add(spordivahend);
            }
        }
    }
}
