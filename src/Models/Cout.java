package Models;

public class Cout {

    private int id;
    private int idProjet;
    private String type;
    private double montant;

    public Cout() {
    }

    public Cout(int id, int idProjet, String type, double montant) {
        this.id = id;
        this.idProjet = idProjet;
        this.type = type;
        this.montant = montant;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
