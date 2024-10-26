package repository.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity customerEntity) {
        String SQL = "INSERT INTO customer (CustID, CustTitle, CustName, CustAddress, phone, email, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(SQL,
                    customerEntity.getCustId(),
                    customerEntity.getCustTitle(),
                    customerEntity.getCustname(),
                    customerEntity.getCustAddress(),
                    customerEntity.getCustphone(),
                    customerEntity.getEmail(),
                    customerEntity.getCreatedAt());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String SQL = "DELETE FROM customer WHERE CustID = ?";
        try {
            return CrudUtil.execute(SQL,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        ObservableList<CustomerEntity> customerEntityObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM customer";
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                String custId = resultSet.getString("CustID");
                String title = resultSet.getString("CustTitle");
                String name = resultSet.getString("CustName");
                String address = resultSet.getString("CustAddress");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                LocalDateTime createdAt = null;

                Timestamp createdAtTimestamp = resultSet.getTimestamp("createdAt");
                if (createdAtTimestamp != null) {
                    createdAt = createdAtTimestamp.toLocalDateTime();
                }
                CustomerEntity customerEntity = new CustomerEntity(custId, title, name, address, phone, email, createdAt);

                customerEntityObservableList.add(customerEntity);
            }
            return customerEntityObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public boolean update(CustomerEntity customerEntity) {
        return false;
    }

    @Override
    public CustomerEntity search(String id) {
        return null;
    }

    @Override
    public String findLastID() {
        String SQL = "SELECT MAX(CustID) FROM customer";

        try {
            ResultSet result = CrudUtil.execute(SQL);
            if(result.next()) {
                return result.getString(1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public CustomerEntity findById(String id) {
        String SQL = "SELECT * FROM customer WHERE CustID=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL, id);
            if(resultSet.next()) {
                return new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getTimestamp(7).toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
