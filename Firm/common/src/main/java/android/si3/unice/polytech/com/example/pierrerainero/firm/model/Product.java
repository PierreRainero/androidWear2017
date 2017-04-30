package android.si3.unice.polytech.com.example.pierrerainero.firm.model;

import android.si3.unice.polytech.com.example.pierrerainero.firm.exception.ProductException;

import java.io.Serializable;

/**
 * Class that represents a commercialized product
 *
 */
public class Product implements Serializable {
    private String name;
    private String reference;
    private String description;
    private double price;
    private boolean promoted = false;
    private boolean flagship = false;
    private String image ="";

    /**
     * Constructor for mandatory parameter of a product
     * @param name the name
     * @param reference the reference
     * @param price the price
     */
    public Product(String name, String reference, double price) throws ProductException {
        if(reference==null)
            throw new ProductException("Reference can't be null.");

        this.name = name;
        this.reference = reference;
        this.price = price;
        this.image = "";
    }


    public Product(String name, String reference, String description, double price) throws ProductException {
        this(name, reference, price);
        this.description = description;
    }

    public Product(String name, String reference, double price, String image) throws ProductException {
        this(name, reference, price);
        this.image = image;
    }

    /**
     * Constructor for a product
     * @param name the name of the product
     * @param reference the reference of the product
     * @param description the description of the product
     * @param price the price of the product
     */
    public Product(String name, String image, String reference, String description, double price, boolean promoted, boolean flagship) throws ProductException {
        this(name, reference, price, description);
        this.image = image;
        this.promoted = promoted;
        this.flagship = flagship;
    }


    /**
     * Allow to mark a product as promoted to have it highlighted on store's page
     */
    public void markProductAsPromoted(){
        promoted = true;
    }

    /**
     * Allow to unmark a product as promoted to remove it from the highlighted products
     */
    public void unmarkProductAsPromoted(){
        promoted = false;
    }

    /**
     * Allow to change the price of a product
     * @param newPrice the new price
     */
    public void changeProductPrice(double newPrice){
        price = newPrice;
    }

    /**
     * Allow to change the name of a product
     * @param newName the new name
     */
    public void changeProductName(String newName){
        name = newName;
    }

    /**
     * Allow to change the description of a product (not modify it yet)
     * @param newDescription the new description
     */
    public void changeProductDescription(String newDescription){
        description = newDescription;
    }

    /**
     * Allow to change the image of a product
     * @param newImage the new image
     */
    public void changeProductImage(String newImage){
        image = newImage;
    }

    /**
     * Allow to know if the product is promoted
     * @return true if the product is promoted
     */
    public boolean isPromoted(){return promoted;}

    /**
     * Getter for the name of the product
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the reference of the product
     * @return the reference of the product
     */
    public String getReference() {
        return reference;
    }

    /**
     * Getter for the description of the product
     * @return the description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the price of the product
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for the image of the product
     * @return the image of the product
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Mark the product as flagship product
     */
    public void markProductAsFlagship(){
    	flagship = true;
    }
    
    /**
     * Getter for the flagship of the product
     * @return the flagship state of the product
     */
    public boolean isFlagship(){
    	return flagship;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Product) || ((Product) obj).getReference()!= reference)
            return false;
        return true;
    }

    @Override
    public String toString(){
        return reference + " : " + name;
    }
}
