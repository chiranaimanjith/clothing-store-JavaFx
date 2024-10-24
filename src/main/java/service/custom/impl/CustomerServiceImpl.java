package service.custom.impl;

import dto.Customer;
import javafx.collections.ObservableList;
import service.custom.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAll() {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer item) {
        return false;
    }
}
