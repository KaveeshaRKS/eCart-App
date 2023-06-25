<?php
require("connect_to_db.php");

$shop = $_POST['shop'];
$category=$_POST['category'];
$product_name=$_POST['product_name'];

$sql = "SELECT product_image FROM product WHERE shop_id='$shop' AND category='$category' AND name='$product_name'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $encoded_image=$row["product_image"];

      $decorded_image=base64_decode($encoded_image);

      echo $decorded_image;
    }
  } 
else {
    echo "0 results";
}
$conn->close();
?>