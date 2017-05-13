package android.si3.unice.polytech.com.example.pierrerainero.firm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represent a firm
 *
 */
public class Firm implements Serializable {
    private String name;
    private String description;
    private List<Store> stores;
    private Map<String, Product> products;
    private List<String> ads; 

    /**
     * Constructor for a firm
     * @param name the name of the firm
     * @param description the description of the firm
	 */
    public Firm(String name, String description) {
        this.name = name;
        this.description = description;
        
        stores = new ArrayList<>();
        products = new HashMap<>();
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
     * Allows to add a product in the firm
     * @param product product to add
     */
    public void addProduct(String reference, Product product){
        products.put(reference, product);
    }

    public Product getProduct(String reference){
        return products.get(reference);
    }

    public List<Product> getProducts(){
        return new ArrayList<Product>(products.values());
    }

    public void rankStoreByProfit(){
        Collections.sort(stores, new Comparator<Store>(){
            public int compare(Store one, Store two) {
                if(one.getProfit()==two.getProfit())
                    return 0;
                if(one.getProfit()>two.getProfit())
                    return -1;
                else
                    return 1;
            }
        });
        for(int i=0; i<stores.size();i++){
            stores.get(i).setLastRank(stores.size());
            stores.get(i).setRank(i+1);
        }
    }

    public void rankStoreByCity(){
        Collections.sort(stores, new Comparator<Store>(){
            public int compare(Store one, Store two) {
                return compareString(one.getCity(), two.getCity());
            }
        });
    }
    
    public void rankStoreByDepartement() {
        Collections.sort(stores, new Comparator<Store>(){
            public int compare(Store one, Store two) {
                return compareString(one.getDepartment(), two.getDepartment());
            }
        });
    }

    public void rankStoreByRegion() {
        Collections.sort(stores, new Comparator<Store>(){
            public int compare(Store one, Store two) {
                return compareString(one.getRegion(), two.getRegion());
            }
        });
    }

    private int compareString(String one, String two){
        for(int i=0;i<one.length();i++){
            if(i==two.length())
                return -1;

            if(one.charAt(i)<two.charAt(i))
                return -1;

            if(one.charAt(i)>two.charAt(i))
                return 1;
        }
        return 0;
    }
}
