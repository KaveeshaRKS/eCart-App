<?php
require("connect_to_db.php");

$shop = $_POST['shop'];

$sql = "SELECT cart_id, product_name, required_amount, total_price, date FROM see_income WHERE shop_id='$shop'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $cart=$row["cart_id"];
      $product=$row["product_name"];
      $amount=$row["required_amount"];
      $price=$row["total_price"];
      $date=$row["date"];

      $sql2 = "SELECT c_name, c_address FROM customer WHERE nic='$cart'";

      $result2 = $conn->query($sql2);

        if ($result2->num_rows > 0) {
            // output data of each row
            while($row2 = $result2->fetch_assoc()) {
            $name=$row2["c_name"];
            $address=$row2["c_address"];
            }
        }
        echo "$name" . "_" . "$address" . "_" . "$product" . "_" . "Required Amount:" . "$amount" . "_" . "$price" . "LKR" . "_" . "$date" . "   ";
    }
  } 
else {
    echo "0 results";
}
$conn->close();
?>