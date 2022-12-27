/**
* This class contains the data that represents a house
 */
public class House  {

    private int price;
    private int footage;
    private String location;

    /**
     * Constructor for house
     *
     * @param price    price of house
     * @param footage  square footage of house
     * @param location location of house
     */
    public House(int price, int footage, String location) {
        this.price = price;
        this.footage = footage;
        this.location = location;
    }


    /**
     * Returns the price of the house
     *
     * @return the price of the house
     */
    public int getPrice() {

        return price;
    }


    /**
     * Returns the location of the house
     *
     * @return the location of the house
     */
    public String getLocation() {
        return location;
    }


    /**
     * Returns the footage of the house
     *
     * @return the footage of the house
     */
    public int getFootage() {

        return footage;
    }

}