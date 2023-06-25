<?php
require("connect_to_db.php");

$shop = $_POST['shop'];

$sql = "SELECT category_name FROM product_category WHERE shop_id='$shop'";

$result = $conn->query($sql);

$i = 0;

if ($result->num_rows > 0) {
    // output data of each row
    while ($i < $result->num_rows) {
        while ($row = $result->fetch_assoc()) {
            $categories = $row['category_name'];

            echo "$categories" . "   ";
        }
        $i++;
    }
} else {
    echo "0 results";
}
$conn->close();
?>