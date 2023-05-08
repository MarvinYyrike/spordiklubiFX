import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Yritused {
  private static List<Yritus> yritused = new ArrayList<>();

  public Yritused() {
  }
  //loeb failist ürituste infot
  public static List<Yritus> loeYritused(String file) throws Exception {
    yritused = new ArrayList<>();
    File fail = new File(file);
    try (Scanner sc = new Scanner(fail, "UTF-8")) {
      while (sc.hasNext()) {
        //eraldab ürituste infoga sõned ja paneb need õigesse vormingusse
        String[] soned = sc.nextLine().trim().split(";");
        yritused.add(new Yritus(soned[0], LocalDate.parse(soned[1])));
      }
    }
    return yritused;
  }
  //meetod otsib asukohanumbri järgi ürituse
  public static Yritus otsiNumbriJargi(int number) {
    if (number < 0 || number - 1 > yritused.size()) {
      return null;
    }
    return yritused.get(number - 1);
  }

  public static int viimaneNr() {
    return yritused.size();
  }

  //meetod otsib ürituse nime järgi ürituse
  public static Yritus otsiNimeKaudu(String nimi) {
    for (Yritus yritus : yritused) {
      if (yritus.getNimi().equalsIgnoreCase(nimi)) {
        return yritus;
      }
    }
    return null;
  }
  
//meetod kuvab ürituste nimekirja koos järjekorranumbritega
  public static void kuvaYritused() {
    for (int i = 0; i < yritused.size(); i++) {
      System.out.println(i + 1 + ". üritus on: " + yritused.get(i));
    }
  }

}
