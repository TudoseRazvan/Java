import java.io.Serializable;

public class Trotinete implements Comparable<Trotinete>, Serializable {
    protected String idTrotineta;
    protected float distantaTotala;
    protected float vitezaMedie;
    protected float vitezaMaxima;

    public Trotinete(){
    }

    public Trotinete(String idTrotineta, float distantaTotala, float vitezaMedie, float vitezaMaxima) {
        this.idTrotineta = idTrotineta;
        this.distantaTotala = distantaTotala;
        this.vitezaMedie = vitezaMedie;
        this.vitezaMaxima = vitezaMaxima;
    }

    public String getIdTrotineta() {
        return idTrotineta;
    }

    public void setIdTrotineta(String idTrotineta) {
        this.idTrotineta = idTrotineta;
    }

    public float getDistantaTotala() {
        return distantaTotala;
    }

    public void setDistantaTotala(float distantaTotala) {
        this.distantaTotala = distantaTotala;
    }

    public float getVitezaMedie() {
        return vitezaMedie;
    }

    public void setVitezaMedie(float vitezaMedie) {
        this.vitezaMedie = vitezaMedie;
    }

    public float getVitezaMaxima() {
        return vitezaMaxima;
    }

    public void setVitezaMaxima(float vitezaMaxima) {
        this.vitezaMaxima = vitezaMaxima;
    }

    @Override
    public String toString() {
        return "Trotinete{" +
                "idTrotineta='" + idTrotineta + '\'' +
                ", distantaTotala=" + distantaTotala +
                ", vitezaMedie=" + vitezaMedie +
                ", vitezaMaxima=" + vitezaMaxima +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trotinete trotinete = (Trotinete) o;

        return Math.abs(this.distantaTotala - trotinete.distantaTotala) < 10;
    }

    @Override
    public int compareTo(Trotinete o) {
        if(Math.abs(this.distantaTotala - o.distantaTotala) < 10) {
            return 0;
        } else if (this.distantaTotala > o.distantaTotala) {
            return 1;
        }
        return -1;
    }



}
