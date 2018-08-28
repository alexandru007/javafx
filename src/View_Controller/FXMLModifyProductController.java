/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alisii
 */
public class FXMLModifyProductController implements Initializable {

    
    Product product = new Product();
    
    // buttons
    @FXML private Button saveButton;
    
    
    // add product, left side
    @FXML private TextField productID;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    
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
            
    }
    
    public void initData(Product product) {
        
        this.product = product;
        
        // populate product text fields
        int partID = this.product.getProductID();
        productID.setText(Integer.toString(partID));
        
        String name = this.product.getName();
        nameTextField.setText(name);
        
        int inventory = this.product.getInStock();
        inventoryTextField.setText(Integer.toString(inventory));
        
        double price = this.product.getPrice();
        priceTextField.setText(Double.toString(price));
        
        int max = this.product.getMax();
        maxTextField.setText(Integer.toString(max));
        
        int min = this.product.getMin();
        minTextField.setText(Integer.toString(min));
        
        // load parts
        loadPartsTableView();
        
        // load associated parts
        loadAssotiatedPartsTableView();
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
        if (nameTextField.getText().equals("")) {
                dialogBoxAlert("Name cannot be empty");
                return false;
        }
        
        // check if the fields are empty
        if ( priceTextField.getText().equals("") ) {
                dialogBoxAlert("Price cannot be empty");
                return false;
        }
        
        // check if the fields are empty
        if (inventoryTextField.getText().equals("")) {
                dialogBoxAlert("Inventory cannot be empty");
                return false;
        }

        //Product product = new Product();
        product.setName(nameTextField.getText());
        product.setInStock(Integer.parseInt(inventoryTextField.getText()));
        product.setPrice(Double.parseDouble(priceTextField.getText()));
        product.setMax(Integer.parseInt(maxTextField.getText()));
        product.setMin(Integer.parseInt(minTextField.getText()));
        
        // check if the price of the product is not less that the parts price
        Double productPrice = Double.parseDouble(priceTextField.getText());
        Double partsPrice = 0.0;
        
        for (Part part : product.getAssociatedPars()){
            partsPrice += part.getPrice();
        }

        if (productPrice < partsPrice) {
            dialogBoxAlert("Product price cannot be less than parts price");
            return false; 
        }
        
        // check if the product has any associated parts
        if (product.getAssociatedPars().isEmpty()){
            
            dialogBoxAlert("Products must have at least one part");
            return false;
        }
        
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
        
        if (!dialogBoxConfirmation("Are you sure you want to remove the item?"))
                return;
        
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
        else{
            if (!dialogBoxConfirmation("Are you sure you want to cancel?"))
            return;
        }
            
        FXMLDocumentControllerMain mainController = loader.getController();
        mainController.loadPartsTableView();
        mainController.loadProductsTableView();
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
    
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
    
    public boolean dialogBoxConfirmation(String alertText) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertText, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
        
        return false;
    }
    
    public void dialogBoxAlert(String alertText) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(alertText);
        alert.showAndWait();
    }
    
    
}
