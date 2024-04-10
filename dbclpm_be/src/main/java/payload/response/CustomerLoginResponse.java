package payload.response;

import com.example.dbclpm_be.entity.Role;
import com.example.dbclpm_be.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerLoginResponse {
    public Role role;
    public String ordinal_numbers;
    public String fullName;
}
