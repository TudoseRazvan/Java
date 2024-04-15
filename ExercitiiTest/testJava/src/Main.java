import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //initializare elemente
        Element e1 = new Element(1, 1, 2);
        Element e2 = new Element(1, 1, 2);
        Element e3 = new Element(3, 4, 2.2);
        Element e4 = new Element(3, 4, 2);
        List<Element> elementeExemplu = new ArrayList();
        elementeExemplu.add(e1);
        elementeExemplu.add(e2);
        elementeExemplu.add(e3);
        //exemplu toString
        System.out.println(e1.toString());

        //exemplu equals
        if (e2.equals(e1)) {
            System.out.println("Elemente egale!");
        } else {
            System.out.println("Elemente diferite");
        }
        //exemplu compareTo
        System.out.println(e2.compareTo(e1));
        System.out.println(e2.compareTo(e3));
        //citire matrice
        List<Element> elemente = citireDate("matricerara.csv");
        //numarul de elemente negative
        int nr = 0;
        for(Element e:elemente){
            if(e.getValoareElement() < 0) {
                nr++;
            }
        }
        System.out.println("Numarul de elemente negative citite: " + nr);

        //implementare collector: afiseaza media pe coloanele matricei
        Map<Integer,Double> medieColoane = elemente.stream().collect(Collectors.groupingBy(
                element -> element.getIndexColoana(),
                Collectors.averagingDouble(Element::getValoareElement))
        );
        medieColoane.keySet().forEach(col-> System.out.println(col + ":" + medieColoane.get(col)));

        //serializare
        serializare(elementeExemplu,"elemente.dat");

        //deserializare
        List<Element> elemente2 = new ArrayList();
        elemente2 = deserializare("elemente.dat");
        for(Element e:elemente2){
            System.out.println(e);
        }
    }

    //citire matrice
    public static List<Element> citireDate(String numeFisier) throws FileNotFoundException {
        List<Element> elemente = new ArrayList();

        try {
            BufferedReader in = new BufferedReader(new FileReader(numeFisier));

            String linie;
            try {
                while ((linie = in.readLine()) != null) {
                    Element element = new Element();
                    String[] tokens = linie.split(",");
                    element.setIndexLinie(Integer.parseInt(tokens[0]));
                    element.setIndexColoana(Integer.parseInt(tokens[1]));
                    element.setValoareElement(Float.parseFloat(tokens[2]));
                    elemente.add(element);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception ex){
            System.err.println(ex);
        }
        return elemente;
    }

    //serializare
    public static void serializare(List<Element> elemente, String numeFisier) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(numeFisier))){
            for (Element e : elemente){
                out.writeObject(e);
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
    }

    //deserializare
    public static List<Element> deserializare(String numeFisiser){
        List<Element> elemente = new ArrayList();
        try(FileInputStream in1 = new FileInputStream(numeFisiser); ObjectInputStream in = new ObjectInputStream(in1)){
            while(in1.available() != 0){
                elemente.add((Element)in.readObject());
            }
        }catch (Exception ex){
            System.err.println(ex);
        }
        return elemente;
    }
}



//public static List<MijlocFix> restaurare(String numeFisier) {
//    List<MijlocFix> mijloaceFixe = new ArrayList<>();
//    try (FileInputStream in1 = new FileInputStream(numeFisier);
//         ObjectInputStream in = new ObjectInputStream(in1)) {
//        while (in1.available()!=0){
//            mijloaceFixe.add((MijlocFix) in.readObject());
//        }
//    } catch (Exception ex) {
//        System.err.println(ex);
//    }
//    return mijloaceFixe;
//}