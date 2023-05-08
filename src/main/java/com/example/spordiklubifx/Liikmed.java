import java.util.ArrayList;
import java.util.List;

public class Liikmed {
  private static List<Isik> liikmed = new ArrayList<>();

  public Liikmed() {
  }

  private static boolean onLiige(Isik isik) {
    for (Isik i : liikmed) {
      if (isik.equals(i)) {
        return true;
      }
    }
    return false;
  }

  //Tuleviku arenduseks
  /*
  public static void lisaLiikmeks(Isik isik) {
    if (onLiige(isik)) {
      liikmed.add(isik);
    }
  }

   */

  //Tuleviku jaoks äkki vaja
  /*
  public static Isik otsiIsikEesnimega(String isikEesnimi) {
    for (Isik isik : liikmed) {
      if (isik.getEesnimi().equalsIgnoreCase(isikEesnimi)) {
        return isik;
      }
    }
    return null;
  }

   */

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

  public static void setLiikmed(List<Isik> liikmed) {
    Liikmed.liikmed = liikmed;
  }
}
