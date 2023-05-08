import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Yritus {
    private String nimi;
    private LocalDate toimumisaeg;

    public Yritus(String nimi, LocalDate toimumisaeg) {
        this.nimi = nimi;
        this.toimumisaeg = toimumisaeg;
    }

    public String getNimi() {
        return nimi;
    }

    public LocalDate getToimumisaeg() {
        return toimumisaeg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Yritus yritus)) return false;
        return nimi.equals(yritus.nimi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nimi);
    }

    public String toString() {
      return nimi + " "  + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(toimumisaeg);
    }
}
