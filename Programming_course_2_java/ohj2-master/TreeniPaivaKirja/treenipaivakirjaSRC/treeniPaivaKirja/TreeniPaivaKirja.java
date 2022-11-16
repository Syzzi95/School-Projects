package treeniPaivaKirja;

import java.io.File;
import java.util.*;

/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class TreeniPaivaKirja {
    //Tämä luokka kokoaa kaikki tietorakenteet ja toimii "linkkinä"
    
    private Treeniohjelmat treeniohjelmat = new Treeniohjelmat();
    private Treenit treenit = new Treenit();
    private Liikkeet liikkeet = new Liikkeet();
    private String hakemisto = "TreeniOhjelmat";
    
    /**
     * @return palauttaa treeniohjelmien lukumäärän
     */
    public int getTreeniOhjelmat() {
        return treeniohjelmat.getLkm();
    }
    
    /**
     * @return treenien lukumäärän
     */
    public int getTreenit() {
        return treenit.getTreenit();
    }
    
    /**
     * @return palauttaa liikkeiden lukumäärän
     */
    public int getLiikkeet() {
        return liikkeet.getLiikkeet();
    }
    /**
    * Poistaa jäsenistöstä ja harrasteista ne joilla on nro. Kesken.
    * @param nro viitenumero, jonka mukaan poistetaan
    * @return montako jäsentä poistettiin
    */
    public int poista(@SuppressWarnings("unused") int nro) {
       return 0;
    }


   /**
    * Lisää treenipäiväkirjaan uuden treeniohjelman
    * @param ohjelma treeniohjelma parametrina
    * @throws SailoException jos lisäystä ei voida tehdä
    * @example
    * <pre name="test">
    * #THROWS SailoException
    * TreeniPaivaKirja uusi = new TreeniPaivaKirja();
    * Treeniohjelma aku1 = new Treeniohjelma(), aku2 = new Treeniohjelma();
    * aku1.lisaaIdOhjelmalle(); aku2.lisaaIdOhjelmalle();
    * uusi.getTreeniOhjelmat() === 0;
    * uusi.lisaa(aku1); uusi.getTreeniOhjelmat() === 1;
    * uusi.lisaa(aku2); uusi.getTreeniOhjelmat() === 2;
    * uusi.lisaa(aku1); uusi.getTreeniOhjelmat() === 3;
    * uusi.getTreeniOhjelmat() === 3;
    * uusi.annaOhjelma(0) === aku1;
    * uusi.annaOhjelma(1) === aku2;
    * uusi.annaOhjelma(2) === aku1;
    * uusi.annaOhjelma(3) === aku1; #THROWS IndexOutOfBoundsException 
    * uusi.lisaa(aku1); uusi.getTreeniOhjelmat() === 4;
    * uusi.lisaa(aku1); uusi.getTreeniOhjelmat() === 5;
    * uusi.lisaa(aku1);            #THROWS SailoException
    * </pre>
    */
   public void lisaa(Treeniohjelma ohjelma) throws SailoException {
       treeniohjelmat.lisaa(ohjelma);
   }


   /**
    * Listään uusi treeni treenipäiväkirjaan
    * @param treeni treenimuoto
    */
   public void lisaa(Treeni treeni) {
       treenit.lisaa(treeni);
   }
   
   /**
    * Listään uusi liike treenipäiväkirjaan
    * @param liike lisättävä treeniliike 
    */
   public void lisaa(Liike liike) {
       liikkeet.lisaa(liike);
   }


   /**
    * Palauttaa i:n treeniohjelman
    * @param i monesko treeniohjelma palautetaan
    * @return viite i:teen treeniohjelmaan
    * @throws IndexOutOfBoundsException jos i väärin
    */
   public Treeniohjelma annaOhjelma(int i) throws IndexOutOfBoundsException {
       return treeniohjelmat.anna(i);
   }


   /**
    * Haetaan kaikki Treeniohjelman treenit
    * @param treeniOhj treeniohjelma jolle treenejä haetaan
    * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
    * @example
    * <pre name="test">
    * #import java.util.*;
    * 
    *  TreeniPaivaKirja kerho = new TreeniPaivaKirja();
    *  Treeniohjelma aku1 = new Treeniohjelma(), aku2 = new Treeniohjelma(), aku3 = new Treeniohjelma();
    *  aku1.lisaaIdOhjelmalle(); aku2.lisaaIdOhjelmalle(); aku3.lisaaIdOhjelmalle();
    *  int id1 = aku1.getTreeniohjelmanId();
    *  int id2 = aku2.getTreeniohjelmanId();
    *  Treeni pitsi11 = new Treeni(id1); kerho.lisaa(pitsi11);
    *  Treeni pitsi12 = new Treeni(id1); kerho.lisaa(pitsi12);
    *  Treeni pitsi21 = new Treeni(id2); kerho.lisaa(pitsi21);
    *  Treeni pitsi22 = new Treeni(id2); kerho.lisaa(pitsi22);
    *  Treeni pitsi23 = new Treeni(id2); kerho.lisaa(pitsi23);
    *  
    *  List<Treeni> loytyneet;
    *  loytyneet = kerho.annaTreenit(aku3);
    *  loytyneet.size() === 0; 
    *  loytyneet = kerho.annaTreenit(aku1);
    *  loytyneet.size() === 2; 
    *  loytyneet.get(0) == pitsi11 === true;
    *  loytyneet.get(1) == pitsi12 === true;
    *  loytyneet = kerho.annaTreenit(aku2);
    *  loytyneet.size() === 3; 
    *  loytyneet.get(0) == pitsi21 === true;
    * </pre> 
    */
   public List<Treeni> annaTreenit(Treeniohjelma treeniOhj) {
       return treenit.annaTreenit(treeniOhj.getTreeniohjelmanId());
   }
   
   /**
    * Haetaan kaikki Treenien liikkeet
    * @param treenilleLiike Treenit jolle liikkeet haetaan
    * @return tietorakenne jossa viiteet löydettyihin treeneihin   
    * @example
    * <pre name="test">
    * #import java.util.*;
    * 
    *  TreeniPaivaKirja kerho = new TreeniPaivaKirja();
    *  Treeni eka = new Treeni(), toka = new Treeni(), kolmas = new Treeni();
    *  eka.lisaaTreeninId(); toka.lisaaTreeninId(); kolmas.lisaaTreeninId();
    *  int id1 = eka.getTreeninId();
    *  int id2 = toka.getTreeninId();
    *  int id3 = kolmas.getTreeninId();
    *  Liikkeet treenit = new Liikkeet();              
    *  Liike pitsi1 = new Liike(eka); treenit.lisaa(pitsi1);
    *  Liike pitsi2 = new Liike(eka); treenit.lisaa(pitsi2);
    *  Liike pitsi3 = new Liike(toka); treenit.lisaa(pitsi3);
    *  Liike pitsi4 = new Liike(toka); treenit.lisaa(pitsi4);
    *  Liike pitsi5 = new Liike(toka); treenit.lisaa(pitsi5);
    *  Liike pitsi6 = new Liike(kolmas); treenit.lisaa(pitsi6);
    *  
    *  List<Liike> loytyneet;
    *  
    *  loytyneet = treenit.annaLiikkeet(eka);
    *       loytyneet.size() === 2; 
    *       loytyneet.get(0) == pitsi1 === true;
    *       loytyneet.get(1) == pitsi2 === true;
    *       
    *  loytyneet = treenit.annaLiikkeet(toka);
    *       loytyneet.size() === 3; 
    *       loytyneet.get(0) == pitsi3 === true;
    *       loytyneet.get(1) == pitsi4 === true;
    *       loytyneet.get(2) == pitsi5 === true;
    *       
    *  loytyneet = treenit.annaLiikkeet(kolmas);
    *       loytyneet.size() === 1; 
    *       loytyneet.get(0) == pitsi6 === true;
    * </pre> 
    */
   public List<Liike> annaLiikkeet(Treeni treenilleLiike){
       return liikkeet.annaLiikkeet(treenilleLiike.getTreeninId());
   }
   

   /**
    * Lukee kerhon tiedot tiedostosta
    * @param nimi jota käyteään lukemisessa
    * @throws SailoException jos lukeminen epäonnistuu
    */
   public void lueTiedostosta(String nimi) throws SailoException {
       File dir = new File(nimi);
       dir.mkdir();
       treeniohjelmat = new Treeniohjelmat();
       treenit = new Treenit();
       liikkeet = new Liikkeet();
       
       hakemisto = nimi;
       treeniohjelmat.lueTiedostosta(nimi);
       treenit.lueTiedostosta(nimi);
       liikkeet.lueTiedostosta(nimi);
   }


   /**
    * Tallettaa kerhon tiedot tiedostoon
    * @throws SailoException jos tallettamisessa ongelmia
    */
   public void tallenna() throws SailoException {
       String virhe = "";
       try {
           treeniohjelmat.tallenna(hakemisto);
       } catch ( SailoException ex ) {
           virhe = ex.getMessage();
       }

       try {
           treenit.tallenna(hakemisto); //<-- lisää tohon argumentiks viel hakemisto 
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
           
       }
       
       try {
           liikkeet.tallenna(hakemisto); //<-- lisää tohon argumentiks viel hakemisto 
       } catch ( SailoException ex ) {
           virhe += ex.getMessage();
       }   
       if ( !"".equals(virhe) ) throw new SailoException(virhe);
 
   }

   /**
    * Testiohjelma kerhosta
    * @param args ei käytössä
    */
   public static void main(String args[]) {
       TreeniPaivaKirja kerho = new TreeniPaivaKirja();
       try {
           
           kerho.lueTiedostosta("TreeniOhjelmat"); 
           
       } catch (SailoException ex) {
           System.out.println(ex.getMessage());
       }
       
       try {

           Treeniohjelma eka = new Treeniohjelma(), toka = new Treeniohjelma();
           eka.lisaaIdOhjelmalle();
           eka.testiOhjelma("EKA");
           toka.lisaaIdOhjelmalle();
           toka.testiOhjelma("TOKA");

           kerho.lisaa(eka);
           kerho.lisaa(toka);
           int id1 = eka.getTreeniohjelmanId();
           int id2 = toka.getTreeniohjelmanId();
           Treeni rinta = new Treeni(id1); rinta.lisaaTreeninId(); rinta.vastaaTreenia(id1); kerho.lisaa(rinta);
           Treeni jalka = new Treeni(id1); jalka.lisaaTreeninId(); jalka.vastaaTreenia(id1); kerho.lisaa(jalka);
           Treeni rintaKevyt = new Treeni(id2); rintaKevyt.lisaaTreeninId(); rintaKevyt.vastaaTreenia(id2); kerho.lisaa(rintaKevyt);
           Treeni jalkaKevyt = new Treeni(id2); jalkaKevyt.lisaaTreeninId(); jalkaKevyt.vastaaTreenia(id2); kerho.lisaa(jalkaKevyt);
           Treeni työntö = new Treeni(id2); työntö.lisaaTreeninId(); työntö.vastaaTreenia(id2); kerho.lisaa(työntö);
           
           int id11 = rinta.getTreeninId();
           int id12 = jalka.getTreeninId();
           
           Liike penkki = new Liike(id11); penkki.lisaaIdLiikkeelle(); penkki.liitaTreeniin(id11); kerho.lisaa(penkki);
           Liike pystäri = new Liike(id11); pystäri.lisaaIdLiikkeelle(); pystäri.liitaTreeniin(id11); kerho.lisaa(pystäri);
           
           Liike kyykky = new Liike(id12); kyykky.lisaaIdLiikkeelle();  kyykky.liitaTreeniin(id12); kerho.lisaa(kyykky);
           Liike jp = new Liike(id12); jp.lisaaIdLiikkeelle(); jp.liitaTreeniin(id12); kerho.lisaa(jp);

       } catch (SailoException ex) {
           System.out.println(ex.getMessage());
       }
       try {

           
           System.out.println("============= Kerhon testi =================");

           for (int i = 0; i < kerho.getTreeniOhjelmat(); i++) {
               Treeniohjelma voima = kerho.annaOhjelma(i);
               System.out.println("Treeniohjelma paikassa: " + i);
               voima.tulosta(System.out);
               Treeni rinta = new Treeni(voima.getTreeniohjelmanId());
               List<Treeni> loydetytTreenit = kerho.annaTreenit(voima);
               
               for (Treeni treeni : loydetytTreenit)
                   treeni.tulosta(System.out);
                   List<Liike> loydetytLiikkeet = kerho.annaLiikkeet(rinta);
                   for(Liike liike : loydetytLiikkeet)
                       liike.tulosta(System.out);
           }
           
           kerho.tallenna();
           
       } catch (SailoException ex) {
           System.out.println(ex.getMessage());
       }
   } 
}
