package Models;

import java.time.LocalDateTime;

public class Programme {

    private int id;
    private String code;
    private String nomProgramme;
    private int nombreLogts;
    private String numeroCD;
    private String dateInscription;
    private LocalDateTime addDate;
    private LocalDateTime updateDate;


    public Programme() {
    }

    public Programme(String code, String nomProgramme, int nombreLogts, String numeroCD, String dateInscription) {
        this.code = code;
        this.nomProgramme = nomProgramme;
        this.nombreLogts = nombreLogts;
        this.numeroCD = numeroCD;
        this.dateInscription = dateInscription;
    }

    public Programme(int id, String code, String nomProgramme, int nombreLogts, String numeroCD, String dateInscription) {
        this.id = id;
        this.code = code;
        this.nomProgramme = nomProgramme;
        this.nombreLogts = nombreLogts;
        this.numeroCD = numeroCD;
        this.dateInscription = dateInscription;
    }

    public Programme(int id, String code, String nomProgramme, int nombreLogts, String numeroCD, String dateInscription, LocalDateTime addDate, LocalDateTime updateDate) {
        this.id = id;
        this.code = code;
        this.nomProgramme = nomProgramme;
        this.nombreLogts = nombreLogts;
        this.numeroCD = numeroCD;
        this.dateInscription = dateInscription;
        this.addDate = addDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomProgramme() {
        return nomProgramme;
    }

    public void setNomProgramme(String nomProgramme) {
        this.nomProgramme = nomProgramme;
    }

    public int getNombreLogts() {
        return nombreLogts;
    }

    public void setNombreLogts(int nombreLogts) {
        this.nombreLogts = nombreLogts;
    }

    public String getNumeroCD() {
        return numeroCD;
    }

    public void setNumeroCD(String numeroCD) {
        this.numeroCD = numeroCD;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
