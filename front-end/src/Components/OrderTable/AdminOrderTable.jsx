import React, { useEffect, useState } from "react";
import { MDBTable, MDBTableHead, MDBTableBody } from "mdb-react-ui-kit";
import axios from "axios";


function AdminOrderTable() {
  const [orderList, setOrderList] = useState([]);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    const getAllOrders = async () => {
      try {
        let uri = `http://localhost:8181/orders/getAllOrders`;

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
            <th scope="col">Order Status</th>
            <th scope="col">Student Email</th>
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
                  {order.currentStatus}
                </td>
                <td>
                {order.email}
                </td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
  );
}
export default AdminOrderTable;
