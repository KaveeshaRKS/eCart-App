<?php
require("connect_to_db.php");

$userId = $_POST['user'];

$sql = "SELECT SUM(total_price) AS value_sum FROM shopping_cart WHERE cart_id='$userId'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      echo $row["value_sum"];
    }
  } 
else {
    echo "0 results";
}
$conn->close();
?>