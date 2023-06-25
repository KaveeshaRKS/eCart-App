<?php
require("connect_to_db.php");

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT nic, c_password FROM customer WHERE nic='$username' AND c_password='$password'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "SUCCESS";
} else {

    $sql = "SELECT shop_id, b_password FROM store WHERE shop_id='$username' AND b_password='$password'";

    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        echo "SUCCESS";
    } else {
        echo "FAILED";
    }
}
?>