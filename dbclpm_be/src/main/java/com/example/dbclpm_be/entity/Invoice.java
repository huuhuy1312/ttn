package com.example.dbclpm_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long newNumber;
    private long oldNumber;
    private Date period;
    private long totalAll;
    private long totalTax;
    private long totalNoTax;
    private String status;
    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "formula_id")
    @JsonIgnore
    private Formula formula;

    // Hàm tạo mới, thay đổi kiểu của period từ String thành Date
    public Invoice(long newNumber, long oldNumber, String periodString, long totalAll, long totalTax, long totalNoTax, String status, Customer customer, Formula formula) throws ParseException {
        this.newNumber = newNumber;
        this.oldNumber = oldNumber;
        this.period = parseDateString(periodString);
        this.totalAll = totalAll;
        this.totalTax = totalTax;
        this.totalNoTax = totalNoTax;
        this.status = status;
        this.customer = customer;
        this.formula = formula;
    }

    // Hàm phụ để chuyển đổi chuỗi thành đối tượng Date
    private Date parseDateString(String periodString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        return sdf.parse(periodString);
    }
}
