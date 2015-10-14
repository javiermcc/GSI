/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.BModel;

/**
 *
 * @author elementary
 */
public class Client implements Comparable
{
    
    private int dni;                      // the identifier of the client
    private static String name;           // the name of the client
    private static String surnames;       // the surnames of the client
    private static ModelDate birthdate;   // the birthdate of the client
    private String[] cards;               // the cards of the client
    
    /**
     * Constructor of the client
     * @param dni
     * @param name
     * @param surnames
     * @param birthdate
     * @param card 
     */ 
    public Client(int dni, String name, String surnames, ModelDate birthdate, String card)
    {
        this.dni = dni;
        this.name = name;
        this.surnames = surnames;
        this.birthdate = birthdate;
        this.cards=new String[1];
        this.cards[0]=card;
    }
    
    /**
     * Gets the client's dni
     * @return int
     */
    public int getDni()
    {
        return this.dni;
    }
    
    /**
     * Gets the client's name
     * @return String
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Gets the client's surname
     * @return String
     */
    public String getSurnames()
    {
        return this.surnames;
    }
    
    /**
     * Gets client's birthdate
     * @return ModelDate
     */
    public ModelDate getBirthdate()
    {
        return this.birthdate;
    }

    /**
     * Gets client's cards
     * @return String[] with the cards
     */
    public String[] getCards()
    {
        return this.cards;
    }
    
    /**
     * Adds a card to the client's cards
     * @param card 
     */
    public void addCard(String card)
    {
        String[] NewCards = new String[cards.length+1];
        for(int i=0;i<cards.length;i++){
            NewCards[i]=cards[i];
        }
        NewCards[NewCards.length-1]=card;
        this.cards=NewCards;
    }
    
    /**
     * Deletes a card from the client's card
     * @param position 
     */
    public void deleteCard(int position)
    {
        String[] NewCards = new String[cards.length-1];
        //BS search if the card is in the array before calling this method
        for(int i = 0 ; i<position;i++){
            NewCards[i]=cards[i];
        }
        for(int i = position+1;i<cards.length;i++){
            NewCards[i-1]=cards[i];
        }
        this.cards=NewCards;
    }
    
    /**
     * Search a card returns its position or -1 if it doesn't exists
     * @param card
     * @return int
     */
    public int searchCard(String card)
    {// this method return -1 if the card isnt in the array or the position in the array
        for(int i=0;i<cards.length;i++){
            if(card.equals(cards[i])){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Text representation of a client
     * @return String
     */
    @Override
    public String toString()
    { 
        return "Dni: " + this.getDni() +
               ", Name: " + this.getName() +
               ", Surnames: " + this.getSurnames() +
               ", Birthday: " + this.getBirthdate() + 
               ", Cards: " + this.getCardsString();
    } 
    
    /**
     * Converts the cards into a string, text representation of the cards
     * @return String
     */
    public String getCardsString(){
        String end="";
        
        for(int i = 0 ; i<cards.length;i++){
            end=end+cards[i]+" ";
        }
        //System.out.println(end);
        return end;
        //return this.cards[0];//end;
    
    }
    
    /**
     * Compares two people
     * @param person
     * @return boolean
     */
    public boolean equals(Client person)
    {
        if (person == null)
        {
            return false;
        }
        if (this.getDni() != person.getDni())
        {
            if (this.name == null ? person.name == null : this.name.equals(person.name))
            {
                if (this.surnames == null ? person.surnames == null : this.surnames.equals(person.surnames))
                {
                    if (this.birthdate == person.birthdate)
                    {
                        if (this.cards.equals(person.cards))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
            int i = this.getDni()-((Client)o).getDni();
            if(i==0){
                return this.getName().compareTo(((Client)o).getName());
            }
            return i;
    }

    
    
}