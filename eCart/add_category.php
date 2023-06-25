<?php
require("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$shop = $_POST['shop'];
$img = $_POST['image'];

$image=base64_encode($img);

$sql = "INSERT INTO product_category (category_id, category_name, shop_id, category_image) VALUES ('$id', '$name', '$shop', '$image')";

if (mysqli_query($conn, $sql)) {
    echo "Inserted successfully!";
} else {
    echo "Error!". mysqli_error($conn);
}
?>