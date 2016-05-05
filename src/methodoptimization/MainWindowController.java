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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    private long steps;
    private long constantSteps;
    
    private Point p;
    private long amount;
    private long print;
    private double start;
    
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
    private CheckMenuItem remember;
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
    @FXML
    private Button step;                                //Кнопка, отвечающая за шаг

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public class TableContent {
        private long steps;
        private double x1;
        private double x2;
        private double func;
        private double power;
        private double speed;
        
        public TableContent(long steps, double x1, double x2, double func, double power, double speed) {
            this.steps = steps;
            this.x1 = x1;
            this.x2 = x2;
            this.func = func;
            this.power = power;
            this.speed = speed;
        }

        /**
         * @return the steps
         */
        public long getSteps() {
            return steps;
        }

        /**
         * @param steps the steps to set
         */
        public void setSteps(long steps) {
            this.steps = steps;
        }

        /**
         * @return the x1
         */
        public double getX1() {
            return x1;
        }

        /**
         * @param x1 the x1 to set
         */
        public void setX1(double x1) {
            this.x1 = x1;
        }

        /**
         * @return the x2
         */
        public double getX2() {
            return x2;
        }

        /**
         * @param x2 the x2 to set
         */
        public void setX2(double x2) {
            this.x2 = x2;
        }

        /**
         * @return the func
         */
        public double getFunc() {
            return func;
        }

        /**
         * @param func the func to set
         */
        public void setFunc(double func) {
            this.func = func;
        }

        /**
         * @return the power
         */
        public double getPower() {
            return power;
        }

        /**
         * @param power the power to set
         */
        public void setPower(double power) {
            this.power = power;
        }

        /**
         * @return the speed
         */
        public double getSpeed() {
            return speed;
        }

        /**
         * @param speed the speed to set
         */
        public void setSpeed(double speed) {
            this.speed = speed;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            //NumberFormat nf = NumberFormat.getCurrencyInstance();
            //nf.
            sb.append(steps).append('\t');
            sb.append(x1).append('\t');
            sb.append(x2).append('\t');
            sb.append(func).append('\t');
            sb.append(power).append('\t');
            sb.append(speed).append('\n');
            
            return sb.toString();
        }
    }
    
    /**
     * Сохранение файла с заменой ранее выбранного, 
     * если фаил не был ранее выбран, 
     * то вызовится метод MainWindowController.onSaveAs()
     */
    @FXML
    private void onSave() {
        if (null != file) {
            try {
                FileOutputStream fout = new FileOutputStream(file);
                for (TableContent e : content) {
                    fout.write(e.toString().getBytes());
                }
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
        //throw new UnsupportedOperationException("Not supported yet");
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
        content.clear();
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
            Point newPoint = p;
            //if (!method.getValue().equals(NameMethods.MethodToDecreaseTheLengthOfThePitch)) constantSteps = 0;
            for (int i = 0; methods.getGradient(p).norma() != 0 && i < amount; i++) {
                steps++;
                methods.setP1(newPoint);
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
                        newPoint = methods.methodToDecreaseTheLengthOfThePitch(++constantSteps);
                        break;
                }
                p = newPoint;
                if (0 == i % print) print();                
            }
            if ((amount - 1) % print != 0) print();
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
        ok.initModality(Modality.APPLICATION_MODAL);
        ok.setHeaderText("Подсчёт завершен");
        ok.setContentText("Подсчёт был успешно завершён. Информация приведена в таблицу.");
        
        //поля, пока недоступные
        aboutMethod.setDisable(true);
        help.setDisable(true);
    }
    
    /**
     * Печатать на окно текущие шаги.
     */
    private void print() {
        //заполнить 6-ю полями
        double power = 0;
        double speed = 0;
        TableContent element = new TableContent(steps,
                                                methods.getP1().getX1(),
                                                methods.getP1().getX2(),
                                                methods.getFunctionValue(methods.getP1()),
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
