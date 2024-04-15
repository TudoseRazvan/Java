public class Punct implements Comparable<Punct> {
    protected String etichetaFigurii;
    protected String etichetaPunct;
    protected Double coordOx;
    protected Double coordOy;


    public Punct(String etichetaFigurii, String etichetaPunct, Double coordOx, Double coordOy) {
        this.etichetaFigurii = etichetaFigurii;
        this.etichetaPunct = etichetaPunct;
        this.coordOx = coordOx;
        this.coordOy = coordOy;
    }
    public Punct(){}
    public String getEtichetaFigurii() {
        return etichetaFigurii;
    }

    public void setEtichetaFigurii(String etichetaFigurii) {
        this.etichetaFigurii = etichetaFigurii;
    }

    public String getEtichetaPunct() {
        return etichetaPunct;
    }

    public void setEtichetaPunct(String etichetaPunct) {
        this.etichetaPunct = etichetaPunct;
    }

    public Double getCoordOx() {
        return coordOx;
    }

    public void setCoordOx(Double coordOx) {
        this.coordOx = coordOx;
    }

    public Double getCoordOy() {
        return coordOy;
    }

    public void setCoordOy(Double coordOy) {
        this.coordOy = coordOy;
    }

    @Override
    public String toString() {
        return "Punct{" +
                "etichetaFigurii='" + etichetaFigurii + '\'' +
                ", etichetaPunct='" + etichetaPunct + '\'' +
                ", coordOx=" + coordOx +
                ", coordOy=" + coordOy +
                '}';
    }

    public Double distanta(){
        return Math.sqrt((Math.pow(coordOx,2) + Math.pow(coordOy, 2)));
    }

    @Override
    public int compareTo(Punct p) {
        if(this.distanta().equals(p.distanta())){
            return 0;
        }else if (this.distanta() > p.distanta()){
            return 1;
        }
        return -1;
    }
}
