package game.frontend;

import javafx.application.Platform;
import javafx.scene.control.*;

import java.util.Optional;

public class AppMenu extends MenuBar {

    public AppMenu() {
        Menu file = new Menu("Archivo");
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(event -> {
            requestConfirmationAndThen(
                    "Salir",
                    "Salir de la aplicación",
                    "¿Está seguro que desea salir de la aplicación?",
                    () -> Platform.exit()
            );
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
        MenuItem level0 = new MenuItem("Estándar");
        level0.setOnAction(event -> {
            requestConfirmationAndThen(
                    "Cambiar de nivel",
                    "Cambiar al nivel Estándar",
                    "¿Está seguro que desea salir del nivel actual?",
                    () -> System.out.println("Not implemented")
            );
        });
        MenuItem level1 = new MenuItem("Golden Board");
        MenuItem level2 = new MenuItem("Wall Blast");
        MenuItem level4 = new MenuItem("Time Limit");
        levels.getItems().add(level0);
        levels.getItems().add(level1);
        levels.getItems().add(level2);
        levels.getItems().add(level4);
        getMenus().addAll(file, help, levels);
    }

    void requestConfirmationAndThen(String title, String headerText, String contentText, Runnable then) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            then.run();
        }
    }
}
