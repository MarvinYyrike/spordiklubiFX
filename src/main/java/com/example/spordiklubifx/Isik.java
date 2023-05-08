import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Isik {
    private String eesnimi;
    private String perenimi;
    private LocalDate synniaeg;
    private String isikukood;

    public Isik(String eesnimi, String perenimi, LocalDate synniaeg, String isikukood) {
        this.eesnimi = eesnimi;
        this.perenimi = perenimi;
        this.synniaeg = synniaeg;
        this.isikukood = isikukood;
        Liikmed.getLiikmed().add(this);
    }



    public void laenutab(Spordivahend spordivahend, LocalDate kuupäev, int tasutudTagatisRaha) {
        if(spordivahend.isKasLaos() && tasutudTagatisRaha >= spordivahend.getTagatisraha()){
            Laenutamine laenutamine = new Laenutamine(this, spordivahend, kuupäev);
            laenutamine.getLaenutamineList().add(laenutamine);
            spordivahend.setKasLaos(false);
            spordivahend.setEsemeEestTasutudTagatisraha(tasutudTagatisRaha);
            System.out.println("Hästi, too ese nädala pärast tagasi");
        } else if (!spordivahend.isKasLaos()){
            System.out.println("Eset ei ole laos");
        } else if(tasutudTagatisRaha < spordivahend.getTagatisraha()){
            System.out.println("Tasutud tagatisraha on liiga väike");
        }
        else {
            System.out.println("Midagi läks valesti, proovi uuesti...");
        }
    }

    public void tagastab(Spordivahend spordivahend) {

        if (spordivahend.isKasLaos()) {
            System.out.println("See ese on juba laos, kust sa selle said???");
        } else {

        //randomiga valime laokoha (0-99), tsekime kas see on vaba ning kui on vaba, siis paneme laokohale
        int laokoht = (int) (Math.random() * 100);
        List<Spordivahend> spordivahendList = Spordivahendid.getSpordivahendList();
        //boolean containsAge35 = people.stream().anyMatch(person -> person.getAge() == 35); chat-GPT abil küsitud näide
        boolean kasLaokohtKinni = spordivahendList.stream().anyMatch(spordivahend_x -> spordivahend_x.getLaokoht() == laokoht);

        if (kasLaokohtKinni) {
            tagastab(spordivahend);
        } else {
            spordivahend.setKasLaos(true);
            System.out.println("Tagastame tagatisraha, " + spordivahend.getEsemeEestTasutudTagatisraha() + " eurot!");
            spordivahend.setEsemeEestTasutudTagatisraha(0);
            spordivahend.setLaokoht(laokoht);
            System.out.println("Panin laokohale: " + laokoht);
        }
    }
    }

    public String getEesnimi() {
        return eesnimi;
    }

    public void setEesnimi(String eesnimi) {
        this.eesnimi = eesnimi;
    }

    public String getPerenimi() {
        return perenimi;
    }

    public void setPerenimi(String perenimi) {
        this.perenimi = perenimi;
    }

    public LocalDate getSynniaeg() {
        return synniaeg;
    }

    public void setSynniaeg(LocalDate synniaeg) {
        this.synniaeg = synniaeg;
    }

    public String getIsikukood() {
        return isikukood;
    }

    public void setIsikukood(String isikukood) {
        this.isikukood = isikukood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Isik isik)) return false;
        return Objects.equals(isikukood, isik.isikukood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isikukood);
    }

    @Override
    public String toString() {
        return eesnimi + " " + perenimi + " , sündinud: " + synniaeg;
    }
}
