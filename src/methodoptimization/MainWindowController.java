package methodoptimization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import methods.Methods;
import methods.NameMethods;
import methods.Point;

public class MainWindowController implements Initializable {
    private Stage stage;
    private File file;
    private FileChooser fileChooser;
    private ObservableList<TableContent> content;
    private Methods methods;
    private Alert errorFileSave;
    private Alert errorInput;
    private Alert ok;
    private TextInputDialog epsilon;
    private long steps;
    private long constantSteps;
    
    private Point p;
    private Point p2;
    private long amount;
    private long print;
    private double start;
    
//    @FXML
//    private MenuItem save;                              //Кнопка меню "Сохранить"
//    @FXML
//    private MenuItem saveAs;                            //Кнопка меню "Сохранить как"
//    @FXML
//    private MenuItem exit;                              //Кнопка меню "Выход"
//    @FXML
//    private MenuItem clear;                             //Кнопка меню "Очистить"
    @FXML
    private MenuItem fullScreen;                        //Кнопка меню "На полный экран/Оконный режим"
//    @FXML
//    private MenuItem eps;
    @FXML
    private CheckMenuItem remember;
    @FXML
    private MenuItem aboutMethod;                       //Кнопка меню "О методе"
//    @FXML
//    private MenuItem aboutProgram;                      //Кнопка меню "О программе"
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
//    @FXML
//    private Button step;                                //Кнопка, отвечающая за шаг

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        ok.setTitle(stage.getTitle());
        epsilon.setTitle(stage.getTitle());
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
                errorFileSave.show();
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
        constantSteps = 0L;
        p2 = null;
        content.clear();
        methods.clear();
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
        Optional<String> result = epsilon.showAndWait(); 
        if (result.isPresent()){ 
            TableContent.setFractionDigits(Integer.parseInt(result.get()));
        }
        logs.refresh();
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
        if (remember.isSelected()) remember();
        else onClear();
        
        if(checkInput()) {
            Point oldPoint = p;
            
            if (0L == steps && method.getValue() != NameMethods.MethodOfRavines) print(p, p);
            
            switch (method.getValue()) {
                case MethodBFS:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodBFS(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodDFP:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodDFP(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodNewtons:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodNewtons(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodOfRavines:
                    if (!remember.isSelected() || null == p2) {
                        p2 = new Point(p);
                        p2.setX1(methods.getResult().getX1()-p2.getX1());
 
                        p = methods.methodOfSteepestDescent(p);
                        p2 = methods.methodOfSteepestDescent(p2);
                        
                        --steps;
                        print(p2, p2);
                        ++steps;
                        print(p, p);
                    }

                    for (int i = 0; methods.gradient(p).norma() >= 1e-7 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodOfRavines(p, p2);
                        p2 = oldPoint;
                        
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodOfSteepestDescent:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodOfSteepestDescent(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodPitchwise:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodPitchwise(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodStepCrushing:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodStepCrushing(p);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
                case MethodToDecreaseTheLengthOfThePitch:
                    for (int i = 0; methods.gradient(p).norma() != 0 && i < amount; i++) {
                        ++steps;
                        oldPoint = p;
                        p = methods.methodToDecreaseTheLengthOfThePitch(p, ++constantSteps);
                        if (0 == i % print) print(oldPoint, p);
                    }
                    break;
            }
            if ((amount - 1) % print != 0) print(oldPoint, p);
            ok.show();
        } else {
            errorInput.show();
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
        method.setValue(NameMethods.MethodOfSteepestDescent);
        
        steps = 0L;
        constantSteps = 0L;
        p = new Point();
        
        colNum.setCellValueFactory(new PropertyValueFactory<>("steps"));
        colX1.setCellValueFactory(new PropertyValueFactory<>("x1"));
        colX2.setCellValueFactory(new PropertyValueFactory<>("x2"));
        colFunc.setCellValueFactory(new PropertyValueFactory<>("func"));
        colPower.setCellValueFactory(new PropertyValueFactory<>("power"));
        colSpeed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        content = FXCollections.observableArrayList();
        logs.setItems(content);
        
        errorInput = new Alert(Alert.AlertType.ERROR);
        errorInput.setTitle("Ошибка");
        errorInput.setContentText("Исправьте данные и повторите попытку");
        errorInput.setHeaderText("Данные введены некорректно");
        
        errorFileSave = new Alert(Alert.AlertType.ERROR);
        errorFileSave.setTitle("Ошибка");
        errorFileSave.setContentText("Данные не были записаны в фаил");
        errorFileSave.setHeaderText("Что-то пошло не так");
        
        ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setHeaderText("Подсчёт завершен");
        ok.setContentText("Подсчёт был успешно завершён. Информация приведена в таблицу.");
        
        epsilon = new TextInputDialog(TableContent.getFractionDigits().toString());
        epsilon.setHeaderText("Задание точности вывода");
        epsilon.setContentText("Введите количество знаков после запятой");
        
        //поля, пока недоступные
        aboutMethod.setDisable(true);
        help.setDisable(true);
    }
    
    /**
     * Печатать на окно текущие шаги.
     */
    private void print(final Point pk, final Point pk1) {
        //заполнить 6-ю полями
        Point res = methods.getResult();
        double resF = methods.getFunctionValue(res);
        //if (steps != 0) {
            double power = Math.log(Point.minus(pk1, res).norma())/Math.log(Point.minus(pk, res).norma());
            double speed = (methods.getFunctionValue(pk1) - resF)/(methods.getFunctionValue(pk) - resF);
        //}
        TableContent element = new TableContent(steps,
                                                pk1.getX1(),
                                                pk1.getX2(),
                                                methods.getFunctionValue(pk1),
                                                power, speed);
        content.add(element);
    }
    
    /**
     *  Запоминает точки, после последнего шага
     */
    private void remember() {
        x1.setText(p.getX1().toString());
        x2.setText(p.getX2().toString());
    }
    
    /**
     * Проверка на корректность введённых польхователем данных.
     */
    private boolean checkInput() {
        boolean result = true;
        try {
            p.setX1(Double.parseDouble(x1.getText()));
        } catch (Throwable e) {
            x1.setText("");
            result = false;
        }
        
        try {
            p.setX2(Double.parseDouble(x2.getText()));
        } catch (Throwable e) {
            x2.setText("");
            result = false;
        }
        
        try {
            amount = Integer.parseInt(amoStep.getText());
            if (amount <= 0) throw new IOException();
        } catch (Throwable e) {
            amoStep.setText("");
            result = false;
        }
        
        try {
            print = Integer.parseInt(printStep.getText());
            if (print <= 0) throw new IOException();
        } catch (Throwable e) {
            printStep.setText("");
            result = false;
        }
        
        try {
            start = Double.parseDouble(startStep.getText());
            if (start <= 0) throw new IOException();
            methods.setStep(start);
        } catch (Throwable e) {
            startStep.setText("");
            result = false;
        }
        
        try {
            Double.parseDouble(a.getText());
            methods.setParameter(Double.parseDouble(a.getText()));
        } catch (Throwable e) {
            a.setText("");
            result = false;
        }
        return result;
    }
}
