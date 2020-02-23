package com.beetalk.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.beetalk.sdk.ShareConstants;
import com.beetalk.sdk.helper.Helper;
import com.garena.android.beepost.service.BeePostRuntimeConfig;

public class SDKConstants {
    public static final String ANDROID_TEST_PURCHASED = "android.test.purchased";
    public static final String API_PAY_CHANNEL_SUCCESS_URL = "api/pay/channel/success";
    public static final String APPLICATION_ID_PROPERTY = "com.garena.sdk.applicationId";
    public static final String APPLICATION_ID_PROPERTY_FACEBOOK = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_PAYMENT_EXTERNAL = "com.garena.sdk.payment.ThirdPartyEnabled";
    public static final String APPLICATION_SOURCE_PROPERTY = "com.garena.sdk.ApplicationSourceId";
    public static final int AUTH_INSPECTION_TIME_INTERVAL = 3600;
    public static final int AUTH_REFRESH_TIME_INTERVAL = 172800;
    public static final String BUNDLE_REQUEST_KEY = "request_extra";
    public static final String BUNDLE_RESULT_KEY = "auth_result";
    public static final String CLIENT_ID_PROPERTY_GOOGLE = "com.garena.sdk.google_client_id";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_BUNDLE = "com.garena.msdk.game_launch_bundle";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_GAME_ID = "com.garena.msdk.game_launch_game_id";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_GAME_URI = "com.garena.msdk.game_launch_game_uri";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_MEDIA_TAG = "com.garena.msdk.game_launch_media_tag";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_OPEN_ID = "com.garena.msdk.game_launch_open_id";
    public static final String COM_GARENA_MSDK_GAME_LAUNCH_OPEN_ID_SOURCE = "com.garena.msdk.game_launch_open_id_source";
    public static boolean DEBUG_SHOW = false;
    public static Integer DEFAULT_ACTIVITY_FACEBOOK_REQUEST_CODE = 56227;
    public static Integer DEFAULT_ACTIVITY_GOOGLE_REQUEST_CODE = 56230;
    public static Integer DEFAULT_ACTIVITY_LAUNCH_REQUEST_CODE = 56221;
    public static Integer DEFAULT_ACTIVITY_LINE_REQUEST_CODE = 56229;
    public static Integer DEFAULT_ACTIVITY_VK_REQUEST_CODE = 56228;
    public static int DEFAULT_REQUEST_CODE = 4353;
    public static final String DOWNLOAD_PUBLICK_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmOpHFEEzXoi4EPRgZN5qUdXSW30PVsiqMblOWiURYh/bWVP8EIOYBHDGNR75gEjxGdP8LPeWVvQQ4WK9KGrMySrX2WP557Et1ZTpqfOYfjqKZJWyxzfzf/oruncNfD0x2OJlrV7gS9TqdaYyZLqT/jA68sNL0iCW1gEht3WeGdLXjwjD+UHPE0fYbWQmreAxwRgLjB7ppUH+dVk35cgjfl82bJIN5JUODrX/pVoHbpVW9phD8Cb5pLKy/H3J62He6pb7Sw/tDsfJ67mrEFynK07zZmts8Q1ntbIqD2H+GGAfkNqyRadG3xtiswUXJEORbZIgPrg4T+pWMzZb+5vt8wIDAQAB";
    public static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    public static final String GAS_ACTION_INSTALL_GAME_UPDATE = "com.garena.gas.intent.GAME_INSTALL";
    public static final String GAS_ACTION_SHARE = "com.garena.gas.intent.GAME_SHARE";
    public static final String GAS_AUTH_ACTIVITY_FQ_NAME = "com.garena.gameauth.GPProxyAuthActivity";
    public static final String GAS_CLASSPATH_PROXY = "com.garena.gameauth.GPProxyCommonActivity";
    public static final String GAS_DEBUG_SIGNATURE = "3082030b308201f3a00302010202047a0636e6300d06092a864886f70d01010b05003036311e301c060355040b1315476172656e61204f6e6c696e6520507464204c7464311430120603550403130b476172656e6120506c7573301e170d3134303831393130323431345a170d3135303831393130323431345a3036311e301c060355040b1315476172656e61204f6e6c696e6520507464204c7464311430120603550403130b476172656e6120506c757330820122300d06092a864886f70d01010105000382010f003082010a028201010084f9e11b108453d15322f95c388cb9f10deded1953a1b2343835345e2bdd2509f390014167000b4e73f1cf302b4d4d173f37eefa986fe7c235741adac823ec50d98f980d5f80c8f2d9e54e0d9113afbd6530b1272299f8b32966405af143041050bc8ce9010ca59ddab081bdc080f95818f7511a2e0f9d754bcfd9b4b9cd54cb08c41a9e42e27ee73689c35262267765379651e0837bec77fdeb8f69fc4ff71ac140d41f620fe0943e5cb1466fd764d3d6822ef8663ed9521b279ba55ed897c9b067dcd15799fd1e1e6783aeeafe3de47e6e004cebcc41cc7472e2d78d52095bea62f5db81e0653db2c08227248d0abfe2c243d49521b645549e1d9b40d2830d0203010001a321301f301d0603551d0e0416041431118e3818aaad6f0025d4724af957f6d9a82e11300d06092a864886f70d01010b050003820101007689d7b46da6e227ffc6fc29f2d8f78d1b79b8fc265745efb461c985eb160a248ddb54601b203b70817838743061f4d20dc0ec68fdd2e7d2e2f268a4626f643cd91126e6c5956bddaefdf6186c9e89a3d300ea9d55065161969acd92e061c2993d8cac35e6517c49942944d54083ddd4f4503018f88d1f5028725a10f1f188a020ee62d72a523c29dae1f93eb657bfe04855d29f83c8964942958b4fea9a5c82a9a4fe5820df5a606e5b950ee809178d167f3e651c474bc5507efa6d935f02dc37dee116f5ed2657b1b53d3087c0f291e333c6726557e9977d64626b7e49f1de3b8fcf8df1d81c09768658300d5b8f06a81ed56a819fe7a34beafcac50f39f5a";
    public static final String GAS_GOOGLE_PLAY_RELEASE_SIGNATURE = "308205873082036fa00302010202150085da7daa7fb5c103987bba339a00f89579bb359d300d06092a864886f70d01010b05003074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964301e170d3137303830333033333431325a170d3437303830333033333431325a3074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f696430820222300d06092a864886f70d01010105000382020f003082020a02820201009a34e999f1a0f4f2148b218cc057b60c9553ea1ca8ebdc2fcba8d781247e74d57455c9ffc32b14b2bc5b0345c003fd67f0aed8c86a497de486a537abbbd80551b3a8604dc3b07cc160a846ea2b4d2a996829d68405418e5756325623d6cde17b1a25a10c8a24acdbb07bd483eaa84718b71cb74894de35464571dcf84da4fe5709245a27763eebbc6580e85f749c46c509b5bd12ebaf9c9595b319778df08c7ff9a4c30b653d736b7b50134a5ee437d1dd3d93fe7c2ecd48318cf865d3c6b9440cea8e55c0ed26427540353c372e5df55a05fc66a4e8451b28f681f3ae90608a779a40336934daee6e0ab19dcdaea947592fb6fc2104fc9ae5a9bb3d1a65a50d5e6e77e4f9ffce6a881bc99dfe4bac6eef228f362b7b5ede79e9d487c31885a08380c1777abb8dd40cb08b2ed96671d12912337ae5ef808af421e402bcba3b8e1a1deb550f2e2c5b9de0b99eb7206385bc2ee1f5b9aa112cc6a92ae6e906d585b7b21a63db32b8417de6ce347dc582b5edfd7d216435b50bc4dafab09d45d0c8d55b4520891e7493880f860301f61b2b1aa40c943ea51784c18ed29256b5f83b4ed727a30e15d0788866de51272740079f437c52b9a52b24e95ce7257133ae2b7d886e89624387fc4f5990585e539ac708e088e2631dafa2432a73c4eb0f0cb5866138e674f1a422d7a6515fcd1d42fcd99dee81dbe092862077f882e32644410203010001a310300e300c0603551d13040530030101ff300d06092a864886f70d01010b050003820201008067799ce549eb1e16e6c0dc53df5c0b9d32db4649701aa57a7f655786bd53e39d6efb711be372a84f1e3a6724d016369ab45dfe73440fc8f758206b362e697a6da6d05b9cd243982dda6fe83c8fdae5f0e6e734867de6698dc172072b0ad8feaab32e33a543c7385ea79e47612242f3ad7d585738a0dee68bbec067f4904552e36c3e47e28fd87eeceb59b9bdc4e3582780c38ea6d3ea5d998076ddb84c054385a41fd18c4fb6242f990f5efd5c21f03aa77444207162276bd77a95510546c2ed653b2d5715f2c467f3808d8525a0ebe02fd9492803b1426bf423696262a493c54d87a069acf8a5917976d9b30dcbd0e60ad797a678a4faec00c34aa58d915efe861e197b832d171c1826e042761df310bf9919126e81e009319a985425539f73beced7a969569b54d0a768fc43cb58c9623e81fb5ba00cd1dc80807d84fa41e824e82ad9b5000b4ca0423b2380b274a7792559edb86d4934ee56a098f224e239a1d1bdcce4149bc0d7eadf977cd0467b777e723bfd14e241bc4adb21a508c2ed492f93959ae5b5695562ea99a4c58b8342f74ff702b4f3ddfecee1f330d44883de94da81d61f819a1541d9020d15510e05b7b9d8affa8266994b4843947ddb961c36336ab75d94cfecb9068f88c911144fd396a56a9d4a2977b6e7e458184b3049acdf975f0ae5ee18b4e7cc7159569bbbd3bc32af36f3fc5f1cb0c9d62c7b";
    public static final String GAS_LITE_PACKAGE = "com.garena.gaslite";
    public static final String GAS_PACKAGE = "com.garena.gas";
    public static final String GAS_RELEASE_SIGNATURE = "308203b53082029da00302010202041c5ce5b9300d06092a864886f70d01010b050030818a310b3009060355040613023635311230100603550408130953696e6761706f7265311230100603550407130953696e6761706f7265311e301c060355040a1315476172656e61204f6e6c696e6520507465204c7464311d301b060355040b1314536f66747761726520456e67696e656572696e67311430120603550403130b476172656e6120506c7573301e170d3134303831393130333330345a170d3339303831333130333330345a30818a310b3009060355040613023635311230100603550408130953696e6761706f7265311230100603550407130953696e6761706f7265311e301c060355040a1315476172656e61204f6e6c696e6520507465204c7464311d301b060355040b1314536f66747761726520456e67696e656572696e67311430120603550403130b476172656e6120506c757330820122300d06092a864886f70d01010105000382010f003082010a02820101008f7cec7d1529db09e6d8bb56b00ca4d2a9ca0ba1d3d17fe249ebcc39472cf1a5733cd9972f48bfc6e56c47d123d5d451ae15bff099d96ca22d9a3d9775e0d59b74a1b23cc7cc745fd29d5060a74d3a48246a82ddaac587a6cfffe0f264e16241b7a91bebd8db4589776e61b9916e20e0c749e205715930e9c54b5a5b5d7d14f3b27693eba1a3264eb0c0818ae11809b85bbe9e6099e641fb24940b534336fcc519e284e4d944a385732ae1a792025eadd49486a10a01b6d09c52bac1798d0d74c9e1ea07ae3b7ac20e0aee6ba9e23c402ae0cc047a8e11b96a47d4ebd4674d1e3c565bb5ca3ef8af9037df8f9d2e3b34b389cfe1fd4854b331f2735b6a8c01250203010001a321301f301d0603551d0e0416041466f6c0a324150c5c5b770186ba3e0d8efbec0e5f300d06092a864886f70d01010b0500038201010083e3ae177a46cf6a51b8f747f546d91a287db288c57553d36cbef7b7271e024cfba3f5b65b9b2fff74c25304da082bee95740ce61acf9bbbcc3004f976ca801244a1a62bcac998107b80f37c3b5b8361305817fa7ef2ba1c52d443228696bbd5d1220da5d5729dd034a01c2e702226d292e1eb00d3e554217934cb392623c06a15c9f0579f8e234f4ddf66077ee787953dc4a206dcef4799d9017b20df91b3098fa6ed640c51f2c215663f27a8dce98b6cf12d014522ad9be495a645cfffc66dac9bf984342161a4a60e06601622020ed9e49a3d886a408b89ae69e85bb903f0a38eb169ed7177a0848401862e96e6a8b6561d567311af414dc203686ea41ecf";
    private static final String GGLIVE_STREAM_PRODUCTION_URL = "https://stream.garena.tv";
    private static final String GGLIVE_STREAM_TEST_URL = "https://streamtv.test.garena.com";
    private static final String GGLIVE_WEB_PRODUCTION_URL = "https://garena.tv";
    private static final String GGLIVE_WEB_TEST_URL = "https://tv.test.garena.com";
    public static final String GG_EXTRA_CLIENT_ID = "com.garena.pay.android.extras.client_id";
    public static final String GG_EXTRA_PAY_APP_KEYHASH = "com.garena.pay.android.extras.key_hash";
    public static final String GG_EXTRA_PAY_CLIENT = "com.garena.pay.android.extras.pay_client";
    public static final String GG_EXTRA_PAY_REQUEST = "com.garena.pay.android.extras.pay_request";
    public static final String GG_EXTRA_RESULT_SERIALIZABLE = "com.garena.pay.android.extras.result";
    private static final String GOP_DEV_HOST = "https://devconnect.garena.com";
    public static final String LINE_PACKAGE = "jp.naver.line.android";
    public static final int MAX_IMG_DATA_LENGTH_BYTES = 500000;
    public static int MIN_BEETALK_VERSION_SUPPORT = ShareConstants.ERROR_CODE.GG_RESULT_NO_AUTH_HANDLER;
    public static int MIN_GAS_VERSION_SUPPORT = 19;
    public static final String NOTIFICATION_LARGE_LOGO_PROPERTY = "com.garena.sdk.push.large_logo";
    public static boolean NO_LOG = true;
    public static Integer OBTAIN_BIND_SESSION_REQUEST_CODE = 56300;
    private static final String PRODUCTION_HOST_FALLBACK = "https://connect.garena.com";
    private static final String PRODUCTION_HOST_FMT = "https://%s.connect.garena.com";
    public static String REDIRECT_URL_PREFIX = "gop";
    public static boolean RELEASE_VERSION = true;
    private static final String TEST_HOST = "https://testconnect.garena.com";
    public static final String TEST_PURCHASE_ITEM_ID = "9999";
    public static final String USER_CANCELLED = "user_cancelled";
    public static final String VK_PACKAGE = "com.vkontakte.android";
    public static final int WEB_VIEW_MARGIN = 20;
    private static boolean gopDevSandboxMode = false;
    private static GGEnvironment sandboxMode = GGEnvironment.TEST;

