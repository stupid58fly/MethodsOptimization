package methodoptimization;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class DialogSingleton {
    static private String title = "Methods of Optimization v1.0";
    static private Alert aboutProgramDialog;
    static private Alert errorFileSave;
    static private Alert errorInput;
    static private Alert calculationIsOver;
    static private TextInputDialog epsilon;
    static private Alert exit;
    
    synchronized public static void showAboutProgramDialog() {
        if (null == epsilon) {
            aboutProgramDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutProgramDialog.setTitle("Информация о программе");
            aboutProgramDialog.setHeaderText("Java Method Optimozation v1.0");
            aboutProgramDialog.setContentText("Программа разработана для учебных целей.\n"
                    + "Программа не предназначена для коммерческого использования.\n"
                    + "Если вы обнаружили баги, свяжитесь по e-mail: ilyabakaev@mail.ru");
        }
        
        aboutProgramDialog.show();
    }
    
    synchronized public static void showErrorFileSaveDialog() {
        if (null == errorFileSave) {
            errorFileSave = new Alert(Alert.AlertType.ERROR);
            errorFileSave.setTitle("Ошибка");
            errorFileSave.setContentText("Данные не были записаны в фаил");
            errorFileSave.setHeaderText("Что-то пошло не так");
        }
        
        errorFileSave.show();
    }
    
    synchronized public static void showErrorInputDialog() {
        if (null == errorInput) {
            errorInput = new Alert(Alert.AlertType.ERROR);
            errorInput.setTitle("Ошибка");
            errorInput.setContentText("Исправьте данные и повторите попытку");
            errorInput.setHeaderText("Не все данные введены или введены некорректно");
        }
        
        errorInput.show();
    }
    
    synchronized public static void showCalculationIsOverDialog() {
        if (null == calculationIsOver) {
            calculationIsOver = new Alert(Alert.AlertType.INFORMATION);
            calculationIsOver.setTitle(title);
            calculationIsOver.setHeaderText("Подсчёт завершен");
            calculationIsOver.setContentText("Подсчёт был успешно завершён. Информация приведена в таблицу.");
        }
        
        calculationIsOver.show();
    }
    
    synchronized public static int showInputEpsilonDialog(Integer accuracy) {
        if (null == epsilon) {
            epsilon = new TextInputDialog();
            epsilon.setTitle(title);
            epsilon.setHeaderText("Задание точности вывода");
            epsilon.setContentText("Введите количество знаков после запятой");
        }
        
        epsilon.getEditor().setText(accuracy.toString());
        Optional<String> result = epsilon.showAndWait(); 
        try {
            if (result.isPresent())
                accuracy =  Integer.parseInt(result.get());        
        } catch(NumberFormatException e) {
        }
        
        return accuracy;
    }
    
    synchronized public static ButtonType showExitDialog() {
        if (null == exit) {
            exit = new Alert(Alert.AlertType.NONE);
            exit.setTitle("Выход из программы");
            exit.setContentText("Сохранить изменения?");
            exit.getButtonTypes().add(ButtonType.YES);
            exit.getButtonTypes().add(ButtonType.NO);
            exit.getButtonTypes().add(ButtonType.CANCEL);
        }
        
        Optional<ButtonType> result = exit.showAndWait();
        if (result.isPresent())
            return result.get();
        return ButtonType.CANCEL;
    } 
}
