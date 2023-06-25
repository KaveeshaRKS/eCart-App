<?php
require("connect_to_db.php");

$user=$_POST['user'];

$sql = "SELECT product_image FROM product INNER JOIN shopping_cart ON product.name=shopping_cart.product_name AND product.category=shopping_cart.category AND product.shop_id=shopping_cart.shop_id WHERE shopping_cart.cart_id='$user'";

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
}  else {
    echo "0 results" . mysqli_error($conn);
}
$conn->close();
?>