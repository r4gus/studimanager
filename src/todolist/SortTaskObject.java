package todolist;

public class SortTaskObject implements Comparable<SortTaskObject> {

   private int iD;
   private String heading;

   public SortTaskObject(int iD, String heading){

       this.heading = heading;
       this.iD = iD;
   }


    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public int compareTo(SortTaskObject sortTaskObject) {

       int compare =  this.getHeading().compareTo(sortTaskObject.getHeading()) ;
       return compare;
    }
}
