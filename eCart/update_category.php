<?php
require("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$img = $_POST['image'];
$user=$_POST['user'];

$image=base64_encode($img);

$sql = "UPDATE product_category SET category_name='" . $name . "', category_image='" . $image . "' WHERE category_id='" . $id . "' AND shop_id='" . $user ."'";

if ($conn->query($sql) === TRUE) {
    echo "Updated successfully";
  } else {
    echo "Error: " . mysqli_error($conn);
  }
?>