public class Vehicul {
    protected String nrInmatriculare;
    protected String marca;
    protected int nrPasageri;

    public Vehicul(){}

    public Vehicul(String nrInmatriculare, String marca, int nrPasageri) {
        this.nrInmatriculare = nrInmatriculare;
        this.marca = marca;
        this.nrPasageri = nrPasageri;
    }

    public String getNrInmatriculare() {
        return nrInmatriculare;
    }

    public void setNrInmatriculare(String nrInmatriculare) {
        this.nrInmatriculare = nrInmatriculare;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getNrPasageri() {
        return nrPasageri;
    }

    public void setNrPasageri(int nrPasageri) {
        this.nrPasageri = nrPasageri;
    }

    @Override
    public String toString() {
        return "Vehicul{" +
                "nrInmatriculare='" + nrInmatriculare + '\'' +
                ", marca='" + marca + '\'' +
                ", nrPasageri=" + nrPasageri +
                '}';
    }

    public boolean DeLux(){
        return "Mercedes".equals(this.marca) || "Audi".equals(this.marca) || "BMW".equals(this.marca);
    }

}
