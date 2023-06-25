<?php
require("connect_to_db.php");

$sql = "SELECT b_name, city, b_image FROM store";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $business=$row['b_name'];
        $b_city=$row['city'];
        $img = $row['b_image'];

        $image=base64_decode($img);

        echo "$business" . "," . "$b_city" . "_" . "$image" . "   ";
    }
} else {
    echo "0 results";
}
$conn->close();
?>