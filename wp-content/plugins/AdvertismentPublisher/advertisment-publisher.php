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
if(!get_option('ads')) {
    $ad_list = array("One", "Two", "Three");
    update_option('ads', $ad_list);
}
function ad_pub_admin_page(){
    $ad_list = get_option('ads');
    update_option('ads', $ad_list);
    if(isset($_POST['new_ad'])){
        echo '<div class="notice notice-success is-dismissible"><p>Settings saved.</p></div>';
        array_push($ad_list, $_POST['new_ad']);
        update_option('ads', $ad_list);
    }
?>
    <h1> Page to manage ads in progress...</h1>
    <h2>Advertisments</h2>
<?php
    $ad_list = get_option('ads');
    for($i = 0; $i < count($ad_list); ++$i) {
        echo "<p> $ad_list[$i]</p>";
    }
?>
    <div class="wrap">
        <h1>Add new advertisment</h1>
        <form name="ad_form" method="post">
            <textarea name="new_ad" id="new_ad" rows="3"></textarea>
            <p class="submit"><input type="submit" value="Submit"></p>
        </form>
    </div>

<?php
}

function add_random_ad_after_title($content){
    $ad_list = get_option('ads');
    $random_key = random_int(0, count($ad_list) - 1 );

    return $content = '<div>' .$ad_list[$random_key] . '</div> <br>' . $content;
}

add_filter("the_content", "add_random_ad_after_title", 10, 2);

?>