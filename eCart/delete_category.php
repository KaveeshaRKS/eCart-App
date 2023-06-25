<?php
require("connect_to_db.php");

$id = $_POST['id'];
$shop = $_POST['shop'];

$sql = "DELETE FROM product_category WHERE category_id='" . $id . "' AND shop_id='" . $shop . "'";

if ($conn->query($sql) === TRUE) {
    echo "Deleted successfully";
  } else {
    echo "Error: " . mysqli_error($conn);
  }
$sql2 = "SELECT category_name FROM product_category WHERE category_id='" . $id . "' AND shop_id='" . $shop . "'";

$result2 = $conn->query($sql2);

if ($result2->num_rows > 0) {
        while ($row = $result2->fetch_assoc()) {
            $category=$row['category_name'];
        }
        $sql3 = "DELETE FROM product WHERE category='$category' AND shop_id='$shop'";

            if ($conn->query($sql3) === TRUE) {
                echo "DDD";
              } else {
                echo "Error: " . mysqli_error($conn);
              }
}
?>