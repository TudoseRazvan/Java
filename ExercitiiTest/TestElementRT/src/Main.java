import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        //Initializare elemente
        Element e1 = new Element(1,1,2);
        Element e2 = new Element(1,1,2);
        Element e3 = new Element(3,4,2.2);
        Element e4 = new Element(3,4,2);

        List<Element> elementeExemplu = new ArrayList<>();
        elementeExemplu.add(e1);
        elementeExemplu.add(e2);
        elementeExemplu.add(e3);

        //Exemplu toString - Testare
        System.out.println(e1.toString());
        System.out.println(e2.toString());
        System.out.println(e3.toString());

        //Exemplu equals - Testare
        if(e2.equals(e1)){
            System.out.println("Elementele sunt egale!");
        }else{
            System.out.println("Eleentele sunt diferite");
        }

        //Exemplu compareTo - Testare
        System.out.println(e2.compareTo(e1));
        System.out.println(e2.compareTo(e3));
        System.out.println(e1.compareTo(e2));

        //2. Citire matrice
        List<Element> elemente = citireDate("matricerara.csv");

        //Afisare la consola numarul de elemente negative
        int nr = 0;
        for(Element e:elemente){
            if(e.getValoareElement() < 0){
                nr++;
            }
        }
        System.out.println("Numarul de elemente negative citite: " + nr);

        //3. Afiseaza mediile pe coloane ale matricei, astfel:
        //index_coloana: valoarea
        //...
        //prin collectors

        /*
        elemente.stream(): Convertim lista elemente într-un stream.
        Stream-urile sunt o caracteristică a Java Stream API care permit operații funcționale pe colecții de date.

        Collectors.groupingBy(...): Folosim Collectors.groupingBy pentru a grupa elementele după indexColoana.

        element -> element.getIndexColoana(): Funcția lambda care returnează indexColoana pentru fiecare element din stream.
        Aceasta determină criteriul de grupare.

        Collectors.averagingDouble(Element::getValoareElement): Calculează media valorilor elementelor din fiecare grup
        folosind metoda getValoareElement. Rezultatul este o medie de tip Double.

        Map<Integer, Double> medieColoane: Rezultatul este stocat într-o mapare unde cheia
        este indexColoana (un integer) și valoarea este media valorilor (un double).
        */

        Map<Integer, Double> medieColoane = elemente.stream().collect(Collectors.groupingBy(
                element -> element.getIndexColoana(),
                Collectors.averagingDouble(Element::getValoareElement))
        );

        /*
        medieColoane.keySet(): Obținem setul de chei al mapării medieColoane, care reprezintă toate coloanele pentru care s-a calculat media.

        forEach cu lambda: Parcurgem fiecare cheie (coloană) și afișăm cheia și valoarea corespunzătoare din mapare.

        col -> System.out.println(col + ":" + medieColoane.get(col)): Funcția lambda care primește o cheie (col) și
        afișează cheia urmată de valoarea asociată din mapare.
        */

        medieColoane.keySet().forEach(col-> System.out.println(col + ":" + medieColoane.get(col)));

        //Serializare
        serializare(elementeExemplu, "elemente.dat");


        //Deserializare
        List<Element> elemente2 = new ArrayList<>();
        elemente2 = deserializare("elemente.dat");
        //Afisarea elementelor din elemente.dat
        for(Element e : elemente2){
            System.out.println(e);
        }


        //serializareDiagonala(elementeExemplu, "diagonala.dat");
        //deserializareSiAfișare("diagonala.dat");
    }

    //Citire matrice
    public static List<Element> citireDate(String numeFisier) throws FileNotFoundException {
        //Initializam o lista pentru a stoca elementele citite din fisierul CSV
        List<Element> elemente = new ArrayList<>();

        try {
            //Deschidem fisierul pentru citire folosind BufferedReader
            BufferedReader in = new BufferedReader(new FileReader(numeFisier));

            String linie;
            try {
                //Parcurgem fisierul linie cu linie
                while ((linie = in.readLine()) != null) {
                    //Initializam un nou obiect Element pentru fiecare linie citita
                    Element element = new Element();

                    //Despartim linia in token-uri folosind virgula ca delimitator
                    String[] tokens = linie.split(",");

                    //Setam proprietatile obiectului Element cu valorile corespunzatoare din token-uri
                    element.setIndexLinie(Integer.parseInt(tokens[0]));
                    element.setIndexColoana(Integer.parseInt(tokens[1]));
                    element.setValoareElement(Float.parseFloat(tokens[2]));

                    //Adaugam obiectul Element in lista elemente
                    elemente.add(element);
                }
            } catch (FileNotFoundException e) {
                //Aruncam o exceptie runtime daca fisierul nu este gasit
                throw new RuntimeException(e);
            } catch (IOException e) {
                //Aruncam o exceptie runtime daca apare o eroare la citirea fisierului
                throw new RuntimeException(e);
            }
        } catch (Exception ex) {
            //Capturam si afisam orice alta exceptie care ar putea aparea
            System.err.println(ex);
        }

        //Returnam lista de elemente citite din fisier
        return elemente;
    }

    //Serializare
    public static void serializare(List<Element> elemente, String numeFisier) {
        //Deschide un flux de iesire catre fisierul specificat
        // si initializeaza ObjectOutputStream pentru a scrie obiecte in flux
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))){
            //Parcurge fiecare Element din lista elemente
            for(Element e : elemente){
                //Serializeaza si scrie obiectul Element e in fluxul de iesire
                out.writeObject(e);
            }
        }catch (Exception ex){
            //Afiseaza exceptia care ar putea aparea
            System.err.println(ex);
        }
    }


    //Deserializare
    public static List<Element> deserializare(String numeFisier){
        //Initializam o lista pentru a stoca elementele deserializate
        List<Element> elemente = new ArrayList<>();
        //FileInputStream Deschide un flux de intrare pentru fisierul specificat
        //Iniatilizeaza ObjectInputStream pentru a citi obiecte din flux
        try(FileInputStream in1 = new FileInputStream(numeFisier); ObjectInputStream in = new ObjectInputStream(in1)){
            //Verifica daca exista date disponibile in flux
            while(in1.available() != 0){
                //Citeste si adauga obiectul deserializat in lista de elemente
                elemente.add((Element)in.readObject());
            }
        }catch (Exception ex){
            //Afiseaza eventualele erori
            System.err.println(ex);
        }
        //Returneaza lista de elemente !!!
        return elemente;
    }

    //Pentru elementele de pe diagonala:
