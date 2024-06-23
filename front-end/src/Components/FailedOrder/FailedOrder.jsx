
import { TableBody, TableRow,TableCell, Typography, Table, TableHead, TableContainer, Paper } from "@mui/material";
import axios from "axios";
import { MDBTable, MDBTableBody, MDBTableHead } from "mdb-react-ui-kit";
import React from "react";

const FailedOrder = ()=>{
    const [failedOrders,setFailedOrders] = React.useState([]);

    React.useEffect(()=>{
      getAllFailOrders();
  },[])

  const getAllFailOrders = async ()=>{

    try{
    const emailId = localStorage.getItem("Student email");
    let uri = `http://localhost:8181/orders/getFailedOrders?email=${emailId}`
    const response = await axios.get(uri);
    if(response.status ===  200){
      let list = response.data;
      let newList = [...list];
      setFailedOrders(newList);
    }
    else
      throw new Error(response.data);
  }
  catch(error){
    alert(error.response.data);
  }
  }
  return (
      <>
        <>
      <MDBTable align="middle">
        <MDBTableHead>
          <tr>
            <th scope="col">Order Id</th>
            <th scope="col">Reason</th>
            <th scope="col">Date & Time</th>
          </tr>
        </MDBTableHead>

        <MDBTableBody>

          {failedOrders.map((failedOrder) => {
            return (
              <tr key={failedOrder.id}>
                <td>{failedOrder.order_Id}</td>
                <td>{failedOrder.reason}</td>
                <td>{failedOrder.currentDateAndTime}</td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
    {/* <div style={{display:'flex',justifyContent:'center'}}><Typography variant="h2" color="info.main">
                  Order Logs
                </Typography>
    </div>

    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Order Id</TableCell>
            <TableCell align="right">Log Id</TableCell>
            <TableCell align="right">Action</TableCell>
            <TableCell align="right">Order Created On</TableCell>
            
          </TableRow>
        </TableHead>
        <TableBody>
          {orderStatus.map((orderStatus) => (
            <TableRow
              key={orderStatus.logId}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
              {orderStatus.order_id}
              </TableCell>
              <TableCell align="right">{orderStatus.logId}</TableCell>
              <TableCell align="right">{orderStatus.action}</TableCell>
              <TableCell align="right">{orderStatus.currentTime}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer> */}
    </>
  );
}


export default FailedOrder;