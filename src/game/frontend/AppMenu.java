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
        //Se añade al menu el botón Archivo con la opción de sair
        Menu file = new Menu("Archivo");
        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(event -> {
            requestConfirmationAndThen(
                    "Salir",
                    "Salir de la aplicación",
                    "¿Está seguro que desea salir de la aplicación?",
                    Platform::exit
            );
        });
        file.getItems().add(exitMenuItem);

        //Se añade al menu el botón Ayuda con información sobre los autores del programa
        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Candy TPE");
            alert.setContentText("Cátedra POO 2018.\n" +
                    "Implementación Original: Laura Zabaleta (POO 2013).\n\nModificación Para Examen Final 2020 2Q:\nNicolás Brisa\nValentín Ye Li\nMassimo Cantú\nDavid Itcovici");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);

        //Se añade al menu el botón Niveles que permite seleccionar el modo de juego entre 4 opciones.
        Menu levels = new Menu("Niveles");
        MenuItem level0 = new LevelSelectMenuItem("Estándar", () -> CandyGame.instance.setLevel(new Level0()));
        MenuItem level1 = new LevelSelectMenuItem("Golden Board", () -> CandyGame.instance.setLevel(new Level1()));
        MenuItem level2 = new LevelSelectMenuItem("Wall Blast", () -> CandyGame.instance.setLevel(new Level2()));
        MenuItem level4 = new LevelSelectMenuItem("Time Limit", () -> CandyGame.instance.setLevel(new Level4()));
        levels.getItems().add(level0);
        levels.getItems().add(level1);
        levels.getItems().add(level2);
        levels.getItems().add(level4);
        getMenus().addAll(file, levels, help);
    }

    //Este metodo abre un pop-up con botones de selección "OK" y "CANCEL"
    static void requestConfirmationAndThen(String title, String headerText, String contentText, Runnable then) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            then.run();
        }
    }

    //Esta clase permite crear las opciones para seleccionar nivel de forma más organizada
    public static class LevelSelectMenuItem extends MenuItem{

        public LevelSelectMenuItem(String name, Runnable then) {
            super(name);
            setOnAction(event -> requestConfirmationAndThen(
                    "Cambiar de nivel",
                    "Cambiar al nivel " + name,
                    "¿Está seguro que desea salir? Se perdera el progreso del nivel actual.",
                    then
            ));

        }
    }
}