    public interface AutoLoginStatus {
        public static final int FAIL = 1;
        public static final int SUCCESS = 0;
    }

    public interface CHANNEL_SOURCE {
        public static final Integer GOOGLE_PLAY = 2;
    }

    public static final class DEBUG {
        public static boolean FORCE_FB_REFRESH = false;
        public static boolean TEST_REFRESH_TOKEN_FAIL = false;
    }

    public interface ERR_GOP_PAY_COMMIT {
        public static final String DUPLICATE_TRANSACTION = "error_duplicate_txn";
        public static final String ERR_COMMIT_TIMEOUT = "error_commit_request_timeout";
        public static final String ERR_SUBSCRIPTION_BINDING = "error_subscription_binding";
        public static final String ERR_UNKNOWN = "error_unknown";
    }

    public interface ErrorFlags {
        public static final String AUTH_ERROR = "auth_error";
        public static final String ERROR_USER_BAN = "error_user_ban";
        public static final String INVALID_TOKEN = "invalid_grant";
        public static final String SERVER_ERROR = "server_error";
    }

    public interface FLOATING_MENU {
        public static final String ACTION_GARENA_DEEPLINK = "garena://deeplink";
        public static final String ACTION_HIDE = "msdk://overlay/action/hide";
        public static final String ACTION_SCREEN_RECORD = "msdk://overlay/action/record_screen";
        public static final int FLAG_FORCE_EMBEDDED = 1;
    }

