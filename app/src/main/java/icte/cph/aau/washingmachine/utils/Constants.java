package icte.cph.aau.washingmachine.utils;


public class Constants {
    public static final String BASE_URL = "http://moneymover.dk/wm/";
    public static final String URL_WM_PIN = BASE_URL + "get_wm_pin.php";
    public static final String URL_MY_WM = BASE_URL + "get_my_wm.php";
    public static final String URL_INSERT_USER_ID = BASE_URL + "insert_user.php";
    public static final String URL_GET_CLOTHES = BASE_URL + "get_clothes.php";

    //Tags
    public static final String TAG_UID = "UID";
    public static final String TAG_FNAME = "fname";
    public static final String TAG_LNAME = "lname";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_RESULT = "result";
    public static final String TAG_CLOTHES = "clothes";
    public static final String TAG_WID = "WID";
    public static final String TAG_PIN = "pin";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_WMB_ID = "wmbID";
    public static final String TAG_WMB_NAME = "wmbName";

    //Clothes
    public static final String TAG_BRAND = "brand";
    public static final String TAG_COLOR = "color";
    public static final String TAG_SHADE = "shade";
    public static final String TAG_MATERIAL = "material";
    public static final String TAG_TEMPERATURE = "temperature";

    //Fragment tags
    public static final String FRAGMENT_TAG_WASHINGMACHINE_ID = "FRAGMENT_TAG_WASHINGMACHINE_ID";

    //Bundles
    public static final String BUNDLE_PIN = "BUNDLE_PIN";
    public static final String BUNDLE_WID = "BUNDLE_WID";

    //Intents
    public static final String INTENT_WID = "INTENT_WID";
    public static final String INTENT_WMS_ID = "INTENT_WMS_ID";
    public static final String INTENT_WMS_NAME = "INTENT_WMS_NAME";
    public static final String INTENT_WMS_BRAND = "INTENT_WMS_BRAND";


    //SharedPreferences
    public static final String SP_UID = "SP_UID";
    public static final String SP_WID = "SP_WID";
    public static final String SP_MY_WID = "SP_MY_WID";

    //Shades
    public static final String SHADE_WHITE = "White";
    public static final String SHADE_LIGHT = "Light";
    public static final String SHADE_NORMAL = "Normal";
    public static final String SHADE_DARK= "Dark";


}
