package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.entity.Base;
import com.example.dbclpm_be.entity.Customer;
import com.example.dbclpm_be.entity.Role;
import com.example.dbclpm_be.entity.User;
import com.example.dbclpm_be.respository.BaseRespository;
import com.example.dbclpm_be.respository.CustomerRepository;
import com.example.dbclpm_be.respository.RoleRepository;
import com.example.dbclpm_be.respository.UserRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import payload.request.CreateUserRequest;
import payload.request.LoginRequest;
import payload.request.RejectRequest;
import payload.response.CustomerLoginResponse;

import java.security.SecureRandom;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private  BaseRespository baseRespository;
    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        StringBuilder randomString = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }
        return randomString.toString();
    }
    public String getInitials(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return "";
        }
        StringBuilder initials = new StringBuilder();
        String[] words = fullName.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(Character.toLowerCase(word.charAt(0)));
            }
        }
        return initials.toString();
    }

    public static String generateUserId(String baseId, long randomNumber) {
        String baseIdString = baseId != null ? baseId : "";
        String randomString = Long.toString(randomNumber);
        StringBuilder result = new StringBuilder();
        result.append(baseIdString);
        for (int i = 0; i < 8  - randomString.length(); i++) {
            result.append('0');
        }
        result.append(randomString);
        return result.toString();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsernameAndPassword(loginRequest.username, loginRequest.password);
        if (user != null) {
            if(user.getRole().getName().equals("customer"))
            {
                return ResponseEntity.ok(new CustomerLoginResponse(user.getRole(),user.getCustomer().getOrdinal_numbers(),user.getCustomer().getInfoCommon().getName()));
            }
            else {
                return ResponseEntity.ok(user);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest)
    {
        if (!isValidEmailAddress(createUserRequest.email)) {
            return ResponseEntity.badRequest().body("Email không hợp lệ");
        }else{
            String tmp = generateUserId(createUserRequest.base_id, createUserRequest.num_of_customer+1);
            String username = getInitials(createUserRequest.full_name) + tmp;
            Optional<Customer> customer = customerRepository.findById(createUserRequest.customer_id);
            Customer customer1 = customer.orElseThrow(()->new RuntimeException("Khôgn tìm thấy customer"+createUserRequest.customer_id));
            customer1.setOrdinal_numbers(tmp);
            customer1.setActived(true);
            customerRepository.save(customer1);

            String password = generateRandomString();
            Role role = roleRepository.findByName("customer");

            User user = new User(username,password,role,customer1);
            userRepository.save(user);
            String content= "Đăng ký sử dụng nước thành công!!!!\n"
                    +"Vui lòng đăng nhập vào app với \n"+
                    "Username: "+username+"\n"
                    +"Password: "+ password+"\n";
            sendMail(createUserRequest.email,content,"Đăng ký sử dụng nước thành công");
            System.out.println(createUserRequest.base_id);
            Optional<Base> base = baseRespository.findByOrdinalNumbers(createUserRequest.base_id);
            Base base1 = base.orElseThrow(()-> new RuntimeException("Không tìm thấy base"));
            base1.setNum_of_customer(createUserRequest.num_of_customer+1);
            baseRespository.save(base1);
            return ResponseEntity.ok("Thêm người dùng thành công");
        }
    }
    @PostMapping("/rejectRequest")
    public ResponseEntity<?> rejectRequest(@RequestBody RejectRequest rejectRequest)
    {
        customerRepository.deleteById(rejectRequest.customer_id);
        return ResponseEntity.ok("Từ chối yêu cầu thành công");
    }

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    public void sendMail(String emailTo, String content, String subject)
    {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailTo);
            mailMessage.setText(content);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
        }catch (Exception e)
        {
            System.out.println("Ko gửi thành cong");
        }
    }
}
