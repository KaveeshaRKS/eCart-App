<?php
require("connect_to_db.php");

$shop = $_POST['shop'];
$category=$_POST['category'];

$sql = "SELECT name, product_image FROM product WHERE category='$category' AND shop_id='$shop'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $product=$row['name'];

            $img = $row['product_image'];

            $image=base64_decode($img);

            echo "$product" . "," . "$image" . "   ";
        }
} else {
    echo "0 results";
}
$conn->close();
?>