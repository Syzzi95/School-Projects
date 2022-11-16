package treeniPaivaKirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class Liikkeet implements Iterable<Liike> {
    private String tiedostonNimi = "";
    
    private final Collection<Liike> liikkeidenAlkiot = new ArrayList<Liike>();
    
    @Override
    public Iterator<Liike> iterator(){
        return liikkeidenAlkiot.iterator();
    }
    
    /**
     * oletusmuodostaja
     */
    public Liikkeet() {
        
    }
    
    /**
     * Haetaan kaikki Treenien liikkeet
     * @param tunnusnro Treenien tunnusnumero jolle liikkeet haetaan
     * @return tietorakenne jossa viiteet löydetteyihin treeneihin   
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Liikkeet treenit = new Liikkeet();              
     *  Liike pitsi21 = new Liike(12); treenit.lisaa(pitsi21);
     *  Liike pitsi11 = new Liike(11); treenit.lisaa(pitsi11);
     *  Liike pitsi22 = new Liike(12); treenit.lisaa(pitsi22);
     *  Liike pitsi12 = new Liike(11); treenit.lisaa(pitsi12);
     *  Liike pitsi23 = new Liike(12); treenit.lisaa(pitsi23);
     *  Liike pitsi51 = new Liike(15); treenit.lisaa(pitsi51);
     *  
     *  List<Liike> loytyneet;
     *  loytyneet = treenit.annaLiikkeet(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = treenit.annaLiikkeet(11);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = treenit.annaLiikkeet(15);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
     */
    public List<Liike> annaLiikkeet(int tunnusnro) {
        List<Liike> loydetyt = new ArrayList<Liike>();
        for (Liike liike : liikkeidenAlkiot)
            if (liike.getTreeninTunniste() == tunnusnro) loydetyt.add(liike);
        return loydetyt;
    }
    
    /** 
     * lisätään liike 
     * @param movement liike
     */
    public void lisaa(Liike movement) {
        liikkeidenAlkiot.add(movement);
    }
    
    /**
     * liikkeiden hakumetodi
     * @return palauttaa alkioiden lukumäärän
     */
    public int getLiikkeet(){
        return liikkeidenAlkiot.size();
    }
    
    /**
     * Lukee treeniohjelman tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * alusta liikkeet olio ja lisää muutama alkio --> luo tallennettavaa 
     * luo tiedosto johon tallennetaan
     * alustetaan uudestaan liikkeet
     * luetaan tiedostot
     * testataan että tiedostot ovat samoja
     * poistetaan tiedostot
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/liikkeet.dat";
        File ftied = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine().trim();
                if ( "".equals(s) || s.charAt(0) == ';' ) continue;
                Liike liike = new Liike();
                liike.parse(s); // voisi olla virhekäsittely
                lisaa(liike);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }
    
    /**
     * Tallentaa treeniohjelma tiedostoon.  
     * TODO Kesken.
     * @param hakemisto hakemisto
     * @throws SailoException jos talletus epäonnistuu
     * @example
     * <pre name="test">
     * testataan samalla luetiedosto metodissa
     * </pre>
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/liikkeet.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (var liike: liikkeidenAlkiot) {
                fo.println(liike.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }  
    }
    //tämä luokka käsittelee liikkeet ja rekisteröi ne
    /**
     * 
     * @param args ei käytössä
     */
    public static void Main(String[] args) {
        ///TODO
        Liikkeet liikkeet = new Liikkeet();
        try {
            liikkeet.lueTiedostosta("TreeniOhjelmat");
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        Liike eka = new Liike();
        eka.lisaaIdLiikkeelle();
        eka.liitaTreeniin(11);
        Liike toka = new Liike();
        toka.lisaaIdLiikkeelle();
        toka.liitaTreeniin(11);
        Liike kolmas = new Liike();
        kolmas.lisaaIdLiikkeelle();
        kolmas.liitaTreeniin(12);
        
        liikkeet.lisaa(eka);
        liikkeet.lisaa(toka);
        liikkeet.lisaa(kolmas);
        
        System.out.println("============ liikkeille testi =============");
        
        List<Liike> liikkeet2 = liikkeet.annaLiikkeet(liikkeet.getLiikkeet());
        
        for (Liike liike : liikkeet2) {
            System.out.print(liike.getLiikkeenId() + " ");
            liike.tulosta(System.out);
        }
            
        try {
            liikkeet.tallenna("TreeniOhjelmat");
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
