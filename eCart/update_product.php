<?php
require("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$category = $_POST['category'];
$existing_amount = $_POST['existing_amount'];
$price = $_POST['price'];
$discount = $_POST['discount'];
$img=$_POST['image'];
$desc=$_POST['description'];
$u=$_POST['unit'];
$user=$_POST['user'];

$image=base64_encode($img);

$sql = "UPDATE product SET name='" . $name . "', existing_amount='" . $existing_amount . "', unit_price='" . $price . "', discount='" . $discount . "', product_image='" . $image . "', description='" . $desc . "', unit_type='" . $u . "' WHERE product_id='" . $id . "' AND category='" . $category ."' AND shop_id='" . $user ."'";

if ($conn->query($sql) === TRUE) {
    echo "Updated successfully";
  } else {
    echo "Error: " . mysqli_error($conn);
  }
?>