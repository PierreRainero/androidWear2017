package android.si3.unice.polytech.com.example.pierrerainero.firm.model;


import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represent a store
 *
 */
public class Store implements Serializable, Comparable<Store> {
    private String name;
    private String description;
    private String image;

    private String address;
    private String city;
    private int cityNumber;
    private String mallName;
    private String region;
    private String department;

    private double turnover;
    private double cost;
    private int employeeNb;

    private double longitude;
    private double latitude;

    private int rank;
    private int lastRank;

    private Map<Product, Map.Entry<Double, Double>> productsProfit;

    /**
     * Constructor for a store
     * @param name the name of the store
     * @param address the address of the store
     * @param city city name
     * @param cityNumber city number
     * @param department the department where the store is located
     * @param region the region where the store is located
     * @param mallName the name of the mall where the store is
     * @param description the description of the store
     * @param image url to the store image
     * @param turnover turnover of the store
     * @param cost cost of maintenance of the store
     * @param employeeNb number of employees of the store
     */
    public Store(String name, String address, String city, int cityNumber , String mallName, String description, String image, String region, String department, double turnover, double cost, int employeeNb, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.city=city;
        this.cityNumber = cityNumber;
        this.mallName = mallName;
        this.description = description;
        this.image = image;
        this.region = region;
        this.department=department;
        this.turnover = turnover;
        this.cost = cost;
        this.employeeNb = employeeNb;
        this.longitude = longitude;
        this.latitude =  latitude;

        rank = 0;
        lastRank = 0;

        productsProfit = new HashMap<>();
    }

    /**
     * Getter for the name of the store
     * @return the name of the store
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the address of the store
     * @return the address of the store
     */
    public String getAddress() {
        return address+"\n"+cityNumber+", "+city;
    }

    /**
     * Getter for the name of the mall where the store is
     * @return the name of the mall
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * Getter for the description of the store
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the link of the store's image
     * @return the link
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Getter for the city where the store is located
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter for the number of the city where the store is located
     * @return the cityNumber
     */
    public int getCityNumber() {
        return cityNumber;
    }

    /**
     * Getter for the region where the store is located
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Getter for the department where the store is located
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    public double getTurnover() {
        return turnover;
    }

    public double getCost() {
        return cost;
    }

    public int getEmployeeNb() {
        return employeeNb;
    }

    public double getProfit(){
        return turnover - cost;
    }

    public int getRank() {
        return rank;
    }

    public int getLastRank() {
        return lastRank;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setLastRank(int lastRank) {
        this.lastRank = lastRank;
    }

    public void addProduct(Product product, double gain, double cost){
        productsProfit.put(product, new AbstractMap.SimpleEntry<>(gain, cost));
    }

    public double getProductProfit(Product product){
        return productsProfit.get(product).getKey() - productsProfit.get(product).getValue();
    }

    public Product getBestProduct(){
        Product returnValue = null;
        double bestProfit = Double.MIN_VALUE;

        for(Product product : productsProfit.keySet()){
            double tmpProfit = getProductProfit(product);
            if(bestProfit<tmpProfit){
                bestProfit = tmpProfit;
                returnValue = product;
            }
        }

        return  returnValue;
    }

    public Product getWorstProduct(){
        Product returnValue = null;
        double bestProfit = Double.MAX_VALUE;

        for(Product product : productsProfit.keySet()){
            double tmpProfit = getProductProfit(product);
            if(bestProfit>tmpProfit){
                bestProfit = tmpProfit;
                returnValue = product;
            }
        }
        return  returnValue;
    }

    @Override
    public int compareTo(Store store) {
        if(getProfit()==store.getProfit())
            return 0;
        if(getProfit()>store.getProfit())
            return -1;
        else
            return 1;
    }

    @Override
    public Store clone(){
        return this;
    }
}

