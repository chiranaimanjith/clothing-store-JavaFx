package repository.custom;

import entity.CustomerEntity;
import repository.CrudRepository;

public interface CustomerDao extends CrudRepository<CustomerEntity> {
    String findLastID();

    CustomerEntity findById(String id);
}
