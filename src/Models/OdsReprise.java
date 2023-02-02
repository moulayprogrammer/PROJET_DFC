package Models;

import java.time.LocalDate;

public class OdsReprise {

    private int id;
    private int idConvention;
    private String number;
    private LocalDate date;

    public OdsReprise() {
    }

    public OdsReprise(int id, int idConvention, String number, LocalDate date) {
        this.id = id;
        this.idConvention = idConvention;
        this.number = number;
        this.date = date;
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
}
