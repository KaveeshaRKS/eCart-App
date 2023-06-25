<?php
require ("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$address = $_POST['address'];
$city = $_POST['city'];
$phone = $_POST['phone'];
$password = $_POST['password'];
$img = $_POST['image'];

$image=base64_encode($img);

$sql = "INSERT INTO store (shop_id, b_name, b_address, city, phone, b_password, b_image) VALUES ('$id', '$name', '$address', '$city', '$phone', '$password', '$image')";

if (mysqli_query($conn, $sql)){
    echo "Registered successfully!";
} else {
    echo "Error: " . mysqli_error($conn);
}
?>