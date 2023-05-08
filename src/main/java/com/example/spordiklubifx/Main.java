import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    //Loon mõned spordiesemed, mis on spordiklubil laos olemas ja on võimalik laenutada
    Spordivahendid.loeSpordivahendid("spordivahendid.txt");

    Yritused.loeYritused("yritused.txt");
    Osalemised osalemised = new Osalemised();
    Isik aktiivneIsik = null;
    Yritus aktiivneYritus = null;

    //test
    // testiYritusi();
    //Scanneriga küsi infot osalemiste ja ürituste kohta.
    //Scanneriga sisesta uus üritusel osalemine.
    // Liides, kus saab valida, mida sisestada soovitakse.
    int valik = 0;
    while (valik != 4) {
      Scanner scan = new Scanner(System.in);
      System.out.println("Oled sisse logitud kui: " + aktiivneIsik);
      System.out.println("Mida soovid teha?");
      System.out.println();
      System.out.println("1 - Sisestada üritusel osalemist.");
      //System.out.println("2 - Otsida üritusi, kus isik on osalenud.");
      System.out.println("3 - Vaadata aktiivse ürituse tulemusi");
      System.out.println("4 - Väljuda.");
      System.out.println("5 - Näha laenutatavaid esemeid");
      System.out.println("6 - laenuta ese: (kõigepealt vajuta 6 (enne pead olema spordivahendeid vaadanud)");
      System.out.println("7 - tagasta ese");
      System.out.println("8 - loo uus konto (uue isiku tekitamine programmi)");
      System.out.println("9 - vaheta kontot");

      valik = scan.nextInt();
      switch (valik) {
        case 1:
          if (aktiivneIsik == null) {
            aktiivneIsik = looVoiValiIsik();
            if (aktiivneIsik == null) break;
          }
          //uus ürituse ja isiku ühendus
          aktiivneYritus = valiYritus();
          osalemised.lisaOsalemine(aktiivneIsik, aktiivneYritus);
          System.out.println("Üritusele: " + aktiivneYritus);
          System.out.println("Isikule:" + aktiivneIsik.getEesnimi() + " " + aktiivneIsik.getPerenimi());
          System.out.println("on osalemine edukalt lisatud!");
          break;
        /*case 2:
          //läheb isiku üritusi otsima
          //TODO Edaspidi arendamiseks.
          break;
         */
        case 3:
          if(aktiivneYritus == null) {
            System.out.println("Üritust pole valitud!");
            // TODO ilmselt peaks eraldi saama aktiivset üritust valida ka
            break;
          }
          List<Isik> tulemused = osalemised.tulemusteArvutamine(aktiivneYritus);
          for (int i = 0; i < tulemused.size(); i++) {
            System.out.println(i + 1 + ". koha sai: " + tulemused.get(i));
          }
          //läheb üritusel osalenuid otsima
          break;
        case 4:
          System.out.println("Väljusid süsteemist.");
          break;
        case 5:
          vaataSpordivahendeid();
          break;
        case 6:
          //vaataSpordivahendeid();
          Spordivahend valitudSpordivahend1 = otsiSpordivahend();
          if (valitudSpordivahend1 == null) {
            break;
          }
          if (aktiivneIsik == null) {
            aktiivneIsik = looVoiValiIsik();
            if (aktiivneIsik == null) break;
          }
          System.out.println("Selle eseme tagatisraha on " + valitudSpordivahend1.getTagatisraha() + ". " + "(Sisesta see vähemalt nii suur number konsooli)");
          Scanner scannerTagatisRaha = new Scanner(System.in);
          int tasutudTagatisRaha = scannerTagatisRaha.nextInt();
          aktiivneIsik.laenutab(valitudSpordivahend1,LocalDate.now(),tasutudTagatisRaha);

          break;
        case 7:
          // tagasta ese
          System.out.println("Tere " + aktiivneIsik);
          //looVoiValiIsik();
          //System.out.println("Mida tagastad:");
          Spordivahend valitudSpordivahend2 = otsiSpordivahend();
          aktiivneIsik.tagastab(valitudSpordivahend2);

          break;
        case 8:
          //loo uus isik programmi
          aktiivneIsik = looIsik();
          break;
        case 9:
          //vaheta isikut
          aktiivneIsik = looVoiValiIsik();
          break;


        default:
          System.out.println("Sisestamisel läks midagi valesti, proovi palun uuesti.");

      }
    }


  }

  private static Yritus valiYritus() {
    System.out.println("Vali üritus:");
    Yritused.kuvaYritused();

    System.out.println("Sisesta ürituse number vahemikus (1-" + Yritused.viimaneNr() + ")");

    int number;
    Yritus yritus = null;
    while (yritus == null) {
      Scanner scan = new Scanner(System.in);
      number = scan.nextInt();
      yritus = Yritused.otsiNumbriJargi(number);
      if (yritus == null) {
        System.out.println("Sellise numbriga üritust ei leitud, palun proovi uuesti");
      }
    }
    return yritus;
  }


  private static Isik looVoiValiIsik() {
    if (!Liikmed.onLiikmeid()) {
      System.out.println("Kuna klubil pole ühtegi liiget, siis palun sisesta esimene:");
      return looIsik();
    }
    System.out.println("Vali isik klubi liikmete seast:");
    Liikmed.kuvaLiikmed();

    System.out.println("Sisesta liikme number vahemikus (1-" + Liikmed.viimaneNr() + ")");
    int number;
    Isik aktiivneIsik = null;
    while (aktiivneIsik == null) {
      Scanner scan = new Scanner(System.in);
      number = scan.nextInt();
      aktiivneIsik = Liikmed.otsiIsikNumbriJargi(number);
      if (aktiivneIsik == null) {
        System.out.println("Sellise numbriga isikut ei leitud, palun proovi uuesti");
      }
    }
    return aktiivneIsik;
  }

  //Seda pole siis vaja, kuna looVÕiValiIsik teeb pm sama aga paremini
  /*
  public static Isik otsiIsikEesnimega(Liikmed liikmed) {
    System.out.println("Sisesta, millist isikut otsid");
    Scanner scanner = new Scanner(System.in);
    String isikEesnimi = scanner.next();
    System.out.println("Otsin isikut:: " + isikEesnimi);

    Isik valitudIsik = Liikmed.otsiIsikEesnimega(isikEesnimi);
    if (valitudIsik == null) {
      System.out.println("Sellist isikut ei ole...");
    }
    return valitudIsik;
  }

   */

  public static Spordivahend otsiSpordivahend() {
    System.out.println("Sisesta, mida tahad laenutada/tagastada");
    Scanner scanner = new Scanner(System.in);
    String spordivahendiNimi = scanner.next();
    System.out.println("Võtan eseme: " + spordivahendiNimi);
    List<Spordivahend> spordivahendid = Spordivahendid.getSpordivahendList();
    Spordivahend valitudSpordivahend = null;
    for (Spordivahend spordivahend : spordivahendid) {
      if (spordivahendiNimi.equalsIgnoreCase(spordivahend.getNimi())) {
        valitudSpordivahend = spordivahend;
      }
    }
    if (valitudSpordivahend == null) {
      System.out.println("Sellist eset ei ole...");
    }
    return valitudSpordivahend;
  }


  public static Isik looIsik() {
    System.out.println("Eesnimi:");
    Scanner scannerEesnimi = new Scanner(System.in);
    String eesnimi = scannerEesnimi.next();

    System.out.println("Perenimi:");
    Scanner scannerPerenimi = new Scanner(System.in);
    String perenimi = scannerPerenimi.next();

    System.out.println("Synniaeg yyyy-mm-dd");
    LocalDate synniaeg = null;
    while (synniaeg == null) {
      Scanner scannerSynniaeg = new Scanner(System.in);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      try {
        synniaeg = LocalDate.parse(scannerSynniaeg.next(), formatter);
      } catch (DateTimeException e) {
        System.out.println("Vigane sünniaeg, palun sisesta uuesti kujul yyyy-mm-dd");
      }
    }

    System.out.println("Isikukood:");
    Scanner scannerIsikukood = new Scanner(System.in);
    String isikokood = scannerIsikukood.next();

    //Liikmed.lisaLiikmeks(isik); //pole vaja, kuna isiku loomisel (konstruktoris) luuakse kohe ka uus liige Liikmed listi
    return new Isik(eesnimi, perenimi, synniaeg, isikokood);
  }

  private static void vaataSpordivahendeid() {
    //To print the Spordivahendid list names
    List<Spordivahend> list = Spordivahendid.getSpordivahendList();
    for (Spordivahend spordivahend : list) {
      System.out.println(spordivahend.getNimi());
    }
  }

  /* //kasutasiem testimiseks
  private static void testiYritusi() {
    List<Yritus> yritused = new ArrayList<>();
    yritused.add(new Yritus("Paide-Türi rahvajooks 2023", LocalDate.parse("2023-09-24")));
    yritused.add(new Yritus("Viljandi järvejooks 2023", LocalDate.parse("2023-05-01")));

    List<Isik> isikud = new ArrayList<>();
    // Siin peaks see olema veel isik, aga hetkel me ei saa seda tüüpi klassi luua
    isikud.add(new Isik("Pavel", "Loskutov", LocalDate.parse("1963-12-02"), "36912021234"));
    isikud.add(new Isik("Kirke", "Kuusik", LocalDate.parse("1988-11-15"), "48811152714"));
    isikud.add(new Isik("Kait", "Kuusik", LocalDate.parse("1986-02-02"), "38601022764"));

    Osalemised osalemised = new Osalemised();
    osalemised.lisaOsalemine(isikud.get(0), yritused.get(0));
    osalemised.lisaOsalemine(isikud.get(0), yritused.get(1));
    osalemised.lisaOsalemine(isikud.get(1), yritused.get(1));
    osalemised.lisaOsalemine(isikud.get(2), yritused.get(1));

    List<Isik> tulemused = osalemised.tulemusteArvutamine(yritused.get(1));// Viljandi järvejooksu tulemused randomiga
    for (int i = 0; i < tulemused.size(); i++) {
      System.out.println(i + 1 + ". koha sai: " + tulemused.get(i));
    }
  }

   */
}
