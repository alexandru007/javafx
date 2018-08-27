/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Product;
import Model.Inventory;
import Model.Part;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class FXMLAddProductController implements Initializable {

    Product product = new Product();
    
    // buttons
    @FXML private Button saveButton;
    
    
    // add product, left side
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    static private int productIDCounter = 1;
    
    // all parts table view top portion
    @FXML private TableView<Part> tableViewParts;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> inventoryLevelColumn; 
    @FXML private TableColumn<Part, Double> pricePerUnitColumn;
    @FXML private TextField searchPartTextField;
    
    // associated parts table view bottom portion
    @FXML private TableView<Part> tableViewAssotiatedParts;
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedInventoryLevelColumn; 
    @FXML private TableColumn<Part, Double> associatedPricePerUnitColumn;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadPartsTableView();
    }
    
    // populate the parts table view with all available parts
    public void loadPartsTableView() {
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        pricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableViewParts.setItems(getParts());
        
    }
    
    public void loadAssotiatedPartsTableView() {
        
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        associatedPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableViewAssotiatedParts.setItems(getAssociatedParts());
    }
    
    public ObservableList<Part> getParts() {
        
        ObservableList<Part> inventoryParts = FXCollections.observableArrayList(Inventory.getAllParts());
        return inventoryParts;
    }
    
    public ObservableList<Part> getAssociatedParts() {
        
        ObservableList<Part> parts = FXCollections.observableArrayList(product.getAssociatedPars());
        return parts;
    }
    
    public boolean saveProduct() {
        
        // check if the fields are empty
        if (nameTextField.getText().equals("") || inventoryTextField.getText().equals("") ||
            priceTextField.getText().equals("") || maxTextField.getText().equals("") || minTextField.getText().equals("")) {
                return false;
        }

        //Product product = new Product();
        product.setProductID(productIDCounter++);
        product.setName(nameTextField.getText());
        product.setInStock(Integer.parseInt(inventoryTextField.getText()));
        product.setPrice(Double.parseDouble(priceTextField.getText()));
        product.setMax(Integer.parseInt(maxTextField.getText()));
        product.setMin(Integer.parseInt(minTextField.getText()));
        
        //TO DO
        // check if the product has any associated parts
        if (product.getAssociatedPars().isEmpty())
            return false;
        
        Inventory.addProduct(product);
        
        return true;
    }
    
    public void addPartToProductTableView() {
        
        // check if there is anything selected
        Part part = tableViewParts.getSelectionModel().getSelectedItem();
        
        if (part == null){
            return;
        }
        
        // add the associated part to product
        // populate the associated parts table view
        product.addAssociatedPart(part);
        loadAssotiatedPartsTableView();
    }
    
    public void removePartFromAssociatedPartstableView() {
        
        Part part = tableViewAssotiatedParts.getSelectionModel().getSelectedItem();
        
        if (part == null){
            return;
        }
        
        product.removeAssociatedPart(part.getPartID());
        
        // reload the tableview
        loadAssotiatedPartsTableView();
    }
    
    public void LoadMainScene(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/FXMLMain.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        
        // if the save button is clicked populate the array inventory
        if( ((Button)event.getSource()).getText().equals("save")){
            
            if (!saveProduct()){
                return;
            }
        }
            
        FXMLDocumentControllerMain mainController = loader.getController();
        mainController.loadPartsTableView();
        mainController.loadProductsTableView();
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
}
