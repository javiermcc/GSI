/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.BSystem;

import GSILabs.BModel.Artist;
import GSILabs.BModel.Client;
import GSILabs.BModel.Collective;
import GSILabs.BModel.Concert;
import GSILabs.BModel.Event;
import GSILabs.BModel.Exhibition;
import GSILabs.BModel.Festival;
import GSILabs.BModel.Location;
import GSILabs.BModel.ModelDate;
import GSILabs.BModel.Performer;
import GSILabs.BModel.Ticket;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author linux1
 */
public class BusinessSystem implements TicketOffice{
    
    TreeSet concerts;
    TreeSet exhibitions;
    TreeSet festivals;
    TreeSet clients;
    TreeSet tickets;
    TreeSet locations;
    Artist[] artists;
    Collective[] collectives;
    
    public BusinessSystem(){
        
        concerts=new TreeSet<Concert>();
        exhibitions=new TreeSet<Exhibition>();
        festivals=new TreeSet<Festival>();
        clients=new TreeSet<Client>();
        tickets=new TreeSet<Ticket>();
        locations=new TreeSet<Location>();
        artists=new Artist[0];
        collectives=new Collective[0];
        
    }

    @Override
    public boolean addNewConcert(Concert c) {
        
        if (c != null)
        {
            Performer[] cPerfor=c.getPerformers();
            Location auxLoc=c.getLocation();
            if (existLocation(auxLoc)){
                if(concerts.isEmpty()){            
                    return concerts.add(c);
                }
                Iterator<Concert> iterator = concerts.iterator();
                Concert aux;
                
                while (iterator.hasNext()){
                    aux=iterator.next();
                    
                    long time=aux.getStartDate().getTime() - c.getStartDate().getTime();
                    time = time /(1000*60*60*24);
                    time = Math.abs(time);
                    if(time<1){//the concerts have less than 24 hours between them
                        Performer[] auxPerfor=aux.getPerformers();
                        for(int k=0;k<cPerfor.length;k++){
                            for(int k2=0;k2<auxPerfor.length;k2++){
                                if(cPerfor[k].equals(auxPerfor[k2])){
                                    // some performer is in both concerts
                                    return false;
                                }
                            }
                        }
                    }
                }
                return concerts.add(c); 
            } 
        }
        return false;
    }

    @Override
    public boolean replaceConcert(Concert c) {
        
        if (c != null)
        {
            concerts.remove(c);
            return concerts.add(c);
        }
        return false;
    }

    @Override
    public boolean deleteConcert(Concert c) {
        
        if (c != null)
        {
            return concerts.remove(c);
        }
        return false;
    }

    @Override
    public boolean addNewExhibition(Exhibition e) {
        
        if (e != null)
        {
            return concerts.add(e);
        }
        return false;
    }

    @Override
    public boolean replaceExhibition(Exhibition e) {
        
        if (e != null)
        {
            exhibitions.remove(e);
            return concerts.add(e);
        }
        return false;
    }

    @Override
    public boolean deleteExhibition(Exhibition e) {
        
        if (e != null)
        {
            return exhibitions.remove(e);
        }
        return false;
    }

    @Override
    public boolean addNewFestival(Festival f) {
        
        if (f != null)
        {
            return festivals.add(f);
        }
        return false;
    }

