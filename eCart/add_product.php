<?php
require("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$category = $_POST['category'];
$existing_amount = $_POST['existing_amount'];
$price = $_POST['price'];
$discount = $_POST['discount'];
$shop = $_POST['shop'];
$img = $_POST['image'];
$desc=$_POST['description'];
$u=$_POST['unit'];

$image=base64_encode($img);

$sql = "INSERT INTO product (product_id, name, category, existing_amount, unit_price, discount, shop_id, product_image, description, unit_type) VALUES ('$id', '$name', '$category', '$existing_amount', '$price', '$discount', '$shop', '$image', '$desc', '$u')";

if (mysqli_query($conn, $sql)) {
    echo "Inserted successfully!";
} else {
    echo "Error!". mysqli_error($conn);
}
?>