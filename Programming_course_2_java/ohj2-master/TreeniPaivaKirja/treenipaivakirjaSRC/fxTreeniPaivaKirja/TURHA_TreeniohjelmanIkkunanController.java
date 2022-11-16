package fxTreeniPaivaKirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import treeniPaivaKirja.SailoException;
import treeniPaivaKirja.TreeniPaivaKirja;


/**
 * @author saids
 * @version 26.3.2021
 *
 */
public class TURHA_TreeniohjelmanIkkunanController// implements ModalControllerInterface<TreeniPaivaKirja> 
{
/**
    TreeniPaivaKirja treenipaivakirja;
    
    
    
    @FXML
    private void TallennaTiedot() {
        tallenna();
    }
    
    

    @FXML
    private void SuljeIkkuna() {
        //TODO

        Dialogs.showMessageDialog("Suljetaan ikkuna! Mutta ei toimi vielä");
    }
    
    @FXML 
    private void PalaaEdelliseenIkkunaan() {
        //TODO
        Dialogs.showMessageDialog(
                "Palataan edelliseen ikkunaan! Mutta ei toimi vielä");
    }
    @FXML
    private void AvaaTreeniMuodot() {
        //
    }
    @FXML
    private void AvaaInfoSivu() {
        //
    }
    
    @FXML 
    private void AvaaNavigointiApu() {
        //TODO
    }
    
    /**
     * @param treenipaivakirja treenipaivakirja
     * @param ikkunalleNimi Antaa avattavalle dokumentille nimen ikkunaan
     *
    public static void avaaIkkuna(TreeniPaivaKirja treenipaivakirja, String ikkunalleNimi) {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource("TreeniOhjelma.fxml");
        ModalController.showModal(resurssi, ikkunalleNimi, null, treenipaivakirja);
    }


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
    public void setDefault(TreeniPaivaKirja treenipaivakirja) {
        // TODO Auto-generated method stub
        this.treenipaivakirja = treenipaivakirja;
    }
   
    private String tallenna() { 
       // TODO Auto-generated method stub
        try {
            treenipaivakirja.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmaa! " + e.getMessage());
            return e.getMessage();
        }
    }*/
}
