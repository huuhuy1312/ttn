import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Address from '../model/address.ts';
const DangKySuDungNuoc = () => {
  const [cities, setCities] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);
  const [selectedCity, setSelectedCity] = useState("");
  const [selectedDistrict, setSelectedDistrict] = useState("");
  const [selectedWard, setSelectedWard] = useState("");
    const [detailsAddress, setDetailsAddress] = useState("");
    const [fullName, setFullName]= useState("");
    const [email,setEmail] = useState("");
    const [phoneNumber,setPhoneNumber] = useState("");
    const [numId, setNumId] = useState("");
    const [imageFront, setImageFront] = useState('');
    const [imageBack, setImageBack] = useState('');
    const [certificationOfProvety, setCertificationOfProvety] = useState(null);
    const [listResidentalType, setListResidentalType] = useState([]);
    const handleImageChange = (event, setImageFunction) => {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageFunction(reader.result);
      };
      if (file) {
        reader.readAsDataURL(file);
      }
    };
    const getAllResidentalType = async () => {
        try {
            const response = await fetch("http://127.0.0.1:8080/api/residentialType/all", {
                method: "GET"
            });
    
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const data = await response.json();
            console.log(data)
            setListResidentalType(data);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json");
        setCities(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    getAllResidentalType()
    fetchData();
  }, []);
  const [selectedOption, setSelectedOption] = useState(1);
  const handleRegister = async () => {
    const requestBody = {
      email: email,
      phoneNumber: phoneNumber,
      name: fullName,
      baseId: 1,
      residentialTypeId: selectedOption,
      provinceOrCity: selectedCity,
      district: selectedDistrict,
      wards: selectedWard,
      detailsAddress: detailsAddress,
      front_image:imageFront,
      back_image:imageBack,
      certificate_of_poverty:certificationOfProvety
    };
  
    console.log("Request Body:", requestBody); // In ra nội dung của body trước khi gửi yêu cầu API
  
    try {
      const response = await axios.post("http://127.0.0.1:8080/api/customer/add", requestBody);
  
      console.log(response.data);
      // Xử lý dữ liệu phản hồi ở đây nếu cần
    } catch (error) {
      console.error('Lỗi:', error);
      // Xử lý lỗi ở đây nếu cần
    }
  }
  // Hàm xử lý sự kiện khi lựa chọn thay đổi
  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };
  const handleCityChange = (e) => {
    const selectedCityName = e.target.value;
    const selectedCity = cities.find(city => city.Name === selectedCityName);
    setSelectedCity(selectedCityName);
    setSelectedDistrict("");
    setSelectedWard("");
    setDistricts(selectedCity ? selectedCity.Districts : []);
    setWards([]);
  };
  
  // Trong phần handleDistrictChange:
  const handleDistrictChange = (e) => {
    const selectedDistrictName = e.target.value;
    const selectedDistrict = districts.find(district => district.Name === selectedDistrictName);
    setSelectedDistrict(selectedDistrictName);
    setSelectedWard("");
    setWards(selectedDistrict ? selectedDistrict.Wards : []);
  };
  
  // Trong phần handleWardChange:
  const handleWardChange = (e) => {
    setSelectedWard(e.target.value);
  };
  const handleDetailsAddress = (e) => {
    setDetailsAddress(e.target.value);
  };
  const handleFullNameChange = (e)=>{
    setFullName(e.target.value);
  }
  const handleEmailChange =(e)=>{
    setEmail(e.target.value);
  }
  const handlePhoneNumberChange =(e)=>{
    setPhoneNumber(e.target.value);
  }
  const handleNumIdChange=(e)=>{
    setNumId(e.target.value);
  }
  
  return (
    <div>
        <div>Nhập thông tin đăng ký sử dụng nước</div>
        <div>
            <div>
                <div>Chọn tỉnh/thành phố</div>
                <select className="form-select form-select-sm mb-3" id="city" aria-label=".form-select-sm" onChange={handleCityChange} value={selectedCity}>
                  <option value="" disabled>Chọn tỉnh thành</option>
                  {cities.map(city => (
                    <option key={city.Id} value={city.Name}>{city.Name}</option>
                  ))}
                </select>
            </div>
            <div>
                <div>Chọn quận huyện</div>
                <select className="form-select form-select-sm mb-3" id="district" aria-label=".form-select-sm" onChange={handleDistrictChange} value={selectedDistrict}>
                  <option value="" disabled>Chọn quận huyện</option>
                  {districts.map(district => (
                    <option key={district.Id} value={district.Name}>{district.Name}</option>
                  ))}
                </select>
            </div>
            <div>
                <div>Chọn phường xã</div>
                <select className="form-select form-select-sm" id="ward" aria-label=".form-select-sm" onChange={handleWardChange} value={selectedWard}>
                  <option value="" disabled>Chọn phường xã</option>
                  {wards.map(ward => (
                    <option key={ward.Id} value={ward.Name}>{ward.Name}</option>
                  ))}
                </select>
            </div>
            <div>
                <div>Nhập địa chỉ cụ thể:</div>
                <input value ={detailsAddress} onChange={(e)=>handleDetailsAddress(e)}placeholder='Nhập địa chỉ cụ thể'></input>
            </div>
            <div>
                <div>Họ và tên:</div>
                <input placeholder='Nhập họ và tên' onChange={(e)=>handleFullNameChange(e)} value={fullName}></input>
            </div>
            <div>
                <div>Email</div>
                <input placeholder='Nhập email' onChange={(e)=>handleEmailChange(e)} value={email}></input>
            </div>
            <div>
                <div>Số điện thoại</div>
                <input placeholder='Nhập số điện thoại' onChange={(e)=>handlePhoneNumberChange(e)} value={phoneNumber}></input>
            </div>
            <div>
                <div>Nhập số CCCD/CMT</div>
                <input type='number' placeholder='Nhập số CCCD/CMT' onChange={(e)=>handleNumIdChange(e)} value={numId}></input>
            </div>
            <div className="mb-3">
                <label htmlFor="frontImage" className="form-label">Ảnh CCCD/CMT mặt trước:</label>
                <div style={{width:100, height:100}}>
                    {imageFront && <img style={{width:"100%", height:"100%", objectFit:"contain"}}src={imageFront} alt="Ảnh CCCD/CMT mặt sau" />}
                </div>
                <input type="file" className="form-control" id="frontImage" accept="image/*" onChange={(e) => handleImageChange(e, setImageFront)} />
            </div>

            <div className="mb-3">
                <label htmlFor="backImage" className="form-label">Ảnh CCCD/CMT mặt sau:</label>
                <div style={{width:100, height:100}}>
                    {imageBack && <img style={{width:"100%", height:"100%", objectFit:"contain"}}src={imageBack} alt="Ảnh CCCD/CMT mặt sau" />}
                </div>
                <input type="file" className="form-control" id="backImage" accept="image/*" onChange={(e) => handleImageChange(e, setImageBack)} />
            </div>

            <div>
                <label htmlFor="choices">Chọn một lựa chọn:</label>
                <select id="choices" value={selectedOption} onChange={handleOptionChange}>
                    {listResidentalType.map((item)=>{
                        return(
                            <option value={item.id}>{item.name}</option>
                        )
                    })}
                </select>
            </div>
            <div>{selectedOption}</div>
            {
              selectedOption == 2 ? (
                <div className="mb-3">
                  <label htmlFor="backImage" className="form-label">Giấy chứng nhận hộ nghèo, hộ cận nghèo:</label>
                  <div style={{ width: 100, height: 100 }}>
                    {imageBack && <img style={{ width: "100%", height: "100%", objectFit: "contain" }} src={certificationOfProvety} alt="Giấy chứng nhận hộ nghèo, cận nghèo" />}
                  </div>
                  <input type="file" className="form-control" id="certificationOfProvety" accept="image/*" onChange={(e) => handleImageChange(e, setCertificationOfProvety)} />
                </div>
              ) : null
            }
            
        </div>
        <button onClick={handleRegister}>Đăng ký sử dụng nước</button>
    </div>
  );
};

export default DangKySuDungNuoc;
