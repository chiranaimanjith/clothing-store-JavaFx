package service.custom;

import dto.Customer;
import javafx.collections.ObservableList;
import service.SuperService;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    ObservableList<Customer> getAll();
    boolean updateCustomer(Customer item);

    String getLastCustomerId();
    Customer getCustomerById(String id);
    public boolean deleteCustomer(String id);
}
