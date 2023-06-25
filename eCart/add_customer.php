<?php
require("connect_to_db.php");

$nic = $_POST['nic'];
$name = $_POST['name'];
$address = $_POST['address'];
$phone = $_POST['phone'];
$email = $_POST['email'];
$password = $_POST['password'];

$sql = "INSERT INTO customer (nic, c_name, c_address, phone, email, c_password) VALUES ('" . $nic . "', '" . $name . "', '" . $address . "', '" . $phone . "', '" . $email . "', '" . $password . "')";

if (($conn->query($sql)) === TRUE) {
    echo "Registered successfully!";
} else {
    echo "Error!";
}
?>