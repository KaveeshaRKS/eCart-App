<?php
require("connect_to_db.php");

$user = $_POST['user'];

$sql = "SELECT nic FROM customer WHERE nic='$user'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "A customer";
} else {
    echo "A shop";
}
$conn->close();
?>