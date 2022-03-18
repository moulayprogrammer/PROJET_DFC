package Models;


public class MarConBc {

    private int id;
    private int idProjet;
    private int idOrganisme;
    private String nomOrganisme;
    private String nom;
    private String type;
    private String numero;
    private int numbreLogts;
    private String date;
    private double ht;
    private double tva;
    private double ttc;
    private String compteNumero;
    private String compteBank;
    private String compteAgence;

    public MarConBc() {
    }

    public MarConBc(int id) {
        this.id = id;
    }

    public MarConBc(int id, int idProjet, int idOrganisme, String nom, String type, String numero, int numbreLogts, String date,
                    double ht, double tva, double ttc) {
        this.id = id;
        this.idProjet = idProjet;
        this.idOrganisme = idOrganisme;
        this.nom = nom;
        this.type = type;
        this.numero = numero;
        this.numbreLogts = numbreLogts;
        this.date = date;
        this.ht = ht;
        this.tva = tva;
        this.ttc = ttc;
    }

    public MarConBc(int id, int idProjet, int idOrganisme, String nom, String type, String numero, int numbreLogts, String date, double ht,
                    double tva, double ttc, String compteNumero, String compteBank, String compteAgence) {
        this.id = id;
        this.idProjet = idProjet;
        this.idOrganisme = idOrganisme;
        this.nom = nom;
        this.type = type;
        this.numero = numero;
        this.numbreLogts = numbreLogts;
        this.date = date;
        this.ht = ht;
        this.tva = tva;
        this.ttc = ttc;
        this.compteNumero = compteNumero;
        this.compteBank = compteBank;
        this.compteAgence = compteAgence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public int getIdOrganisme() {
        return idOrganisme;
    }

    public void setIdOrganisme(int idOrganisme) {
        this.idOrganisme = idOrganisme;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getNumbreLogts() {
        return numbreLogts;
    }

    public void setNumbreLogts(int numbreLogts) {
        this.numbreLogts = numbreLogts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHt() {
        return ht;
    }

    public void setHt(double ht) {
        this.ht = ht;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public double getTtc() {
        return ttc;
    }

    public void setTtc(double ttc) {
        this.ttc = ttc;
    }

    public String getCompteNumero() {
        return compteNumero;
    }

    public void setCompteNumero(String compteNumero) {
        this.compteNumero = compteNumero;
    }

    public String getCompteBank() {
        return compteBank;
    }

    public void setCompteBank(String compteBank) {
        this.compteBank = compteBank;
    }

    public String getCompteAgence() {
        return compteAgence;
    }

    public void setCompteAgence(String compteAgence) {
        this.compteAgence = compteAgence;
    }

    public String getNomOrganisme() {
        return nomOrganisme;
    }

    public void setNomOrganisme(String nomOrganisme) {
        this.nomOrganisme = nomOrganisme;
    }
}
