package Models;

public class Organism {

    private int id;
    private String raisonSocial;
    private String adresse;
    private String tel;
    private String rc;
    private String nif;

    public Organism() {
    }

    public Organism(int id, String raisonSocial, String adresse, String tel, String rc, String nif) {
        this.id = id;
        this.raisonSocial = raisonSocial;
        this.adresse = adresse;
        this.tel = tel;
        this.rc = rc;
        this.nif = nif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
