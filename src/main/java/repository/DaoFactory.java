package repository;

import repository.custom.impl.CustomerDaoImpl;
import repository.custom.impl.OrderDaoImpl;
import repository.custom.impl.ProductDaoImpl;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type) {
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case ORDER:
                return (T) new OrderDaoImpl();
        }
        return null;
    }
}
