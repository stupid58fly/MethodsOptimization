package methodoptimization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import methods.Function;
import methods.NameMethods;
import utils.Point;

public class MainWindowController implements Initializable {
    private Stage stage;
    private File file;
    private FileChooser fileChooser;
    private ObservableList<TableContent> content;
    private long steps;
    
    private Point p1;                                   //начальная точка
    private long amount;
    private long print;
    private double start;
    
    @FXML
    private MenuItem fullScreen;                        //Кнопка меню "На полный экран/Оконный режим"
    @FXML
    private CheckMenuItem remember;
    @FXML
    private MenuItem aboutMethod;                       //Кнопка меню "О методе"
    @FXML
    private MenuItem help;                              //Кнопка меню "Справка"
    @FXML
    private TableView logs;                             //Таблица для вывода
    @FXML
    private TableColumn colNum;                         //Столбец № шага
    @FXML
    private TableColumn colX1;                          //Столбец x1
    @FXML
    private TableColumn colX2;                          //Столбец x2
    @FXML
    private TableColumn colFunc;                        //Столбец значение функции
    @FXML
    private TableColumn colPower;                       //Столбец порядок сходимости
    @FXML
    private TableColumn colSpeed;                       //Столбец скорость сходимости
    @FXML
    private ComboBox<NameMethods> method;               //Поле выбора метода оптимизации
    @FXML
    private TextField x1;                               //Поле ввода значения начального вектора
    @FXML
    private TextField x2;                               //Поле ввода значения начального вектора
    @FXML
    private TextField a;                                //Поле ввода значения параметра А
    @FXML
    private TextField amoStep;                          //Поле ввода значения количества шагов
    @FXML
    private TextField printStep;                        //Поле ввода значения количества шагов, через которые печатается результат
    @FXML
    private TextField startStep;                        //Поле ввода значения начального шага

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Сохранение файла с заменой ранее выбранного, 
     * если фаил не был ранее выбран, 
     * то вызовится метод MainWindowController.onSaveAs()
     */
    @FXML
    private void onSave() {
        if (null != file) {
            try (FileOutputStream fout = new FileOutputStream(file)) {
                for (TableContent e : content) {
                    fout.write(e.toString().getBytes());
                }
                fout.flush();
                fout.close();
            } catch (IOException ex) {
                DialogSingleton.showErrorFileSaveDialog();
            }
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
        content.clear();
        stage.close();  
    }
    
    /**
     * Очистка таблицы без сохранения данных.
     */
    @FXML
    private void onClear() {
        steps = 0L;
        content.clear();
        NameMethods.clear();
    }
    
    /**
     * Разворачивание на полный экран окна и возврат к оконному режиму.
     */
    @FXML
    private void onFullScreen() {
        if (stage.isFullScreen()) fullScreen.setText("На полный экран");
        else fullScreen.setText("Оконный режим");
        stage.setFullScreen(!stage.isFullScreen());
    }
    
    @FXML
    private void onEps() {
        int accuracy = TableContent.getFractionDigits();
        accuracy = DialogSingleton.showInputEpsilonDialog(accuracy);
        TableContent.setFractionDigits(accuracy);
        logs.refresh();
    }
    
    @FXML
    private void onRemember() {
        if (remember.isSelected()) remember();
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
        DialogSingleton.showAboutProgramDialog();
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
        if (remember.isSelected()) remember();
        else onClear();
        
        if(checkInput()) {
            if (steps == 0L) {
                print(p1, p1);
            }
            
            Point oldPoint = p1;
            NameMethods.p1 = p1;
            NameMethods m = method.getValue();
            int i;
            for (i = 0; i < amount && Function.gradient(p1).norma() >= 1e-15; i++) {
                if (p1.getX1().isNaN() || p1.getX1().isInfinite()) break;
                if (p1.getX2().isNaN() || p1.getX2().isInfinite()) break;
                
                ++steps;
                m.step();
                oldPoint = p1;
                p1 = NameMethods.p1;
                if (i % print == 0) print(oldPoint, p1);
            }
            
            if (i % print != 0) print(oldPoint, p1);
            DialogSingleton.showCalculationIsOverDialog();
        } else {
            DialogSingleton.showErrorInputDialog();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные как");
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("Text format", "*.txt"));
        fileChooser.getExtensionFilters().add(1, new FileChooser.ExtensionFilter("All format", "*.*"));
        
        method.getItems().addAll(NameMethods.values());
        method.setValue(NameMethods.MethodOfSteepestDescent);
        
        p1 = new Point();
        
        colNum.setCellValueFactory(new PropertyValueFactory<>("steps"));
        colX1.setCellValueFactory(new PropertyValueFactory<>("x1"));
        colX2.setCellValueFactory(new PropertyValueFactory<>("x2"));
        colFunc.setCellValueFactory(new PropertyValueFactory<>("func"));
        colPower.setCellValueFactory(new PropertyValueFactory<>("power"));
        colSpeed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        content = FXCollections.observableArrayList();
        logs.setItems(content);

        //поля, пока недоступные
        aboutMethod.setDisable(true);
        help.setDisable(true);
    }
    
    /**
     * Печатать на окно текущие шаги.
     */
    private void print(final Point pk, final Point pk1) {
        //заполнить 6-ю полями
        Point res = Function.getResult();
        Double resF = Function.getFunctionValue(res);
        Double power;
        Double speed;
        if (pk.equals(pk1)) {
            power = Double.NaN;
            speed = Double.NaN;
        } else {
            power = Math.log(Point.minus(pk1, res).norma())/Math.log(Point.minus(pk, res).norma());
            speed = (Function.getFunctionValue(pk1) - resF)/(Function.getFunctionValue(pk) - resF);
        }
        TableContent element = new TableContent(steps,
                                                pk1.getX1(),
                                                pk1.getX2(),
                                                Function.getFunctionValue(pk1),
                                                power, speed);
        content.add(element);
        logs.refresh();
    }
    
    /**
     *  Запоминает точки, после последнего шага
     */
    private void remember() {
        x1.setText(p1.getX1().toString());
        x2.setText(p1.getX2().toString());
    }
    
    /**
     * Проверка на корректность введённых польхователем данных.
     */
    private boolean checkInput() {
        boolean result = true;
        try {
            p1.setX1(Double.parseDouble(x1.getText()));
        } catch (Throwable e) {
            x1.setText("");
            result = false;
        }
        
        try {
            p1.setX2(Double.parseDouble(x2.getText()));
        } catch (Throwable e) {
            x2.setText("");
            result = false;
        }
        
        try {
            amount = Integer.parseInt(amoStep.getText());
            if (amount <= 0) throw new IOException();
        } catch (NumberFormatException | IOException e) {
            amoStep.setText("");
            result = false;
        }
        
        try {
            print = Integer.parseInt(printStep.getText());
            if (print <= 0) throw new IOException();
        } catch (NumberFormatException | IOException e) {
            printStep.setText("");
            result = false;
        }
        
        try {
            start = Double.parseDouble(startStep.getText());
            if (start <= 0) throw new IOException();
            NameMethods.step = start;
        } catch (NumberFormatException | IOException e) {
            startStep.setText("");
            result = false;
        }
        
        try {
            Double.parseDouble(a.getText());
            Function.setParameter(Double.parseDouble(a.getText()));
        } catch (Throwable e) {
            a.setText("");
            result = false;
        }
        return result;
    }
}
