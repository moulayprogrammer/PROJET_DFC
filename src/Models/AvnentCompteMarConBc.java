package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AvnentCompteMarConBc {

    private int id;
    private int idMarConBc;
    private String numero;
    private LocalDate date;
    private String compteNumero;
    private String compteBank;
    private String compteAgence;
    private LocalDateTime creeLe;
    private LocalDateTime UpdateLe;

    public AvnentCompteMarConBc() {
    }

    public AvnentCompteMarConBc(int id, int idMarConBc, String numero, LocalDate date, String compteNumero, String compteBank, String compteAgence, LocalDateTime creeLe, LocalDateTime updateLe) {
        this.id = id;
        this.idMarConBc = idMarConBc;
        this.numero = numero;
        this.date = date;
        this.compteNumero = compteNumero;
        this.compteBank = compteBank;
        this.compteAgence = compteAgence;
        this.creeLe = creeLe;
        UpdateLe = updateLe;
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

    public LocalDateTime getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDateTime creeLe) {
        this.creeLe = creeLe;
    }

    public LocalDateTime getUpdateLe() {
        return UpdateLe;
    }

    public void setUpdateLe(LocalDateTime updateLe) {
        UpdateLe = updateLe;
    }
}
