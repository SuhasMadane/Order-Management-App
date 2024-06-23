import React, { useEffect, useState } from "react";
import {  MDBTable, MDBTableHead, MDBTableBody } from "mdb-react-ui-kit";
import axios from "axios";


 function AdminLogsTable() {
  const [logList, setLogList] = useState([]);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    const getAllLogs = async () => {
      try {

        let uri = `http://localhost:8181/orders/getAllLogs`;

        const response = await axios.get(uri);

        if (response.status === 200) {
          const list = response.data;
          const newList = [...list];
          console.log(newList);
          setLogList(newList);
          setLoading(false);
        } else {
          throw new Error(response.data);
        }
      } catch (error) {
        alert(error.response.data);
        setLoading(false);
      }
    };

    getAllLogs();
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <>
      <MDBTable align="middle">
        <MDBTableHead>
          <tr>
            <th scope="col">Log_Id</th>
            <th scope="col">0rder_Id</th>
            <th scope="col">Action</th>
            <th scope="col">Generated On</th>
            <th scope="col">Student Email</th>
          </tr>
        </MDBTableHead>

        <MDBTableBody>
          {logList.map((log) => {
            return (
              <tr key={log.id}>
                <td>{log.logId}</td>
                <td>{log.order_id}</td>
                <td>{log.action}</td>
                <td>{log.currentTime}</td>
                <td>{log.email}</td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
  );
}

export default AdminLogsTable;
