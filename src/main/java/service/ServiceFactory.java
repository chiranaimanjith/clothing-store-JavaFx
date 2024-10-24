package service;

import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.OrderServiceImpl;
import service.custom.impl.ProductServiceImpl;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    //Type Casting //Factory Design Pattern
    public <T extends SuperService> T getServiceType(ServiceType type) {
        switch (type) {
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case PRODUCT:
                return (T) new ProductServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
        }
        return null;
    }

}
