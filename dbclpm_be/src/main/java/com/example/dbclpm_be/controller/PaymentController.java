package com.example.dbclpm_be.controller;

import com.example.dbclpm_be.DTO.PaymentResDTO;
import com.example.dbclpm_be.DTO.TransactionsStatusDTO;
import com.example.dbclpm_be.config.Config;
import com.example.dbclpm_be.entity.Customer;
import com.example.dbclpm_be.entity.Invoice;
import com.example.dbclpm_be.respository.CustomerRepository;
import com.example.dbclpm_be.respository.InvoiceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @PostMapping("/create_payment/{amount}/{cus_id}/{newIndex}/{invoiceId}")
    public ResponseEntity<?> createPayment(HttpServletRequest req, @PathVariable long amount, @PathVariable long cus_id, @PathVariable long newIndex,@PathVariable long invoiceId) throws UnsupportedEncodingException {
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        System.out.println(amount);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("OK");
        paymentResDTO.setMessage("Success");
        paymentResDTO.setUrl(paymentUrl);
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        invoice.setStatus("Đã Thanh Toán");
        invoiceRepository.save(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);

    }
    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount")String amount,
            @RequestParam(value = "vnp_BankCode")String bankCode,
            @RequestParam(value = "vnp_OrderInfo")String order,
            @org.jetbrains.annotations.NotNull @RequestParam(value = "vnp_ResponseCode")String responseCode
    ){
        TransactionsStatusDTO transactionsStatusDTO = new TransactionsStatusDTO();
        if(responseCode.equals("00"))
        {
            transactionsStatusDTO.setStatus("OK");
            transactionsStatusDTO.setMessage("Successfully");
            transactionsStatusDTO.setData("");
        }else {
            transactionsStatusDTO.setStatus("No");
            transactionsStatusDTO.setMessage("Failed");
            transactionsStatusDTO.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionsStatusDTO);
    }
//    @GetMapping("/paymentSuccess")
//    public  ResponseEntity<?> paymentSucess()
//    {
//
//    }
}
