import React, { useEffect, useState } from 'react';
import "../css/updateWaterIndex.css"
import axios from "axios";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faTimes } from '@fortawesome/free-solid-svg-icons';
function Notification({ message, onClose }) {
  const [fadeOut, setFadeOut] = useState(false);

  useEffect(() => {
    let timer;
    if (fadeOut) {
      timer = setTimeout(() => {
        onClose();
      }, 3000);
    }

    return () => clearTimeout(timer);
  }, [fadeOut, onClose]);

  const handleFadeOut = () => {
    setFadeOut(true);
  };

  return (
    
    <div className={`notification${fadeOut ? ' fadeOut' : ''}`}>
      <FontAwesomeIcon icon={faCheckCircle} className="success-icon" />
      <span>{message}</span>
      <FontAwesomeIcon icon={faTimes} className="close-icon" onClick={handleFadeOut} />
    </div>
  );
}
function UpdateWaterIndex() {
  const [showPart, setShowPart] = useState(1);
  const [idSearch1, setIdSearch1] = useState("");
  const [nameSearch1, setNameSearch1] = useState("");
  const [baseSearch1, setBaseSearch1] = useState("");
  const [residentialName1,setResidentialName1] = useState("");
  const [listResult1, setListResult1] = useState([]);
  const [idSearch2, setIdSearch2] = useState("");
  const [nameSearch2, setNameSearch2] = useState("");
  const [baseSearch2, setBaseSearch2] = useState("");
  const [residentialName2,setResidentialName2] = useState("");
  const [listResult2, setListResult2] = useState([]);

  const [baseList,setBaseList] = useState([]);
  const [residentialList, setResidentialList] = useState([]);
  const [newWaterValues1, setNewWaterValues1] = useState({});
  const [errorMessages, setErrorMessages] = useState({});
  
  const filterCustomer1 = async () => {
    try {
      const url = `http://127.0.0.1:8080/api/customer/getActivedCustomers?${nameSearch1 && `name=${nameSearch1}`}&${baseSearch1 && `baseName=${baseSearch1}`}&${idSearch1 && `ordinalNumbers=${idSearch1}`}&${residentialName1 && `residentialName=${residentialName1}`}`;
      console.log("URL: ", url);
      const response = await axios.get(url);
      setListResult1(response.data)
    } catch (error) {
      console.log("Error: ", error);
    }
  };
  const filterCustomer2 = async () => {
    try {
      const url = `http://127.0.0.1:8080/api/customer/getActivedCustomers?${nameSearch2 && `name=${nameSearch2}`}&${baseSearch2 && `baseName=${baseSearch2}`}&${idSearch2 && `ordinalNumbers=${idSearch2}`}&${residentialName2 && `residentialName=${residentialName2}`}`;
      console.log("URL: ", url);
      const response = await axios.get(url);
      setListResult2(response.data)
    } catch (error) {
      console.log("Error: ", error);
    }
  };
  const findAllBases = async () => {
    try {
      const url = `http://127.0.0.1:8080/api/base/all`;
      const response = await axios.get(url);
      console.log(response.data)
      setBaseList(response.data)
    } catch (error) {
      console.log("Error: ", error);
    }
  };
  const findAllResidental = async () => {
    try {
      const url = `http://127.0.0.1:8080/api/residentialType/all`;
      const response = await axios.get(url);
      setResidentialList(response.data)
    } catch (error) {
      console.log("Error: ", error);
    }
  };
  
  
  const handleChange = (value, index, part) => {
    setNewWaterValues1(prevValues => ({
      ...prevValues,
      [`${part}_${index}`]: { value: value }
    }));
  };
    useEffect(()=>{
        filterCustomer1()
        filterCustomer2()
        findAllBases()
        findAllResidental()
    },[])
    
  const handleUpdate = async (id,part) => {
    if (newWaterValues1[`${part}_${id}`]?.value === '') {
      const errorMessage = { message: 'Vui lòng nhập số nước mới.' };
      setErrorMessages({ [`${part}_${id}`]: errorMessage });
    } else {
      if(part ==1)
      {
        const url = `http://127.0.0.1:8080/api/customer/updateIndexWater?cus_id=${id}&newIndexWater=${newWaterValues1[`${part}_${id}`]?.value}`;
        const response = await axios.post(url);
        showNotification()
        setNewWaterValues1(prevValues => ({
          ...prevValues,
          [`${part}_${id}`]: { value: "" }
        }));
      }
      else{
        const url = `http://127.0.0.1:8080/api/invoice/add`;
        const response = await axios.post(url,{
          customer_id: id,
          new_number:newWaterValues1[`${part}_${id}`]?.value
        });
        showNotification()
        setNewWaterValues1(prevValues => ({
          ...prevValues,
          [`${part}_${id}`]: { value: "" }
        }));
      }
      filterCustomer1()
      filterCustomer2()
      setErrorMessages({});
    }
  };
  //Thông báo
  useEffect(()=>{
    console.log(listResult2)
  },[listResult2])
  const [notifications, setNotifications] = useState([]);

  const closeNotification = (index) => {
    setNotifications((prevNotifications) =>
      prevNotifications.filter((_, i) => i !== index)
    );
  };

  const showNotification = () => {
    setNotifications((prevNotifications) => [
      ...prevNotifications,
      { message: 'Cập nhật thành công' },
    ]);
  };

  return (
    <div className="App">
      <div className='notification-container'>
        {notifications.map((notification, index) => (
          <Notification
            key={index}
            message={notification.message}
            onClose={() => closeNotification(index)}
          />
        ))}
      </div>
      <div className={`section ${showPart === 1 ? 'active' : 'gray'}`} onClick={() => setShowPart(1)}>
        <h2>Cập nhật số nước không xuất hoá đơn</h2>

        {showPart === 1 && (
          <div>
            <div className="search">
              <input
                type="text"
                placeholder="Số định danh"
                value={idSearch1}
                onChange={(e) => setIdSearch1(e.target.value)}
              />
              <input
                type="text"
                placeholder="Tên"
                value={nameSearch1}
                onChange={(e) => setNameSearch1(e.target.value)}
              />
             <select
                value={baseSearch1}
                onChange={(e) => setBaseSearch1(e.target.value)}
              >
                <option value="">---Chọn cơ sở---</option>
                {baseList.map((item, index) => (
                  <option key={index} value={item.name}>
                    {item.name}
                  </option>
                ))}
              </select>
              <select
                value={residentialName1}
                onChange={(e) => setResidentialName1(e.target.value)}
              >
                <option value="">---Chọn cơ sở---</option>
                {residentialList?.map((item, index) => (
                  <option key={index} value={item.name}>
                    {item.name}
                  </option>
                ))}
              </select>
              <button onClick={filterCustomer1}>Tìm kiếm</button>
            </div>
            <table>
              <thead>
                <tr>
                  <th>Số định danh</th>
                  <th>Thông tin khách hàng</th>
                  <th>Số nước cũ</th>
                  <th>Nhập số nước mới</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {listResult1?.map((item,index)=>{
                  return(
                    <tr>
                      <td>{item?.ordinal_numbers}</td>
                      <td>
                        <div>
                          <div>Họ và tên: {item?.infoCommon.name}</div>
                          <div>Số điện thoại: {item?.infoCommon.phoneNumber}</div>
                          <div>Địa chỉ :{`${item?.address.detailsAddress},${item?.address.wards},${item?.address.district},${item.address.provinceOrCity}`}</div>
                          <div>Cơ sở: {item?.base.name}</div>
                          <div>Loại hộ:{item?.residentialType.name}</div>
                        </div>
                      </td>
                      <td>{item?.waterIndex}</td>
                      <td> 
                        <input
                          type='number'
                          placeholder='Nhập số nước mới'
                          value={newWaterValues1[`1_${item?.id}`]?.value}
                          onChange={(e) => handleChange(e.target.value,item?.id,1)}
                        />
                        {errorMessages && errorMessages[`1_${item?.id}`] && (
                          <span className="error-message">{errorMessages[`1_${item?.id}`].message}</span>
                        )}                        
                      </td>
                      <td><button onClick={()=>handleUpdate(item?.id,1)}>Cập nhật</button></td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          </div>
        )}
      </div>
      <div className={`section ${showPart === 2 ? 'active' : 'gray'}`} onClick={() => setShowPart(2)}>
        <h2>Cập nhật số nước xuất hoá đơn</h2>

        {showPart === 2 && (
          <div>
            <div className="search">
              <input
                type="text"
                placeholder="Số định danh"
                value={idSearch2}
                onChange={(e) => setIdSearch2(e.target.value)}
              />
              <input
                type="text"
                placeholder="Tên"
                value={nameSearch2}
                onChange={(e) => setNameSearch2(e.target.value)}
              />
              <select
                value={baseSearch2}
                onChange={(e) => setBaseSearch2(e.target.value)}
              >
                <option value="">---Chọn cơ sở---</option>
                {baseList.map((item, index) => (
                  <option key={index} value={item.name}>
                    {item.name}
                  </option>
                ))}
              </select>
              <select
                value={residentialName2}
                onChange={(e) => setResidentialName2(e.target.value)}
              >
                <option value="">---Chọn loại hộ---</option>
                {residentialList?.map((item) => (
                  <option key={item.id} value={item.name}>
                    {item.name}
                  </option>
                ))}
              </select>
              <button onClick={filterCustomer2}>Tìm kiếm</button>
            </div>
            <table>
              <thead>
                <tr>
                  <th>Số định danh</th>
                  <th>Thông tin khách hàng</th>
                  <th>Số nước cũ</th>
                  <th>Nhập số nước mới</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {listResult2.map((item,index)=>{
                  return(
                    <tr>
                      <td>{item?.ordinal_numbers}</td>
                      <td>
                        <div>
                          <div>Họ và tên: {item?.infoCommon.name}</div>
                          <div>Số điện thoại: {item?.infoCommon.phoneNumber}</div>
                          <div>Địa chỉ :{`${item?.address.detailsAddress},${item?.address.wards},${item?.address.district},${item.address.provinceOrCity}`}</div>
                          <div>Cơ sở: {item?.base.name}</div>
                          <div>Loại hộ:{item?.residentialType.name}</div>
                        </div>
                      </td>
                      <td>{item?.waterIndex}</td>
                      <td> 
                        <input
                          type='number'
                          placeholder='Nhập số nước mới'
                          value={newWaterValues1[`2_${item?.id}`]?.value}
                          onChange={(e) => handleChange(e.target.value,item?.id,2)}
                        />
                        {errorMessages && errorMessages[`2_${item?.id}`] && (
                          <span className="error-message">{errorMessages[`2_${item?.id}`]?.message}</span>
                        )}                        
                      </td>
                      <td><button onClick={()=>handleUpdate(item?.id,2)}>Cập nhật</button></td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default UpdateWaterIndex;
