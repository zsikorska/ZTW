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
        if(strlen($_POST['new_ad'])>0){
            echo '<div class="notice notice-success is-dismissible"><p>Settings saved.</p></div>';
            array_push($ad_list, $_POST['new_ad']);
            update_option('ads', $ad_list);
        }
        else{
            echo '<div class="notice notice-info is-dismissible"><p>Ad can not be without text.</p></div>';
        }
    }
?>
    <h1> Page to manage ads in progress...</h1>
    <h2>Advertisments</h2>
<?php
    $ad_list = get_option('ads');
    for($i = 0; $i < count($ad_list); ++$i) {
        echo "<p> $ad_list[$i]</p>";
        ?>
        <input type="button" value="Edit">
        <input type="button" value="Delete">
        <?php
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

add_filter("the_content", "add_random_ad_after_title");

function remove_ad_filter($content)
{
    if (has_filter( 'the_content', 'add_random_ad_after_title' ))
    {
        remove_filter( 'the_content', 'add_random_ad_after_title' ); 
    }
    return $content;
}
add_filter('get_the_excerpt', 'remove_ad_filter', 9); //priority needs to be lower than that of wp_trim_excerpt, which has priority of 10. Otherwise, it will still be triggered for the first post in the loop. 

function readd_ad_filter($content)
{
    add_filter( 'the_content', 'add_random_ad_after_title' ); 
    return $content;
}
add_filter('get_the_excerpt', 'readd_ad_filter', 11); //priority needs to be higher than that of wp_trim_excerpt, which has priority of 10.


?>