package methodoptimization;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import methods.Methods;
import methods.NameMethods;
import methods.Point;

public class MainWindowController implements Initializable {
    private File file;
    private FileChooser fileChooser;
    private Methods methods;
    
    @FXML
    private MenuItem save;                              //Кнопка меню "Сохранить"
    @FXML
    private MenuItem saveAs;                            //Кнопка меню "Сохранить как"
    @FXML
    private MenuItem exit;                              //Кнопка меню "Выход"
    @FXML
    private MenuItem clear;                             //Кнопка меню "Очистить"
    @FXML
    private MenuItem fullScreen;                        //Кнопка меню "На полный экран/Оконный режим"
    @FXML
    private MenuItem aboutMethod;                       //Кнопка меню "О методе"
    @FXML
    private MenuItem aboutProgram;                      //Кнопка меню "О программе"
    @FXML
    private MenuItem help;                              //Кнопка меню "Справка"
    @FXML
    private TableView logs;                             //Таблица для вывода
    @FXML
    private TableColumn colNum;                         //Столбец № шага
    @FXML
    private TableColumn colX11;                         //Столбец x1
    @FXML
    private TableColumn colX12;                         //Столбец x2
    @FXML
    private TableColumn colFunc;                        //Столбец значение функции
    @FXML
    private TableColumn colPower;                       //Столбец порядок сходимости
    @FXML
    private TableColumn colSpeed;                       //Столбец скорость сходимости
    @FXML
    private ComboBox<NameMethods> method;                    //Поле выбора метода оптимизации
    @FXML
    private TextField x11;                              //Поле ввода значения начального вектора
    @FXML
    private TextField x12;                              //Поле ввода значения начального вектора
    @FXML
    private TextField a;                                //Поле ввода значения параметра А
    @FXML
    private TextField amoStep;                          //Поле ввода значения количества шагов
    @FXML
    private TextField printStep;                        //Поле ввода значения количества шагов, через которые печатается результат
    @FXML
    private TextField startStep;                        //Поле ввода значения начального шага
    @FXML
    private Button step;                                //Кнопка, отвечающая за шаг
    
    /**
     * Сохранение файла с заменой ранее выбранного, 
     * если фаил не был ранее выбран, 
     * то вызовится метод MainWindowController.onSaveAs()
     */
    @FXML
    private void onSave() {
        if (null != file) {
            throw new UnsupportedOperationException("Not supported yet");
        } else onSaveAs();
    }
    
    /**
     * Сохранение файла с выбором файла в диалоговом режиме. 
     * Если фаил не будет выбран, то сохранения не произайдет.
     */
    @FXML
    private void onSaveAs() {
        //Если пользователь сохранял фаил, то откроется дириктория с этим файлом
        if (null != file) fileChooser.setInitialDirectory(file.getParentFile());
        
        File newFile = fileChooser.showSaveDialog(null);
        if (null != newFile) {
            file = newFile;
            onSave();
        }
    }
    
    /**
     * Выход из программы с выдачей диалогового окна с параметрами 
     * "Сохранить", "Не сохранять", "Отмена".
     * В случаи отмены, выход совершён не будет.
     */
    @FXML
    private void onExit() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Очистка таблицы без сохранения данных.
     */
    @FXML
    private void onClear() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Разворачивание на полный экран окна и возврат к оконному режиму.
     */
    @FXML
    private void onFullScreen() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Выдача диалогового окна с информацией о текущем методе оптимизации.
     */
    @FXML
    private void onAboutMethod() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Выдача диалогового окна с информацией о программе.
     */
    @FXML
    private void onAboutProgram() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация о программе");
        alert.setHeaderText("Java Method Optimozation v1.0");
        alert.setContentText("Программа разработана для учебных целей.\n"
                + "Программа не предназначена для коммерческого использования.\n"
                + "Если вы обнаружили баги, свяжитесь по e-mail: ilyabakaev@mail.ru");
        alert.showAndWait();
    }
    
    /**
     * Выдача диалогового окна со справкой по программе.
     */
    @FXML
    private void onHelp() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Подсчет программой по заданным условиям и выдачей результата в таблицу.
     */
    @FXML
    private void onStep() {
        boolean doStep = true;
        Point p = new Point();
        int stepsAmount = 0;
        int stepsPrint = 0;
        double stepsStart;
        
        try {
            p.setX1(Double.parseDouble(x11.getText()));
        } catch (Exception e) {
            x11.setText("");
            doStep = false;
        }
        
        try {
            p.setX2(Double.parseDouble(x12.getText()));
        } catch (Exception e) {
            x12.setText("");
            doStep = false;
        }
        
        try {
            stepsAmount = Integer.parseInt(amoStep.getText());
            if (stepsAmount <= 0) throw new Exception();
        } catch (Exception e) {
            amoStep.setText("");
            doStep = false;
        }
        
        try {
            stepsPrint = Integer.parseInt(printStep.getText());
            if (stepsPrint <= 0) throw new Exception();
        } catch (Exception e) {
            printStep.setText("");
            doStep = false;
        }
        
        try {
            stepsStart = Double.parseDouble(startStep.getText());
            if (stepsStart <= 0) throw new Exception();
            methods.setStep(stepsStart);
        } catch (Exception e) {
            startStep.setText("");
            doStep = false;
        }
        
        try {
            methods.setParameter(Double.parseDouble(a.getText()));
        } catch (Exception e) {
            a.setText("");
            doStep = false;
        }
        
        if (null == method.getValue()) doStep = false;
        
        if(doStep) {
            for (int i = 0; i < stepsAmount; i++) {
                Point newPoint = null;
                methods.setP1(p);
                switch (method.getValue()) {
                    case MethodBFS:
                        newPoint = methods.methodBFS();
                        break;
                    case MethodDFP:
                        newPoint = methods.methodDFP();
                        break;
                    case MethodNewtons:
                        newPoint = methods.methodNewtons();
                        break;
                    case MethodOfRavines:
                        newPoint = methods.methodOfRavines();
                        break;
                    case MethodOfSteepestDescent:
                        newPoint = methods.methodOfSteepestDescent();
                        break;
                    case MethodPitchwise:
                        newPoint = methods.methodPitchwise();
                        break;
                    case MethodStepCrushing:
                        newPoint = methods.methodStepCrushing();
                        break;
                    case MethodToDecreaseTheLengthOfThePitch:
                        newPoint = methods.methodToDecreaseTheLengthOfThePitch();
                        break;
                }
                if (Point.minus(p, newPoint).norma() < 1e-15) break;
                if (0 == i % stepsPrint){
                    print();
                }
            }
            print();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Исправьте данные и повторите попытку");
            alert.setHeaderText("Данные введены некорректно");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные как");
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("Text format", "*.txt"));
        fileChooser.getExtensionFilters().add(1, new FileChooser.ExtensionFilter("All format", "*.*"));
        
        methods = new Methods();
        method.getItems().addAll(NameMethods.values());
        
        //поля, пока недоступные
        aboutMethod.setDisable(true);
        help.setDisable(true);
    }
    
    /**
     * Печатать на окно текущие шаги.
     */
    private void print() {
        
    }
}