    public enum GGEnvironment {
        PRODUCTION,
        TEST
    }

    public interface GGSHARE_ACTION {
        public static final String BT_SNS_JUMP_APP = "";
        public static final String BT_SNS_JUMP_SHOW_RANK = "";
        public static final String BT_SNS_JUMP_URL = "";
    }

    public interface GG_SHARE_SCENE {
        public static final int BUZZ = 1;
        public static final int CHAT = 0;
    }

    public interface PAYMENT_CHANNEL_FLAG {
        public static final int HOT = 2;
        public static final int NEW = 1;
        public static final int SALE = 4;
    }

    public interface PLUGIN_KEYS {
        public static final String APP_FEEDBACK = "app.feedback";
        public static final String APP_FRIEND_REQUEST = "app.request.friend";
        public static final String BEEPOST_DELETE_TAGS = "beepost.delete.tags";
        public static final String BEEPOST_SET_TAGS = "beepost.set.tags";
        public static final String FACEBOOK_GAME_MESSAGE = "facebook.game.message";
        public static final String FACEBOOK_GRAPH_SHARE = "facebook.share.graph";
        public static final String FACEBOOK_INVITE = "facebook.invite";
        public static final String FACEBOOK_MESSAGE = "facebook.message";
        public static final String FACEBOOK_MESSENGER_SEND_IMAGE = "facebook.messenger.send.image";
        public static final String FACEBOOK_REQUEST_ME = "facebook.request.me";
        public static final String FACEBOOK_SHARE = "facebook.share";
        public static final String FACEBOOK_SHARE_FALLBACK = "facebook.share.fallback";
        public static final String GAS_SHARE_PHOTO = "gas.share.photo";
        public static final String GAS_SHARE_TEXT = "gas.share.text";
        public static final String GAS_SHARE_URL = "gas.share.url";
        public static final String GET_FRIEND_INFO = "friend.info.get";
        public static final String GET_INAPP_FRIEND_ID_LIST = "friend.inapp.id.list";
        public static final String GET_USER_FRIEND_ID_LIST = "msdk.user.friend.id.list";
        public static final String GET_USER_INFO = "msdk.user.info.get";
        public static final String GGLIVE_GET_CHANNEL_INFO = "gglive.get.channel.info";
        public static final String GGLIVE_HEARTBEAT = "gglive.heartbeat";
        public static final String GGLIVE_INIT_CHANNEL_STREAM = "gglive.init.channel.stream";
        public static final String GGLIVE_LOGIN = "gglive.login";
        public static final String GGLIVE_LOGOUT = "gglive.logout";
        public static final String GGLIVE_REGISTER_CHANNEL = "gglive.register.channel";
        public static final String GGLIVE_STOP_CHANNEL_STREAM = "gglive.stop.channel.stream";
        public static final String GGLIVE_UPDATE_CHANNEL_INFO = "gglive.update.channel.info";
        public static final String GGLIVE_VERIFY_CHANNEL_STREAM = "gglive.verify.channel.stream";
        public static final String LINE_SHARE = "line.share";
        public static final String LOAD_FRIEND_GROUPS_LIST = "msdk.load.friendgroups.list";
        public static final String LOAD_GROUP_FRIEND_INFO = "msdk.group.friend.info";
        public static final String LOAD_INAPP_FRIEND_GROUPS_LIST = "msdk.load.inapp.friendgroups.list";
        public static final String VK_SHARE = "vk.share";
    }

