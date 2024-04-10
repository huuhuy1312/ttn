import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try {
            const response = await axios.post("http://127.0.0.1:8080/api/auth/login", {
                username: username,
                password: password
            });
            if (response.status === 200) {
                document.cookie = `userInfo=${JSON.stringify(response.data)}; expires=Thu, 18 Dec 2025 12:00:00 UTC; path=/`;
                window.location.href = '/trangchu';
            }
        } catch (error) {
            console.error('Lỗi:', error);
            setError("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
    }

    return (
        <div>
            <div className="title">Đăng nhập</div>
            <div className="form-input">
                <input placeholder="Nhập tên đăng nhập" type="text" onChange={(e) => setUsername(e.target.value)} value={username}></input>
                <input placeholder="Nhập mật khẩu" type="password" onChange={(e) => setPassword(e.target.value)} value={password}></input>
                
            </div>
            <div><Link to={"/dangKySuDungNuoc"}>Đăng ký sử dụng nước</Link></div>
            <button onClick={handleLogin}>Xác Nhận</button>
            {error && <div style={{ color: 'red' }}>{error}</div>}
        </div>
    )
}
export default Login;
