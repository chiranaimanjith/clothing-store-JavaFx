package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private Integer custId;
    private String custTitle;
    private String custname;
    private String custAddress;
    private Integer custphone;
    private String email;
}
