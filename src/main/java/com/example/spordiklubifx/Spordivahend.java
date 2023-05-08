public class Spordivahend extends Spordivahendid{
    private boolean kasLaos;
    private int tagatisraha;
    private String nimi;
    private int laokoht;
    private int esemeEestTasutudTagatisraha;

    public Spordivahend(boolean kasLaos, int tagatisraha, String nimi, int laokoht, int esemeEestTasutudTagatisraha) {
        this.kasLaos = kasLaos;
        this.tagatisraha = tagatisraha;
        this.nimi = nimi;
        this.laokoht = laokoht;
        this.esemeEestTasutudTagatisraha = esemeEestTasutudTagatisraha;
        Spordivahendid.getSpordivahendList().add(this);
    }

    public boolean isKasLaos() {
        return kasLaos;
    }

    public void setKasLaos(boolean kasLaos) {
        this.kasLaos = kasLaos;
    }

    public int getTagatisraha() {
        return tagatisraha;
    }

    public void setTagatisraha(int tagatisraha) {
        this.tagatisraha = tagatisraha;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getLaokoht() {
        return laokoht;
    }

    public void setLaokoht(int laokoht) {
        this.laokoht = laokoht;
    }

    public int getEsemeEestTasutudTagatisraha() {
        return esemeEestTasutudTagatisraha;
    }

    public void setEsemeEestTasutudTagatisraha(int esemeEestTasutudTagatisraha) {
        this.esemeEestTasutudTagatisraha = esemeEestTasutudTagatisraha;
    }
}
