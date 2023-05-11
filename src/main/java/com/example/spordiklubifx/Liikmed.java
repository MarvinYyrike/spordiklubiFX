package com.example.spordiklubifx;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Liikmed {
  private static List<Isik> liikmed = new ArrayList<>();

  public Liikmed() {
  }

  public static void loeLiikmed(String file) throws Exception {
    // nullime igaks juhuks ära seisu kui mitu korda loetakse või array on väärtustamata
    liikmed = new ArrayList<>();
    File fail = new File(file);
    try (Scanner sc = new Scanner(fail, "UTF-8")) {
      while (sc.hasNext()) {
        String[] soned = sc.nextLine().trim().split(", ");
        //kontroll, et failist loetav rida on sobival kujul
        if (soned.length != 4) {
          throw new IllegalStateException("kontod.txt fail ei ole korrektne");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //loob failist loodud andmete põhjal  uue isiku ja lisab selle liikmete listi
        Isik isik = new Isik(soned[0], soned[1], LocalDate.parse(soned[2], formatter), soned[3]);
        liikmed.add(isik);
      }
    }
  }
//kirjutab liikmed nimekirjas olevad isikud programmile ette antud faili
  public static void salvestaLiikmed(String failiNimi) throws Exception {
    File fail = new File(failiNimi);
    try (FileWriter writer = new FileWriter(fail)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      for (Isik isik : liikmed) {
        writer.write(isik.getEesnimi() + ", "
            + isik.getPerenimi() + ", "
            + isik.getSynniaeg().format(formatter) + ", "
            + isik.getIsikukood() + "\n");
      }
    }
  }

  private static boolean onLiige(Isik isik) {
    for (Isik i : liikmed) {
      if (isik.equals(i)) {
        return true;
      }
    }
    return false;
  }

  public static void lisaLiige(Isik isik) {
      liikmed.add(isik);
  }

  public static Isik otsiIsikTäisnimeJärgi(String nimi) {
    for (Isik isik : liikmed) {
      if (nimi.contains(isik.getEesnimi()) && nimi.contains(isik.getPerenimi())) {
        return isik;
      }
    }
    return null;
  }


  public static void kuvaLiikmed() {
    for (int i = 0; i < liikmed.size(); i++) {
      System.out.println(i + 1 + ". liige on: " + liikmed.get(i));
    }
  }

  public static boolean onLiikmeid() {
    return !liikmed.isEmpty();
  }

  public static Isik otsiIsikNumbriJargi(int number) {
    if (number < 0 || number - 1 > liikmed.size()) {
      return null;
    }
    return liikmed.get(number - 1);
  }

  public static int viimaneNr() {
    return liikmed.size();
  }

  public static List<Isik> getLiikmed() {
    return liikmed;
  }

}
