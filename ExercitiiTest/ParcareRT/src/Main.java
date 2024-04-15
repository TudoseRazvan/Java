import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final float TAXA_PARCARE = 10.0f; //Taxa de parcare standard in lei

    public static void main(String[] args) {
        Vehicul v1 = new Vehicul("B-210-AAA", "Mercedes", 2);
        Vehicul v2 = new Vehicul("IF-777-TDS", "BMW", 1);
        Vehicul v3 = new Vehicul("BV-839-AXA", "Audi", 4);
        Vehicul v4 = new Vehicul("VS-999-WXZ", "Dacia", 2);

        List<Vehicul> vehicule = new ArrayList<>();
        vehicule.add(v1);
        vehicule.add(v2);
        vehicule.add(v3);
        vehicule.add(v4);

        //Exemplu toString()
        System.out.println(v1.toString());
        System.out.println(v2.toString());
        System.out.println(v3.toString());
        System.out.println(v4.toString());

        //Exemplu DeLux()
        if(v1.DeLux()){
            System.out.println("Masina este de lux");
        }else{
            System.out.println("Masina nu este de lux");
        }

        if(v4.DeLux()){
            System.out.println("Masina este de lux");
        }else{
            System.out.println("Masina nu este de lux");
        }

        //Exercitiul 2: Citire date din fisier text separate prin ","
        //Afisare numar total de vehicule si numarul total de pasageri
        List<Vehicul> vehicule2 = citireDate("parcare.txt");

        int nrVehicule = 0;
        int nrPasageri = 0;
        for(Vehicul v : vehicule2){
            //System.out.println(v);
            nrVehicule++;
            nrPasageri += v.getNrPasageri();
        }
        System.out.println("Numarul de vehicule este: " + nrVehicule + ", si numarul de pasageri este: " + nrPasageri);

        //Exercitiul 3: Sa se afiseze numarul total de pasageri pentru vehiculele considerate de lux si pentru celalalte vehicule separat
        int pasageriVehiculDeLux = 0;
        int pasageriVehiculeNormale = 0;

        for(Vehicul v : vehicule2){
            if(v.DeLux()){
                pasageriVehiculDeLux += v.getNrPasageri();
            }else{
                pasageriVehiculeNormale += v.getNrPasageri();
            }
        }
        System.out.println("Vehicule de lux: " + pasageriVehiculDeLux + " persoane");
        System.out.println("Alte vehicule: " + pasageriVehiculeNormale + " persoane");

        //Exerciitul 4: Constanta (taxa de parcare per vehicul) a carei valoare se majoreaza cu 20% pt vehiculele de lux
        //Sa se genereze in fisierul raportParcare.txt un raport text cu taxele totale colectate defalcate pe judete
        //B,22.00
        //BZ,11.00 ...
        //Pe fiecare linie se afiseaza judetul identificat pe baza nr de inmatriculare si taxa de parcare totala

        //Folosim Map pentru a tine evidenta taxelor pe judet
        Map<String, Float> taxePeJudet = new HashMap<>();

        for(Vehicul v: vehicule2){
            float taxa =v.DeLux() ? TAXA_PARCARE * 1.2f : TAXA_PARCARE;

            String judet = v.getNrInmatriculare().substring(0,2);
            taxePeJudet.put(judet, taxePeJudet.getOrDefault(judet,0.0f) + taxa);
        }

        //Scrierea in fisierul raportParcare.txt
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("raportParcare.txt"))){
            for(Map.Entry<String, Float> entry : taxePeJudet.entrySet()){
                writer.write(entry.getKey() + "," + String.format("%.2f", entry.getValue()) + " lei\n");
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
    }

    //Citirea datelor din fisier TEXT separate prin ","
    public static List<Vehicul> citireDate(String numeFisier){
        List<Vehicul> vehicule = new ArrayList<>();

        try{
            BufferedReader in = new BufferedReader(new FileReader(numeFisier));
            String linie;

            try{
                while((linie = in.readLine())!= null){
                    Vehicul v = new Vehicul();
                    String[] tokens = linie.split(",");
                    v.setNrInmatriculare(tokens[0]);
                    v.setMarca(tokens[1]);
                    v.setNrPasageri(Integer.parseInt(tokens[2]));

                    vehicule.add(v);
                }
            }catch (Exception ex){
                System.err.println(ex);
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
        return vehicule;
    }

}