
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SearchInvoice from './pages/searchInvoice';
import ViewDetailsInvoice from './pages/viewDetailsInvoice';
import DangKySuDungNuoc from './pages/dangKySuDungNuoc';
import Login from './pages/login';
import TrangChu from './pages/trangChu';
import YeuCauDangKySuDungNuoc from './pages/yeuCauDangKySuDungNuoc';
import UpdateWaterIndex from './pages/UpdateWaterIndex';
import PageSuccess from './pages/pageSuccess';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login/>}></Route>
        <Route path="/viewDetailsInvoice/:id" element={<ViewDetailsInvoice/>}></Route>
        <Route path="/dangKySuDungNuoc" element={<DangKySuDungNuoc/>}></Route>
        <Route path="/viewWaterRegistrationRequire" element={<YeuCauDangKySuDungNuoc/>}></Route>
        <Route path="/trangchu" element={<TrangChu/>}></Route>
        <Route path='/updateWaterIndex' element={<UpdateWaterIndex/>}></Route>
        <Route path='/searchInvoice' element={<SearchInvoice/>}></Route>
        <Route path='/thanhCong' element={<PageSuccess/>}></Route>
      </Routes>
    </Router>
  );
}

export default App;
