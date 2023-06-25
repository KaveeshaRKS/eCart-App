<?php
require("connect_to_db.php");

$shop = $_POST['shop'];
$category=$_POST['category'];
$product_name=$_POST['product_name'];

$sql = "SELECT name, unit_price, description, existing_amount, unit_type FROM product WHERE shop_id='$shop' AND category='$category' AND name='$product_name'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $name=$row["name"];

      $price=$row["unit_price"];
      $p=trim($price);

      $description=$row["description"];
      $quantity=$row["existing_amount"];
      $unit_type=$row["unit_type"];

      echo $name . "   " . $p . "   " . $description . "   " . $quantity . "   " . $unit_type;
    }
  } 
else {
    echo "0 results";
}
$conn->close();
?>