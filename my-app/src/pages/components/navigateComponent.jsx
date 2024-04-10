import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../../css/navigate.css"
const NavigateComponent = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [showManagerDashBoard, setShowManagerDashBoard] = useState(false);
    const [showEmployeeBoard, setShowEmployeeBoard] = useState(false);
    const [showCustomerBoard, setShowCustomerBoard] = useState(false);

    useEffect(() => {
        // Hàm để lấy thông tin người dùng từ cookie
        const getUserInfoFromCookie = () => {
            const cookies = document.cookie.split(';');
            for (let cookie of cookies) {
                const [name, value] = cookie.trim().split('=');
                if (name === 'userInfo') {
                    return JSON.parse(value);
                }
            }
            return null;
        };

        // Gọi hàm để lấy thông tin người dùng từ cookie
        const userData = getUserInfoFromCookie();
        setUserInfo(userData);

        if (userData) {
            setShowManagerDashBoard(userData.role.name === "manager");
            setShowCustomerBoard(userData.role.name === "customer");
            setShowEmployeeBoard(userData.role.name === "employee");
        }
    }, []);

    return (
        <div className="contains_navigate">
            <div className="dashboard">
                <div><Link to={"/trangchu"}>TrangChu</Link></div>
                {
                    showCustomerBoard && (
                        <div>
                            <div><Link to={"/searchInvoice"}>Tìm hóa đơn</Link></div>
                            <div><Link to={"/viewDetailsInfo"}>Xem chi tiết thông tin</Link></div>
                        </div>
                    )
                }
                {
                    showManagerDashBoard && (
                        <div>
                            <div><Link to={"/viewWaterRegistrationRequire"}>Xem yêu cầu đăng ký sử dụng nước</Link></div>
                        </div>
                    )
                }
                {
                    showEmployeeBoard && (
                        <div>
                            <div><Link to={"/updateWaterIndex"}>Sửa số nước không xuất hoá đơn</Link></div>
                            <div><Link to={"/updateWaterIndex"}>Cập nhật số nước và xuất hoá đơn</Link></div>
                        </div>
                    )
                }
            </div>
        </div>
    );
}

export default NavigateComponent;
