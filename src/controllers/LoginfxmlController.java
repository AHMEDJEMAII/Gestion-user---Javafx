/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.Utilisateur;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import services.ServiceUtilisateurIMP;
import static utils.CodeQR.generateEAN13BarcodeImage;

/**
 * FXML Controller class
 *
 * @author ahmed_jemai
 */
public class LoginfxmlController implements Initializable {

    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;

    /**
     * Initializes the controller class.
     */
    
    // isntacier le service 
    ServiceUtilisateurIMP su = new ServiceUtilisateurIMP();
    @FXML
    private ImageView qrimage;
    
        String messageAcceuil = "Welcome to our application : contact us"
            + "\n  Contact : www.facebook.com/realelitecoders"
            + "\n my profile Contact : www.facebook.com/EsperanceSportivedeTunis";

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            // TODO
            BufferedImage buffer = generateEAN13BarcodeImage(messageAcceuil);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffer, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
            BufferedImage bImage2 = ImageIO.read(bis);
            // a modifire avec chemin
            ImageIO.write(bImage2, "png", new File("C:\\Users\\ahmed jemai\\Downloads\\ch\\ahmed\\src\\Ressources\\Docs\\Image\\wlecomeQR.png"));
            System.out.println("image created");
            qrimage.setImage(new Image("file:C:\\Users\\ahmed jemai\\Downloads\\ch\\ahmed\\src\\Ressources\\Docs\\Image\\wlecomeQR.png", 193, 200, false, false));

        } catch (Exception ex) {
             System.out.println(ex.getMessage());    
        }
    }    

    
    @FXML
    private void Login(ActionEvent event) {
        Utilisateur user = null;
        String encPass = su.encrypt(tf_password.getText());
        if (su.Authentification(tf_email.getText(), encPass) != null) {

            System.out.println(encPass);
            user = su.Authentification(tf_email.getText(), encPass);
            Utilisateur.user_connecter = user;
            if (null != user.getRoles()) {
                if (user.getIsactive().equals("desactive")) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Alert");
                    al.setContentText("Votre account est suspendue, Veuillez contacter l admininstrateur");
                    al.setHeaderText(null);
                    al.show();
                } else {
                    switch (user.getRoles()) {
                        case "[\"ROLE_USER\"]":
                            // go to admin
                            GoToUser(event);
                            System.out.println("user");
                            break;
                        case "[\"ROLE_COACH\"]":
                            // go to prestatire
                            GoToUser(event);
                            System.out.println("coach");
                            break;
                        case "[\"ROLE_NUTRITIONISTE\"]":
                            // go to fournisseur
                               GoToUser(event);
                            System.out.println("nutritioniste");
                            break;
                             case "[\"ROLE_ADMIN\"]":
                            // go to admin
                            GoToAdminDashboard(event);
                            System.out.println("admin");
                            break;
                     
                        default:
                            break;
                    }
                }
            }

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("invalid login or mot de passe");
            al.setHeaderText(null);
            al.show();

        }

    }
    
    // check if the user is banned
   public Boolean Active_account() {
        Boolean verif = true;
        List<Utilisateur> list_user = su.afficherUtilisateurs();
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

   @FXML
   private void GoToSignUp(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/signupfxml.fxml"));
            Parent root = loader.load();
            tf_email.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
   private void GoToAdminDashboard(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AdminDashboardFXML.fxml"));
            Parent root = loader.load();
            tf_email.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    
   private void GoToUser(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/clientfxml.fxml"));
            Parent root = loader.load();
            tf_email.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

   @FXML
   private void RestPassword(ActionEvent event) {
         if (null != su.FindByEmail(tf_email.getText())) {

            Utilisateur.user_connecter = su.FindByEmail(tf_email.getText());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ModifierPasswordClient.fxml"));
                Parent root = loader.load();
                tf_email.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("invalid email");
            al.setHeaderText(null);
            al.show();
        }
        
        
    }
    
    
    
    
    
    
    
}