    public interface PLUGIN_REQUEST_CODES {
        public static final Integer APP_FRIEND_REQUEST = 2901;
        public static final Integer BEEPOST_DELETE_TAGS_PLUGIN = 2893;
        public static final Integer BEEPOST_SET_TAGS_PLUGIN = 2892;
        public static final Integer FACEBOOK_GAME_MESSAGE_PLUGIN = 2900;
        public static final Integer FACEBOOK_MESSAGE_PLUGIN = 2884;
        public static final Integer FACEBOOK_MESSENGER_SEND_IMAGE_PLUGIN = 2902;
        public static final Integer FB_FALLBACK_SHARE = 2881;
        public static final Integer FB_GRAPH_SHARE = 2882;
        public static final Integer FB_INVITE_PLUGIN = 2883;
        public static final Integer FB_REQUEST_ME = 2885;
        public static final Integer FB_SHARE_PLUGIN = 2880;
        public static final Integer FEEDBACK = 2891;
        public static final Integer GAS_SHARE_PHOTO = 2895;
        public static final Integer GAS_SHARE_TEXT = 2894;
        public static final Integer GAS_SHARE_URL = 2896;
        public static final Integer GET_FRIEND_INFO = 2888;
        public static final Integer GET_INAPP_FRIEND_ID_LIST = 2889;
        public static final Integer GET_USER_FRIEND_ID_LIST = 2879;
        public static final Integer GET_USER_INFO = 2878;
        public static final Integer LOAD_FRIEND_GROUPS_LIST = 2897;
        public static final Integer LOAD_GROUP_FRIEND_INFO = 2899;
        public static final Integer LOAD_INAPP_FRIEND_GROUPS_LIST = 2898;
    }

