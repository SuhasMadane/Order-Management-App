import React, { useEffect, useState } from "react";
import { MDBBtn, MDBTable, MDBTableHead, MDBTableBody } from "mdb-react-ui-kit";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function OrderTable() {
  const [orderList, setOrderList] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  
  useEffect(() => {
    const getAllOrders = async () => {
      try {
        const emailId = localStorage.getItem("Student email");
        let uri = `http://localhost:8181/orders/getOrders?email=${emailId}`;

        const response = await axios.get(uri);

        if (response.status === 200) {
          const list = response.data;
          const newList = [...list];
          console.log(newList);
          setOrderList(newList);
          setLoading(false);
        } else {
          throw new Error(response.data);
        }
      } catch (error) {
        alert(error.response.data);
        setLoading(false);
      }
    };

    getAllOrders();
  }, []);

  const handlePlaceClick = async(orderId)=>{
    // const bookData = { bookTitle: title, bookQuantity: quantity, bookOrderId: orderId, totalAmount:totalPrice, bookPrice:0 };
    // console.log(`Selected book: ${title}`);
    try{
      let uri = `http://localhost:8181/orders/place/${orderId}`
      const response = await axios.get(uri);
      if(response.status === 201){
        alert(response.data);
        navigate("/myorders")
      }
      else{
        throw new Error(response.data)
      }
    }
    catch(error){
      alert(error.response.data);
    }
  }
  const handleUpdateClick = async (orderId,title,quantity,totalPrice)=>{
    const bookData = { bookTitle: title, bookQuantity: quantity, bookOrderId: orderId, totalAmount:totalPrice, bookPrice:0 };
    console.log(`Selected book: ${title}`);
    try{
      let uri = `http://localhost:8181/books/getSingleBook?title=${title}`
      const response = await axios.get(uri);
      bookData.bookPrice = response.data.price;
      console.log(bookData);
      navigate("/updateOrder", { state: { bookData } });
    }
    catch(error){
      alert(error);
    }
  }
  // const handleDeleteClick = async(orderId)=>{
  //   try{
  //     let uri = `http://localhost:8181/orders/delete/${orderId}`
  //     const response = await axios.get(uri);
  //     if(response.status === 200){
  //       alert(response.data);
  //       navigate("/myorders")
  //     }
  //     else{
  //       throw new Error(response.data)
  //     }
  //   }
  //   catch(error){
  //     alert(error);
  //   }
  // }
  if (loading) return <p>Loading...</p>;

  return (
    <>
      <MDBTable align="middle">
        <MDBTableHead>
          <tr>
            <th scope="col">Title</th>
            <th scope="col">Quantity</th>
            <th scope="col">Total Price</th>
            <th scope="col">Order Id</th>
            <th scope="col">Order Created On</th>
            <th scope="col">Place Order</th>
            <th scope="col">Update Action</th>
          </tr>
        </MDBTableHead>

        <MDBTableBody>
          {orderList.map((order) => {
            return (
              <tr key={order.id}>
                <td>{order.bookTitle}</td>
                <td>{order.quantity}</td>
                <td>{order.totalPrice}</td>
                <td>{order.order_Id}</td>
                <td>{order.createdOn}</td>
                <td>
                  {order.currentStatus !== 'placed' && order.currentStatus !== 'failed'?(
                    <MDBBtn color="primary" rounded className="mx-2" onClick={()=>handlePlaceClick(order.order_Id)}>
                    Place
                  </MDBBtn>): <p color="">&nbsp;&nbsp;&nbsp;&nbsp;{order.currentStatus}</p>}
                </td>
                <td>
                {order.currentStatus !== 'placed' && order.currentStatus !== 'failed'?(
                  <MDBBtn color="secondary" rounded className="mx-2" onClick={()=>handleUpdateClick(order.order_Id,order.bookTitle,order.quantity,order.totalPrice)}>
                    Update
                  </MDBBtn>): <p color="">&nbsp;&nbsp;&nbsp;&nbsp;{order.currentStatus}</p>}
                </td>
                {/* <td>
                  <MDBBtn color="warning" rounded className="mx-2" onClick={()=>handleDeleteClick(order.order_Id)}>
                    Delete
                  </MDBBtn>
                </td> */}
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
  );
}
