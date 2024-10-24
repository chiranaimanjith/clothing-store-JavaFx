package service.custom;

import dto.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    ObservableList<Customer> getAll();
    boolean updateCustomer(Customer item);
}
