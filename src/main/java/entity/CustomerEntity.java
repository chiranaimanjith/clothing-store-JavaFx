package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEntity {
    private String custId;
    private String custTitle;
    private String custname;
    private String custAddress;
    private String custphone;
    private String email;
    private LocalDateTime createdAt;

}
