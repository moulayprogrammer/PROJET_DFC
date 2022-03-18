package Models;

public class Projet {

    private int id;
    private int idProgramme;
    private String nom;
    private String site;
    private int nomberLogts;
    private String numeroCF;
    private String dateInsription;

    public Projet() {
    }

    public Projet(int id) {
        this.id = id;
    }

    public Projet(int id, int idProgramme, String nom, int nomberLogts, String numeroCF, String dateInsription) {
        this.id = id;
        this.idProgramme = idProgramme;
        this.nom = nom;
        this.nomberLogts = nomberLogts;
        this.numeroCF = numeroCF;
        this.dateInsription = dateInsription;
    }

    public Projet(int id, int idProgramme, String nom, String site, int nomberLogts, String numeroCF, String dateInsription) {
        this.id = id;
        this.idProgramme = idProgramme;
        this.nom = nom;
        this.site = site;
        this.nomberLogts = nomberLogts;
        this.numeroCF = numeroCF;
        this.dateInsription = dateInsription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProgramme() {
        return idProgramme;
    }

    public void setIdProgramme(int idProgramme) {
        this.idProgramme = idProgramme;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNomberLogts() {
        return nomberLogts;
    }

    public void setNomberLogts(int nomberLogts) {
        this.nomberLogts = nomberLogts;
    }

    public String getNumeroCF() {
        return numeroCF;
    }

    public void setNumeroCF(String numeroCF) {
        this.numeroCF = numeroCF;
    }

    public String getDateInsription() {
        return dateInsription;
    }

    public void setDateInsription(String dateInsription) {
        this.dateInsription = dateInsription;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
