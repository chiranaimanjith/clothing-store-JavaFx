package controller.customer;

public class CustomerController{
    private static CustomerController instance;

    private CustomerController() {
    }

    public static CustomerController getInstance() {
        return instance == null ? instance = new CustomerController() : instance;
    }
}

