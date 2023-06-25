<?php
require("connect_to_db.php");

$shop = $_POST['shop'];
$category=$_POST['category'];

$sql = "SELECT product_image FROM product WHERE shop_id='$shop' AND category='$category'";

$result = $conn->query($sql);

$i = 0;

if ($result->num_rows > 0) {
    // output data of each row
    while ($i < $result->num_rows) {
        while ($row = $result->fetch_assoc()) {
            $img=$row['product_image'];

            $image=base64_decode($img);

            echo $image . "   ";
        }
        $i++;
    }
} else {
    echo "0 results";
}
$conn->close();
?>