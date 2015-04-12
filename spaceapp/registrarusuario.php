<?PHP
$hostname_localhost="localhost";
$database_localhost="spaceapp";
$username_localhost="root";
$password_localhost="";

$user = $_POST['user'];
$name = $_POST['nombre'];
$correo = $_POST['correo'];
$pw = $_POST['pw'];
$cantidad = 0;

$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(msql_error(),E_USER_ERROR);


mysql_select_db($database_localhost,$localhost);

$query_search = "insert into usuario(user, name, email, password) values('".$user."','".$name."','".$correo."','".$pw."') ";
$query_exec = mysql_query($query_search) or die(mysql_error());

if ($query_search == true ) {
	$cantidad = 1;
}else{
	$cantidad = 0;
}

mysql_close($localhost);
$json['personas']=$cantidad;
echo json_encode($json);
?>