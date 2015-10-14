/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.BModel;

import java.util.TreeSet;
import java.util.Date;
import java.util.Iterator;


/**
 *
 * @author linux1
 */
public class Festival implements LastingEvent, Comparable{

    TreeSet<Concert> concerts;  // The concerts that make a festival
    Date startDate;             // The date of the first concert, the festival's start date
    Date endDate;               // The date of the last concert, the festival's end date
    String name;                // The name of the festival  
    
    /**
     * Gets the festival's start date
     * @return Date
     */
    @Override
    public Date getStartDate() {
        
        return startDate;
    }

    /**
     * Gets the festival's end date
     * @return Date
     */
    @Override
    public Date getEndingDate() {
        return endDate;
    }

    /**
     * Gets festival's name
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets festival's concert's dates
     * @return Date[]
     */
    @Override
    public Date[] getDates() {
        Date[] dates = null;
        int i=0;
        Concert c;
        Iterator<Concert> iterator = concerts.iterator();
        while(iterator.hasNext()){
            c = iterator.next();
            dates[i]=c.getStartDate();
            i++;
        }
        return dates;
    }

    /**
     * Checks if the perfomer is in the festival
     * @param p
     * @return boolean, true if the performer is in the concert
     */
    @Override
    public boolean involvesPerformer(Performer p) {
        
        Performer[] performers=this.getPerformers();
        
        for(int i=0;i<performers.length;i++){
            if(p.equals(performers[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets festival's performers
     * @return Performer[]
     */
    @Override
    public Performer[] getPerformers() {
        
        Performer[] performers=null;
        Performer[] perfaux=null;
        boolean isIn=false;
        int j=0;
        int i=0;
        Concert c;
        Iterator<Concert> iterator = concerts.iterator();
        //the performers of the first concert are added
        c=iterator.next();
        perfaux = c.getPerformers();
        for(i=0; i < perfaux.length;i++){
            performers[i]=perfaux[i];
        }
        
        //on each of the next concerts we check that the performers havent been already added
        while(iterator.hasNext()){
            c = iterator.next();
            perfaux = c.getPerformers();
            for(i=0;i<perfaux.length;i++){
                isIn=false;
                //check if has already been added
                for(j=0;j<performers.length;j++){
                    if(performers[j].equals(perfaux[i])){
                        isIn=true;
                    }
                }//if it hasnt been added
                if(isIn==false){
                    performers[performers.length]=perfaux[i];
                }
            }
        }
        
        return performers;
        
    }

    /**
     * Compares two festivals
     * @param o
     * @return int, 0 if are the same
     */
    @Override
    public int compareTo(Object o) {
        
        int i = this.startDate.compareTo(((Festival)o).startDate);
        if (i == 0)
        {
            return this.getName().compareTo(((Festival)o).getName());
        }
        return i;
    }
    
    
    /**
     * Text representation of a festival
     * @return String
     */public String toString(){
        String s="";
        s=s+"Festival which name is "+this.getName()+"\n";
        Concert c;
        Iterator<Concert> itc= concerts.iterator();
        s=s+"Involves the following concerts:\n";
        while (itc.hasNext()){
            c=itc.next();
            s=s+"---------------------------------------";
            s=s+c.toString();
        }
        return s;
    }
    
    /**
     * Adds a concert to the festival
     * @param c
     * @return True, if added succesfully
     */
     public boolean addConcert(Concert c)
    {
        return this.concerts.add(c);
    }
    
    /**
     * Gets festival's concerts
     * @return Concert[]
     */
     public Concert[] getConcerts()
    {
        Concert[] aux = null;
        int i = 0;
        Iterator<Concert> iterator = concerts.iterator();
        while(iterator.hasNext())
        {
            aux[i] = iterator.next();
            i++;
        }
        return aux;
    }
}
