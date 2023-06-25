<?php
require("connect_to_db.php");

$y = $_POST['year'];
$m=$_POST['month'];
$user=$_POST['user'];

$sql = "SELECT SUM(total_price) AS income FROM see_income WHERE shop_id='$user' AND EXTRACT(YEAR FROM date)='$y' AND EXTRACT(MONTH FROM date)='$m'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        echo $row['income'];
    }
} else {
    echo "0 results";
}
$conn->close();
?>