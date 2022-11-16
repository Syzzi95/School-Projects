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
public class TURHA_TuloksetController //implements ModalControllerInterface<TreeniPaivaKirja>
{ 
    /**
    
    TreeniPaivaKirja treenipaivakirja;
    
    
    @FXML
    private void AvaaInfoSivu() {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource("InfoSivu.fxml");
        ModalController.showModal(resurssi, "Tulokset", null, null);
    }


    @FXML
    private void AvaaNavigointiApu() {
        Dialogs.showMessageDialog(
                "Tähän aukeaa navigointiapu! Mutta ei toimi vielä");
    }
    
    @FXML 
    private void poistaTietoja() {
        //TODO
    }
    
    
    @Override
    public TreeniPaivaKirja getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(TreeniPaivaKirja oletus) {
        // TODO Auto-generated method stub
        
    }
    /**
     * 
     * @param treenipaivis treenipaivakirja
     * @param ikkunalleNimi nimin joka asetetaan päiväkirjalle
     *
    public static void avaaIkkuna(TreeniPaivaKirja treenipaivis, String ikkunalleNimi) {
        var resurssi = TreeniPaivaKirjaGUIController.class
                .getResource("Tulokset.fxml");
        ModalController.showModal(resurssi, ikkunalleNimi, null, treenipaivis);
    }
//todo */
}

