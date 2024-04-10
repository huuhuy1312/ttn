import React, { useEffect, useState } from "react";
import NavigateComponent from "./components/navigateComponent";
import "../css/homePage.css"
import { Link } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
const TrangChu = () => {
    const [userInfo, setUserInfo] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
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
        const userData = getUserInfoFromCookie();
        console.log(userData)
        if (!userData) {
            navigate('/');
        }
    }, [navigate]);

    return (
        <div className="contains">
            <div><NavigateComponent/></div>
            <div className="component2">
                <div className="header">
                    <div><Link to="/logout">Đăng xuất</Link></div>
                </div>
            </div>
        </div>
    );
};

export default TrangChu;
