package treeniPaivaKirja;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class Treeni {
    // tämä lluokka käsittelee treeni tietorakenteen. treeni tietorakenteessa
    // tärkeää on päivämäärän tallennus
    // sekä relaatio Treeniohjelmaan
    // Periaatteessa voisit tehdä päivämäärän alustuksen ja automaattisen oton.

    private int treeninId = 0;
    private String treeninNimi = "";
    private String pvm = "17.3.2021";
    private int ohjelmanNumero;
    private static int treeninSeuraavaID = 11;
    /*
     * private int pv = 1; private int kk = 1; private int vv = 2021; riippuu
     * haluatko tehdä asiat vaikeiksi itsellesi
     */

    /**
     * Oletusmuodostaja
     */
    public Treeni() {

    }


    /**
     * Alustetaan tietyn ohjelman treenit.  
     * @param treeniohjelmanNumero treeniohjelman viitenumero 
     */
    public Treeni(int treeniohjelmanNumero) {
        this.ohjelmanNumero = treeniohjelmanNumero;
    }


    /**
     * 
     * @return palauttaa päivämäärän string mmuotoisena
     */
    public String getPvm() {
        return pvm;
    }


    /**
     * 
     * @param pvm parametri, jolla asetetaan päivämäärä
     * @return palauttaa asetetun paivamaaran
     */
    public String setPvm(String pvm) {
        return this.pvm = pvm;
    }


    /**
     * 
     * @return palauttaa treenin nimen
     */
    public String getTreeninNimi() {
        return this.treeninNimi;
    }


    /**
     * 
     * @param treeninNimi asettaa treenin nimen 
     * @return palauttaa asetetun treenin nimen
     */
    public String setTreeninNimi(String treeninNimi) {
        return this.treeninNimi = treeninNimi;
    }


    /**
     * 
     * @return palauttaa treenin tunnisteen
     */
    public int getTreeninId() {
        return treeninId;
    }


    /**
     * 
     * @param Id asettaa treenille tunnisteen
     */
    public void setTreeninId(int Id) {
        this.treeninId = Id;
        if(treeninId >= treeninSeuraavaID)
            treeninSeuraavaID = treeninId + 1;
        
    }
    
    /**
     * testaa
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTreeninId() + '|' + ohjelmanNumero + '|' + getTreeninNimi() + '|' + getPvm();
    }


    /**
     * @param out harrastuksen tiedot
     */
    public void tulosta(PrintStream out) {
        out.println(this.getTreeninId() + " " + this.getTreeninNimi() + " "
                + this.getPvm());
    }


    /**
     * @param os ohjelman tiedot
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));

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
        int uusiNumero = getTreeninNumero();
        setTreeninId(Mjonot.erota(merkkijono, '|', getTreeninId()));
        setOhjelmanNumero(Mjonot.erota(merkkijono, '|', uusiNumero));
        treeninNimi = Mjonot.erota(merkkijono, '|', treeninNimi);
        pvm = Mjonot.erota(merkkijono, '|', pvm);
    }


    /**
     * Antaa treenille seuraavan tunnistenumeron.
     * @return treenin uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Treeni pitsi1 = new Treeni();
     *   pitsi1.getTreeninId() === 0;
     *   pitsi1.lisaaTreeninId();
     *   Treeni pitsi2 = new Treeni();
     *   pitsi2.lisaaTreeninId();
     *   int n1 = pitsi1.getTreeninId();
     *   int n2 = pitsi2.getTreeninId();
     *   n1 === n2-1;
     * </pre>
     */
    public int lisaaTreeninId() {
        treeninId = treeninSeuraavaID;
        treeninSeuraavaID++;
        return treeninId;
    }


    /**
    * Apumetodi, jolla saadaan täytettyä testiarvot treenille.
    * @param nro viite treeniohjelmaan, jonka treenistä on kyse
    */
    public void vastaaTreenia(int nro) {
        this.setOhjelmanNumero(nro);
        this.setTreeninNimi("Rintatreeni");
    }


    /**
     * Testiohjelma Harrastukselle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Treeni har = new Treeni(), par = new Treeni();
        har.lisaaTreeninId();
        har.vastaaTreenia(2);
        par.lisaaTreeninId();
        par.vastaaTreenia(1);
        
        har.tulosta(System.out);
        par.tulosta(System.out);
    }


    /**
     * @return palauttaa ohjelman numeron
     */ 
    public int getTreeninNumero() { 
        return ohjelmanNumero;
    }


    /**
     * @param treeninNumero asettaa ohjelmannumeron
     */
    public void setOhjelmanNumero(int treeninNumero) {
        this.ohjelmanNumero = treeninNumero;
    }

}
