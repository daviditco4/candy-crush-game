package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level0;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level4;
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
        level0.setOnAction(event -> requestConfirmationAndThen(
                "Cambiar de nivel",
                "Cambiar al nivel Estándar",
                "¿Está seguro que desea salir del nivel actual?",
                () -> CandyGame.instance.setLevel(new Level0())
        ));
        MenuItem level1 = new MenuItem("Golden Board");
        level1.setOnAction(event -> requestConfirmationAndThen(
                "Cambiar de nivel",
                "Cambiar al nivel Golden Board",
                "¿Está seguro que desea salir del nivel actual?",
                () -> CandyGame.instance.setLevel(new Level1())
        ));
        MenuItem level2 = new MenuItem("Wall Blast");
        level2.setOnAction(event -> requestConfirmationAndThen(
                "Cambiar de nivel",
                "Cambiar al nivel Golden Board",
                "¿Está seguro que desea salir del nivel actual?",
                () -> CandyGame.instance.setLevel(new Level2())
        ));
        MenuItem level4 = new MenuItem("Time Limit");
        level4.setOnAction(event -> requestConfirmationAndThen(
                "Cambiar de nivel",
                "Cambiar al nivel Golden Board",
                "¿Está seguro que desea salir del nivel actual?",
                () -> CandyGame.instance.setLevel(new Level4())
        ));
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
