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
public class Inventory {
    
    static ArrayList<Product> Products = new ArrayList<>();
    static ArrayList<Part> Parts = new ArrayList<>();
    
    public static void addProduct(Product product) {
        Products.add(product);
    }
    
    public static boolean removeProduct(Product product) {
        return Products.remove(product);
    }
    
    public static Product lookupProduct(int productID){
        
        for (Product product : Products) {
            if (product.productID == productID) {
                return product;
            }
        }
        
        return null;
    }
    
    public static void updateProduct(int productID, Product updatedProduct) {
        
        for (Product product : Products) {
            if (product.productID == productID) {
                product = updatedProduct;
            }
        }
    }
    
    public static void addPart(Part part) {
        Parts.add(part);
    }
    
    public static boolean deletePart(Part part) {
        return Parts.remove(part);
    }
    
    public static Part lookupPart(int partID) throws Exception {
        
        for (Part part : Parts) {
            if (part.partID == partID) {
                return part;
            }
        }
        
        throw new Exception("This part does not exist");
    }
    
    public static void updatePart(int partID, Part updatedPart) {
        
        for (Part part : Parts) {
            if (part.partID == partID) {
                part.name = updatedPart.name;
                part.inStock = updatedPart.inStock;
                part.min = updatedPart.min;
                part.max = updatedPart.max;
                part.price = updatedPart.price;
                
                if (part instanceof InHouse){
                    ((InHouse) part).machineID = ((InHouse) updatedPart).machineID;
                }
                
                if (part instanceof Outsourced) {
                    ((Outsourced) part).companyName = ((Outsourced)updatedPart).companyName;
                }
            }
        }
    }
    
    // all parts
    public static ArrayList<Part> getAllParts() {
        return Parts;
    }
}
