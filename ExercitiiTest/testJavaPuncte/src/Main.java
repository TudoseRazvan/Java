import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        //instantiere puncte
        Punct p1 = new Punct("a","aaa",1,-1);
        Punct p2 = new Punct("a","aaa",4,13);
        Punct p3 = new Punct("a","aaa",4,13);

        //toString()
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());

        //distanta()
        System.out.println(p1.distanta());
        System.out.println(p2.distanta());
        System.out.println(p3.distanta());

        //compareTo()
        System.out.println(p1.compareTo(p2));
        System.out.println(p2.compareTo(p3));
        System.out.println(p2.compareTo(p1));

        //numarul de puncte citite din fisier
        Integer nr = 0;
        List<Punct> puncte = citireDate("puncte.csv");
        for (Punct p : puncte){
            //System.out.println(p);
            nr++;
        }
        System.out.println("Numarul de puncte citite: "+nr);

        //afisare in consola in format "eticheta_figura:numar_puncte"
        //stocarea intr-un map - folosind collections
        Map<String,Long> map = puncte.stream().collect(Collectors.groupingBy(
                punct -> punct.getEtichetaFigura(),
                Collectors.counting()
        ));
        //afisarea propriu-zisa a map-ului
        map.keySet().forEach(fig-> System.out.println(fig + ":" + map.get(fig)));

        //scriere in fisier csv
        try {
            salvareCSV(puncte,"distante.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Citire din CSV
    public static List<Punct> citireDate(String numeFisier){
        List<Punct> puncte = new ArrayList();

        try{
            BufferedReader in = new BufferedReader(new FileReader(numeFisier));
            String linie;

            try{
                while((linie = in.readLine()) != null){
                    Punct p = new Punct();
                    String[] tokens = linie.split(",");
                    p.setEtichetaFigura(tokens[0]);
                    p.setEtichetaPunct(tokens[1]);
                    p.setCoordOx(Double.parseDouble(tokens[2]));
                    p.setCoordOy(Double.parseDouble(tokens[3]));
                    puncte.add(p);
                }
            }catch (Exception ex){
                System.err.println(ex);
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
        return puncte;
    }

    //scriere in fisier CSV
    public static void salvareCSV(List<Punct> puncte, String numeFisier) throws IOException {
        FileWriter writer = new FileWriter(numeFisier);

        Collections.sort(puncte, Punct::compareTo);
        Collections.reverse(puncte);

        for (Punct p : puncte) {
            StringBuilder line = new StringBuilder();
            line.append(p.getEtichetaFigura());
            line.append(",");
            line.append(p.getEtichetaPunct());
            line.append(",");
            line.append(String.format("%.2f",p.distanta()).toString());
            line.append("\n");
            writer.write(line.toString());
        }
        writer.close();
    }
}