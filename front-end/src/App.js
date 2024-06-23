
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import AddBook from './Components/Book/AddBook';
import FailedOrder from './Components/FailedOrder/FailedOrder';
import MainPage from './Components/MainPage/MainPage';
import CreateOrder from './Components/Order/CreateOrder';
import UpdateOrder from './Components/Order/UpdateOrder';
import AdminOrderTable from './Components/OrderTable/AdminOrderTable';
import OrderTable from './Components/OrderTable/OrderTable';
import StudentTable from './Components/OrderTable/StudentTable';
import AdminLogsTable from './Components/Order_Logs/AdminOrderLogs';
import OrderLog from './Components/Order_Logs/OrderLogs';
import SignIn from './Components/Signin/Signin';
import SignUp from './Components/Signup/Signup';

function App() {
  return (
    <>
    <BrowserRouter>
     <Routes>
       <Route path="/signup" element={<SignUp/>}/>
       <Route path="/signin" element={<SignIn/>}/>
       <Route path="/" element={<MainPage/>}/>
      <Route path="/createOrder" element={<ProtectedRoute><CreateOrder/></ProtectedRoute>}/> 
      <Route path="/myorders" element={<ProtectedRoute><OrderTable/></ProtectedRoute>}/>
      <Route path="/orderlogs" element={<ProtectedRoute><OrderLog/></ProtectedRoute>}/>
     <Route path="/failedOrders" element={<ProtectedRoute><FailedOrder/></ProtectedRoute>}/> 
     <Route path="/updateOrder" element={<ProtectedRoute><UpdateOrder/></ProtectedRoute>}/> 
     <Route path="/addBook" element={<ProtectedRoute><AddBook/></ProtectedRoute>}/>
     <Route path="/getAllOrders" element={<ProtectedRoute><AdminOrderTable/></ProtectedRoute>}/>
     <Route path="/getAllStudents" element={<ProtectedRoute><StudentTable/></ProtectedRoute>}/>
     <Route path="/getAllLogs" element={<ProtectedRoute><AdminLogsTable/></ProtectedRoute>}/>
     </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;

function ProtectedRoute({children}){
  let email = localStorage.getItem("Student email");
  if(!email){
    return <Navigate to={"/"} replace={true}/>;
  }
  return children;
}
