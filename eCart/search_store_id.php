<?php
require("connect_to_db.php");

$shop = $_POST['shop'];

$sh=trim($shop);

$sql = "SELECT shop_id FROM store WHERE b_name='$sh'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      echo $row["shop_id"];
    }
  } 
else {
    echo "No shop-ID" . $shop . mysqli_error($conn);
}
$conn->close();
?>