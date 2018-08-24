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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLAddProductController implements Initializable {

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
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    public void addProduct() {
        
        Product product = new Product();
        product.setProductID(productIDCounter++);
        product.setName(nameTextField.getText());
        product.setInStock(Integer.parseInt(inventoryTextField.getText()));
        product.setPrice(Double.parseDouble(priceTextField.getText()));
        product.setMax(Integer.parseInt(maxTextField.getText()));
        product.setMin(Integer.parseInt(minTextField.getText()));
        
        //TO DO
        // add associated parts
        
        Inventory.addProduct(product);
    }
    
    public void LoadMainSceneWithUpdatedPart (ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/FXMLMain.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        
        // populate the array inventory
        addProduct();
        
        FXMLDocumentControllerMain mainController = loader.getController();
        mainController.loadPartsTableView();
        mainController.loadProductsTableView();
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
    
}
