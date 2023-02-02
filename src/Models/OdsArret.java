package Models;

import java.time.LocalDate;

public class OdsArret {

    private int id;
    private int idConvention;
    private String number;
    private LocalDate date;
    private String raison;

    public OdsArret() {
    }

    public OdsArret(int id, int idConvention, String number, LocalDate date, String raison) {
        this.id = id;
        this.idConvention = idConvention;
        this.number = number;
        this.date = date;
        this.raison = raison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConvention() {
        return idConvention;
    }

    public void setIdConvention(int idConvention) {
        this.idConvention = idConvention;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }
}
