import java.time.LocalDate;

//Antud klass hoiab ühte laenutamise objekti (üks inimene laenutab ühe eseme)
public class Laenutamine extends Laenutamised{
    private Isik isik;
    private Spordivahend spordivahend;
    private LocalDate laenutamisKuupäev;

    public Laenutamine(Isik isik, Spordivahend spordivahend, LocalDate laenutamisKuupäev) {
        this.isik = isik;
        this.spordivahend = spordivahend;
        this.laenutamisKuupäev = laenutamisKuupäev;

    }
    //Possible to add laenutus for future developement.
    /*
    public void addLaenutus(Laenutamine laenutamine){
        laenutamine.getLaenutamineList().add(laenutamine);
    }

     */
}
