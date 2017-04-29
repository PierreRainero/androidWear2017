package android.si3.unice.polytech.com.example.pierrerainero.firm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a firm
 *
 */
public class Firm {
    private String name;
    private String description;
    private String logo;
    private String linkForMoreInfo;
    private String banner;
    private List<Store> stores;
    private List<Product> products;
    private List<String> ads; 

    /**
     * Constructor for a firm
     * @param name the name of the firm
     * @param description the description of the firm
	 * @param logo url to the logo
	 * @param linkForMoreInfo website url
	 * @param banner url to the banner
	 */
    public Firm(String name, String description, String logo, String linkForMoreInfo, String banner) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.linkForMoreInfo = linkForMoreInfo;
        this.banner = banner;
        
        stores = new ArrayList<>();
        products = new ArrayList<>();
        ads = new ArrayList<>();
    }

    /**
     * Allow to change the name of a firm
     * @param newName the new name
     */
    public void changeName(String newName){
        name = newName;
    }

    /**
     * Allow to change the description of a firm
     * @param newDescription the new description
     */
    public void changeDecription(String newDescription){
        description = newDescription;
    }


    /**
     * Allow to know if the firm has at least one store
     * @return true if the firm has 0 stores
     */
    public boolean hasNoStores(){
        return stores.isEmpty();
    }

    /**
     * Allow to add a store to the firm
     * @param store the store to add
     */
    public void addStore(Store store){
        stores.add(store);
    }

    /**
     * Getter for the name of the firm
     * @return the name of the firm
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the description of the firm
     * @return the description of the firm
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the list of stores of the firm
     * @return the list of stores
     */
    public List<Store> getStores() {
        return stores;
    }
    
    /**
     * Consultation accessor of logo
     * @return url to the firm logo
     */
    public String getLogo(){
    	return logo;
    }
    
    /**
     * Consultation accessor of banner
     * @return url to the firm banner
     */
    public String getBanner(){
    	return banner;
    }
    
    /**
     * Consultation accessor of website link
     * @return url to the firm website url
     */
    public String getLinkForMoreInfo(){
    	return linkForMoreInfo;
    }
    
    /**
     * Consultation accessor of ads
     * @return list of all ads
     */
    public List<String> getAds(){
    	return ads;
    }
    
    /**
     * Allows to add an ad in the firm
     * @param ad ad to add
     */
    public void addAdvertisement(String ad){
    	ads.add(ad);
    }
    
    /**
     * Consultation accessor of products
     * @return url to the firm products
     */
    public List<Product> getProducts() {
        return products;
    }
    
    /**
     * Allows to add a product in the firm
     * @param product product to add
     */
    public void addProduct(Product product){
    	products.add(product);
    }
    
}
