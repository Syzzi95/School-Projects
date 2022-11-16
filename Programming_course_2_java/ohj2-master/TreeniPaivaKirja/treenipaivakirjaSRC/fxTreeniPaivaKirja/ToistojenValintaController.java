package fxTreeniPaivaKirja;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import treeniPaivaKirja.TreeniPaivaKirja;

/**
 * @author saids
 * @version 26.3.2021
 *
 */
public class ToistojenValintaController implements ModalControllerInterface<TreeniPaivaKirja>{
    
    TreeniPaivaKirja treenipaivakirja;
    
    @FXML 
    private void TallennaTiedot() {
        //tallenna();
    }
    
    @FXML 
    private void SuljeIkkuna() {
        //tallenna();

        Dialogs.showMessageDialog("Suljetaan ikkuna! Mutta ei toimi vielä");
    }
    
    @FXML 
    private void PalaaEdelliseenIkkunaan() {
        SuljeIkkuna();
        Dialogs.showMessageDialog(
                "Palataan edelliseen ikkunaan! Mutta ei toimi vielä");
    }
    
    @FXML 
    private void TallennaOmatToistot() {
        //tallennatoistot();
    }
    
    @FXML 
    private void LisaaToistopainot() {
        //lisaatoistopainot
    }
    
    /**
     * @param treenipaivakirja treenipaivakirja
     * @param ikkunalleNimi Antaa avattavalle dokumentille nimen ikkunaan
     */
    public static void avaaIkkuna(TreeniPaivaKirja treenipaivakirja, String ikkunalleNimi) {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource("LiikkeidenJaToistojenMaara.fxml");
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
    public void setDefault(TreeniPaivaKirja oletus) {
        // TODO Auto-generated method stub
        this.treenipaivakirja = oletus;
    }

    //todo
    
}
