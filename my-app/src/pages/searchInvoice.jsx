import React, { useEffect, useState } from "react";
import "../css/searchInvoice.css"
import { Link } from "react-router-dom";
const SearchInvoice = () => {
  const [customer,setCustomer] = useState(null);
  const [address,setAddress] = useState(null);
  const [listInvoice, setListInvoice] = useState(null);
  const getCustomerById = async (customerID) => {
    try {
        const response = await fetch(`http://localhost:8080/api/customer/${customerID}`, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        const data = await response.json();
        const addressTmp = data.address;
        setAddress(`${addressTmp.detailsAddress},${addressTmp.wards},${addressTmp.district},${addressTmp.provinceOrCity}`)
        setCustomer(data)
        return data; // Trả về dữ liệu lấy được từ API
    } catch (error) {
        console.error(error);
        throw error; // Ném lỗi ra bên ngoài để xử lý ở phần gọi hàm này
    }
};
const getInvoiceByCusId = async (customerID) => {
  try {
      const response = await fetch(`http://localhost:8080/api/invoice/getByCustomer/${customerID}`, {
          method: "GET"
      });
      if (!response.ok) {
          throw new Error("Network response was not ok");
      }
      const data = await response.json();
      console.log(data)
      setListInvoice(data);
  } catch (error) {
      console.error(error);
      throw error; // Ném lỗi ra bên ngoài để xử lý ở phần gọi hàm này
  }
};
function formatPriceToVND(price) {
  return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
  }).format(price);
}
  useEffect(()=>{
    getCustomerById(1)
    getInvoiceByCusId(1)
  },[])
  function convertAndAdjustTime(isoTime) {
    const timeObj = new Date(isoTime);
    timeObj.setHours(timeObj.getHours() + 7);
    const formattedTime = `${timeObj.getMonth() + 1}/${timeObj.getFullYear()}`;
    
    return formattedTime;
}
  return (
    
    <div className="contains">
      <div className="title-page">Tra cứu hoá đơn</div>
      <div className="search-contains">
        <div className="search-form">
          <div className="search-form_input">
            <div className="search-form_items">
              <div>Tên:</div>
              <input placeholder="Nhập tên"></input>
            </div>
            <div className="search-form_items">
              <div>Số danh bộ:</div>
              <input placeholder="Nhập số danh bộ"></input>
            </div>
            <div className="search-form_items">
              <div>Điện thoại:</div>
              <input placeholder="Nhập số điện thoại"></input>
            </div>
          </div>
          <button>Tìm kiếm</button>
        </div>
        <div className="info-cus-form">
          <div className="info-cus-title">Thông tin khách hàng</div>
          <div className="info-cus-content">
            <div className="info-cus-item">Số danh bộ : {customer?.id}</div>
            <div className="info-cus-item">Khách Hàng : {customer?.name}</div>
            <div className="info-cus-item">Địa chỉ : {address}</div>
            <div className="info-cus-item">Số diện thoại : {customer?.phoneNumber}</div>
            <div className="info-cus-item">Email : {customer?.email}</div>
          </div>
        </div>
      </div>
      <div className="table-invoice">
        <div className="title">Danh sách hoá đơn</div>
        <table>
            <thead>
                <tr>
                    <td>Kỳ</td>
                    <td>Chỉ số mới</td>
                    <td>Chỉ số cũ</td>
                    <td>Tiêu thụ</td>
                    <td>Tiền nước</td>
                    <td>Tổng thuế</td>
                    <td>Tổng cộng</td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
              {listInvoice?.map(item => (
               <tr key={item.id}>
                <td>{convertAndAdjustTime(item.period)}</td>
                <td>{item.newNumber}</td>
                <td>{item.oldNumber}</td>
                <td>{item.newNumber - item.oldNumber}</td>
                <td>{formatPriceToVND(item.totalNoTax)}</td>
                <td>{formatPriceToVND(item.totalTax)}</td>
                <td>{formatPriceToVND(item.totalAll)}</td>
                <td><a href={`viewDetailsInvoice/${item.id}`}>Xem chi tiết</a></td>
              </tr>
              
              ))}
            </tbody>

        </table>
      </div>
    </div>
  );
};
export default SearchInvoice;
