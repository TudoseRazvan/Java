import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //Instantierea punctelor
        Punct p1 = new Punct("a", "aaa", 1.0, -1.0);
        Punct p2 = new Punct("b", "bbb", 4.0, 13.0);
        Punct p3 = new Punct("b", "bbb", 4.0, 13.0);

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

        //Exercitiul 2: citirea datelor din fisierul CSV
        List<Punct> puncte = citireDate("puncte.csv");

        //Afisarea la consola a numarului de puncte din fisierul CSV
        int nr = 0;
        for(Punct p : puncte) {
            //System.out.println(p); //Afiseaza toate punctele din fisier
            nr++;
        }
        System.out.println("Numarul de puncte citite: " + nr);

        //Exercitiul 3: Sa se afiseze numarul de puncte pentru fiecare figura prin collectors, astfel
        //eticheta_figura: numar_puncte

        /*Colectarea cu Collectors.groupingBy:
          puncte.stream(): Convertim lista de puncte într-un stream de obiecte.

          Collectors.groupingBy(punct -> punct.getEtichetaFigurii(), Collectors.counting()):
          Acesta este un colector care grupează elementele din stream în funcție de o funcție de grupare și numără elementele din fiecare grup.

          punct -> punct.getEtichetaFigurii(): Funcția de grupare care extrage eticheta figurii pentru fiecare punct.

          Collectors.counting(): Colectorul care numără elementele din fiecare grup.

        Gruparea după etichetaFigurii:
         punct -> punct.getEtichetaFigurii() va grupa punctele în funcție de etichetaFigurii. Asta înseamnă că toate punctele cu aceeași etichetă figurii vor fi grupate împreună.

        Numărarea punctelor în fiecare grup:
         Colectorul Collectors.counting() numără elementele din fiecare grup generat de etichetaFigurii.

        Rezultatul - Map<String, Long>:
         Rezultatul este un Map în care cheile sunt etichetele figurilor și valorile sunt numărul de puncte asociate fiecărei etichete.
        */

        Map<String, Long> map = puncte.stream().collect(Collectors.groupingBy(
                punct -> punct.getEtichetaFigurii(),
                Collectors.counting()
        ));
        //Afisarea propriu-zisa a map-ului:
        map.keySet().forEach(fig-> System.out.println(fig + ":" + map.get(fig)));
        /*
        map.keySet(): Obținem un set de chei din map.
        forEach(fig -> System.out.println(fig + ":" + map.get(fig))): Pentru fiecare cheie (fig), afișăm cheia și valoarea asociată (numărul de puncte) din map.
        */


        //Exercitiul 4: Salvare in fisier CSV: distantele punctelor fata de origine, calculate prin metoda ceruta la 1
        //Sortate descrescator, astfel: eticheta_figura: numar_puncte
        try{
            salvareCSV(puncte, "distante.csv");
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        //Apelul pentru exercitiul 4: Salvare in fisier TEXT
        try{
            salvareCSV(puncte, "distante2.txt");
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        //Suplimentar: Salvare puncte in fisier text
        try{
            salvareText(puncte, "puncte.txt");
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    //Citirea datelor din fisier CSV
    public static List<Punct> citireDate(String numeFisier){
        List<Punct> puncte = new ArrayList<>();

        try{
            BufferedReader in = new BufferedReader(new FileReader(numeFisier));
            String linie;

            try{
                while((linie = in.readLine()) != null){
                    Punct p = new Punct();
                    String[] tokens = linie.split(",");
                    p.setEtichetaFigurii(tokens[0]);
                    p.setEtichetaPunct(tokens[1]);
                    p.setCoordOx(Double.parseDouble(tokens[2]));
                    p.setCoordOy(Double.parseDouble(tokens[3]));

                    //Adaugare la lista de puncte
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

    //Scriere in fisier CSV
    public static void salvareCSV(List<Punct> puncte, String numeFisier) throws IOException{
        //Deschidem un FileWriter pentru a scrie in fisierul specificat
        FileWriter writer = new FileWriter(numeFisier);

        //Sortam lista de puncte folosind metoda compareTo din clasa Punct
        Collections.sort(puncte, Punct::compareTo);
        //Inversam ordinea listei pentru a obtine sortarea descrescatoare ca in cerinta
        Collections.reverse(puncte);

        //Parcurgem fiecare punct din lista
        for(Punct p:puncte) {
            //Cream un StringBuilder pentru a construi o linie noua in fisierul CSV
            StringBuilder line = new StringBuilder();
            //Adaugam eticheta figurii la linie
            line.append(p.getEtichetaFigurii());
            //Adauga virgula ca separator
            line.append(",");
            //Adaugam eticheta punctului la linie
            line.append(p.getEtichetaPunct());
            //Adauga virgula ca separator
            line.append(",");
            //Adaugam distanta calculata fata de origine la linie
            //Am folosit formatul .2f pentru a afisa distanta cu 2 zecimale
            line.append(String.format("%.2f",p.distanta()).toString());
            line.append("\n");
            //Scriem linie in fisier folosind writer
            writer.write(line.toString());
        }
        //Inchidem writer-ul pentru a elibera resursele
        writer.close();
    }

    public static void salvareText(List<Punct> puncte, String numeFisier) throws IOException {
        FileWriter writer = new FileWriter(numeFisier);

        for(Punct p : puncte){
            StringBuilder line = new StringBuilder();
            // Folosim metoda toString() pentru a obtine o reprezentare text a punctului
            line.append(p.toString());
            //Adaugam un caracter de linie noua la sfarsitul fiecarei linii
            line.append("\n");

            //Scriem linia in fisier folosind writer
            writer.write(line.toString());
        }
        //Inchidem writer-ul pentru a elibera resursele
        writer.close();
    }

}
