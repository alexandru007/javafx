/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author alisii
 */
public class Product {
    
    ArrayList<Part> associatedParts = new ArrayList<>();
    int productID;
    String name;
    double price;
    int inStock;
    int min;
    int max;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    
    public int getInStock() {
        return inStock;
    }
    
    public void setMin(int min) {
        this.min = min;
    }
    
    public int getMin() {
        return min;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getMax() {
        return max;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID) {
        
        // loop over the array list, find the part and remove it
        for (Part part : associatedParts) {
            if (part.partID == partID) {
                return associatedParts.remove(part);
            }
        }
        
        return false;
    }
    
    public Part loocupAssociatedPart(int partID) throws Exception {
        
        // loop over the array list, find the part and remove it
        for (Part part : associatedParts) {
            if (part.partID == partID) {
                return part;
            }
        }
        
        throw new Exception("This part does not exist");
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getProductID() {
        return productID;
    }
    
    public ArrayList<Part> getAssociatedPars() {
        return associatedParts;
    }
}
