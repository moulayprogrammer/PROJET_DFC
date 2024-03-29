package Models;

import java.time.LocalDate;

public class AvnentCout {

    private int id;
    private int idCout;
    private String coutApplique;
    private LocalDate date;
    private String type;
    private double montant;

    public AvnentCout() {
    }

    public AvnentCout(int idCout, String coutApplique, LocalDate date, String type) {
        this.idCout = idCout;
        this.coutApplique = coutApplique;
        this.date = date;
        this.type = type;
    }

    public AvnentCout(int id, int idCout, String coutApplique, LocalDate date, String type, double montant) {
        this.id = id;
        this.idCout = idCout;
        this.coutApplique = coutApplique;
        this.date = date;
        this.type = type;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCout() {
        return idCout;
    }

    public void setIdCout(int idCout) {
        this.idCout = idCout;
    }

    public String getCoutApplique() {
        return coutApplique;
    }

    public void setCoutApplique(String coutApplique) {
        this.coutApplique = coutApplique;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
