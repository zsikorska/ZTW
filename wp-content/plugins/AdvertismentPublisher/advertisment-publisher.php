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
    $ad_list = array();
    update_option('ads', $ad_list);
}

function ad_pub_admin_page(){
    $editor_content = "";
    $ad_list = get_option('ads');
    if(isset($_POST['ad_content'])){
        if(strlen($_POST['ad_content'])>0 && !empty($_POST['ad_start_time']) && !empty($_POST['ad_end_time'])){
            $formattedContent = wp_kses_post(stripslashes($_POST['ad_content']));
            $selected_start_time = $_POST['ad_start_time'];
            $selected_end_time = $_POST['ad_end_time'];

            if(isset($_POST['ad_id'])) {
                $ad_list[$_POST['ad_id']] = array('content' => $formattedContent, 'start_time' => $selected_start_time, 'end_time' => $selected_end_time);
            } else {                
                array_push($ad_list, array('content' => $formattedContent, 'start_time' => $selected_start_time, 'end_time' => $selected_end_time));
            }
            update_option('ads', $ad_list);
            unset($_POST['ad_id']);

            echo '<div class="notice notice-success is-dismissible"><p>Settings saved.</p></div>';
        }
        else{
            echo '<div class="notice notice-info is-dismissible"><p>Ad can not be saved. All the fields have to be set.</p></div>';
        }
    } else if (isset($_POST['ad_id'])){
        $ad_id = $_POST['ad_id'];
        switch($_POST['subject']) {

            case 'edit': 
                $editor_content = $ad_list[$ad_id]['content'];
                ?>
                <script type="text/javascript">scrollToEditor();</script>
                <?php
                $selected_start_time = $ad_list[$ad_id]['start_time'];
                $selected_end_time = $ad_list[$ad_id]['end_time'];
                break;
        
            case 'delete': 
                array_splice($ad_list, $ad_id, 1);
                update_option('ads', $ad_list);
                break;
            }
        }
?>
    <h1> Advertisment publisher</h1>
    <div class="wrap">
        <h1>Add/edit advertisment</h1>
        <form name="ad_form" id="ad_form" method="post">
                <?php if(isset($_POST['ad_id'])) echo ("<input type=\"hidden\" name=\"ad_id\"  value={$_POST['ad_id']}>");
                else echo ("<input type=\"hidden\" name=\"ad_id\" disabled>");
                ?>
        <?php
            wp_editor($editor_content, "ad_content", [])
        ?>
            <label for="ad_start_time">Start time:</label>
            <input type="time" id="ad_start_time" name="ad_start_time" value="<?php echo $selected_start_time ?>">
            <label for="ad_end_time">End time:</label>
            <input type="time" id="ad_end_time" name="ad_end_time" value="<?php echo $selected_end_time ?>">
            <input type="submit" value=<?php if(isset($_POST['ad_id'])) echo "Save"; else echo "Submit";?>>
            <input class="submit" type="reset" value="Reset">
        </form>
    </div>
    <h2>Advertisments</h2>
<?php
    $ad_list = get_option('ads');
    // var_dump($ad_list);
    for($i = 0; $i < count($ad_list); ++$i) {
        $content = $ad_list[$i]['content'];
        $start_time = $ad_list[$i]['start_time'];
        $end_time = $ad_list[$i]['end_time'];
        echo "<p> $content $start_time $end_time</p>";
        ?>
        <form name="ad_edit_delete_form" method="post">
            <input type="hidden" name="ad_id" value="<?php echo $i ?>">
            <button name="subject" value="delete">Delete</button>
            <button name="subject" value="edit">Edit</button>
        </form>
        <?php
    }
}


function add_random_ad_after_title($content){
    $ad_list = get_option('ads');
    $now = current_time('H:i');
    $matching_ads = array();

    for ($i = 0; $i < count($ad_list); ++$i) {
        $start_time = $ad_list[$i]['start_time'];
        $end_time = $ad_list[$i]['end_time'];
        if (($start_time <= $now && $end_time >= $now) || ($start_time <= $now && $end_time <= $start_time) || ($start_time >= $now && $end_time >= $now && $end_time <= $start_time)) {
            array_push($matching_ads, $ad_list[$i]);
        }
    }

    if (count($matching_ads) > 0) {
        $random_key = random_int(0, count($matching_ads) - 1 );
        return $content = '<div>' .$matching_ads[$random_key]['content'] . '</div> <br>' . $content;
    } else {
        return $content;
    }
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