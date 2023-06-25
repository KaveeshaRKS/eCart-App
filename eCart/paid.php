<?php
require("connect_to_db.php");

$user=$_POST['user'];

$sql = "SELECT product_name, category, shop_id, required_amount FROM shopping_cart WHERE cart_id='$user'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
      while ($row = $result->fetch_assoc()) {
          $product=$row['product_name'];
          $category=$row['category'];
          $shop_id=$row['shop_id'];
          $required_amount=$row['required_amount'];

          $sql2="SELECT existing_amount FROM product WHERE name='$product' AND category='$category' AND shop_id='$shop_id'";

          $result2 = $conn->query($sql2);

          if ($result2->num_rows > 0){
            while ($row2 = $result2->fetch_assoc()){
              $existing_amount=$row2['existing_amount'];
            }
          }
          $new_amount=$existing_amount-$required_amount;

          $sql3="UPDATE product SET existing_amount='$new_amount' WHERE name='$product' AND category='$category' AND shop_id='$shop_id'";

          if ($conn->query($sql3) === TRUE) {
            echo "Updated successfully";
          } else {
            echo "Error: " . mysqli_error($conn);
          }
      }
} else {
  echo "0 results";
}
$sql3 = "DELETE FROM shopping_cart WHERE cart_id='" . $user . "'";

if ($conn->query($sql3) === TRUE) {
    echo "";
  } else {
    echo "Error: " . mysqli_error($conn);
  }

$conn->close();

?>