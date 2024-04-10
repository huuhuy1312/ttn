import React, { useEffect, useState } from "react";
import "../css/viewDetailsInvoice.css"
import { useParams } from "react-router-dom";
import axios from 'axios';
const ViewDetailsInvoice = () => {
  const {id} = useParams();
  const [invoice, setInvoice] = useState(null);
  const [customer,setCustomer] = useState(null);
  const [address,setAddress] = useState(null);
  const [formula,setFormula] = useState(null);
  function formatPriceToVND(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    }).format(price);
  }
 
  const showDetails = (sonuoc) => {
    const bangGia = [
        formula?.price_smaller_or_equal_to_10m3,
        formula?.price_from_10m3_to_20m3,
        formula?.price_from_20m3_to_30m3,
        formula?.price_greater_or_equal_30m3
    ];
    
    let result = [];
    let count = 0;
    
    while (sonuoc > 0 && count < 4) {
        let tmp = count < 3 ? Math.min(sonuoc, 10) : sonuoc;
        result.push(
            <tr key={count}>
                <td>{count}</td>
                <td>{tmp}</td>
                <td>{formatPriceToVND(bangGia[count])}</td>
                <td>{formatPriceToVND(tmp * bangGia[count])}</td>
            </tr>
        );
        sonuoc -= tmp; // Trừ đi lượng nước đã xử lý
        count++;
    }

    return result;
};

  const getDetailsInvoiceById = async () => {
    try {
        const response = await fetch(`http://127.0.0.1:8080/api/invoice/${id}`, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        const data = await response.json();
        console.log(data)
        setInvoice(data.invoice);
        setCustomer(data.customer)
        setFormula(data.formula)
        setAddress(data.customer.address)
        // Sau khi hóa đơn được tải, tìm thông tin của khách hàng
        
    } catch (error) {
        console.error(error);
        throw error;
    }
  }
  
  useEffect(()=>{
    getDetailsInvoiceById();
  },[])
  const thanhToan = async (amount) => {
    try {
        const response = await axios.get(`http://127.0.0.1:8080/api/payment/create_payment/${amount *100}`);
        console.log(response);
        const url = response.data.url;
        window.location.href = url; // Điều hướng đến URL trả về
    } catch (error) {
        console.error(error);
        throw error;
    }
}
  // useEffect(()=>{
  //   soNuoc = invoice?.newNumber - invoice?.oldNumber;
  //   console.log(soNuoc)
  // },[invoice])
  useEffect(()=>{
    console.log(customer);
  },[customer])
  return (
    <div className="contains">
      <div className="title">Hoá Đơn Nước</div>
      <div className="info_customer">
        <div className="column1">
          <div>Mã Khách Hàng: {customer?.id}</div>
          <div>Tên khách hàng: {customer?.name}</div>
          <div>Địa chỉ: {`${address?.detailsAddress},${address?.wards},${address?.district},${address?.provinceOrCity}`}</div>
          <div>Email: {customer?.email}</div>
          <div>Số điện thoại: {customer?.phoneNumber}</div>
        </div>
        <div className="column2">
          <div>Chỉ số cũ: {invoice?.oldNumber}</div>
          <div>Chỉ số mới: {invoice?.newNumber}</div>
          <div>Số nước: {invoice?.newNumber - invoice?.oldNumber}</div>
          <div>Đối tượng: {customer?.residentialType.name}</div>
          <div>Trạng thái: {invoice?.status}</div>
        </div>
      </div>
      <div className="table_cal">
        <table>
          <thead>
            <tr>
              <td>Bậc</td>
              <td>Số nước</td>
              <td>Đơn giá</td>
              <td>Thành tiền</td>
            </tr>
          </thead>
          <tbody>
            {showDetails(invoice?.newNumber - invoice?.oldNumber)}
          </tbody>
        </table>
        <div class="vax_details">
          <div style={{display:"flex"}}>
            <div>{`Phí bảo vệ môi trường( ${formula?.bvmtTax}% ): `}</div>
            <div>{formatPriceToVND(invoice?.totalNoTax*formula?.bvmtTax/100)}</div>
          </div>
          <div style={{display:"flex"}}>
            <div>{`VAT( ${formula?.vatTaxPer}% ): `}</div>
            <div>{formatPriceToVND(invoice?.totalNoTax*formula?.vatTaxPer/100)}</div>
          </div>
          <div style={{display:"flex"}}>
            <div>Tổng tiền cần thanh toán : </div>
            <div>{formatPriceToVND(invoice?.totalAll)}</div>
          </div>
        </div>
      </div>
      <button onClick={()=>thanhToan(invoice?.totalAll)}>Thanh toán</button>
    </div>
  );
};
export default ViewDetailsInvoice;