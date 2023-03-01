<?php
/**
 * Plugin Name: Advertisment Publisher
 * Description: Display advertisment after post title.
 * Version: 1.0
 * Author: Magdalena Nowicka, Zuzanna Sikorska
*/


function ad_pub_admin_actions_register_menu(){
    add_options_page("Advertisment Publisher", "Advertisment Publisher", 'manage_options', "ad-pub", "ad_pub_admin_page");
}

add_action('admin_menu', 'ad_pub_admin_actions_register_menu');

function ad_pub_admin_page(){
?>
    <h1> Page to manage adds in progress...</h1>
<?php
}

?>