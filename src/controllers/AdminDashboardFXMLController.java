/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import entities.Utilisateur;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import services.ServiceUtilisateurIMP;
import utils.MailTools;

/**
 * FXML Controller class
 *
 * @author ahmed_jemai
 */
public class AdminDashboardFXMLController implements Initializable {

    @FXML
    private TableView<Utilisateur> tableView_user;
    @FXML
    private TableColumn<Utilisateur, String> nom;
    @FXML
    private TableColumn<Utilisateur, String> prenom;
    @FXML
    private TableColumn<Utilisateur, Integer> cin;
    @FXML
    private TableColumn<Utilisateur, String> region;
    @FXML
    private TableColumn<Utilisateur, String> ville;
    @FXML
    private TableColumn<Utilisateur, String> adresse;
    @FXML
    private TableColumn<Utilisateur, String> email;
    @FXML
    private TableColumn<Utilisateur, String> isactive;
    @FXML
    private TableColumn<Utilisateur, Boolean> is_verified;
    @FXML
    private ChoiceBox<String> ChoiceBox;
    @FXML
    private TextField setPromptText;
    @FXML
    private TextField tf_cin;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_region;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_ville;
    @FXML
    private TextField tf_prenom;

    /**
     * Initializes the controller class.
     */
    // isntancier le service
    ServiceUtilisateurIMP su = new ServiceUtilisateurIMP();

    // user connecter actuellement 
    Utilisateur userConn = Utilisateur.user_connecter;
    @FXML
    private Button changerEtat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tf_nom.setText(userConn.getNom());
        tf_prenom.setText(userConn.getPrenom());
        tf_ville.setText(userConn.getVille());
        tf_region.setText(userConn.getRegion());
        tf_adresse.setText(userConn.getAdress());
        tf_cin.setText(String.valueOf(userConn.getCin()));

        ChoiceBox.getItems().addAll("Nom", "Email", "Region", "Prenom");
        ChoiceBox.setValue("Nom");
        setPromptText.setPromptText("Rechercher ");

        afficheTableView();
        filteredSearch();

    }

    @FXML
    private void btn_modifier(ActionEvent event) {
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
                user.setPhoto(userConn.getPhoto());

                
                user.setNom(tf_nom.getText());
                user.setPrenom(tf_prenom.getText());
                user.setCin(Integer.parseInt(tf_cin.getText()));
                user.setAdress(tf_adresse.getText());
                user.setVille(tf_ville.getText());
                user.setRegion(tf_region.getText());

                // user.setImage(mewImagePath);
                su.modifierUtilisateur(user, userConn.getId());

                // String path = "Users/ahmed ah,ed/Desktop/ahmed/src/Ressources/Image/" + mewImagePath;
                // image.setImage(new Image("file:/" + path, 193, 200, false, false));
                Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                resAlert.setHeaderText(null);
                resAlert.setContentText("La modification a été effectuer avec succes");
                resAlert.showAndWait();
            } else {
                // mewImagePath = userConn.getImage();
                //String path = "Users/ahmed ahmde/Desktop/ahmed/src/Ressources/Image/" + mewImagePath;
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

//        if (passwordTexte.getText().trim().equals("")) {
//            passwordTexte.setStyle(style);
//            verif = 1;
//
//        }
        if (tf_nom.getText().trim().equals("")) {
            tf_nom.setStyle(style);
            verif = 1;

        }
        if (tf_prenom.getText().trim().equals("")) {
            tf_prenom.setStyle(style);
            verif = 1;

        }
        if (tf_adresse.getText().trim().equals("")) {
            tf_adresse.setStyle(style);
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

    private void afficheTableView() {
        List users = su.afficherUtilisateurs();
        users.addAll(su.afficherUtilisateurs());
        ObservableList list = FXCollections.observableArrayList(users);
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        region.setCellValueFactory(new PropertyValueFactory<>("region"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adress"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        isactive.setCellValueFactory(new PropertyValueFactory<>("isactive"));
        is_verified.setCellValueFactory(new PropertyValueFactory<>("is_verified"));

        tableView_user.setItems(list);

    }

    public void filteredSearch() {

        List<Utilisateur> list_user = su.afficherUtilisateurs();
        ObservableList<Utilisateur> list = FXCollections.observableArrayList(list_user);
        FilteredList<Utilisateur> fluser = new FilteredList(list, p -> true);
        setPromptText.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (ChoiceBox.getValue()) {
                case "Nom":
                    fluser.setPredicate(p -> p.getNom().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Email":
                    fluser.setPredicate(p -> p.getEmail().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Region":
                    fluser.setPredicate(p -> p.getRegion().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Prenom":
                    fluser.setPredicate(p -> p.getPrenom().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
            }

        });
        tableView_user.setItems(fluser);
        ChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {
            if (newVal != null) {
                setPromptText.setText("");
            }
        });

    }

    @FXML
    private void changerEtats(ActionEvent event) {
        Utilisateur user = tableView_user.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Warning");
            al.setContentText("Selctionner un ustilisateur");
            al.setHeaderText(null);
            al.show();
        } else if (user.getIsactive().equals("active")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande de confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Est-ce que vous etes sure de votre supspension ");
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.get() == ButtonType.OK) {
                su.DescativerUser(user.getId());
             
                try {
                    MailTools.sendMail(user,"Desactivation de compte","Votre compte a ete desactivee.\n contacter l administrateur pour plus d information");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
                Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                resAlert.setHeaderText(null);
                resAlert.setContentText("La suspension a été effecuter avec sucess. Un mail a ete envoye a " + user.getNom());
                resAlert.showAndWait();
            } else {

                alert.close();
            }

        } else if (user.getIsactive().equals("desactive")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Demande de confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Est-ce que vous etes sure de votre activation ");
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.get() == ButtonType.OK) {
                su.ActiverUser(user.getId());
             
                try {
                     MailTools.sendMail(user,"Activation de compte","Votre compte a ete reactivee.\n Vous pouvez vous connecter");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                   
                Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                resAlert.setHeaderText(null);
                resAlert.setContentText("L activation a été effecuter avec sucess. Un mail a ete envoye a " + user.getNom());
                resAlert.showAndWait();
            } else {

                alert.close();
            }

        }

        afficheTableView();

    }

    @FXML
    private void GoToStatistic(ActionEvent event) {
           try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/statfxml.fxml"));
            Parent root = loader.load();
            tf_prenom.getScene().setRoot(root);
        } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
    }

    @FXML
    private void logout(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginfxml.fxml"));
            Parent root = loader.load();
            tf_prenom.getScene().setRoot(root);
        } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
    }

    @FXML
    private void exportToCsv(ActionEvent event) {
               FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File outputFile = fileChooser.showSaveDialog(tableView_user.getScene().getWindow());
        
        if (outputFile != null) {
            try {
                // Open a FileWriter for the output file
                FileWriter writer = new FileWriter(outputFile);
                
                // Write the header row
                writer.write("Column 1,Column 2,Column 3\n");
                
                // Write the data rows
                for (Utilisateur item : tableView_user.getItems()) {
                    String row = String.format("%s,%s,%s\n", item.getEmail(), item.getCin(), item.getNom());
                    writer.write(row);
                }
                
                // Close the FileWriter
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
