<?php
require("connect_to_db.php");

$userId = $_POST['user'];

$sql = "SELECT c_address FROM customer WHERE nic='$userId'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      echo $row["c_address"];
    }
  } 
else {
    echo "0 results";
}
$conn->close();
?>