import java.util.Objects;
//klassi alusel luuakse uus isend osalemine, mis ühendab isikuid ja üritusi.
public class Osalemine {
    private Isik isik;
    private Yritus yritus;

    public Osalemine(Isik isik, Yritus yritus) {
        this.isik = isik;
        this.yritus = yritus;
    }

    public Isik getIsik() {
        return isik;
    }

    public Yritus getYritus() {
        return yritus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Osalemine osalemine)) return false;
        return Objects.equals(isik, osalemine.isik) && Objects.equals(yritus, osalemine.yritus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isik, yritus);
    }
}
