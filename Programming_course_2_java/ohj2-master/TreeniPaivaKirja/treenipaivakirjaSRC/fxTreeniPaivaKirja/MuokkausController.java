package fxTreeniPaivaKirja;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.*;
import treeniPaivaKirja.Liike;
import treeniPaivaKirja.SailoException;
import treeniPaivaKirja.Treeni;
import treeniPaivaKirja.TreeniPaivaKirja;
import treeniPaivaKirja.Treeniohjelma;
import treeniPaivaKirja.Treeniohjelmat;

/**
 * @author saids
 * @version 26.3.2021
 *
 */
public class MuokkausController implements ModalControllerInterface<TreeniPaivaKirja>, Initializable {
    @FXML
    private ListChooser<Treeniohjelma> chooserOhjelmat;
    @FXML
    private ListChooser<Treeni> chooserTreenit;
    @FXML
    private ListChooser<Liike> chooserLiikkeet;
    
    private TreeniPaivaKirja treenipaivakirja;
    private Treeniohjelma treeniOhjelmanKohdalla;
    private Treeni treeninKohdalla;
    private String treeniPaivaKirjanNimi = "TreeniOhjelmat";
    
//todo
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    @FXML 
    private void AvaaInfoSivu() {
        
        InfoController.avaaInfo(treenipaivakirja, "Info");
        
    }
    
    @FXML
    private void SuljeIkkuna() {
        TallennaTiedot();
        //Ikkunnan sulkeminen
    }
    
    @FXML
    private void TallennaTiedot() {
        tallenna();
    }
    
    @FXML
    private void PalaaEdelliseenIkkunaan() {
        TallennaTiedot();
        //ikkunan sulkeminen
    }
    
    @FXML 
    private void poistaTietoja() {
        Dialogs.showMessageDialog("Poistetaan tietoja");
    }
    
    @FXML
    private void lisaaTreeniOhjelma() {
        //
        uusiTreeniOhjelma();
    }
    
    @FXML
    private void lisaaTreeni() {
       //
        uusiTreeni();
    }
    
