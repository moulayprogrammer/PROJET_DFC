package Models;

import java.time.LocalDate;

public class Facture {

    private int id;
    private int idMarConBc;
    private String numero;
    private LocalDate date;
    private double montant;

    public Facture() {
    }

    public Facture(int id, int idMarConBc, String numero, LocalDate date, double montant) {
        this.id = id;
        this.idMarConBc = idMarConBc;
        this.numero = numero;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
