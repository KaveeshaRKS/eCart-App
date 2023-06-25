<?php
require("connect_to_db.php");

$user = $_POST['user'];

$sql = "SELECT * FROM shopping_cart WHERE cart_id='$user'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
        while ($row = $result->fetch_assoc()) {
            $name_of_product=$row['product_name'];
            $category=$row['category'];
            $shop=$row['shop_id'];

            $sql2="SELECT b_name FROM store WHERE shop_id='$shop'";

            $result2=$conn->query($sql2);

            if($result2->num_rows > 0){
                while($row1 = $result2->fetch_assoc()){
                    $shop_name=$row1['b_name'];
                }
            }

            $sql3="SELECT product_image FROM product WHERE shop_id='$shop' AND category='$category' AND name='$name_of_product'";

            $result3=$conn->query($sql3);

            if($result3->num_rows > 0){
                while($row2 = $result3->fetch_assoc()){
                    $product_image_encoded=$row2['product_image'];

                    $decorded_image=base64_decode($product_image_encoded);
                }
            }

            $amount=$row['required_amount'];
            $price=$row['total_price'];

            echo "$name_of_product" . "," . "$category" . "," . "$shop" . "," . "$shop_name" . "," . "Required Amount:" . "$amount" . "," . "Total Price:" . "$price" . "," . "$decorded_image" . "_";
        }
} else {
    echo "0 results";
}
$conn->close();
?>