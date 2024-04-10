import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../css/ycdksdn.css";

const YeuCauDangKySuDungNuoc = () => {
  const [nameFind, setNameFind] = useState(null);
  const [baseNameFind, setBaseNameFind] = useState(null);
  const [residentialNameFind, setResidentialNameFind] = useState(null);
  const [listResult, setListResult] = useState([]);
  const [rejectReason, setRejectReason] = useState("");
  const [showRejectModal, setShowRejectModal] = useState(false);
  const [rejectItemId, setRejectItemId] = useState(null);
  const [notification, setNotification] = useState("");
  const getRequiredRegisterUseWater = async () => {
    try {
      const response = await axios.get(
        `http://127.0.0.1:8080/api/customer/getNonActivedCustomers?${nameFind && `name=${nameFind}`}&${baseNameFind && `baseName=${baseNameFind}`}&${residentialNameFind && `residentialName=${residentialNameFind}`}`
      );
      setListResult(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Lỗi:", error);
    }
  };
  // useEffect(() => {
  //   if (notification !== "") {
  //     const timeout = setTimeout(() => {
  //       setNotification("");
  //     }, 3000);

  //     return () => clearTimeout(timeout);
  //   }
  // }, [notification]);
  useEffect(() => {
    getRequiredRegisterUseWater();
  }, [notification]);

  const handleConfirm = async (cus_id, full_name, base_id, num_of_customer, email) => {
    // Kiểm tra xem tất cả các tham số đều đã được cung cấp
    if (!cus_id || !full_name || !base_id || !email) {
      console.log("Thông tin trong body trước khi thực hiện yêu cầu:");
    console.log("customer_id:", cus_id);
    console.log("full_name:", full_name);
    console.log("base_id:", base_id);
    console.log("num_of_customer:", num_of_customer);
    console.log("email:", email);
      console.error("Thiếu thông tin, không thể thực hiện yêu cầu.");
      return; // Dừng hàm nếu có thông tin thiếu
    }
  
    // Hiển thị các thông tin sẽ gửi trong body
    console.log("Thông tin trong body trước khi thực hiện yêu cầu:");
    console.log("customer_id:", cus_id);
    console.log("full_name:", full_name);
    console.log("base_id:", base_id);
    console.log("num_of_customer:", num_of_customer);
    console.log("email:", email);
  
    try {
      const response = await axios.post("http://127.0.0.1:8080/api/auth/createUser", {
        full_name: full_name,
        base_id: base_id,
        customer_id: cus_id,
        num_of_customer: num_of_customer,
        email: email
      });
      setNotification(response.data);
    } catch (error) {
      console.error("Lỗi:", error);
    }
  };
  
  const handleReject = async (itemId, reason) => {
    try {
      // Gửi yêu cầu từ chối đến server
      const response = await axios.post("http://127.0.0.1:8080/api/auth/rejectRequest", {
        customer_id: itemId,
        reason: reason,
      });
      setNotification(response.data)
      setShowRejectModal(false);
      getRequiredRegisterUseWater();
    } catch (error) {
      console.error("Lỗi:", error);
    }
  };

  return (
    <div className="container">
        {notification && <div className="notification">{notification}</div>}
      <div>Các yêu cầu đăng ký sử dụng nước</div>
      <table>
        <thead>
          <tr>
            <th>STT</th>
            <th>Thông tin chi tiết</th>
            <th>Hành Động</th>
          </tr>
        </thead>
        <tbody>
          {listResult.map((item, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>
                <div className="info-container">
                  <div>Họ và tên : {item?.infoCommon.name}</div>
                  <div>Số điện thoại : {item?.infoCommon.phoneNumber}</div>
                  <div>Email : {item?.infoCommon.email}</div>
                  <div>Địa chỉ: {`${item?.address.detailsAddress},${item?.address.wards},${item?.address.district}.${item?.address.provinceOrCity}`}</div>
                  <div>
                    <div className="image-container">
                      <div>
                        <div>Ảnh CCCD/CMT mặt trước :</div>
                        <img src={item?.front_image} alt="Ảnh CCCD/CMT mặt trước" />
                      </div>
                      <div>
                        <div>Ảnh CCCD/CMT mặt sau :</div>
                        <img src={item?.back_image} alt="Ảnh CCCD/CMT mặt sau" />
                      </div>
                    </div>
                  </div>
                  <div>Loại hộ :{item?.residentialType.name}</div>
                  <div>
                    {item?.certificate_of_poverty ? (
                      <div className="image-container">
                        <div>
                          <div>Giấy chứng nhận hộ nghèo:</div>
                          <img src={item?.certificate_of_poverty} alt="Giấy chứng nhận hộ nghèo:" />
                        </div>
                      </div>
                    ) : null}
                  </div>
                </div>
              </td>
              <td>
                <div>
                  <button className="btn btn-confirm" onClick={() => { handleConfirm(item?.id, item?.infoCommon.name, item?.base.ordinalNumbers, item?.base.num_of_customer,item?.infoCommon.email) }}>Xác nhận</button>
                  <button className="btn btn-reject" onClick={() => { setRejectItemId(item?.id); setShowRejectModal(true); }}>Từ chối</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showRejectModal && (
        <div className="modal" style={{ display: showRejectModal ? 'block' : 'none' }}>
        <div className="modal-content">
            <span className="close" onClick={() => setShowRejectModal(false)}>×</span>
            <h2>Nhập lý do từ chối</h2>
            <textarea value={rejectReason} onChange={(e) => setRejectReason(e.target.value)}></textarea>
            <button onClick={() => handleReject(rejectItemId, rejectReason)}>Xác nhận</button>
        </div>
        </div>

      )}
    </div>
  );
}
export default YeuCauDangKySuDungNuoc;
