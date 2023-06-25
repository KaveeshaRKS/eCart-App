<?php
require("connect_to_db.php");

$id = $_POST['id'];
$name = $_POST['name'];
$category = $_POST['category'];
$shop_name = $_POST['shop'];

$sql1="SELECT shop_id FROM store WHERE b_name='$shop_name'";

$result1=$conn->query($sql1);

            if($result1->num_rows > 0){
                while($row = $result1->fetch_assoc()){
                    $shop_id=$row['shop_id'];
                }
            }

$sql2 = "DELETE FROM shopping_cart WHERE cart_id='" . $id . "' AND product_name='" . $name . "'  AND category='" . $category . "' AND shop_id='" . $shop_id . "'";

if ($conn->query($sql2) === TRUE) {
    echo "Removed successfully";
  } else {
    echo "Error: " . mysqli_error($conn);
  }

  $sql3 = "DELETE FROM see_income WHERE cart_id='" . $id . "' AND product_name='" . $name . "'  AND category='" . $category . "' AND shop_id='" . $shop_id . "'";

if ($conn->query($sql3) === TRUE) {
    echo "";
  } else {
    echo "Error: " . mysqli_error($conn);
  }
?>