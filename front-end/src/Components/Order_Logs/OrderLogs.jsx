import axios from "axios";
import { MDBTable, MDBTableBody, MDBTableHead } from "mdb-react-ui-kit";
import React from "react";

const OrderLog = () => {
  const [orderLogList, setOrderLogList] = React.useState([]);

  React.useEffect(() => {
    getAllOrderLogs();
  }, []);

  const getAllOrderLogs = async () => {
    try {
      const emailId = localStorage.getItem("Student email");
      let uri = `http://localhost:8181/orders/getLogs?email=${emailId}`;
      const response = await axios.get(uri);
      if (response.status === 200) {
        console.log(response.data);
        let list = response.data;
        console.log(list);
        let newList = [...list];
        console.log(newList);
        setOrderLogList(newList);
        console.log(orderLogList);
      } else throw new Error(response.data);
    } catch (error) {
      alert(error.response.data);
    }
  };
  return (
    <>
      <MDBTable align="middle">
        <MDBTableHead>
          <tr>
            <th scope="col">Order Id</th>
            <th scope="col">Log Id</th>
            <th scope="col">Action</th>
            <th scope="col">Date & Time</th>
          </tr>
        </MDBTableHead>

        <MDBTableBody>
          {orderLogList.map((orderLog) => {
            return (
              <tr key={orderLog.order_id}>
                <td>{orderLog.order_id}</td>
                <td>{orderLog.logId}</td>
                <td>{orderLog.action}</td>
                <td>{orderLog.currentTime}</td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
    //   <>
    // <div style={{display:'flex',justifyContent:'center'}}><Typography variant="h2" color="info.main">
    //               Order Logs
    //             </Typography>
    // </div>

    // <TableContainer component={Paper}>
    //   <Table sx={{ minWidth: 650 }} aria-label="simple table">
    //     <TableHead>
    //       <TableRow>
    //         <TableCell>Order Id</TableCell>
    //         <TableCell align="right">Log Id</TableCell>
    //         <TableCell align="right">Action</TableCell>
    //         <TableCell align="right">Order Created On</TableCell>

    //       </TableRow>
    //     </TableHead>
    //     <TableBody>
    //       {orderStatus.map((orderStatus) => (
    //         <TableRow
    //           key={orderStatus.logId}
    //           sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
    //         >
    //           <TableCell component="th" scope="row">
    //           {orderStatus.order_id}
    //           </TableCell>
    //           <TableCell align="right">{orderStatus.logId}</TableCell>
    //           <TableCell align="right">{orderStatus.action}</TableCell>
    //           <TableCell align="right">{orderStatus.currentTime}</TableCell>
    //         </TableRow>
    //       ))}
    //     </TableBody>
    //   </Table>
    // </TableContainer>
    // </>
  );
};

export default OrderLog;
