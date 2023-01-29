package Models;

import java.time.LocalDate;

public class AvnentMarConBc {

    private int id;
    private int idMarConBc;
    private String type;
    private LocalDate date;
    private double montant;

    public AvnentMarConBc() {
    }

    public AvnentMarConBc(int id, int idMarConBc, String type, LocalDate date, double montant) {
        this.id = id;
        this.idMarConBc = idMarConBc;
        this.type = type;
        this.date = date;
        this.montant = montant;
    }

    public AvnentMarConBc(int id, String type, LocalDate date, double montant) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMarConBc() {
        return idMarConBc;
    }

    public void setIdMarConBc(int idMarConBc) {
        this.idMarConBc = idMarConBc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
