<?php
require("connect_to_db.php");

$id = $_POST['id'];
$shop = $_POST['shop'];
$category=$_POST['category'];

$sql = "DELETE FROM product WHERE product_id='" . $id . "' AND shop_id='" . $shop . "' AND category='" . $category . "'";

if ($conn->query($sql) === TRUE) {
    echo "Deleted successfully";
  } else {
    echo "Error: " . mysqli_error($conn);
  }
?>