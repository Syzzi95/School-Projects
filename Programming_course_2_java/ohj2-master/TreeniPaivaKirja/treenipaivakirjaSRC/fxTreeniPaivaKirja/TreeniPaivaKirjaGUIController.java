package fxTreeniPaivaKirja;

import fi.jyu.mit.fxgui.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import treeniPaivaKirja.Liike;
import treeniPaivaKirja.SailoException;
import treeniPaivaKirja.Treeni;
import treeniPaivaKirja.TreeniPaivaKirja;
import treeniPaivaKirja.Treeniohjelma;
import treeniPaivaKirja.Treeniohjelmat;

//import javafx.scene.control.Button;
/**
 * @author saids
 * @version 3.2.2021
 */
public class TreeniPaivaKirjaGUIController
        implements ModalControllerInterface<TreeniPaivaKirja>, Initializable {

    @FXML
    private ListChooser<Treeniohjelma> chooserOhjelmat;
    @FXML
    private ListChooser<Treeni> chooserTreenit;
    @FXML
    private ListChooser<Liike> chooserLiikkeet;
   
 
    /**
     *  @FXML
     *
    private Button kirjautumisNappain;
    @FXML
    private Button rekisteroitymisNappain;  
    */
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @FXML
    private void AvaaInfoSivu() {
        
        InfoController.avaaInfo(treenipaivakirja, "Info");
    }


    @FXML
    private void AvaaNavigointiApu() {
        Dialogs.showMessageDialog(
                "Tähän aukeaa navigointiapu! Mutta ei toimi vielä");
    }

    @FXML
    private void AvaaTreeniMuodot() {
        MuokkausController.avaaIkkuna(treenipaivakirja, "Ohjelmien lisäys");
    }

  
    /**
     * Avataan liikkeet ja sarjat ikkuna
     */
    @FXML
    public void MeneValitsemaanLiikkeetJaSarjat() {
        ToistojenValintaController.avaaIkkuna(treenipaivakirja, "Liikkeiden ja toistojen valinta");
    }

    @FXML
    private void PalaaEdelliseenIkkunaan() {
        Dialogs.showMessageDialog(
                "Palataan edelliseen ikkunaan! Mutta ei toimi vielä");
    }


    @FXML
    private void SuljeIkkuna() {
        Dialogs.showMessageDialog("Suljetaan ikkuna! Mutta ei toimi vielä");
    }


    @FXML
    private void TallennaTiedot() {
        tallenna();
    }

    /**
     * 
     */
    @FXML
    public void poistaTietoja() {
        Dialogs.showMessageDialog("Poistetaan tietoja! Mutta ei toimi vielä");
    }


    @FXML
    private void LisaaToistopainot() {
        Dialogs.showMessageDialog("Tallennetaan toistopainot");
    }


    @FXML
    private void TallennaOmatToistot() {
        Dialogs.showMessageDialog("Tallennetaan oma määrä toistoja");
    }

    // Tänne yläpuolelle FXML-koodit
    // ________________________________________________________________________________________________________________
    // ________________________________________________________________________________________________________________
    //
    
    // Tänne alapuolelle toiminnalliset itse kirjoitetut koodit
    // Tee oma ModalController Luokka.
    /**
     * attribuutti
     */
    private String treeniPaivaKirjanNimi = "TreeniOhjelmat";
    private TreeniPaivaKirja treenipaivakirja;
    private Treeniohjelma treeniOhjelmanKohdalla;
    private Treeni treeninKohdalla;
    
    
    /**
     * 
     * @param ikkunaFXML Avattavan FXML-dokumentin nimi
     * @param ikkunalleNimi Antaa avattavalle dokumentille nimen ikkunaan
     */
    public void avaaIkkuna(String ikkunaFXML, String ikkunalleNimi) {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource(ikkunaFXML);
        ModalController.showModal(resurssi, ikkunalleNimi, null, null);
    }
    
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
     * @param treenipaivakirj luokka jota käytetään
     */
    public void setTreeniPaivaKirja(TreeniPaivaKirja treenipaivakirj) {
        this.treenipaivakirja = treenipaivakirj;
    }

    /**
     * 
     */
    @Override
    public TreeniPaivaKirja getResult() {
        // TODO Auto-generated method stub
        return this.treenipaivakirja;
    }

    /**
     * 
     */
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub

    }
    
    /**
     * @param oletus oletusasetus
     */
    @Override
    public void setDefault(TreeniPaivaKirja oletus) {
        // TODO Auto-generated method stub
        this.treenipaivakirja = oletus;
        
        //lueTiedosto(treeniPaivaKirjanNimi); minne helkkariin tää menee?
    }

     

    /**
     * tallenna metodi treenipaivakirjan controllerille
     */
    private String tallenna() {
        try {
            treenipaivakirja.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmaa! " + e.getMessage());
            return e.getMessage();
        }
    }
    
    /**
     * @param nimi tiedoston nimi
     * @return palauttaa tyhjää tai mikäli ei onnistu niin virheen
     */
    protected String lueTiedosto(String nimi) {
        treeniPaivaKirjanNimi = nimi;
        try {
            treenipaivakirja.lueTiedostosta(nimi);
            hae(0);
            haeTreeni(0);
            haeLiike(0);
            return null;
        } catch (SailoException e) {
            String virhe = e.getMessage();
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
        //hae 
    }
   
}