    public interface PaymentProvider {
        public static final String GARENA = "com.garena";
        public static final String GOOGLE_IAB = "com.google.inappbilling";
        public static final int GOOGLE_PROVIDER_ID = 201069;
    }

    public interface Platform {
        public static final int ANDROID = 2;
        public static final int IOS = 1;
    }

    public interface SERVER_ERRORS {
        public static final String ERROR_PARAMS = "error_params";
        public static final String ERROR_SCOPE = "error_scope";
        public static final String ERROR_TOKEN = "error_token";
        public static final String SERVER_ERROR = "server_error";
    }

    public interface SHARE_BUNDLE_TAG {
        public static final String COM_GARENA_MSDK_GAME_SDK_ENV = "com.garena.msdk.share.SDKEnv";
        public static final String COM_GARENA_MSDK_GAME_SDK_VERSION = "com.garena.msdk.share.SDKVer";
        public static final String COM_GARENA_MSDK_GAME_SHARE_ACTION = "com.garena.msdk.share.MessageExt";
        public static final String COM_GARENA_MSDK_GAME_SHARE_AUTO_AUTH = "com.garena.msdk.share.AutoAuth";
        public static final String COM_GARENA_MSDK_GAME_SHARE_DESC = "com.garena.msdk.share.Description";
        public static final String COM_GARENA_MSDK_GAME_SHARE_GAME_ID = "com.garena.msdk.share.GameId";
        public static final String COM_GARENA_MSDK_GAME_SHARE_IMG_DATA = "com.garena.msdk.share.ImgData";
        public static final String COM_GARENA_MSDK_GAME_SHARE_IMG_DATA_LEN = "com.garena.msdk.share.ImgDataLength";
        public static final String COM_GARENA_MSDK_GAME_SHARE_ITEM_TYPE = "com.garena.msdk.share.ItemType";
        public static final String COM_GARENA_MSDK_GAME_SHARE_MEDIA_TAG_NAME = "com.garena.msdk.share.MediaTagName";
        public static final String COM_GARENA_MSDK_GAME_SHARE_OPENID = "com.garena.msdk.share.OpenId";
        public static final String COM_GARENA_MSDK_GAME_SHARE_SHARE_TO = "com.garena.msdk.share.ShareTo";
        public static final String COM_GARENA_MSDK_GAME_SHARE_TITLE = "com.garena.msdk.share.Title";
        public static final String COM_GARENA_MSDK_GAME_SHARE_URL = "com.garena.msdk.share.Url";
    }