public static void serializareDiagonala(List<Element> elemente, String numeFisier) {
        try {
            // Deschidem un flux de ieșire către fișierul specificat
            // și inițializăm ObjectOutputStream pentru a scrie obiecte în flux
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))) {

                // Filtrăm elementele care se află pe diagonala principală
                List<Element> diagonala = elemente.stream()
                        .filter(e -> e.getIndexLinie() == e.getIndexColoana())
                        .collect(Collectors.toList());

                // Parcurgem fiecare Element din lista diagonala
                for (Element e : diagonala) {
                    // Serializăm și scriem obiectul Element în fluxul de ieșire
                    out.writeObject(e);
                }

            } catch (FileNotFoundException e) {
                // Afișăm eroarea dacă nu se găsește fișierul
                System.err.println("Fișierul nu a fost gasit: " + e.getMessage());

            } catch (IOException e) {
                // Afișăm eroarea dacă apare o problemă la scrierea în fișier
                System.err.println("Eroare la scrierea în fisier: " + e.getMessage());
            }

        } catch (Exception ex) {
            // Afișăm orice altă excepție care ar putea apărea
            System.err.println("Eroare generala: " + ex.getMessage());
        }
    }


    // Metodă pentru deserializarea și afișarea elementelor dintr-un fișier
    public static void deserializareSiAfișare(String numeFisier) {
        try {
            // Deschidem un flux de intrare pentru fișierul specificat
            // și inițializăm ObjectInputStream pentru a citi obiecte din flux
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(numeFisier))) {

                // Folosim un loop infinit pentru a citi obiectele până când se ajunge la sfârșitul fișierului
                while (true) {
                    try {
                        // Citim un obiect din fluxul de intrare
                        Element e = (Element) in.readObject();

                        // Afișăm obiectul citit
                        System.out.println(e);

                    } catch (EOFException e) {
                        // Sfârșitul fișierului a fost atins, ieșim din loop
                        break;
                    }
                }

            } catch (FileNotFoundException e) {
                // Afișăm eroarea dacă nu se găsește fișierul
                System.err.println("Fișierul nu a fost găsit: " + e.getMessage());

            } catch (IOException e) {
                // Afișăm eroarea dacă apare o problemă la citirea din fișier
                System.err.println("Eroare la citirea din fișier: " + e.getMessage());

            } catch (ClassNotFoundException e) {
                // Afișăm eroarea dacă nu se poate găsi clasa obiectului
                System.err.println("Clasa obiectului nu a putut fi găsită: " + e.getMessage());
            }

        } catch (Exception ex) {
            // Afișăm orice altă excepție care ar putea apărea
            System.err.println("Eroare generală: " + ex.getMessage());
        }
    }


}