    @Override
    public boolean addConcertToFestival(Festival f, Concert c) {
        if ((f != null) && (c != null))
        {
            boolean existsc = false;
            boolean existsf = false;
            Concert auxc;
            Iterator<Concert> iteratorc = concerts.iterator();
            while (iteratorc.hasNext())
            {
                auxc = iteratorc.next();
                if (c.compareTo(auxc) == 0) // exists concert
                {
                    existsc = true;
                }
            }
            
            Festival auxf;
            Festival[] auxf2 = null;
            int i = 0;
            Iterator<Festival> iteratorf = festivals.iterator();
            while(iteratorf.hasNext())
            {
                auxf = iteratorf.next();
                if ((auxf.getStartDate().getTime() <= c.getStartDate().getTime()) && (c.getStartDate().getTime() <= auxf.getEndingDate().getTime()))
                {
                    auxf2[i] = auxf;
                    i++;
                    if (auxf.compareTo(f) == 0)
                    {
                        existsf = true;
                    }
                }
            }   
            
            if (existsc && existsf)
            {
                for (int j = 0; j < auxf2.length; j++)
                {
                    for (int k = 0; k < auxf2[j].getConcerts().length; k++)
                    {
                        if ((auxf2[j].getConcerts()[k].compareTo(c) == 0) && (auxf2[j].getConcerts()[k].getStartDate() != c.getStartDate()))
                        {
                            return f.addConcert(c);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceFestival(Festival f) {
        
        if (f != null)
        {
            festivals.remove(f);
            return festivals.add(f);
        }
        return false;
    }

    @Override
    public boolean deleteFestival(Festival f) {
        
        if (f != null)
        {
            return festivals.remove(f);
        }
        return false;
    }

    @Override
    public boolean existsEvent(Event e) {
        
        if (e != null)
        {
            Festival aux1;
            Iterator<Festival> Fiterator= festivals.iterator();
            while(Fiterator.hasNext()){
                aux1=Fiterator.next();
                if(((Festival)e).equals(aux1)){
                    return true;
                }
            }
            
            Concert aux2;
            Iterator<Concert> Citerator= concerts.iterator();
            while(Citerator.hasNext()){
                aux2=Citerator.next();
                if(((Concert)e).equals(aux2)){
                    return true;
                }
            }
            
            Exhibition aux3;
            Iterator<Exhibition> Eiterator= exhibitions.iterator();
            while(Eiterator.hasNext()){
                aux3=Eiterator.next();
                if(((Exhibition)e).equals(aux3)){
                    return true;
                }
            }
            
            return false;
            
            
        }
        return false;
    }

    @Override
    public Event[] retrieveEvents(String name) {
        
        if (name != null)
        {
            Event[] events = new Event[1];
            Event[] eventsaux;
            boolean empty=true;
            
            Festival aux1;
            Iterator<Festival> Fiterator= festivals.iterator();
            while(Fiterator.hasNext()){
                aux1=Fiterator.next();
                if(aux1.getName().contains(name)){
                    if(empty){
                        events[0]=aux1;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux1;
                        events=eventsaux;
                    }
                }
            }
            
            Concert aux2;
            Iterator<Concert> Citerator= concerts.iterator();
            while(Citerator.hasNext()){
                aux2=Citerator.next();
                if(aux2.getName().contains(name)){
                    if(empty){
                        events[0]=aux2;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux2;
                        events=eventsaux;
                    }
                }
            }
            
            Exhibition aux3;
            Iterator<Exhibition> Eiterator= exhibitions.iterator();
            while(Eiterator.hasNext()){
                aux3=Eiterator.next();
                if(aux3.getName().contains(name)){
                    if(empty){
                        events[0]=aux3;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux3;
                        events=eventsaux;
                    }
                }
                if(empty){
                    return new Event[0];
                }else{
                    return events;
                }
            }
            
            return new Event[0];
            
        }
        return new Event[0];
    }

    @Override
    public Event[] retrieveEvents(Location loc) {
        
        if (loc != null)
        {
            Event[] events = new Event[1];
            Event[] eventsaux;
            boolean empty=true;
            boolean added=false;
            
            Festival aux1;
            Iterator<Festival> Fiterator= festivals.iterator();
            while(Fiterator.hasNext()){
                aux1=Fiterator.next();
                Concert[] Caux =aux1.getConcerts();
                added=false;
                for(int i=0;i<Caux.length && !added;i++){
                    if(Caux[i].getLocation().equals(loc)){
                        if(empty){
                            events[0]=aux1;
                            empty=false;
                            
                        }else{
                            eventsaux= new Event[events.length+1];
                            for(int j=0;j<events.length;j++){
                                eventsaux[j]=events[j];
                            }
                            eventsaux[eventsaux.length]=aux1;
                            events=eventsaux;
                        }
                        added=true;
                    }
                }
            }
            
            Concert aux2;
            Iterator<Concert> Citerator= concerts.iterator();
            while(Citerator.hasNext()){
                aux2=Citerator.next();
                if(aux2.getLocation().equals(loc)){
                    if(empty){
                        events[0]=aux2;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux2;
                        events=eventsaux;
                    }
                }
            }
            
            Exhibition aux3;
            Iterator<Exhibition> Eiterator= exhibitions.iterator();
            while(Eiterator.hasNext()){
                aux3=Eiterator.next();
                if(aux3.getLocation().equals(loc)){
                    if(empty){
                        events[0]=aux3;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux3;
                        events=eventsaux;
                    }
                }
                if(empty){
                    return new Event[0];
                }else{
                    return events;
                }
            }
            
            return new Event[0];
        }
        return new Event[0];
    }

    @Override
    public Event[] retrieveEvents(Date d) {
        
        if (d != null)
        {
            Event[] events = new Event[1];
            Event[] eventsaux;
            boolean empty=true;
            boolean added=false;
            
            Festival aux1;
            Iterator<Festival> Fiterator= festivals.iterator();
            while(Fiterator.hasNext()){
                aux1=Fiterator.next();
                Concert[] Caux =aux1.getConcerts();
                added=false;
                for(int i=0;i<Caux.length && !added;i++){
                    if(Caux[i].getDates()[0].equals(d)){
                        if(empty){
                            events[0]=aux1;
                            empty=false;
                            
                        }else{
                            eventsaux= new Event[events.length+1];
                            for(int j=0;j<events.length;j++){
                                eventsaux[j]=events[j];
                            }
                            eventsaux[eventsaux.length]=aux1;
                            events=eventsaux;
                        }
                        added=true;
                    }
                }
            }
            
            Concert aux2;
            Iterator<Concert> Citerator= concerts.iterator();
            while(Citerator.hasNext()){
                aux2=Citerator.next();
                if(aux2.getDates().equals(d)){
                    if(empty){
                        events[0]=aux2;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux2;
                        events=eventsaux;
                    }
                }
            }
            
            Exhibition aux3;
            Iterator<Exhibition> Eiterator= exhibitions.iterator();
            while(Eiterator.hasNext()){
                aux3=Eiterator.next();
                if(aux3.getDates().equals(d)){
                    if(empty){
                        events[0]=aux3;
                        empty=false;
                    }else{
                        eventsaux= new Event[events.length+1];
                        for(int i=0;i<events.length;i++){
                            eventsaux[i]=events[i];
                        }
                        eventsaux[eventsaux.length]=aux3;
                        events=eventsaux;
                    }
                }
                if(empty){
                    return new Event[0];
                }else{
                    return events;
                }
            }
            
            return new Event[0];
        }
        return new Event[0];
    }


    /**
     * Checks the age of majority
     * @param birthdate
     * @return boolean
     */
    private static boolean ageMajority(ModelDate birthdate)
    {
        Date today = new Date();
        boolean resul=false;
        if ((today.getYear()-birthdate.getYear())>18){
            resul=true;
        }else if ((today.getYear()-birthdate.getYear())==18){
            if (today.getMonth()<birthdate.getMonth()){
               resul=false;
            }else if (today.getMonth()==birthdate.getMonth()){
                resul=(today.getDate()>=birthdate.getDay());
            }else if(today.getMonth()>birthdate.getMonth()){
                resul=true;
            }
        }else if ((today.getYear()-birthdate.getYear())<18){
            resul=false;
        }
        return resul;
    }
    
    @Override
    public boolean addClient(Client c) {
        
        boolean b=false;
        if ((c != null) && (ageMajority(c.getBirthdate())))
        {
            b= clients.add(c);
        }
        return b;
    }

    @Override
    public boolean modifyClient(Client c) {
        
        if ((c != null) && (ageMajority(c.getBirthdate())))
        {
            clients.remove(c);
            return clients.add(c);
        }
        return false;
    }

    @Override
    public boolean addCardToClient(Client c, String cCard) {
        
        if ((c != null) && cCard != null)
        {
            if(c.searchCard(cCard)==-1){
                c.addCard(cCard);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsClient(Client c) {
        
        if (c != null)
        {
            return clients.contains(c);
        }
        return false;
    }

    @Override
    public boolean containsClient(int id) {
        
        Client c;
        Iterator<Client> iterator = clients.iterator();
        while(iterator.hasNext())
        {
            c = iterator.next();
            return c.getDni() == id;
        }
        return false;
    }

    @Override
    public Client retrieveClient(int identifier) {
        
        Client c,auxClient=null;
        boolean b=false;
        Iterator<Client> iterator = clients.iterator();
        while(iterator.hasNext())
        {
            c = iterator.next();
            if (c.getDni() == identifier)
            {
                auxClient=c;
                b=true;
            }
        }
        
        return auxClient;
    }

    @Override
        public Ticket[] getListOfTickets(Client c) {
        
        if (c != null)
        {
            Ticket aux;
            Ticket[] tics = null;
            int i = 0;
            Iterator<Ticket> iterator = tickets.iterator();
            while(iterator.hasNext())
            {
                aux = iterator.next();
                if (aux.getAssociated().compareTo(c) == 0)
                {
                    tics[i] = aux;
                    i++;
                }
            } 
            return tics;
        }
        return null;
    }

    @Override
    public float getTotalSpending(Client c) {
        
        if (c != null)
        {
            Ticket aux;
            float price = 0;
            Iterator<Ticket> iterator = tickets.iterator();
            while(iterator.hasNext())
            {
                aux = iterator.next();
                if (aux.getAssociated().compareTo(c) == 0)
                {
                    price = price + aux.getPrice();
                }
            } 
            return price;
        }
        return -1;
    }

    @Override
    public boolean addNewTicket(Ticket t) {
       
        if ((t != null) && (t.getEvent() != null)) //&& (t.getIDs() != null) && (t.getAssociated() != null))
        {
            if (existsEvent(t.getEvent()))
            {
                if (clients.contains(t.getAssociated()))
                {
                    
                    return tickets.add(t);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasIDCollision(Ticket t) {
        
        if (t != null)
        {
            Ticket aux;
            Iterator<Ticket> iterator = tickets.iterator();
            while(iterator.hasNext())
            {
                aux = iterator.next();
                if (aux.getIDs().equals(t.getIDs()))
                {
  
                    return true;
                }                
            }
           // return tickets.contains(t);
        }
        return false;
    }

    @Override
    public boolean availableTicketID(Event e, int id) {
        
        if ((e != null) && (id > 0))
        {
            if (existsEvent(e))
            {
                Ticket aux;
                Iterator<Ticket> iterator = tickets.iterator();
                while(iterator.hasNext())
                {
                    aux = iterator.next();
                    if (aux.getEvent().equals(e))
                    {
                        if (aux.getId() == id)
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean setIDUsed(Ticket t, Event e, int id) {
        
        if ((t != null) && (e != null) && (id > 0))
        {
            if (existsEvent(e))
            {
                Ticket aux;
                Iterator<Ticket> iterator = tickets.iterator();
                while(iterator.hasNext())
                {
                    aux = iterator.next();
                    if (aux.compareTo(t) == 0)
                    {
                        if (aux.getId() == id)
                        {
                            if (aux.getUsed())
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean addSale(Ticket t, Client c, Float price, String cCard) {
        
        if ((t != null) && (c != null) && (cCard != null) && (price > 0))
        {
            Ticket auxt;
            Iterator<Ticket> iterator = tickets.iterator();
            while(iterator.hasNext())
            {
                
                auxt = iterator.next();
                if (auxt.compareTo(t) == 0)
                {
                   
                    Client auxc;
                    Iterator<Client> it = clients.iterator();
                    while (it.hasNext())
                    {
                        auxc = it.next();
                        if (auxc.compareTo(c) == 0)
                        {
                            if (auxc.searchCard(cCard) != -1)
                            {                       
                               
                                t.Sold(); //esta bien? tiene que poner vendido?
                                t.Sold(price);                              
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean addLocation(Location loc) {
        
        if ((loc != null) && (loc.getName() != null) && (loc.getCapacity() > 0) && (loc.getCountry() != null) && (loc.getCountry() != null) 
                && (loc.getProvince() != null) && (loc.getCity() != null) && (loc.getStreet() != null) && (loc.getNumber() > 0))
        {
            return locations.add(loc);
        }
        return false;
    }
    
    public boolean existLocation(Location l){
        
        Location loc;
        if ( l != null){
            
            Iterator<Location> iterator = locations.iterator();
            while(iterator.hasNext()){
                
                loc=iterator.next();
                return loc.equals(l);
            }
        }
        
        return false;
    }

    @Override
    public Location getLocation(String name) {
        
        if (name != null)
        {
            Location loc;
            Iterator<Location> iterator = locations.iterator();
            while(iterator.hasNext())
            {
                loc = iterator.next();
                if (loc.getName().equals(name))
                {
                    return loc;
                }
            }  
        }
        return null;
    }

    @Override
    public boolean deleteLocation(Location loc) {
        
        if (loc != null)
        {
            return locations.remove(loc);
        }
        return false;
    }

    @Override
    public Location[] getLocations(int minCapacity) {
        
        if (minCapacity > 0)
        {
            Location loc;
            int i =0;
            TreeSet<Location> t=new TreeSet<>();
            Iterator<Location> iterator = locations.iterator();
            while(iterator.hasNext())
            {
                loc = iterator.next();
                if (loc.getCapacity() >= minCapacity)
                {
                    t.add(loc);
                    i++;
                }
            } 
            Iterator<Location> it = t.iterator();
            int k=0;
            Location[] locs=new Location[i];
            while(it.hasNext()){
                locs[k]=it.next();
                k++;
            }
            return locs;
        }
        return null;
    }

    @Override
    public boolean addArtist(Artist a) {
        
        if (a != null)
        {
            if(existsPerformer(a.getName())){
                return false;
            }else{
                Artist[] aux=new Artist[artists.length+1];
                for(int i=0;i<artists.length;i++){
                    aux[i]=artists[i];
                }
                aux[artists.length]=a;
                artists=aux;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCollective(Collective c) {
        
        if (c != null)
        {
            if(existsPerformer(c.getName())){
                return false;
            }
            Performer[] aux = c.getComponents();
            
            for(int i=0;i<aux.length;i++){
                if(existsArtist(aux[i].getName())){}else{
                    return false;
                }
            }
            
            Collective[] aux2=new Collective[collectives.length+1];
            for(int i=0;i<collectives.length;i++){
                aux2[i]=collectives[i];
            }
            aux2[collectives.length]=c;
            collectives=aux2;
            return true;
            
        }
        return false;
    }

    @Override
    public boolean modifyArtist(Artist a) {
        
        if (a != null)
        {
            for(int i=0;i<artists.length;i++){
                if(artists[i].equals(a)){
                    artists[i]=a;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean modifyCollective(Collective c) {
        
        if (c != null)
        {
            for(int i=0;i<collectives.length;i++){
                if(collectives[i].equals(c)){
                    collectives[i]=c;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removePerformer(String performerName) {
        int k=0;
        if (performerName != null)
        {
            if(existsArtist(performerName)){
                Artist[] aux=new Artist[artists.length-1];
                for(int i=0;i<artists.length;i++){
                    if(artists[i].getName().equals(performerName)){
                        
                    }else{
                        aux[k]=artists[i];
                        k++;
                    }
                } 
                artists=aux;
                return true;
            }
             
            if(existsCollective(performerName)){
                Collective[] aux=new Collective[collectives.length-1];
                for(int i=0;i<collectives.length;i++){
                    if(collectives[i].getName().equals(performerName)){
                        
                    }else{
                        aux[k]=collectives[i];
                        k++;
                    }
                } 
                collectives=aux;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean existsPerformer(String performerName) {
        
        if (performerName != null)
        {
            for(int i=0;i<artists.length;i++){
                if(artists[i].getName().equals(performerName)){
                    return true;
                }
            }
            for(int i=0;i<collectives.length;i++){
                if(collectives[i].getName().equals(performerName)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean existsArtist(String artistName) {
        
        if (artistName != null)
        {
            for(int i=0;i<artists.length;i++){
                if(artists[i].getName().equals(artistName)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean existsCollective(String artistName) {
        
        if (artistName != null)
        {
            for(int i=0;i<collectives.length;i++){
                if(collectives[i].getName().equals(artistName)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public Performer retrievePerformer(String performerName) {
        
        if (performerName != null)
        {
            for(int i=0;i<artists.length;i++){
                if(artists[i].getName().equals(performerName)){
                    return artists[i];
                }
            }
            for(int i=0;i<collectives.length;i++){
                if(collectives[i].getName().equals(performerName)){
                    return collectives[i];
                }
            }
            return null;
        }
        return null;
    }
}