    public interface SUBSCRIPTION_PERIOD {
        public static final int INVALID = 0;
        public static final int MONTH = 2;
        public static final int SIX_MONTH = 4;
        public static final int THREE_MONTH = 3;
        public static final int WEEK = 1;
        public static final int YEAR = 5;
    }

    public interface SUBSCRIPTION_STATUS {
        public static final int ACTIVE = 0;
        public static final int CANCELLED = 2;
        public static final int EXPIRED = 1;
        public static final int NONE = -1;
        public static final int REVOKED = 3;
        public static final int SCHEDULED = 5;
        public static final int TRANSFERRED = 4;
    }

    public interface ShareErrorCode {
        public static final int INVALID_INFO = 2;
        public static final int NO_ERROR = 0;
        public static final int UNKNOWN = 3;
        public static final int UN_AUTHED = 1;
    }

    public interface ShareResultCode {
        public static final int CANCELED = 0;
        public static final int SHARED = 1;
    }

    public interface UPDATE_INFO_FLAG {
        public static final int DELAY_UPDATE = 512;
        public static final int FORCE_UPDATE = 32;
        public static final int INSTALLER_UPDATE = 2;
        public static final int OFFICIAL_UPDATE = 1;
        public static final int PROMOTE_UPDATE = 16;
        public static final int SILENT_DOWNLOAD = 256;
        public static final int UPDATE_NEXT_LAUNCH = 1024;
    }

