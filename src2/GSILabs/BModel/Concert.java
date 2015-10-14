/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.BModel;


import java.util.Date;

/**
 *
 * @author linux1
 */
public class Concert implements ImpermanentEvent,Comparable {
    
    private Location location; //the location of the concert
    private Performer performer; // the performer of the concert
    private String name; // name associated with the concert
    private Date date; // the date the concert takes place, also the start date
    
    /**
     * Constructor of a concert
     * @param l the Location
     * @param p the Performer
     * @param n the Name
     * @param d the Date
     */
    public Concert(Location l,Performer p,String n,Date d){
        this.location=l;
        this.performer=p;
        this.name=n;
        this.date=d;
    }

    /**
     * Gets concert's start date
     * @return Date
     */
    @Override
    public Date getStartDate() {
        return this.date;

    }
    /**
     * Gets concert's location
     * @return Location
     */
    @Override
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets concert's name
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets concert's dates
     * @return Date[]
     */
    @Override
    public Date[] getDates() {
        Date[] dates=null;
        dates[0]=this.date;
        return dates;
    }

    /**
     * Checks if the perfomer is in the concert
     * @param p
     * @return boolean, true if the performer is in the concert
     */
    @Override
    public boolean involvesPerformer(Performer p) {
        

        if(p.equals(this.performer))
            return true;

        if(this.performer instanceof Collective){// if the concert involves a collective
            // we search all of the artist in the collective
            Collective c = (Collective)p;
            Performer[] perfs = c.getComponents();
            int i=0;
            int l=perfs.length;
            while(i<l){
                if(p.equals(perfs[i])){
                    return true;
                }
                i++;
            }
        }
        return false;
}

    /**
     * Gets the performers of the concert
     * @return Performer[]
     */
    @Override
    public Performer[] getPerformers() {
        
        if(this.performer instanceof Collective){//si es un collectivo
            Collective c=(Collective)this.performer;
            return c.getComponents();
        }else{
            Performer[] p=new Performer[1];
            p[0]=this.performer;
            return p;
        }
 }

    /**
     * Compares two concerts
     * @param o
     * @return int, 0 if are the same
     */
    @Override
    public int compareTo(Object o) {
       int i = this.getStartDate().compareTo(((Concert)o).getStartDate());
       if(i==0){
           return this.getName().compareTo(((Concert)o).getName());
       }
       return i;
    }
    
    /**
     * Text representation of a concert
     * @return String
     */
    public String toString(){
        String s="";
        s=s+"Concert which name is "+this.getName()+"\n";
        s=s+"Starts the "+this.getStartDate().toString()+"\n";
        s=s+"on "+this.getLocation().toString()+"\n";
        s=s+"Stars "+this.getPerformers().toString()+"\n";
        return s;
    }
    
}
