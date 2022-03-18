package Models.ModelesTabels;

public class ProjetProgrammeList {

    private int id;
    private String code;
    private String Nom;
    private String cd;

    public ProjetProgrammeList() {
    }

    public ProjetProgrammeList(String code, String nom, String cd) {
        this.code = code;
        Nom = nom;
        this.cd = cd;
    }

    public ProjetProgrammeList(int id, String code, String nom, String cd) {
        this.id = id;
        this.code = code;
        Nom = nom;
        this.cd = cd;
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

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }
}
