package controller.customer;

import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;
import validation.CustomerValidator;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
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
    private TableColumn<?, ?> colDateAndTime;

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

    private final CustomerService service = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        generateNextID();
        colID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("custphone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        lodeTable();
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue != null) {
                        setTextToValues((Customer) newValue);
                    }
                }
        );
    }


    private void lodeTable() {
        ObservableList<Customer> customerObservableList = service.getAll();
        tblCustomers.setItems(customerObservableList);
    }

    private void setTextToValues(Customer customer) {
        lblCustomerID.setText(customer.getCustId());
        txtTitle.setText(customer.getCustTitle());
        txtName.setText(customer.getCustname());
        txtAddress.setText(customer.getCustAddress());
        txtNumber.setText(customer.getCustphone());
        txtEmail.setText(customer.getEmail());
    }


    private void loadDateAndTime() {
        //------------Date------------
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = simpleDateFormat.format(date);

        lblDate.setText(dateNow);

        //------------Time------------
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }

    private void clearFields() {
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtNumber.clear();
        txtEmail.clear();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        CustomerValidator validator = new CustomerValidator();

        String custID = lblCustomerID.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phone = txtNumber.getText();
        String email = txtEmail.getText();
        LocalDateTime createdAt = LocalDateTime.now();

        if (validator.validateCustomerData(custID, title, name, address, phone, email)) {
            Customer customer = new Customer(custID, title, name, address, phone, email, createdAt);
            boolean isSaved = service.addCustomer(customer);

            if (isSaved) {
                showAlert("Success", "Customer added successfully.");
                clearFields();
            } else {
                showAlert("Error", "Failed to add customer. Please try again.");
            }
            clearFields();
        } else {
            showAlert("Validation Error", "Please check the entered data. Ensure all fields are filled correctly:\n" +
                    "- Cust ID cannot be empty.\n" +
                    "- Title must be either 'Mr', 'Ms', or 'Miss'.\n" +
                    "- Name and Address cannot be empty.\n" +
                    "- Phone must be 10 to 12 digits.\n" +
                    "- Email must be a valid format.");
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + txtName.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteCustomer();
        }

    }

    private void deleteCustomer() {
        if (service.deleteCustomer(lblCustomerID.getText())) {
            disableTextField();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,
                    "Customer Deleted Successfully!"
            );
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Error Occurred while deleting " + txtName.getText()
            );
        }
    }

    private void disableTextField() {
        txtName.setEditable(false);
        txtAddress.setEditable(false);
        txtNumber.setEditable(false);
        txtEmail.setEditable(false);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    //Generate Next ID
    private void generateNextID() {
        String base = "C";
        int id = Integer.parseInt(service.getLastCustomerId());

        if (id < 10) {
            base += "00";
        } else if (id < 100) {
            base += "0";
        }
        lblCustomerID.setText(base + (id + 1));
    }

    private void showAlert(String message, String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



