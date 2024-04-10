package payload.response;

import com.example.dbclpm_be.entity.Customer;
import com.example.dbclpm_be.entity.Formula;
import com.example.dbclpm_be.entity.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.Normalizer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailsResponse {
    public Invoice invoice;
    public Customer customer;
    public Formula formula;
}
