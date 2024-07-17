package simpleCRUD.unitTest.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer_id;


    private String product_name;

    private Integer quantity;
    private Integer price;

}
