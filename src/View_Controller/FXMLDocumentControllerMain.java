/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alisii
 */
public class FXMLDocumentControllerMain implements Initializable {

    // parts portion
    @FXML private TableView<Part> tableViewParts;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> inventoryLevelColumn; 
    @FXML private TableColumn<Part, Double> pricePerUnitColumn;
    @FXML private TextField searchPartTextField;
    
    
    // products portion
    @FXML private TableView<Product> tableViewProducts;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryLevelColumn; 
    @FXML private TableColumn<Product, Double> productPricePerUnitColumn;
    @FXML private TextField searchProductTextField;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void loadPartsTableView() {
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        pricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableViewParts.setItems(getParts());
    }
    
    public void loadProductsTableView() {
        
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableViewProducts.setItems(getProducts());
    }

    public ObservableList<Part> getParts() {
        
        ObservableList<Part> inventoryParts = FXCollections.observableArrayList(Inventory.getAllParts());
        return inventoryParts;
    }
    
    public ObservableList<Product> getProducts() {
        
        ObservableList<Product> inventoryParts = FXCollections.observableArrayList(Inventory.getAllProducts());
        return inventoryParts;
    }
    
    @FXML
    public void ExitAction (ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void AddPartScene (ActionEvent event) throws IOException {
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();   
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/FXMLAddPart.fxml"));   
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    public void AddProductScene (ActionEvent event) throws IOException {
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();   
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/FXMLAddProduct.fxml"));   
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    public void ModifyPartScene (ActionEvent event) throws IOException {
        
        // if no row is selected in table view, the return value is null
        Part part = tableViewParts.getSelectionModel().getSelectedItem();
        
        if (part == null) {
            return;
        }
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/FXMLModifyPart.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        
        // populate the array inventory
        FXMLModifyPartController modifyController = loader.getController();
        modifyController.initData(part); // sent the part to modify screen
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
    
    @FXML
    public void deletePart() {
        
        // if no row is selected in table view, the return value is null
        Part part = tableViewParts.getSelectionModel().getSelectedItem();
        
        if (part == null) {
            return;
        }
        
        // remove the part from the Inventory
        Inventory.deletePart(part);
        
        // reload the table view again
        loadPartsTableView();
        
    }
    
    public void deleteProduct() {
        
        // if no row is selected in table view, the return value is null
        Product product = tableViewProducts.getSelectionModel().getSelectedItem();
        
        if (product == null) {
            return;
        }
        
        // remove the part from the Inventory
        Inventory.removeProduct(product);
        
        // reload the table view again
        loadProductsTableView();
        
    }
    
    @FXML
    public void searchPart() {
        // search for a part by ID and highlight it on the table view
        // read data from search field
        int searchID;
        try{
            searchID = Integer.parseInt(searchPartTextField.getText());
        }
        catch(Exception e){
            System.out.println(e.toString());
            return;
        }
        
        Part part;
        try{
            part = Inventory.lookupPart(searchID);
            
            if (part != null)
                tableViewParts.getSelectionModel().select(part);
            return;
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
