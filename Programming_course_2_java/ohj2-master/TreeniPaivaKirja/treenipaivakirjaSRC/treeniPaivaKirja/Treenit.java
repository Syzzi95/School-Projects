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
public class Treenit implements Iterable<Treeni>{
    
    private String tiedostonNimi;
    private Collection<Treeni> trnAlkiot = new ArrayList<Treeni>();

    @Override
    public Iterator<Treeni> iterator() {
        
        return trnAlkiot.iterator();
    }
    /**
     * Haetaan kaikki Treeniohjelman treenit
     * @param tunnusnro Treeniohjelman tunnusnumero jolle treenejä haetaan
     * @return tietorakenne jossa viiteet löydetteyihin treeneihin   
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Treenit treenit = new Treenit();              
     *  Treeni pitsi21 = new Treeni(2); treenit.lisaa(pitsi21);
     *  Treeni pitsi11 = new Treeni(1); treenit.lisaa(pitsi11);
     *  Treeni pitsi22 = new Treeni(2); treenit.lisaa(pitsi22);
     *  Treeni pitsi12 = new Treeni(1); treenit.lisaa(pitsi12);
     *  Treeni pitsi23 = new Treeni(2); treenit.lisaa(pitsi23);
     *  Treeni pitsi51 = new Treeni(5); treenit.lisaa(pitsi51);
     *  
     *  List<Treeni> loytyneet;
     *  loytyneet = treenit.annaTreenit(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = treenit.annaTreenit(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = treenit.annaTreenit(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
     */
    public List<Treeni> annaTreenit(int tunnusnro) {
        List<Treeni> loydetyt = new ArrayList<Treeni>();
        for (Treeni trn : trnAlkiot)
            if (trn.getTreeninNumero() == tunnusnro) loydetyt.add(trn);
        return loydetyt;
    }
    
    /**
     * 
     */
    public Treenit() {
        
    }
    /**
     * @param trn treenimuoto
     */
    //todo
    
    public void lisaa(Treeni trn) {
        trnAlkiot.add(trn);
    }
    
    /**
     * @return palauttaa alkioiden lukumäärän
     */
    public int getTreenit(){
        return trnAlkiot.size();
    }
    
    /**
     * Lukee treeniohjelman tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/treenit.dat";
        File ftied = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine().trim();
                if ( "".equals(s) || s.charAt(0) == ';' ) continue;
                Treeni treeni = new Treeni();
                treeni.parse(s); // voisi olla virhekäsittely
                lisaa(treeni);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }

    
    /**
     * Tallentaa treeniohjelma tiedostoon.  
     * TODO Kesken.
     * @param hakemisto tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/treenit.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (var har: trnAlkiot) {
                fo.println(har.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }        
    }

    
    /**
     * @param args ei käytössä vielä
     */
    public static void main(String[] args) {
        Treenit treenit = new Treenit();
        try {
            treenit.lueTiedostosta("TreeniOhjelmat");
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        Treeni eka = new Treeni();
        eka.lisaaTreeninId();
        eka.vastaaTreenia(1);
        Treeni toka = new Treeni();
        toka.lisaaTreeninId();
        toka.vastaaTreenia(1);
        Treeni kolmas = new Treeni();
        kolmas.lisaaTreeninId();
        kolmas.vastaaTreenia(2);
        
        treenit.lisaa(eka);
        treenit.lisaa(toka);
        treenit.lisaa(kolmas);
        
        System.out.println("============ treeneille testi =============");
        
        List<Treeni> treenit2 = treenit.annaTreenit(treenit.getTreenit());
        
        for (Treeni treeni : treenit2) {
            System.out.print(treeni.getTreeninNumero() + " ");
            treeni.tulosta(System.out);
        }
            
        try {
            treenit.tallenna("TreeniOhjelmat");
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
    }
    /**
     * @param tiedostonNimi jota halutaan käsitellä
     */
    public void setTiedostonNimi(String tiedostonNimi) {
        this.tiedostonNimi = tiedostonNimi;
    }
    
}
