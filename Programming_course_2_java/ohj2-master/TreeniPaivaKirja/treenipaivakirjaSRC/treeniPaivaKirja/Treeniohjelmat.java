package treeniPaivaKirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class Treeniohjelmat implements Cloneable  {
    /**
     * @author saids
     * @version 23.4.2021
     *
     */
    //tämä luokka ylläpitää rekisteriIterable<Treeniohjelma>ä treeniohjelmista
    
    private static final int MAX_TREENIOHJELMIA = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Treeniohjelma ohjelmat[] = new Treeniohjelma[MAX_TREENIOHJELMIA];
    
    /**
     * Treeniohjelmien muodostaja
     */
    public Treeniohjelmat() {
        //
    }
    
    /**
     * @return palauttaa treeniohjelmien lukumaaran
     */
    public int getLkm() {
        return lkm;
        
    }
    
    /**
     * @return palauttaa tiedoston nimen
     */
    public String getTiedostonNimi() {
        return tiedostonNimi;
        
    }
    
    /**
     * @param treeniohjelma treeniohjelma-olio joka täyttää taulukon  
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treeniohjelmat ohjelmat = new Treeniohjelmat();
     * Treeniohjelma eka = new Treeniohjelma(), toka = new Treeniohjelma();
     * ohjelmat.getLkm() === 0;
     * ohjelmat.lisaa(eka); ohjelmat.getLkm() === 1;
     * ohjelmat.lisaa(toka); ohjelmat.getLkm() === 2;
     * ohjelmat.lisaa(eka); ohjelmat.getLkm() === 3;
     * ohjelmat.anna(0) === eka;
     * ohjelmat.anna(1) === toka;
     * ohjelmat.anna(2) === eka;
     * ohjelmat.anna(1) == eka === false;
     * ohjelmat.anna(1) == toka === true;
     * ohjelmat.anna(3) === eka; #THROWS IndexOutOfBoundsException 
     * ohjelmat.lisaa(eka); ohjelmat.getLkm() === 4;
     * ohjelmat.lisaa(eka); ohjelmat.getLkm() === 5;
     * ohjelmat.lisaa(eka);  #THROWS SailoException 
     * </pre>
     */
    public void lisaa(Treeniohjelma treeniohjelma) throws SailoException {
        Treeniohjelma lisaaAlkioita[] = Arrays.copyOf(this.ohjelmat, this.ohjelmat.length + 5);
        
        if  (this.lkm >= ohjelmat.length) { 
            
            for (int i = 0; i < ohjelmat.length; i++)
            {
                 lisaaAlkioita[i] = ohjelmat[i];
            }
             
        }
        ohjelmat = lisaaAlkioita;
        ohjelmat[this.lkm] = treeniohjelma;
        lkm++;
    }
    
    /**
     * Palauttaa viitteen i:teen treeniohjelmaan.
     * @param i monennenko treeniohjelman viite halutaan
     * @return viite treeniohjelmaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Treeniohjelma anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return ohjelmat[i];
    }
    
    /**
     * Lukee treeniohjelmat tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/treeniohjelmat.dat";
        File tiedosto = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(tiedosto))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine().trim();
                if ( "".equals(s) || s.charAt(0) == ';') continue;
                
                Treeniohjelma treeniohjelma = new Treeniohjelma();
                treeniohjelma.parse(s); // voisi palauttaa jotakin??
                lisaa(treeniohjelma);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        // } catch ( IOException e ) {
        //     throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }

    }


    /**
     * Tallentaa treeniohjelmat tiedostoon.  Kesken.
     * @param hakemisto tallennettavan tiedoston nimi
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File tiedosto = new File(hakemisto + "/treeniohjelmat.dat");
        try (PrintStream tietoVirtaUlos = new PrintStream(new FileOutputStream(tiedosto, false))) {
            for (int i=0; i<getLkm(); i++) {
                Treeniohjelma treeniohjelma = anna(i);
                tietoVirtaUlos.println(treeniohjelma.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + tiedosto.getAbsolutePath() + " ei aukea");
        }        
 
        //throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Testiohjelma treeniohjelmille
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Treeniohjelmat treeniohjelmat = new Treeniohjelmat();
        
        try {
            treeniohjelmat.lueTiedostosta("TreeniOhjelmat");
        } catch (SailoException e1) {
            System.err.println(e1.getMessage());
        }
        
        Treeniohjelma voima = new Treeniohjelma(), kestävyys = new Treeniohjelma();
        voima.setTreeniohjelmanNimi("Voima"); kestävyys.setTreeniohjelmanNimi("Kestavyys");
        voima.lisaaIdOhjelmalle();
        kestävyys.lisaaIdOhjelmalle();

        try {
            treeniohjelmat.lisaa(voima);
            treeniohjelmat.lisaa(kestävyys);

            System.out.println("============= Treeniohjelmat testi =================");

            for (int i = 0; i < treeniohjelmat.getLkm(); i++) {
                Treeniohjelma annaOhjelma = treeniohjelmat.anna(i);
                System.out.println("Treeniohjelman nro: " + i);
                annaOhjelma.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println(treeniohjelmat.getLkm());
        
        try {
            treeniohjelmat.tallenna("TreeniOhjelmat");
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }
}

