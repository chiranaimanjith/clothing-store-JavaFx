package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public boolean addCustomer(Customer customer) {
            return customerDao.save(modelMapper.map(customer, CustomerEntity.class));
        //new Alert(Alert.AlertType.ERROR,"Error Occurred :(").show();
    }

    @Override
    public ObservableList<Customer> getAll() {
        List<CustomerEntity> customerEntityEntities = customerDao.getAll();

        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for (CustomerEntity entity : customerEntityEntities) {
            Customer customer = modelMapper.map(entity, Customer.class);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean updateCustomer(Customer item) {
        return false;
    }

    @Override
    public Customer getCustomerById(String id) {
        CustomerEntity customerEntity = customerDao.findById(id);
        if (customerEntity != null) {
            return modelMapper.map(customerEntity, Customer.class);
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }

    @Override
    public String getLastCustomerId() {
        String lastID = customerDao.findLastID();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(lastID);

        return (matcher.find()) ? matcher.group() : null;
    }

}
