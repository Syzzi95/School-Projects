package treeniPaivaKirja;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author saids
 * @version 17.3.2021
 *
 */
public class Liike {
    //tee tähän ohjelma joka käsittelee liikettä
    private int liikkeenTunnus = 0; 
    private int treeninTunniste; 
    private String liikkeenNimi; 
    private int sarjat; 
    private int toistot; 
    private int paino;
    private int yhteensaNostettu;
    
    private static int seuraavaLiike = 1;
    
    /**
     * Liikkeen alustus
     */
    public Liike() {
    }
    
    /**
     * @param treeniTunniste liitetään Liike treeniin
     */
    public Liike(int treeniTunniste) {
        this.setTreeninTunniste(treeniTunniste);
    }
    
    /**
     * @param nro numero
     * testiohjelma
     */
    public void liitaTreeniin(int nro) {
        this.setTreeninTunniste(nro);
        this.liikkeenNimi = "Ylätalja";
        this.sarjat = 5; 
        this.toistot = 10;
        this.paino = 100;
    }
    
    /**
     * @return palauttaa liikkeen nimen
     */
    public String getNimi() {
        return this.liikkeenNimi;
    }
    
    /**
     * @return palauttaa yhteensänostetun määrän
     */
    public int tuloksenTallennusMetodi() {
        
        this.yhteensaNostettu = (this.sarjat * this.toistot * this.paino); 
        return yhteensaNostettu;
    }
    /**
     * Lisää id liikkeelle
     * @return ID eli asettaa seuraavalle treeniohjelmalle tunnisteen
     * @example
     * <pre name="test">
     *   Liike penkki = new Liike();
     *   penkki.liikkeenTunnus === 0;
     *   penkki.lisaaIdLiikeelle();
     *   Liike kyykky = new Liike();
     *   kyykky.lisaaIdLiikkeelle();
     *   int n1 = penkki.liikkeenTunnus();
     *   int n2 = kestävyys.liikkeenTunnus();
     *   n1 === n2-1;
     * </pre>
     */
    public int lisaaIdLiikkeelle() {
         liikkeenTunnus = seuraavaLiike; 
         seuraavaLiike++;
         return liikkeenTunnus;
    }
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.liikkeenNimi + " " + this.sarjat + " " + this.toistot + " " + this.paino + " " + this.yhteensaNostettu);
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
        return "" + getLiikkeenId() +
    '|' + getTreeninTunniste() +
    '|' + getNimi() +
    '|' + this.sarjat +
    '|' + this.toistot +
    '|' + this.paino;
    }
    
    
    /**
     * muista testata
     * pilkotaan merkkijono luettavaan tiedostoon
     * @param s parsetettava merkkijono
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void parse(String s) {
        var merkkijono = new StringBuilder(s);
        int liikTrnTun = getTreeninTunniste();
        setLiikkeenId(Mjonot.erota(merkkijono, '|', getLiikkeenId()));
        setTreeninTunniste(Mjonot.erota(merkkijono, '|', liikTrnTun));
        liikkeenNimi = Mjonot.erota(merkkijono, '|', liikkeenNimi);
        sarjat = Mjonot.erota(merkkijono, '|', sarjat);
        toistot = Mjonot.erota(merkkijono, '|', toistot);
        paino = Mjonot.erota(merkkijono, '|', paino);
    } 
    
    /**
     * 
     * @param id asetetaan liikkeelle Id
     */
    private void setLiikkeenId(int id) {
        // TODO Auto-generated method stub
        liikkeenTunnus = id;
        if (liikkeenTunnus >= seuraavaLiike)
            seuraavaLiike = liikkeenTunnus + 1; 
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Liike hyppy = new Liike();
        hyppy.liitaTreeniin(11);
        hyppy.tulosta(System.out);
    }

    /**
     * @return palauttaa treenin tunnisteen
     */
    public int getTreeninTunniste() {
        return treeninTunniste;
    }

    /**
     * @param treeninTunniste paskaaa
     */
    public void setTreeninTunniste(int treeninTunniste) {
        this.treeninTunniste = treeninTunniste;
    }
    
    /**
     * @return palauttaa liikkeen ominaistunnuksen
     */
    public int getLiikkeenId() {
        return this.liikkeenTunnus;
    }
    
    
}
