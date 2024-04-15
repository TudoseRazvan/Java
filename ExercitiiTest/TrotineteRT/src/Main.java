import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Initializare trotinete
        Trotinete t1 = new Trotinete("1", 1490, 30, 60);
        Trotinete t2 = new Trotinete("2", 1485, 32, 61);
        Trotinete t3 = new Trotinete("3", 1782, 27, 59);
        Trotinete t4 = new Trotinete("4", 2549, 29, 63);

        List<Trotinete> trotinete = new ArrayList<>();
        trotinete.add(t1);
        trotinete.add(t2);
        trotinete.add(t3);
        trotinete.add(t4);

        //Exemplu toString
        System.out.println(t1.toString());
        System.out.println(t2.toString());
        System.out.println(t3.toString());
        System.out.println(t4.toString());

        //Exemplu equals()
        if(t1.equals(t2)){
            System.out.println("Trotinetele sunt egale!");
        }else{
            System.out.println("Trotinetele sunt diferite");
        }

        //Exemplu compareTo
        System.out.println(t1.compareTo(t2));
        System.out.println(t2.compareTo(t3));
        System.out.println(t3.compareTo(t4));
        System.out.println(t4.compareTo(t1));

        //2. Citire date din fisier text intr-un Map<String, Trotineta>, unde cheia de tip String
        //este data de identificatorul trotineta. Sa se afiseze la consola trotinetele ce au rulat
        //cu o viteza maxima mai mare de 50 km/h
        Map<String, Trotinete> trotineteMap = citireDate("trotinete.txt");

        //Afisare tuturor trotinetelor din map
        trotineteMap.forEach((id,trotineta)-> System.out.println(id + " -> "+ trotineta));

        System.out.println("\n");
        //Afisarea trotinetelor cu o viteza maxima de peste 50 km/h
        trotineteMap.values().stream()
                .filter(t -> t.getVitezaMaxima() > 50)
                .forEach(System.out::println);

        System.out.println();
        //3.Pentru fiecare valoarea a vitezei medii a trotinetelor din flota, sa se afiseze la consola
        // numarul trotinetelor ce au aceeasi viteza medie si suma distantelor totale parcurse de acestea:
        // viteza medie 15 km/h -> 3 trotinete, suma distantelor parcurse 257 km

        //Grupare trotinete dupa viteza medie
        Map<Float, List<Trotinete>> grupareVitezaMedie = new HashMap<>();

        trotineteMap.values().forEach(t -> {
            float vitezaMedie = t.getVitezaMedie();
            grupareVitezaMedie.computeIfAbsent(vitezaMedie, k -> new ArrayList<>()).add(t);
        });

        /*trotineteMap.values().forEach(t -> { ... });: Iterăm prin toate valorile din trotineteMap, adică toate trotinetele.

        float vitezaMedie = t.getVitezaMedie();: Pentru fiecare trotinetă, obținem viteza medie.

        grupareVitezaMedie.computeIfAbsent(vitezaMedie, k -> new ArrayList<>()).add(t);:
        Folosim computeIfAbsent pentru a adăuga trotineta în lista corespunzătoare vitezei medii.
        Dacă lista nu există încă pentru viteza medie respectivă, o creăm.*/

        // Afisare numar trotinete si suma distante pentru fiecare viteza medie
        grupareVitezaMedie.forEach((vitezaMedie, trotinete2) -> {
            int numarTrotinete = trotinete2.size();
            float sumaDistante = trotinete2.stream()
                    .map(Trotinete::getDistantaTotala)
                    .reduce(0f, Float::sum);

            System.out.println("Viteza medie " + vitezaMedie + " km/h -> " + numarTrotinete + " trotinete, suma distantelor parcurse " + sumaDistante + " km");
        });
        /* Iterăm prin fiecare pereche cheie-valoare din grupareVitezaMedie, unde cheia este viteza medie și valoarea este lista de trotinete cu acea viteză medie.

        int numarTrotinete = trotinete2.size();: Numărăm câte trotinete sunt în lista pentru viteza medie curentă.

        float sumaDistante = trotinete2.stream()...: Folosim un stream pe lista de trotinete pentru a calcula suma distanțelor totale parcurse de trotinetele respective.

        .map(Trotinete::getDistantaTotala): Transformăm fiecare trotinetă în distanța sa totală parcurasă.

        .reduce(0f, Float::sum): Reducem lista de distanțe la o singură valoare, adică suma lor.*/

        //Exercitiu 4: Salveza in fisierul binar trotineteRapide.dat trotinetele care au circulat cu o viteza medie de peste 14 km/h sau au atins o viteza maxima de 50 km/h
        salvareInFiserBinar(trotineteMap, "trotineteRapide.dat");

        //Exercitiu 5: Citire din fisier binar si afisare la consola
        Map<String, Trotinete> trotineteMap2 = citireDinFisierBinar("trotineteRapide.dat");

        //Afisarea trotinetelor din fisier
        trotineteMap2.values().forEach(System.out::println);
    }

    public static Map<String, Trotinete> citireDate(String numeFisier){
        Map<String, Trotinete> trotineteMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))){
            String linie;

            while((linie = reader.readLine()) != null){
                String[] tokens = linie.split("\t"); //tabul este separatorul

                String idTrotineta = tokens[0].trim();
                float distantaTotala = Float.parseFloat(tokens[1].trim());
                float vitezaMedie = Float.parseFloat(tokens[2].trim());
                float vitezaMaxima = Float.parseFloat(tokens[3].trim());

                Trotinete trotineta = new Trotinete(idTrotineta, distantaTotala, vitezaMedie, vitezaMaxima);
                trotineteMap.put(idTrotineta, trotineta);
            }

        }catch(Exception ex){
            System.err.println(ex);
        }
        return trotineteMap;
    }

    public static void salvareInFiserBinar (Map<String, Trotinete> trotineteMap, String numeFisier) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))){
            trotineteMap.values().forEach(trotineta -> {
                if(trotineta.getVitezaMedie() > 14 || trotineta.getVitezaMaxima() > 50)
                    try{
                        out.writeObject(trotineta);
                    }catch (Exception e){
                        System.err.println(e);
                }
            });
        }catch(Exception ex){
            System.err.println(ex);
        }
    }

    public static Map<String, Trotinete> citireDinFisierBinar(String numeFisier){
        Map<String, Trotinete> trotineteMap = new HashMap<>();

        //Deschide un flux de intrare ObjectInputStream pentru fisierul binar dat
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(numeFisier))){
            //Citeste obiecte din fisier pana cand se ajunge la sfarsitul fisierului
            while(true){
                Trotinete trotineta = (Trotinete) in.readObject();
                trotineteMap.put(trotineta.getIdTrotineta(), trotineta);
            }

        } catch (java.io.EOFException e){
            //Acesta este semnalul ca am ajuns la sfarsitul fisierului
            System.out.println("Citirea din fisier a fost finalizata");
        } catch (Exception ex){
            System.err.println(ex);
        }

        return trotineteMap;
    }

}