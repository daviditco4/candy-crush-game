package game.frontend;

import javafx.application.Platform;
import javafx.scene.control.*;

import java.util.Optional;

public class AppMenu extends MenuBar {

    public AppMenu() {
        Menu file = new Menu("Archivo");
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir de la aplicación");
            alert.setContentText("¿Está seguro que desea salir de la aplicación?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        });
        file.getItems().add(exitMenuItem);

        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Candy TPE");
            alert.setContentText("Cátedra POO 2018.\n" +
                    "Implementación Original: Laura Zabaleta (POO 2013).");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);

        Menu levels = new Menu("Niveles");
        MenuItem level1 = new MenuItem("Estandar");
        MenuItem level2 = new MenuItem("Golden Board");
        MenuItem level3 = new MenuItem("Wall Blast");
        MenuItem level4 = new MenuItem("Time Bomb");
        levels.getItems().add(level1);
        levels.getItems().add(level2);
        levels.getItems().add(level3);
        levels.getItems().add(level4);
        getMenus().addAll(file, help, levels);
    }

}
