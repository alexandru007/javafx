/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author alisii
 */
public class FXMLAddPartController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyNameTextField;
    @FXML private RadioButton inHouseCheckBox;
    @FXML private RadioButton outSourcedCheckBox;
    @FXML private Label companyNameLabel;
    private ToggleGroup toggleGroup;
    static private int partIDCounter = 1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // when the window appears, create a toggle group and set it to radio buttons
        toggleGroup = new ToggleGroup();
        this.inHouseCheckBox.setToggleGroup(toggleGroup);
        this.outSourcedCheckBox.setToggleGroup(toggleGroup);
        
        // set the default value
        this.outSourcedCheckBox.setSelected(true);
    }
    
    // method to update the label and promt text based on radiobuton
    public void updateWindowBasedOnCheckBox (){
        if(outSourcedCheckBox.isSelected()){
            // update the label
            companyNameLabel.setText("Company name");
            companyNameTextField.setPromptText("Comp name");
        }
        else {
            companyNameLabel.setText("Machine ID");
            companyNameTextField.setPromptText("mach id");
            
        }
    }
    
    // add a new part
    public void addNewPart() {
        
        if (outSourcedCheckBox.isSelected()) {
            
            Outsourced outsourcedPart = new Outsourced();
            
            outsourcedPart.setPartID(partIDCounter++);
            outsourcedPart.setName(nameTextField.getText());
            outsourcedPart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            outsourcedPart.setPrice(Double.parseDouble(priceTextField.getText()));
            outsourcedPart.setMax(Integer.parseInt(maxTextField.getText()));
            outsourcedPart.setMin(Integer.parseInt(minTextField.getText()));
            outsourcedPart.setCompanyName(companyNameTextField.getText());

            Inventory.addPart(outsourcedPart);
        
        }
        else{
            InHouse inHousePart = new InHouse();
            inHousePart.setPartID(partIDCounter++);
            inHousePart.setName(nameTextField.getText());
            inHousePart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            inHousePart.setPrice(Double.parseDouble(priceTextField.getText()));
            inHousePart.setMax(Integer.parseInt(maxTextField.getText()));
            inHousePart.setMin(Integer.parseInt(minTextField.getText()));
            inHousePart.setMachineID(Integer.parseInt(companyNameTextField.getText()));

            Inventory.addPart(inHousePart);
        }
        
        
    }
    
    public void LoadMainScene (ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/FXMLMain.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        
        FXMLDocumentControllerMain mainController = loader.getController();
        mainController.loadPartsTableView();
        mainController.loadProductsTableView();
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
    
    public void LoadMainSceneWithUpdatedPart (ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/FXMLMain.fxml"));
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        
        // populate the array inventory
        addNewPart();
        
        FXMLDocumentControllerMain mainController = loader.getController();
        mainController.loadPartsTableView();
        mainController.loadProductsTableView();
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();  
        stage.setScene(scene);
    }
}