    @FXML 
    private void lisaaLiikeTreenille() {
        //
        uusiLiike();
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Tekee tarvittavat muut alustukset
     * Alustetaan myös Treeniohjelmalistan kuuntelija 
     */
    
    protected void alusta() {
        chooserOhjelmat.clear();
        chooserTreenit.clear();
        chooserLiikkeet.clear();
        chooserOhjelmat.addSelectionListener(e -> naytaOhjelma());
        chooserTreenit.addSelectionListener(e -> naytaTreeni());
        
    }
    
    /**
     * tallenna metodi treenipaivakirjan controllerille
     */
    private String tallenna() {
        try {
            treenipaivakirja.tallenna();
            Dialogs.showMessageDialog("Tallennettu!");
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmaa! " + e.getMessage());
            return e.getMessage();
        }
    }
    
    /**
     * 
     *
    public void setTreeniPaivaKirja(TreeniPaivaKirja treenipaivakirj) {
        this.treenipaivakirja = treenipaivakirj;
        naytaOhjelma();
        naytaTreeni();
    } */
    
    @Override
    public TreeniPaivaKirja getResult() {
        // TODO Auto-generated method stub
        return this.treenipaivakirja;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(TreeniPaivaKirja oletus) {
        this.treenipaivakirja = oletus;
        hae(0);
        haeTreeni(0);
        haeLiike(0);
    }
    
    /**
     * @param treenipaivakirja treenipaivakirja
     * @param ikkunalleNimi Antaa avattavalle dokumentille nimen ikkunaan
     */
    public static void avaaIkkuna(TreeniPaivaKirja treenipaivakirja, String ikkunalleNimi) {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource("OhjelmanMuokkaus.fxml");
        ModalController.showModal(resurssi, ikkunalleNimi, null, treenipaivakirja);
    }
    

    /**
     * Näyttää listasta valitun treeniohjelman tiedot
     */
    protected void naytaOhjelma() {
        treeniOhjelmanKohdalla = chooserOhjelmat.getSelectedObject();
        if (treeniOhjelmanKohdalla == null) {
            return;
        }
        haeTreeni(chooserTreenit.getSelectedIndex());
        
    }
    
    /**
     * Näyttää listasta valitun treenin tiedot
     */
    protected void naytaTreeni() {
        treeninKohdalla = chooserTreenit.getSelectedObject(); 
        if (treeniOhjelmanKohdalla == null)
            return;
        haeLiike(chooserLiikkeet.getSelectedIndex());
    }


    /**
     * 
     * @param tONum treeniohjelman tunnistenumero
     */
    protected void hae(int tONum) {
        chooserOhjelmat.clear();
        int index = 0;
        for (int i = 0; i < treenipaivakirja.getTreeniOhjelmat(); i++) {
            Treeniohjelma ohjelma = treenipaivakirja.annaOhjelma(i);
            if (ohjelma.getTreeniohjelmanId() == tONum)
                index = i;
            chooserOhjelmat.add(ohjelma.getTreeniohjelmanNimi(), ohjelma);
        }
        chooserOhjelmat.setSelectedIndex(index);
    }
    /**
     * 
     * @param treeninNumero treeniohjelman tunnistenumero
     */
    protected void haeTreeni(int treeninNumero) {
        chooserTreenit.clear();
        int index = 0;
        int i = 0;
        List<Treeni> treenit = treenipaivakirja.annaTreenit(treeniOhjelmanKohdalla);
        for (Treeni treeni : treenit) {
            if (treeni.getTreeninId() == treeninNumero)
                index = i;
            chooserTreenit.add(treeni.getTreeninNimi(), treeni);
            i++;
        }
        if (index < 0) return;
        chooserTreenit.setSelectedIndex(index);
    }
    /**
     * haeLiike aliohjelma, joka hakee liikkeet listaan
     * @param liikkeenNumero liikkeenId johon verrataan
     */
    protected void haeLiike(int liikkeenNumero) {
        chooserLiikkeet.clear();
        int index = 0;
        int i = 0;
        List<Liike> liikkeet = treenipaivakirja.annaLiikkeet(treeninKohdalla);
        for (Liike liike : liikkeet) {
            if (liike.getLiikkeenId() == liikkeenNumero);
                index = i;
            chooserLiikkeet.add(liike.getNimi(), liike);
            i++;
        }
        if (index <= 0) return;
        chooserLiikkeet.setSelectedIndex(index);
    }
    
    /**
     * Lisätään treeniohjelma päiväkirjaan
     */
    protected void uusiTreeniOhjelma() {
        Treeniohjelma treeniohjelma = new Treeniohjelma();
        treeniohjelma.lisaaIdOhjelmalle();
        treeniohjelma.testiOhjelma(); // TODO: korvaa dialogilla aikanaan
        try {
            treenipaivakirja.lisaa(treeniohjelma);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tila on täynnä " + e.getMessage());
            return;
        }
        hae(treeniohjelma.getTreeniohjelmanId());
    }


    /**
     * Lisätään treeni päiväkirjaan
     */
    protected void uusiTreeni() {
        treeniOhjelmanKohdalla = chooserOhjelmat.getSelectedObject();
        if (treeniOhjelmanKohdalla == null)
            return;
        Treeni treeni = new Treeni();
        treeni.lisaaTreeninId();
        treeni.vastaaTreenia(treeniOhjelmanKohdalla.getTreeniohjelmanId());
        treenipaivakirja.lisaa(treeni);
        hae(treeniOhjelmanKohdalla.getTreeniohjelmanId());
        haeTreeni(treeni.getTreeninId());
        
    }


    /**
     * Lisätään liike päiväkirjaan
     */
    protected void uusiLiike() {
        treeninKohdalla = chooserTreenit.getSelectedObject();
        if (treeninKohdalla == null)
            return;
        Liike liike = new Liike();
        liike.lisaaIdLiikkeelle();
        liike.liitaTreeniin(treeninKohdalla.getTreeninId());        
        treenipaivakirja.lisaa(liike);
        haeTreeni(treeninKohdalla.getTreeninId());
        haeLiike(liike.getLiikkeenId());
    }
    
    /**
     * @param nimi nimi joka haetaan treenipaivakirjalle
     * @return virhe
     *
    protected String lueTiedosto(String nimi) {
        treeniPaivaKirjanNimi = nimi;
        try {
            treenipaivakirja.lueTiedostosta(nimi);
            hae(0);
            haeTreeni(0);
            haeLiike(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            haeTreeni(0);
            haeLiike(0);
            String virhe = e.getMessage();
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }*/
    
}