    public interface VERSION {
        public static final String VERSION_NAME = "4.0.2";
    }

    public interface WEB_JS_CMD {
        public static final String CMD_GET_IMSI = "getImsi";
        public static final String CMD_REQUEST_PERMISSION_SETTING = "requestPermissionSetting";
        public static final String CMD_SEND_SMS = "sendSms";
        public static final String CMD_SEND_SMS_INAPP = "sendSmsInApp";
    }

    public interface WEB_JS_RESULT {
        public static final int RESULT_FAIL = 1;
        public static final int RESULT_SUCCESS = 0;
    }

    public interface WEB_PAY {
        public static final String EXTRA_AMOUNT = "app_point_amount";
        public static final String EXTRA_ERROR = "error";
        public static final String EXTRA_ERROR_CODE = "extra_error_code";
        public static final String EXTRA_ICON = "item_icon";
        public static final String EXTRA_NAME = "item_name";
        public static final String EXTRA_REBATE_CARD_ID = "rebate_card_id";
        public static final String EXTRA_REMAINING_DAYS = "remaining_days";
        public static final String EXTRA_TXN_ID = "txn_id";
    }

    public static GGEnvironment getEnvironment() {
        return sandboxMode;
    }

    public static String getUserFriendIDListUrl() {
        return getRootUrl() + "/oauth/user/friends/get";
    }

    public static String getInAppFriendIDListUrl() {
        return getRootUrl() + "/oauth/user/friends/inapp/get";
    }

    public static String getLoadFriendGroupUrl() {
        return getRootUrl() + "/oauth/user/friends/get/v2";
    }

    public static String getLoadInAppFriendGroupUrl() {
        return getRootUrl() + "/oauth/user/friends/inapp/get/v2";
    }

    public static String getLoadGroupFriendsInfoUrl() {
        return getRootUrl() + "/oauth/user/friends/info/get/v2";
    }

    public static String getAppConfigURL() {
        return sandboxMode == GGEnvironment.TEST ? "https://testconnect.garenanow.com/app/info/get" : "https://connect.garenanow.com/app/info/get";
    }

    public static String getRegisterGuestAccountURL() {
        return getRootUrl() + "/oauth/guest/register";
    }

    public static String getGuestGrantTokenURL() {
        return getRootUrl() + "/oauth/guest/token/grant";
    }

    public static String getBindGuestAccountURL() {
        return getRootUrl() + "/game/guest/bind";
    }

    public static String getSsoURL() {
        if (sandboxMode == GGEnvironment.TEST) {
            return gopDevSandboxMode ? "https://devsso.garena.com" : "https://testsso.garena.com";
        }
        return "https://sso.garena.com";
    }

    public static void setSandboxMode(GGEnvironment mode) {
        sandboxMode = mode;
        __init(mode);
    }

    public static void setGopDevSandboxMode(boolean enabled) {
        gopDevSandboxMode = enabled;
    }

    private static String getRootUrl() {
        Context context;
        if (sandboxMode != GGEnvironment.TEST) {
            String appId = null;
            GGLoginSession session = GGLoginSession.getCurrentSession();
            if (session != null) {
                appId = session.getApplicationId();
            }
            if (TextUtils.isEmpty(appId) && (context = GGLoginSession.getApplicationContext()) != null) {
                appId = Helper.getMetaDataAppId(context);
            }
            if (TextUtils.isEmpty(appId)) {
                return PRODUCTION_HOST_FALLBACK;
            }
            return String.format(PRODUCTION_HOST_FMT, new Object[]{appId});
        } else if (gopDevSandboxMode) {
            return GOP_DEV_HOST;
        } else {
            return TEST_HOST;
        }
    }

    private static void __init(GGEnvironment mode) {
        if (mode == GGEnvironment.TEST) {
            BeePostRuntimeConfig.SandboxMode = true;
            BeePostRuntimeConfig.LogEnabled = true;
            return;
        }
        BeePostRuntimeConfig.SandboxMode = false;
        BeePostRuntimeConfig.LogEnabled = false;
    }

