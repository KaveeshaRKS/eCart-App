<?php
require("connect_to_db.php");

$shop = $_POST['shop'];

$sql = "SELECT category_name, category_image FROM product_category WHERE shop_id='$shop'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $category=$row['category_name'];

            $img = $row['category_image'];

            $image=base64_decode($img);

            echo "$category" . "," . "$image" . "   ";
        }
} else {
    echo "0 results";
}
$conn->close();
?>