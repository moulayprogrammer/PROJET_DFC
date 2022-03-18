package Models;

import java.time.LocalDate;

public class OrderePaiment {

    private int id;
    private int idFacture;
    private String numero;
    private LocalDate date;
    private  double montant;
    private  double penaliteRotarde;
    private  double retuneGarante;

    public OrderePaiment() {
    }

    public OrderePaiment(int id, int idFacture, String numero, LocalDate date, double montant, double penaliteRotarde, double retuneGarante) {
        this.id = id;
        this.idFacture = idFacture;
        this.numero = numero;
        this.date = date;
        this.montant = montant;
        this.penaliteRotarde = penaliteRotarde;
        this.retuneGarante = retuneGarante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
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

    public double getPenaliteRotarde() {
        return penaliteRotarde;
    }

    public void setPenaliteRotarde(double penaliteRotarde) {
        this.penaliteRotarde = penaliteRotarde;
    }

    public double getRetuneGarante() {
        return retuneGarante;
    }

    public void setRetuneGarante(double retuneGarante) {
        this.retuneGarante = retuneGarante;
    }
}
