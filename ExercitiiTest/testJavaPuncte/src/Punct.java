import java.lang.Math;

public class Punct implements Comparable<Punct>{
    protected String etichetaFigura;
    protected String etichetaPunct;
    protected Double coordOx;
    protected Double coordOy;

    public Punct(String etichetaFigura, String etichetaPunct, double coordOx, double coordOy) {
        this.etichetaFigura = etichetaFigura;
        this.etichetaPunct = etichetaPunct;
        this.coordOx = coordOx;
        this.coordOy = coordOy;
    }

    public Punct() {
    }

    public String getEtichetaFigura() {
        return etichetaFigura;
    }

    public void setEtichetaFigura(String etichetaFigura) {
        this.etichetaFigura = etichetaFigura;
    }

    public String getEtichetaPunct() {
        return etichetaPunct;
    }

    public void setEtichetaPunct(String etichetaPunct) {
        this.etichetaPunct = etichetaPunct;
    }

    public double getCoordOx() {
        return coordOx;
    }

    public void setCoordOx(double coordOx) {
        this.coordOx = coordOx;
    }

    public double getCoordOy() {
        return coordOy;
    }

    public void setCoordOy(double coordOy) {
        this.coordOy = coordOy;
    }

    @Override
    public String toString() {
        return "Punct{" +
                "etichetaFigura='" + etichetaFigura + '\'' +
                ", etichetaPunct='" + etichetaPunct + '\'' +
                ", coordOx=" + coordOx +
                ", coordOy=" + coordOy +
                '}';
    }

    public Double distanta(){
        return Math.sqrt((Math.pow(coordOx,2) + Math.pow(coordOy,2)));
    }

    @Override
    public int compareTo(Punct p) {
        if(this.distanta().equals(p.distanta())){
            return 0;
        } else if (this.distanta() > p.distanta()) {
            return 1;
        }
        return -1;
    }
}
