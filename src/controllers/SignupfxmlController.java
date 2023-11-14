/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import services.ServiceUtilisateurIMP;

/**
 * FXML Controller class
 *
 * @author ahmed_jemai
 */
public class SignupfxmlController implements Initializable {
    
    
    @FXML
    private TextField tf_ville;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_prenom;
    @FXML
    private TextField tf_region;
    @FXML
    private TextField tf_cin;
    @FXML
    private TextField tf_aadresse;
    @FXML
    private TextField tf_photo;

    /**
     * Initializes the controller class.
     */
      private List<String> listfiles;
    
    // isntancier le service 
    ServiceUtilisateurIMP service_user = new ServiceUtilisateurIMP();
    @FXML
    private ImageView ImageUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listfiles = new ArrayList<>();
        listfiles.add("*.png");
        listfiles.add("*.jpg");
    }    
    
    @FXML
    private void RegisterAction(ActionEvent event) {
        
        Utilisateur user = new Utilisateur();
        if (CheckLogin() && VerifUserChamps() && validateNumberCIN() && ValidateEmail()) {
            
            user.setEmail(tf_email.getText());
            user.setPassword(service_user.encrypt(tf_password.getText()));
            user.setRoles("[\"ROLE_USER\"]");
            user.setNom(tf_nom.getText());
            
            user.setPrenom(tf_prenom.getText());
            
            user.setPhoto(tf_photo.getText());
            
            user.setCin(Integer.parseInt(tf_cin.getText()));
            user.setRegion(tf_region.getText());
            
            user.setVille(tf_ville.getText());
            
            user.setAdress(tf_aadresse.getText());
            
            user.setIsactive("active");
            user.setIs_verified(true);
            
            service_user.ajoutUtilisateur(user);
            Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
            resAlert.setHeaderText(null);
            resAlert.setContentText("Account a ete cree avec sucess");
            resAlert.showAndWait();
            GoToLogin(event);

        }
        
    }

    // tester si l email est unique 
    public Boolean CheckLogin() {
        Boolean verif = true;
        List<Utilisateur> list_user = service_user.afficherUtilisateurs();
        for (int i = 0; i < list_user.size(); i++) {
            if (list_user.get(i).getEmail().equals(tf_email.getText())) {
                verif = false;
                
            }
            
        }
        if (verif == false) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("User login existe déja");
            al.setHeaderText(null);
            al.show();
        }
        
        return verif;
    }
    
    public boolean ValidateEmail() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$");
        Matcher m = p.matcher(tf_email.getText());
        if (m.find() && m.group().equals(tf_email.getText())) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email is wrong");
            al.setHeaderText(null);
            al.show();
            
        }
        return false;
    }
    
    public boolean validateNumberCIN() {
        
        Pattern p = Pattern.compile("[0-9]+\\.[0-9]+|[0-9]+");
        Matcher m = p.matcher(tf_cin.getText());
        if (m.find() && m.group().equals(tf_cin.getText()) && tf_cin.getText().length() == 8) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("cin only can containes 8 didgets");
            al.setHeaderText(null);
            al.show();
            
        }
        return false;
    }
    
    public Boolean VerifUserChamps() {
        
        int verif = 0;
        
        String style = " -fx-border-color: red;";
        
        if (tf_email.getText().trim().equals("")) {
            tf_email.setStyle(style);
            verif = 1;
            
        }
        if (tf_password.getText().trim().equals("")) {
            tf_password.setStyle(style);
            verif = 1;
            
        }
        if (tf_nom.getText().trim().equals("")) {
            tf_nom.setStyle(style);
            verif = 1;
            
        }
        if (tf_prenom.getText().trim().equals("")) {
            tf_prenom.setStyle(style);
            verif = 1;
            
        }
        if (tf_ville.getText().trim().equals("")) {
            tf_ville.setStyle(style);
            verif = 1;
            
        }
        if (tf_region.getText().trim().equals("")) {
            tf_region.setStyle(style);
            verif = 1;
            
        }
        if (tf_cin.getText().trim().equals("")) {
            tf_cin.setStyle(style);
            verif = 1;
            
        }
        if (tf_aadresse.getText().trim().equals("")) {
            tf_aadresse.setStyle(style);
            verif = 1;
            
        }
        
        if (verif == 0) {
            return true;
        }
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Alert");
        al.setContentText("Verifier les champs");
        al.setHeaderText(null);
        al.show();
        
        return false;
        
    }
    
    private void GoToLogin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginfxml.fxml"));
            Parent root = loader.load();
            tf_email.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void backLogin(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginfxml.fxml"));
            Parent root = loader.load();
            tf_email.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
}
