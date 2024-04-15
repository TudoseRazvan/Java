import java.io.Serializable;

public class Achizitie implements Comparable<Achizitie>, Serializable {
    static class Data implements Serializable {
        protected int an;
        protected int luna;
        protected int zi;

        public Data(){
            an = 0;
            luna = 0;
            zi = 0;
        }

        public Data(int an, int luna, int zi) {
            this.an = an;
            this.luna = luna;
            this.zi = zi;
        }

        public int getAn() {
            return an;
        }

        public void setAn(int an) {
            this.an = an;
        }

        public int getLuna() {
            return luna;
        }

        public void setLuna(int luna) {
            this.luna = luna;
        }

        public int getZi() {
            return zi;
        }

        public void setZi(int zi) {
            this.zi = zi;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "an=" + an +
                    ", luna=" + luna +
                    ", zi=" + zi +
                    '}';
        }
    }
    protected String cod;
    protected Data dataAchizitie;
    protected int cantitate;
    protected  float pretUnitar;

    public Achizitie() {
        cod = "";
        dataAchizitie = new Data();
        cantitate = 0;
        pretUnitar = 0;
    }

    public Achizitie(String cod, Data dataAchizitie, int cantitate, float pretUnitar) {
        this.cod = cod;
        this.dataAchizitie = dataAchizitie;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Data getDataAchizitie() {
        return dataAchizitie;
    }

    public void setDataAchizitie(Data dataAchizitie) {
        this.dataAchizitie = dataAchizitie;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(float pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    @Override
    public String toString() {
        return "Achizitie{" +
                "cod='" + cod + '\'' +
                ", dataAchizitie=" + dataAchizitie +
                ", cantitate=" + cantitate +
                ", pretUnitar=" + pretUnitar +
                '}';
    }

    public float valoare(){
        return pretUnitar * cantitate;
    }

    //Comparabilitate intre achizitii dupa valoarea acestora
    @Override
    public int compareTo(Achizitie Obj) {
        float valoareThis = this.valoare();
        float valoareObj = Obj.valoare();

        if(valoareObj > valoareThis){
            return 1;
        }else if(valoareObj < valoareThis){
            return -1;
        }
            return 0;
    }
}
