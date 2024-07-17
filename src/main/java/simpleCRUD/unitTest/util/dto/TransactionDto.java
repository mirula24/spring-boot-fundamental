package simpleCRUD.unitTest.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import simpleCRUD.unitTest.model.Customer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Integer customer_id;
    private String product_name;
    private Integer quantity;
    private Integer price;

}
