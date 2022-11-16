package treeniPaivaKirja;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class Treeniohjelma {
    // tämä luokka käsittelee treeniohjelman

    private int treeniohjelmanId;
    private String treeniohjelmanNimi = "";
    
    private static int seuraavaId = 1;
    
    /**
     * muodostaja
     */
    public Treeniohjelma() {
        
    }
    
    /**
     * testiarvot pääohjelmatestausta varten
     * @param treeniohjelmalleNimi nimi ohjelmalle
     */
    public void testiOhjelma(String treeniohjelmalleNimi) {
        this.treeniohjelmanNimi = setTreeniohjelmanNimi(treeniohjelmalleNimi);
    }
    
    /**
     * apumetodi, jolla saadaan täytettyä nimi
     * treeniohjelmalle
     */
    public void testiOhjelma() {
        String nimi = "Treeniohjelma";
        testiOhjelma(nimi);
    }

    /**
     * @return palauttaa treeniohjelman nimen
     */
    public String getTreeniohjelmanNimi() {
        return treeniohjelmanNimi;
    }
    
    /**
     * @param treeniohjelmalleNimi annetaan nimi treeniohjelmalle
     * @return palauttaa treeniohjelman nimen
     */
    public String setTreeniohjelmanNimi(String treeniohjelmalleNimi) {
        return treeniohjelmanNimi = treeniohjelmalleNimi;
        
    }
    
    /**
     * @return palauttaa treeniohjelman ID:n
     */
    public int getTreeniohjelmanId() {
        return treeniohjelmanId;
        
    }
    
    /**
     * @param ID treeniohjelmalle asetettava tunniste
     */
    public void setTreeniohjelmanId(int ID) {
        treeniohjelmanId = ID;
        if (treeniohjelmanId >= seuraavaId) 
            seuraavaId = treeniohjelmanId + 1;
    }
    
    /**
     * @return ID eli asettaa seuraavalle treeniohjelmalle tunnisteen
     * @example
     * <pre name="test">
     *   Treeniohjelma voima = new Treeniohjelma();
     *   voima.getTreeniohjelmanId() === 0;
     *   voima.lisaaIdLiikeelle();
     *   Treeniohjelma kestävyys = new Treeniohjelma();
     *   kestävyys.lisaaIdOhjelmalle();
     *   int n1 = voima.getTreeniohjelmanId();
     *   int n2 = kestävyys.getTreeniohjelmanId();
     *   n1 === n2-1;
     * </pre>
     */
    public int lisaaIdOhjelmalle() {
         treeniohjelmanId = seuraavaId;
         seuraavaId++;
         return treeniohjelmanId;
    }
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("" + this.getTreeniohjelmanId() + " " + this.getTreeniohjelmanNimi() + "");
    }

    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * 
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    @Override
    public String toString() {
        return treeniohjelmanId + "|" + treeniohjelmanNimi;
    }
    
    /**
     * @param args ei ole vielä  käytössä
     */
    public static void  main(String [] args) {
        Treeniohjelma voima = new Treeniohjelma();
        Treeniohjelma kestävyys = new Treeniohjelma();
        voima.lisaaIdOhjelmalle();
        kestävyys.lisaaIdOhjelmalle();
        voima.setTreeniohjelmanNimi("voima");
        kestävyys.setTreeniohjelmanNimi("kestävyys");
        
        voima.tulosta(System.out);
        kestävyys.tulosta(System.out);
        
    }

    /**
     * @param s parsetettava merkkijono
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void parse(String s) {
        var merkkijono = new StringBuilder(s);
        setTreeniohjelmanId(Mjonot.erota(merkkijono, '|', getTreeniohjelmanId()));
        treeniohjelmanNimi = Mjonot.erota(merkkijono, '|', treeniohjelmanNimi);
    }
}
