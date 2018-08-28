/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alisii
 */
public class FXMLModifyPartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField IDTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyNameTextField;
    @FXML private RadioButton inHouseCheckBox;
    @FXML private RadioButton outSourcedCheckBox;
    @FXML private Label companyNameLabel;
    
    private Part part;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // method to display the part details on the screen
    public void initData(Part part) {
        
        this.part = part;
        IDTextField.setText((Integer.toString(this.part.getPartID())));
        nameTextField.setText(this.part.getName());
        inventoryTextField.setText((Integer.toString(this.part.getInStock())));
        priceTextField.setText(Double.toString(this.part.getPrice()));
        maxTextField.setText(Integer.toString(this.part.getMax()));
        minTextField.setText(Integer.toString(this.part.getMin()));
        
        // to update the inhouse or outsourced, check what type of object is the part
        if (part instanceof InHouse) {
            inHouseCheckBox.setSelected(true);
            companyNameLabel.setText("Machine ID");
            
            int machineID = ((InHouse) this.part ).getMachineID();          
            companyNameTextField.setText(Integer.toString(machineID));
        }
        else {
            outSourcedCheckBox.setSelected(true);
            // label stays the same
            // change the text field text
            String companyName = ((Outsourced) this.part).getCompanyName();
            companyNameTextField.setText(companyName);
        }
        
    }
    
    public void modifyPart() {
        
        // get all the changes
        if (outSourcedCheckBox.isSelected()) {
            
            Outsourced outsourcedPart = new Outsourced();
            
            outsourcedPart.setPartID(this.part.getPartID());
            outsourcedPart.setName(nameTextField.getText());
            outsourcedPart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            outsourcedPart.setPrice(Double.parseDouble(priceTextField.getText()));
            outsourcedPart.setMax(Integer.parseInt(maxTextField.getText()));
            outsourcedPart.setMin(Integer.parseInt(minTextField.getText()));
            outsourcedPart.setCompanyName(companyNameTextField.getText());

            Inventory.updatePart(this.part.getPartID(), outsourcedPart);
        
        }
        else{
            InHouse inHousePart = new InHouse();
            inHousePart.setPartID(this.part.getPartID());
            inHousePart.setName(nameTextField.getText());
            inHousePart.setInStock(Integer.parseInt(inventoryTextField.getText()));
            inHousePart.setPrice(Double.parseDouble(priceTextField.getText()));
            inHousePart.setMax(Integer.parseInt(maxTextField.getText()));
            inHousePart.setMin(Integer.parseInt(minTextField.getText()));
            inHousePart.setMachineID(Integer.parseInt(companyNameTextField.getText()));

            Inventory.updatePart(this.part.getPartID(), inHousePart);
        }
    }
    
    public void LoadMainScene (ActionEvent event) throws IOException {
        
        // get the hold of the stage (window of the button) 
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow(); 
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/FXMLMain.fxml"));  
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
    }
    
    public void LoadMainSceneWithUpdatedPart (ActionEvent event) throws IOException {
        
        if(!dialogBoxConfirmation("Are you sure you want to cancel?"))
            return;
        
        // before loading the main window, update the part that was recieved
        modifyPart();
        
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
    
    public boolean dialogBoxConfirmation(String alertText) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertText, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return true;
        }
        
        return false;
    }
}
