package Models.ModelesTabels;

public class ProjetTable {

    private int id;
    private String nom;
    private String site;
    private String cf;
    private String date;
    private int nbLogts;
    private String coutR;
    private String  coutE;
    private String coutV;
    private String ttcAvnantR;
    private String ttcAvnantE;
    private String ttcAvnantV;

    public ProjetTable() {
    }

    public ProjetTable(int id, String nom, String site, String cf, String date, int nbLogts, String coutR, String coutE, String coutV, String ttcAvnantR, String ttcAvnantE, String ttcAvnantV) {
        this.id = id;
        this.nom = nom;
        this.site = site;
        this.cf = cf;
        this.date = date;
        this.nbLogts = nbLogts;
        this.coutR = coutR;
        this.coutE = coutE;
        this.coutV = coutV;
        this.ttcAvnantR = ttcAvnantR;
        this.ttcAvnantE = ttcAvnantE;
        this.ttcAvnantV = ttcAvnantV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNbLogts() {
        return nbLogts;
    }

    public void setNbLogts(int nbLogts) {
        this.nbLogts = nbLogts;
    }

    public String getCoutR() {
        return coutR;
    }

    public void setCoutR(String coutR) {
        this.coutR = coutR;
    }

    public String getCoutE() {
        return coutE;
    }

    public void setCoutE(String coutE) {
        this.coutE = coutE;
    }

    public String getCoutV() {
        return coutV;
    }

    public void setCoutV(String coutV) {
        this.coutV = coutV;
    }

    public String getTtcAvnantR() {
        return ttcAvnantR;
    }

    public void setTtcAvnantR(String ttcAvnantR) {
        this.ttcAvnantR = ttcAvnantR;
    }

    public String getTtcAvnantE() {
        return ttcAvnantE;
    }

    public void setTtcAvnantE(String ttcAvnantE) {
        this.ttcAvnantE = ttcAvnantE;
    }

    public String getTtcAvnantV() {
        return ttcAvnantV;
    }

    public void setTtcAvnantV(String ttcAvnantV) {
        this.ttcAvnantV = ttcAvnantV;
    }
}