    public static String getAuthServerExchangeTokenUrl() {
        return getRootUrl() + "/oauth/token/exchange";
    }

    public static String getUserInfoUrl() {
        return getRootUrl() + "/oauth/user/info/get";
    }

    public static String getAuthRefreshTokenUrl() {
        return getRootUrl() + "/oauth/token";
    }

    public static String getTokenInspectUrl() {
        return getRootUrl() + "/oauth/token/inspect";
    }

    public static String getLogoutUrl() {
        return getRootUrl() + "/oauth/logout";
    }

    public static String getServerSendRequest() {
        return getRootUrl() + "/game/user/request/send";
    }

    public static String getFeedbackServerUrl() {
        return "https://connect.garenanow.com/app/feedback";
    }

    public static String getAuthFacebookTokenExchangeUrl() {
        return getRootUrl() + "/oauth/token/facebook/exchange";
    }

    public static String getAuthVkTokenExchangeUrl() {
        return getRootUrl() + "/oauth/token/vk/exchange/v2";
    }

    public static String getAuthLineTokenExchangeUrl() {
        return getRootUrl() + "/oauth/token/line/exchange";
    }

    public static String getAuthGoogleTokenExchangeUrl() {
        return getRootUrl() + "/oauth/token/google/exchange";
    }

    public static String getGarenaOAuthUrl() {
        return getRootUrl() + "/oauth/login?";
    }

    public static String getGarenaOAuthRedirectUrl() {
        return getRootUrl() + "/oauth/garena?";
    }

    public static String getFriendsInfoFromOpenId() {
        return getRootUrl() + "/oauth/user/friends/info/get";
    }

    public static String getGGLiveStreamApiUrl() {
        return sandboxMode == GGEnvironment.TEST ? GGLIVE_STREAM_TEST_URL : GGLIVE_STREAM_PRODUCTION_URL;
    }

    public static String getGGLiveWebApiUrl() {
        return sandboxMode == GGEnvironment.TEST ? GGLIVE_WEB_TEST_URL : GGLIVE_WEB_PRODUCTION_URL;
    }

    public static String getGGLiveGetChannelInfoUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_info_get";
    }

    public static String getGGLiveUpdateChannelInfoUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_info_update";
    }

    public static String getGGLiveGetSessionTokenUrl() {
        return getGGLiveStreamApiUrl() + "/api/session_token_get";
    }

    public static String getGGLiveGetRefreshTokenUrl() {
        return getGGLiveStreamApiUrl() + "/api/refresh_token_get";
    }

    public static String getGGLiveRegisterChannelUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_register";
    }

    public static String getGGLiveInitChannelStreamUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_stream_init";
    }

    public static String getGGLiveStopChannelStreamUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_stream_stop";
    }

    public static String getGGLiveVerifyChannelStreamUrl() {
        return getGGLiveStreamApiUrl() + "/api/channel_stream_verify";
    }

    public static String getGGLiveHeartbeatUrl() {
        return getGGLiveStreamApiUrl() + "/api/heartbeat";
    }

    public static String getGGLiveGetAccountInfoUrl() {
        return getGGLiveWebApiUrl() + "/api/account_info_get";
    }

    public static void setDebugMode(boolean mode) {
        NO_LOG = !mode;
        DEBUG_SHOW = mode;
    }

    public static String getAPIHost() {
        return getRootUrl();
    }

    public static String getRootPayAPIUrl() {
        return getRootUrl() + "/pay";
    }

    public static String getPointBalanceURL() {
        return getRootUrl() + "/app/point/get_balance";
    }

    public static String getChannelPayUrl() {
        return getRootPayAPIUrl() + "/channel/pay/%d/%s";
    }

    public static String getChannelsGetUrl() {
        return getRootPayAPIUrl() + "/options/get";
    }

    public static String getInitGooglePayUrl() {
        return getRootPayAPIUrl() + "/google/init";
    }

    public static String getGoogleCommitPayUrl() {
        return getRootPayAPIUrl() + "/google/commit";
    }

    public static String getRedeemURL() {
        return getRootPayAPIUrl() + "/rebates/redeem";
    }

    public static String getRebateOptionsUrl() {
        return getRootPayAPIUrl() + "/rebate/options/get";
    }
}
