/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author alisii
 */
public abstract class Part {
    
    int partID;
    String name;
    double price;
    int inStock;
    int min;
    int max;
    int highlight;
    
    public void setName(String name){
        this.name = name;
    }
            
    public String getName(){
        return this.name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    
    public void setInStock(int inStock){
        this.inStock = inStock;
    }
    
    public int getInStock(){
        return this.inStock;
    }
    
    public void setMin(int min){
        this.min = min;
    }
    
    public int getMin(){
        return min;
    }
    
    public void setMax(int max){
        this.max = max;
    }
    
    public int getMax(){
        return max;
    }
    
    public void setPartID(int partID){
        this.partID = partID;
    }
    
    public int getPartID(){
        return partID;
    }
}
