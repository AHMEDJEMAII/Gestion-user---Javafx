/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author ahmed_jemai
 */
public class Utilisateur {
    
    
    // les attributs 
    
    private int id;
    private String email;
    private String roles;
    private String password;
    private String nom;
    private String prenom;
    private String photo;
    private int cin;
    private String region;
    private String ville;
    private String adress;
    private String isactive;
    private Boolean is_verified;
    
    
    // les constructeurs

    public Utilisateur() {
    }

    public Utilisateur(int id, String email, String roles, String password, String nom, String prenom, String photo, int cin, String region, String ville, String adress, String isactive, Boolean is_verified) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.cin = cin;
        this.region = region;
        this.ville = ville;
        this.adress = adress;
        this.isactive = isactive;
        this.is_verified = is_verified;
    }

    public Utilisateur(String email, String roles, String password, String nom, String prenom, String photo, int cin, String region, String ville, String adress, String isactive, Boolean is_verified) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.cin = cin;
        this.region = region;
        this.ville = ville;
        this.adress = adress;
        this.isactive = isactive;
        this.is_verified = is_verified;
    }
    
    
    
    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }
    
    // ToString

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", email=" + email + ", roles=" + roles + ", password=" + password + ", nom=" + nom + ", prenom=" + prenom + ", photo=" + photo + ", cin=" + cin + ", region=" + region + ", ville=" + ville + ", adress=" + adress + ", isactive=" + isactive + ", is_verified=" + is_verified + '}';
    }
    
    
    
    // user actuellement connecter
    
            public static Utilisateur user_connecter;

    
    
}
