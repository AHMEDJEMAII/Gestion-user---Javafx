/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import services.ServiceUtilisateurIMP;

/**
 * FXML Controller class
 *
 * @author ahmed_jemai
 */
public class ClientfxmlController implements Initializable {

    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_region;
    @FXML
    private TextField tf_prenom;
    @FXML
    private TextField tf_ville;
    @FXML
    private TextField tf_cin;
    @FXML
    private TextField tf_adresse;

    /**
     * Initializes the controller class.
     */
    ServiceUtilisateurIMP su = new ServiceUtilisateurIMP();
    Utilisateur userConn = Utilisateur.user_connecter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tf_nom.setText(userConn.getNom());
        tf_prenom.setText(userConn.getPrenom());
        tf_ville.setText(userConn.getVille());
        tf_region.setText(userConn.getRegion());
        tf_adresse.setText(userConn.getAdress());
        tf_cin.setText(String.valueOf(userConn.getCin()));
    }

    @FXML
    private void modifierClinet(ActionEvent event) {

        Utilisateur user = new Utilisateur();
        if (VerifUserChamps() && validateNumberCIN()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande de confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Est-ce que vous etes sur de votre modification");
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.get() == ButtonType.OK) {

                user.setEmail(userConn.getEmail());
                user.setPassword(userConn.getPassword());
                user.setRoles(userConn.getRoles());
                user.setIs_verified(userConn.getIs_verified());
                user.setIsactive(userConn.getIsactive());
                user.setRoles(userConn.getRoles());
                user.setPhoto(userConn.getPhoto());

                user.setNom(tf_nom.getText());
                user.setPrenom(tf_prenom.getText());
                user.setCin(Integer.parseInt(tf_cin.getText()));
                user.setAdress(tf_adresse.getText());
                user.setVille(tf_ville.getText());
                user.setRegion(tf_region.getText());

                su.modifierUtilisateur(user, userConn.getId());

                Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                resAlert.setHeaderText(null);
                resAlert.setContentText("La modification a été effectuer avec succes");
                resAlert.showAndWait();
            } else {
                tf_nom.setText(userConn.getNom());
                tf_prenom.setText(userConn.getPrenom());
                tf_ville.setText(userConn.getVille());
                tf_region.setText(userConn.getRegion());
                tf_adresse.setText(userConn.getAdress());
                tf_cin.setText(String.valueOf(userConn.getCin()));

                alert.close();
            }

        }

    }

    @FXML
    private void changepassword(ActionEvent event) {
      
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/changerpasswordfxml.fxml"));
            Parent root = loader.load();
            tf_adresse.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginfxml.fxml"));
            Parent root = loader.load();
            tf_adresse.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean validateNumberCIN() {

        Pattern p = Pattern.compile("[0-9]+\\.[0-9]+|[0-9]+");
        Matcher m = p.matcher(tf_cin.getText());
        if (m.find() && m.group().equals(tf_cin.getText()) && tf_cin.getText().length() == 8) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("cin number only can containes 8 didgets");
            al.setHeaderText(null);
            al.show();

        }
        return false;
    }

    public Boolean VerifUserChamps() {

        int verif = 0;

        String style = " -fx-border-color: red;";
        // i will make it better 

        if (tf_adresse.getText().trim().equals("")) {
            tf_adresse.setStyle(style);
            verif = 1;

        }
        if (tf_cin.getText().trim().equals("")) {
            tf_cin.setStyle(style);
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
        if (tf_region.getText().trim().equals("")) {
            tf_region.setStyle(style);
            verif = 1;

        }
        if (tf_ville.getText().trim().equals("")) {
            tf_ville.setStyle(style);
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

}
