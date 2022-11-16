package fxTreeniPaivaKirja;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import treeniPaivaKirja.TreeniPaivaKirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
//import fi.jyu.mit.fxgui.*;


/**
 * @author saids
 * @version 3.2.2021
 *
 */
public class TreeniPaivaKirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("TreeniPaivaKirjaGUIViewMain.fxml"));
            final Pane root = ldr.load();
            final TreeniPaivaKirjaGUIController treenipaivakirjaCtrl = (TreeniPaivaKirjaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("treenipaivakirja.css").toExternalForm());
            TreeniPaivaKirja treenipaivakirja = new TreeniPaivaKirja();
            treenipaivakirjaCtrl.setTreeniPaivaKirja(treenipaivakirja);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TreeniPaivaKirja");
            
            Application.Parameters params = getParameters();
            if (params.getRaw().size() > 0)
            treenipaivakirjaCtrl.lueTiedosto(params.getRaw().get(0));
            else treenipaivakirjaCtrl.lueTiedosto("TreeniOhjelmat");
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}