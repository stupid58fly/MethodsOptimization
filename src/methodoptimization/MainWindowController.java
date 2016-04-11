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

public class MainWindowController implements Initializable {
    private File file;
    private FileChooser fileChooser;
    
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
    private ComboBox method;                            //Поле выбора метода оптимизации
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
    public void onSave() {
        if (null != file) {
            throw new UnsupportedOperationException("Not supported yet");
        } else onSaveAs();
    }
    
    /**
     * Сохранение файла с выбором файла в диалоговом режиме. 
     * Если фаил не будет выбран, то сохранения не произайдет.
     */
    @FXML
    public void onSaveAs() {
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
    public void onExit() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Очистка таблицы без сохранения данных.
     */
    @FXML
    public void onClear() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Разворачивание на полный экран окна и возврат к оконному режиму.
     */
    @FXML
    public void onFullScreen() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Выдача диалогового окна с информацией о текущем методе оптимизации.
     */
    @FXML
    public void onAboutMethod() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Выдача диалогового окна с информацией о программе.
     */
    @FXML
    public void onAboutProgram() {
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
    public void onHelp() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    /**
     * Подсчет программой по заданным условиям и выдачей результата в таблицу.
     */
    @FXML
    public void onStep() {
        throw new UnsupportedOperationException("Not supported yet");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные как");
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("Text format", "*.txt"));
        fileChooser.getExtensionFilters().add(1, new FileChooser.ExtensionFilter("All format", "*.*"));
        
        //поля, пока недоступные
        aboutMethod.setDisable(true);
        help.setDisable(true);
    }
}
