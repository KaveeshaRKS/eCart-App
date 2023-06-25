<?php
require("connect_to_db.php");

$sql = "SELECT b_image FROM store";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $img = $row['b_image'];

            $image=base64_decode($img);

            echo "$image" . "   ";
        }
} else {
    echo "0 results";
}
$conn->close();
?>