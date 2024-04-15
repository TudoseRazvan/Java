import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Initializare pentru clasa Data
        Achizitie.Data data1 = new Achizitie.Data(2022, 4, 10);
        Achizitie.Data data2 = new Achizitie.Data(2022, 5, 15);
        Achizitie.Data data3 = new Achizitie.Data(2022, 5, 17);
        Achizitie.Data data4 = new Achizitie.Data(2022, 6, 7);

        //Initializare pentru Achizitii
        Achizitie a1 = new Achizitie("1", data1, 4, 850);
        Achizitie a2 = new Achizitie("2", data2, 3, 600);
        Achizitie a3 = new Achizitie("3", data3, 14, 3450);
        Achizitie a4 = new Achizitie("4", data4, 8, 2000);

        List<Achizitie> achizitii = new ArrayList<>();
        achizitii.add(a1);
        achizitii.add(a2);
        achizitii.add(a3);
        achizitii.add(a4);

        //Exemplu toString()
        System.out.println(a1.toString());
        System.out.println(a2.toString());
        System.out.println(a3.toString());
        System.out.println(a4.toString());

        //Exemlu compareTo
        System.out.println(a1.compareTo(a2));
        System.out.println(a2.compareTo(a3));
        System.out.println(a3.compareTo(a4));
        System.out.println(a4.compareTo(a1));

        //2. Citire din fisierul achizitii.txt intr-o lista List<Achizitie>
        //Afisare la consola a achizitiilor efectuate in prima jumatate a lunii si care au o cantitate achizitionate mai mare de 100 de bucati
        List<Achizitie> achizitii2 = citireDinFisierText("achizitii.txt");

        //Afisare
        for(Achizitie achizitie : achizitii2){
            if(achizitie.getDataAchizitie().getZi() <= 15 && achizitie.getCantitate() > 100){
                System.out.println(achizitie);
            }
        }

        //3.User
        //Pentru fiecare produs sa se afiseze la consola numarul de achizitii efectuate in luna analizata si valoarea totala achizitionata din produsul respectiv.
        //Produsele vor fi afisate in ordinea descrescatoare a valorii totale achizitionate: Produs P10231 -> 5 achizitii, valoarea totala 12303.5 Lei

        //Grupare achizitii dupa cod produs
        Map<String, List<Achizitie>> grupareProduse = new HashMap<>();
        for(Achizitie achizitie : achizitii2){
            String codProdus = achizitie.getCod();
            grupareProduse.computeIfAbsent(codProdus, k -> new ArrayList<>()).add(achizitie);
        }

        //Calculare numar de achizitii si valoarea totala pentru fiecare produs
        Map<String, Float> valoareTotala = new HashMap<>();
        for(Map.Entry<String, List<Achizitie>> entry : grupareProduse.entrySet()) {
            float sumaTotala = 0;
            for(Achizitie achizitie : entry.getValue()){
                sumaTotala += achizitie.valoare();
            }
            valoareTotala.put(entry.getKey(), sumaTotala);
        }

        //Sortare produse in ordinea descrescatoare a valorii totale achizitionate
        List<Map.Entry<String, Float>> sortate = new ArrayList<>(valoareTotala.entrySet());
        sortate.sort((a,b) -> b.getValue().compareTo(a.getValue()));

        //Afisare rezultate
        for(Map.Entry<String, Float> entry : sortate){
            String codProdus = entry.getKey();
            float valoare = entry.getValue();
            int numarAchizitii = grupareProduse.get(codProdus).size();
            System.out.println("Produs "+codProdus + " -> "+ numarAchizitii + " achizitii, valoarea totala "+ valoare + " Lei");
        }

        //4. Scriere in fisier binar pentru produsele pt care au fost realizate mai mult de 3 achizitii pe luna
        Map<String,Map<Integer, List<Achizitie>>> grupareProduseSiLuni = new HashMap<>();
        for(Achizitie achizitie : achizitii2){
            String codProdus = achizitie.getCod();
            int luna = achizitie.getDataAchizitie().getLuna();

            grupareProduseSiLuni
                    .computeIfAbsent(codProdus, k -> new HashMap<>())
                    .computeIfAbsent(luna, k -> new ArrayList<>())
                    .add(achizitie);
        }

        List<Achizitie> achizitiiCerute = new ArrayList<>();
        for(Map.Entry<String, Map<Integer, List<Achizitie>>> entryProdus : grupareProduseSiLuni.entrySet()){
            for(Map.Entry<Integer, List<Achizitie>> entryLuna : entryProdus.getValue().entrySet()){
                if (entryLuna.getValue().size() > 3){
                    achizitiiCerute.addAll(entryLuna.getValue());
                }
            }
        }

        salvareInFisierBinar(achizitiiCerute, "produseFrecvente.dat");

        //5. Citirea produselor din fisierul binar produseFrecvente.dat si afisare la consola
        List<Achizitie> achizitiiCititeDinFisierBinar = citireDinFisierBinar("produseFrecvente.dat");

        //Afisare
        for (Achizitie a : achizitiiCititeDinFisierBinar){
            System.out.println(a);
        }

    }

    //Citire date din fisier text intr-o lista
    public static List<Achizitie> citireDinFisierText(String numeFisier){
        List<Achizitie> achizitii = new ArrayList<>();

        try(BufferedReader in = new BufferedReader(new FileReader(numeFisier))){
            String linie;

            while((linie = in.readLine()) != null){
                String[] tokens = linie.split(","); //Asa este in fisierul text achizitii
                if(tokens.length == 6) {
                    //Parsare datelor
                    String cod = tokens[0];

                    Achizitie.Data dataAchizitie = new Achizitie.Data();
                    dataAchizitie.setAn(Integer.parseInt(tokens[1]));
                    dataAchizitie.setLuna(Integer.parseInt(tokens[2]));
                    dataAchizitie.setZi(Integer.parseInt(tokens[3]));

                    int cantitate = Integer.parseInt(tokens[4]);
                    float pretUnitar = Float.parseFloat(tokens[5]);

                    Achizitie achizitie = new Achizitie(cod, dataAchizitie, cantitate, pretUnitar);
                    achizitii.add(achizitie);
                }
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
        return achizitii;
    }
    //Serializare
    public static void salvareInFisierBinar(List<Achizitie> achizitii, String numeFisier){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))){
            for(Achizitie a : achizitii){
                //Serializeaza si scrie obiectul Achizitie a in fluxul de iesire
                out.writeObject(a);
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
    }

    public static List<Achizitie> citireDinFisierBinar(String numeFisier){
        List<Achizitie> achizitii = new ArrayList<>();

        try(FileInputStream in1 = new FileInputStream(numeFisier);ObjectInputStream in = new ObjectInputStream(in1)){
            while(in1.available() != 0){
                achizitii.add((Achizitie) in.readObject());
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
        return achizitii;
    }


}