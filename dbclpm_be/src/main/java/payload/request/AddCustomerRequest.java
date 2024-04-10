package payload.request;

import com.example.dbclpm_be.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCustomerRequest {
    public String email;
    public String phoneNumber;
    public String name;
    public long baseId;
    public long residentialTypeId;
    public String provinceOrCity;
    public String district;
    public String wards;
    public String detailsAddress;
    public String front_image;
    public String back_image;
    public String certificate_of_poverty;
}
