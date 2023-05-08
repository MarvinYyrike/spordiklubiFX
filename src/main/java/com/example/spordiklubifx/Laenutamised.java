import java.util.ArrayList;
import java.util.List;

//Antud klass talletab kogu laenutamiste ajaloo
public class Laenutamised {
    private List<Laenutamine> laenutamineList;

    public Laenutamised() {
        this.laenutamineList = new ArrayList<>();
    }
    public List<Laenutamine> getLaenutamineList() {
        return laenutamineList;
    }
}
