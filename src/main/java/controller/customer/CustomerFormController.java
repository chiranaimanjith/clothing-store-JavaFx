package controller.customer;

import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableView tblCustomers;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private JFXTextField txtTitle;


    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();

        colID.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("CustTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("CustName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CustAddress"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        //lodeTable();
        tblCustomers.getSelectionModel()
                .selectedItemProperty()
                .addListener((
                        (observableValue, oldValue, newValue) -> {
                            if (newValue!=null){
                                setTextToValues((Customer)newValue);
                            }
                        }));
    }

//    private void lodeTable() {
//        ObservableList<Customer> customerObservableList = itemservice.getAll();
//        tblCustomers.setItems(customerObservableList);
//    }

    private void setTextToValues(Customer newValue) {
        lblCustomerID.setText(String.valueOf(newValue.getCustId()));
        txtTitle.setText(newValue.getCustTitle());
        txtName.setText(newValue.getCustname());
        txtAddress.setText(newValue.getCustAddress());
        txtNumber.setText(String.valueOf(newValue.getCustphone()));
        txtEmail.setText(newValue.getEmail());

    }

    private void loadDateAndTime(){
        //------------Date------------
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = simpleDateFormat.format(date);

        lblDate.setText(dateNow);

        //------------Time------------
        Timeline tl= new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }
}
