<?PHP
$hostname_localhost="localhost";
$database_localhost="spaceapp";
$username_localhost="root";
$password_localhost="";

$correo = $_POST['correo'];
$pw = $_POST['pw'];

$cantidad = 0;

$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(msql_error(),E_USER_ERROR);

mysql_select_db($database_localhost,$localhost);

$query_search = "select * from usuario where email='$correo' and password = '$pw' ";

$query_exec = mysql_query($query_search) or die(mysql_error());
$json = array();

	if (mysql_num_rows($query_exec)) {
		# code...
		while ($row=mysql_fetch_assoc($query_exec)) {
			# code...
			$cantidad = $cantidad + 1;
		}
	}
	mysql_close($localhost);
	$json['personas']=$cantidad;
	echo json_encode($json);
?>