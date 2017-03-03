/**
 * Created by Katerina Intzevidou on 25-Feb-17.
 * Email: <aintzevi@csd.auth.gr> <intz.katerina@gmail.com>
 */
public class SNP {
    // Unique id
    private int SNPid;
    // Rank of SNP for a specific ranking system
    private int SNPrank;
    // Grade of the SNP (in case we use grading to aggregate, rather than just ranking)
    private float SNPgrading;

    // Class constructor
    public SNP(int SNPid, int SNPrank, float SNPgrading) {
        this.SNPid = SNPid;
        this.SNPrank = SNPrank;
        this.SNPgrading = SNPgrading;
    }

    public int getSNPid() {
        return SNPid;
    }

    public void setSNPid(int SNPid) {
        this.SNPid = SNPid;
    }

    public int getSNPrank() {
        return SNPrank;
    }

    public void setSNPrank(int SNPrank) {
        this.SNPrank = SNPrank;
    }

    public float getSNPgrading() {
        return SNPgrading;
    }

    public void setSNPgrading(float SNPgrading) {
        this.SNPgrading = SNPgrading;
    }
}
