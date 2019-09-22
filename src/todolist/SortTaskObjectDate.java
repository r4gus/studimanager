package todolist;

import java.time.LocalDate;

public class SortTaskObjectDate implements Comparable<SortTaskObjectDate> {

    private int iD;
    private LocalDate localDate;

    public SortTaskObjectDate(int iD, LocalDate localDate) {
        this.iD = iD;
        this.localDate = localDate;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public int compareTo(SortTaskObjectDate o) {

        int compare =  this.getLocalDate().compareTo(o.getLocalDate()) ;
        return compare;
    }
}
