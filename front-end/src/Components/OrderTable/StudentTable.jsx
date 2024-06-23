import React, { useEffect, useState } from "react";
import {  MDBTable, MDBTableHead, MDBTableBody } from "mdb-react-ui-kit";
import axios from "axios";


 function StudentTable() {
  const [studentList, setStudentList] = useState([]);
  const [loading, setLoading] = useState(true);

  
  useEffect(() => {
    const getAllStudents = async () => {
      try {
        let uri = `http://localhost:8181/student/getAllStudents`;

        const response = await axios.get(uri);

        if (response.status === 200) {
          const list = response.data;
          const newList = [...list];
          console.log(newList);
          setStudentList(newList);
          setLoading(false);
        } else {
          throw new Error(response.data);
        }
      } catch (error) {
        alert(error.response.data);
        setLoading(false);
      }
    };

    getAllStudents();
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <>
      <MDBTable align="middle">
        <MDBTableHead>
          <tr>
            <th scope="col">Student_Id</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Email</th>
            <th scope="col">Password</th>
          </tr>
        </MDBTableHead>

        <MDBTableBody>
          {studentList.map((student) => {
            return (
              <tr key={student.id}>
                <td>{student.student_Id}</td>
                <td>{student.firstName}</td>
                <td>{student.lastName}</td>
                <td>{student.emailId}</td>
                <td>{student.passWord}</td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    </>
  );
}
export default StudentTable;
