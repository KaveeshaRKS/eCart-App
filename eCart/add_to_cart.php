<?php
require("connect_to_db.php");

$cart_id = $_POST['cart_id'];
$product_name = $_POST['product_name'];
$category = $_POST['category'];
$shop_id = $_POST['shop_id'];
$required_amount = $_POST['required_amount'];
$total = $_POST['total'];

$sql = "INSERT INTO shopping_cart (cart_id, product_name, category, shop_id, required_amount, total_price) VALUES ('$cart_id', '$product_name', '$category', '$shop_id', '$required_amount', '$total')";

if (mysqli_query($conn, $sql)) {
    echo "Added To Cart!";
} else {
    echo "Error!". mysqli_error($conn);
}

$myDate = date('Y/m/d');

$sql2 = "INSERT INTO see_income (cart_id, product_name, category, shop_id, required_amount, total_price, date) VALUES ('$cart_id', '$product_name', '$category', '$shop_id', '$required_amount', '$total', '$myDate')";

if (mysqli_query($conn, $sql2)) {
    echo " ";
} else {
    echo "  ";
}
?>