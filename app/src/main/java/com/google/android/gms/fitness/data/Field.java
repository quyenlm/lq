package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.smtt.sdk.TbsListener;

public final class Field extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<Field> CREATOR = new zzq();
    public static final Field FIELD_ACCURACY = zzdc("accuracy");
    public static final Field FIELD_ACTIVITY = zzda("activity");
    public static final Field FIELD_ACTIVITY_CONFIDENCE = zzde("activity_confidence");
    public static final Field FIELD_ALTITUDE = new Field("altitude", 2, true);
    public static final Field FIELD_AVERAGE = zzdc("average");
    public static final Field FIELD_BPM = zzdc("bpm");
    public static final Field FIELD_CALORIES = zzdc(NUTRIENT_CALORIES);
    public static final Field FIELD_CIRCUMFERENCE = zzdc("circumference");
    public static final Field FIELD_CONFIDENCE = zzdc("confidence");
    public static final Field FIELD_DISTANCE = zzdc("distance");
    public static final Field FIELD_DURATION = zzda("duration");
    public static final Field FIELD_EXERCISE = zzdd("exercise");
    public static final Field FIELD_FOOD_ITEM = zzdd("food_item");
    public static final Field FIELD_HEIGHT = zzdc("height");
    public static final Field FIELD_HIGH_LATITUDE = zzdc("high_latitude");
    public static final Field FIELD_HIGH_LONGITUDE = zzdc("high_longitude");
    public static final Field FIELD_LATITUDE = zzdc("latitude");
    public static final Field FIELD_LONGITUDE = zzdc("longitude");
    public static final Field FIELD_LOW_LATITUDE = zzdc("low_latitude");
    public static final Field FIELD_LOW_LONGITUDE = zzdc("low_longitude");
    public static final Field FIELD_MAX = zzdc("max");
    public static final Field FIELD_MEAL_TYPE = zzda("meal_type");
    public static final Field FIELD_MIN = zzdc("min");
    public static final Field FIELD_NUM_SEGMENTS = zzda("num_segments");
    public static final Field FIELD_NUTRIENTS = zzde("nutrients");
    public static final Field FIELD_OCCURRENCES = zzda("occurrences");
    public static final Field FIELD_PERCENTAGE = zzdc("percentage");
    public static final Field FIELD_REPETITIONS = zzda("repetitions");
    public static final Field FIELD_RESISTANCE = zzdc("resistance");
    public static final Field FIELD_RESISTANCE_TYPE = zzda("resistance_type");
    public static final Field FIELD_REVOLUTIONS = zzda("revolutions");
    public static final Field FIELD_RPM = zzdc("rpm");
    public static final Field FIELD_SPEED = zzdc(TransferTable.COLUMN_SPEED);
    public static final Field FIELD_STEPS = zzda("steps");
    public static final Field FIELD_STEP_LENGTH = zzdc("step_length");
    public static final Field FIELD_VOLUME = zzdc(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME);
    public static final Field FIELD_WATTS = zzdc("watts");
    public static final Field FIELD_WEIGHT = zzdc("weight");
    public static final int FORMAT_FLOAT = 2;
    public static final int FORMAT_INT32 = 1;
    public static final int FORMAT_MAP = 4;
    public static final int FORMAT_STRING = 3;
    public static final int MEAL_TYPE_BREAKFAST = 1;
    public static final int MEAL_TYPE_DINNER = 3;
    public static final int MEAL_TYPE_LUNCH = 2;
    public static final int MEAL_TYPE_SNACK = 4;
    public static final int MEAL_TYPE_UNKNOWN = 0;
    public static final String NUTRIENT_CALCIUM = "calcium";
    public static final String NUTRIENT_CALORIES = "calories";
    public static final String NUTRIENT_CHOLESTEROL = "cholesterol";
    public static final String NUTRIENT_DIETARY_FIBER = "dietary_fiber";
    public static final String NUTRIENT_IRON = "iron";
    public static final String NUTRIENT_MONOUNSATURATED_FAT = "fat.monounsaturated";
    public static final String NUTRIENT_POLYUNSATURATED_FAT = "fat.polyunsaturated";
    public static final String NUTRIENT_POTASSIUM = "potassium";
    public static final String NUTRIENT_PROTEIN = "protein";
    public static final String NUTRIENT_SATURATED_FAT = "fat.saturated";
    public static final String NUTRIENT_SODIUM = "sodium";
    public static final String NUTRIENT_SUGAR = "sugar";
    public static final String NUTRIENT_TOTAL_CARBS = "carbs.total";
    public static final String NUTRIENT_TOTAL_FAT = "fat.total";
    public static final String NUTRIENT_TRANS_FAT = "fat.trans";
    public static final String NUTRIENT_UNSATURATED_FAT = "fat.unsaturated";
    public static final String NUTRIENT_VITAMIN_A = "vitamin_a";
    public static final String NUTRIENT_VITAMIN_C = "vitamin_c";
    public static final int RESISTANCE_TYPE_BARBELL = 1;
    public static final int RESISTANCE_TYPE_BODY = 6;
    public static final int RESISTANCE_TYPE_CABLE = 2;
    public static final int RESISTANCE_TYPE_DUMBBELL = 3;
    public static final int RESISTANCE_TYPE_KETTLEBELL = 4;
    public static final int RESISTANCE_TYPE_MACHINE = 5;
    public static final int RESISTANCE_TYPE_UNKNOWN = 0;
    public static final Field zzaUA = new Field("sensor_values", 6);
    private static Field zzaUj = zzdb("duration");
    private static Field zzaUk = zzde("activity_duration");
    public static final Field zzaUl = zzde("activity_duration.ascending");
    public static final Field zzaUm = zzde("activity_duration.descending");
    public static final Field zzaUn = new Field("google.android.fitness.StrideModel", 7);
    public static final Field zzaUo = zzdc("elevation.change");
    public static final Field zzaUp = zzde("elevation.gain");
    public static final Field zzaUq = zzde("elevation.loss");
    public static final Field zzaUr = zzdc("floors");
    public static final Field zzaUs = zzde("floor.gain");
    public static final Field zzaUt = zzde("floor.loss");
    public static final Field zzaUu = zzda("sensor_type");
    public static final Field zzaUv = zzda("sensor_types");
    public static final Field zzaUw = new Field("timestamps", 5);
    public static final Field zzaUx = zzda("sample_period");
    public static final Field zzaUy = zzda("num_samples");
    public static final Field zzaUz = zzda("num_dimensions");
    private final int format;
    private final String name;
    private final int versionCode;
    private final Boolean zzaUB;

    public static class zza {
        public static final Field zzaUC = Field.zzdc("x");
        public static final Field zzaUD = Field.zzdc("y");
        public static final Field zzaUE = Field.zzdc("z");
        public static final Field zzaUF = Field.zzdf("debug_session");
        public static final Field zzaUG = Field.zzdf("google.android.fitness.SessionV2");
    }

    Field(int i, String str, int i2, Boolean bool) {
        this.versionCode = i;
        this.name = (String) zzbo.zzu(str);
        this.format = i2;
        this.zzaUB = bool;
    }

    private Field(String str, int i) {
        this(2, str, i, (Boolean) null);
    }

    private Field(String str, int i, Boolean bool) {
        this(2, str, i, bool);
    }

    private static Field zzda(String str) {
        return new Field(str, 1);
    }

    static Field zzdb(String str) {
        return new Field(str, 1, true);
    }

    static Field zzdc(String str) {
        return new Field(str, 2);
    }

    private static Field zzdd(String str) {
        return new Field(str, 3);
    }

    private static Field zzde(String str) {
        return new Field(str, 4);
    }

    static Field zzdf(String str) {
        return new Field(str, 7, true);
    }

    public static Field zzm(String str, int i) {
        char c = 65535;
        switch (str.hashCode()) {
            case -2131707655:
                if (str.equals("accuracy")) {
                    c = 0;
                    break;
                }
                break;
            case -2083865430:
                if (str.equals("debug_session")) {
                    c = '\\';
                    break;
                }
                break;
            case -2006370880:
                if (str.equals("body_temperature_measurement_location")) {
                    c = 21;
                    break;
                }
                break;
            case -1992012396:
                if (str.equals("duration")) {
                    c = ' ';
                    break;
                }
                break;
            case -1859447186:
                if (str.equals("blood_glucose_level")) {
                    c = 8;
                    break;
                }
                break;
            case -1655966961:
                if (str.equals("activity")) {
                    c = 1;
                    break;
                }
                break;
            case -1595712862:
                if (str.equals("cervical_dilation")) {
                    c = 24;
                    break;
                }
                break;
            case -1579612127:
                if (str.equals("floor.gain")) {
                    c = '%';
                    break;
                }
                break;
            case -1579449403:
                if (str.equals("floor.loss")) {
                    c = '&';
                    break;
                }
                break;
            case -1569430471:
                if (str.equals("num_segments")) {
                    c = '6';
                    break;
                }
                break;
            case -1531570079:
                if (str.equals("elevation.change")) {
                    c = '!';
                    break;
                }
                break;
            case -1440707631:
                if (str.equals("oxygen_saturation")) {
                    c = ':';
                    break;
                }
                break;
            case -1439978388:
                if (str.equals("latitude")) {
                    c = ',';
                    break;
                }
                break;
            case -1352492506:
                if (str.equals("num_dimensions")) {
                    c = '4';
                    break;
                }
                break;
            case -1271636505:
                if (str.equals("floors")) {
                    c = '\'';
                    break;
                }
                break;
            case -1248595573:
                if (str.equals("supplemental_oxygen_flow_rate_average")) {
                    c = 'G';
                    break;
                }
                break;
            case -1221029593:
                if (str.equals("height")) {
                    c = ')';
                    break;
                }
                break;
            case -1220952307:
                if (str.equals("blood_pressure_measurement_location")) {
                    c = 14;
                    break;
                }
                break;
            case -1133736764:
                if (str.equals("activity_duration")) {
                    c = 3;
                    break;
                }
                break;
            case -1129337776:
                if (str.equals("num_samples")) {
                    c = '5';
                    break;
                }
                break;
            case -1110756780:
                if (str.equals("food_item")) {
                    c = '(';
                    break;
                }
                break;
            case -921832806:
                if (str.equals("percentage")) {
                    c = 'J';
                    break;
                }
                break;
            case -918978307:
                if (str.equals("cervical_position")) {
                    c = 28;
                    break;
                }
                break;
            case -810883302:
                if (str.equals(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME)) {
                    c = 'V';
                    break;
                }
                break;
            case -803244749:
                if (str.equals("blood_pressure_systolic")) {
                    c = 15;
                    break;
                }
                break;
            case -791592328:
                if (str.equals("weight")) {
                    c = 'X';
                    break;
                }
                break;
            case -631448035:
                if (str.equals("average")) {
                    c = 7;
                    break;
                }
                break;
            case -626344110:
                if (str.equals("high_longitude")) {
                    c = '+';
                    break;
                }
                break;
            case -619868540:
                if (str.equals("low_longitude")) {
                    c = '/';
                    break;
                }
                break;
            case -511934137:
                if (str.equals("sensor_values")) {
                    c = 'E';
                    break;
                }
                break;
            case -494782871:
                if (str.equals("high_latitude")) {
                    c = '*';
                    break;
                }
                break;
            case -452643911:
                if (str.equals("step_length")) {
                    c = 'R';
                    break;
                }
                break;
            case -437053898:
                if (str.equals("meal_type")) {
                    c = '1';
                    break;
                }
                break;
            case -277306353:
                if (str.equals("circumference")) {
                    c = 29;
                    break;
                }
                break;
            case -266093204:
                if (str.equals("nutrients")) {
                    c = '7';
                    break;
                }
                break;
            case -228366862:
                if (str.equals("oxygen_saturation_measurement_method")) {
                    c = '=';
                    break;
                }
                break;
            case -168965370:
                if (str.equals(NUTRIENT_CALORIES)) {
                    c = 23;
                    break;
                }
                break;
            case -126538880:
                if (str.equals("resistance_type")) {
                    c = 'M';
                    break;
                }
                break;
            case -28590302:
                if (str.equals("ovulation_test_result")) {
                    c = '9';
                    break;
                }
                break;
            case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                if (str.equals("x")) {
                    c = 'Y';
                    break;
                }
                break;
            case TbsListener.ErrorCode.THREAD_INIT_ERROR:
                if (str.equals("y")) {
                    c = 'Z';
                    break;
                }
                break;
            case TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR:
                if (str.equals("z")) {
                    c = '[';
                    break;
                }
                break;
            case 97759:
                if (str.equals("bpm")) {
                    c = 22;
                    break;
                }
                break;
            case 107876:
                if (str.equals("max")) {
                    c = '0';
                    break;
                }
                break;
            case 108114:
                if (str.equals("min")) {
                    c = '3';
                    break;
                }
                break;
            case 113135:
                if (str.equals("rpm")) {
                    c = 'O';
                    break;
                }
                break;
            case 66639641:
                if (str.equals("temporal_relation_to_sleep")) {
                    c = 'U';
                    break;
                }
                break;
            case 109641799:
                if (str.equals(TransferTable.COLUMN_SPEED)) {
                    c = 'P';
                    break;
                }
                break;
            case 109761319:
                if (str.equals("steps")) {
                    c = 'Q';
                    break;
                }
                break;
            case 112903913:
                if (str.equals("watts")) {
                    c = 'W';
                    break;
                }
                break;
            case 120904628:
                if (str.equals("sensor_types")) {
                    c = 'D';
                    break;
                }
                break;
            case 137365935:
                if (str.equals("longitude")) {
                    c = '-';
                    break;
                }
                break;
            case 198162679:
                if (str.equals("low_latitude")) {
                    c = '.';
                    break;
                }
                break;
            case 220648413:
                if (str.equals("blood_pressure_diastolic_average")) {
                    c = 11;
                    break;
                }
                break;
            case 248891292:
                if (str.equals("blood_glucose_specimen_source")) {
                    c = 9;
                    break;
                }
                break;
            case 286612066:
                if (str.equals("activity_duration.descending")) {
                    c = 5;
                    break;
                }
                break;
            case 288459765:
                if (str.equals("distance")) {
                    c = 31;
                    break;
                }
                break;
            case 306600408:
                if (str.equals("google.android.fitness.SessionV2")) {
                    c = ']';
                    break;
                }
                break;
            case 320627489:
                if (str.equals("cervical_mucus_texture")) {
                    c = 27;
                    break;
                }
                break;
            case 455965230:
                if (str.equals("activity_duration.ascending")) {
                    c = 4;
                    break;
                }
                break;
            case 475560024:
                if (str.equals("blood_pressure_systolic_max")) {
                    c = 17;
                    break;
                }
                break;
            case 475560262:
                if (str.equals("blood_pressure_systolic_min")) {
                    c = 18;
                    break;
                }
                break;
            case 581888402:
                if (str.equals("cervical_mucus_amount")) {
                    c = 26;
                    break;
                }
                break;
            case 623947695:
                if (str.equals("oxygen_saturation_average")) {
                    c = ';';
                    break;
                }
                break;
            case 738210934:
                if (str.equals("google.android.fitness.StrideModel")) {
                    c = 'S';
                    break;
                }
                break;
            case 784486594:
                if (str.equals("occurrences")) {
                    c = '8';
                    break;
                }
                break;
            case 811264586:
                if (str.equals("revolutions")) {
                    c = 'N';
                    break;
                }
                break;
            case 815736413:
                if (str.equals("oxygen_saturation_system")) {
                    c = '?';
                    break;
                }
                break;
            case 829251210:
                if (str.equals("confidence")) {
                    c = 30;
                    break;
                }
                break;
            case 833248065:
                if (str.equals("temporal_relation_to_meal")) {
                    c = 'T';
                    break;
                }
                break;
            case 883161687:
                if (str.equals("body_temperature")) {
                    c = 20;
                    break;
                }
                break;
            case 984367650:
                if (str.equals("repetitions")) {
                    c = 'K';
                    break;
                }
                break;
            case 998412730:
                if (str.equals("activity_confidence")) {
                    c = 2;
                    break;
                }
                break;
            case 1136011766:
                if (str.equals("sample_period")) {
                    c = 'B';
                    break;
                }
                break;
            case 1276952063:
                if (str.equals("blood_pressure_diastolic")) {
                    c = 10;
                    break;
                }
                break;
            case 1284575222:
                if (str.equals("oxygen_saturation_max")) {
                    c = '<';
                    break;
                }
                break;
            case 1284575460:
                if (str.equals("oxygen_saturation_min")) {
                    c = '>';
                    break;
                }
                break;
            case 1403812644:
                if (str.equals("blood_pressure_diastolic_max")) {
                    c = 12;
                    break;
                }
                break;
            case 1403812882:
                if (str.equals("blood_pressure_diastolic_min")) {
                    c = 13;
                    break;
                }
                break;
            case 1527920799:
                if (str.equals("sensor_type")) {
                    c = 'C';
                    break;
                }
                break;
            case 1708915229:
                if (str.equals("timestamps")) {
                    c = 'A';
                    break;
                }
                break;
            case 1857734768:
                if (str.equals("elevation.gain")) {
                    c = '\"';
                    break;
                }
                break;
            case 1857897492:
                if (str.equals("elevation.loss")) {
                    c = '#';
                    break;
                }
                break;
            case 1863800889:
                if (str.equals("resistance")) {
                    c = 'L';
                    break;
                }
                break;
            case 1880897007:
                if (str.equals("oxygen_therapy_administration_mode")) {
                    c = '@';
                    break;
                }
                break;
            case 1892583496:
                if (str.equals("menstrual_flow")) {
                    c = '2';
                    break;
                }
                break;
            case 1958191058:
                if (str.equals("supplemental_oxygen_flow_rate_max")) {
                    c = 'H';
                    break;
                }
                break;
            case 1958191296:
                if (str.equals("supplemental_oxygen_flow_rate_min")) {
                    c = 'I';
                    break;
                }
                break;
            case 1983072038:
                if (str.equals("body_position")) {
                    c = 19;
                    break;
                }
                break;
            case 2020153105:
                if (str.equals("blood_pressure_systolic_average")) {
                    c = 16;
                    break;
                }
                break;
            case 2036550306:
                if (str.equals("altitude")) {
                    c = 6;
                    break;
                }
                break;
            case 2056323544:
                if (str.equals("exercise")) {
                    c = '$';
                    break;
                }
                break;
            case 2072582505:
                if (str.equals("cervical_firmness")) {
                    c = 25;
                    break;
                }
                break;
            case 2078370221:
                if (str.equals("supplemental_oxygen_flow_rate")) {
                    c = 'F';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return FIELD_ACCURACY;
            case 1:
                return FIELD_ACTIVITY;
            case 2:
                return FIELD_ACTIVITY_CONFIDENCE;
            case 3:
                return zzaUk;
            case 4:
                return zzaUl;
            case 5:
                return zzaUm;
            case 6:
                return FIELD_ALTITUDE;
            case 7:
                return FIELD_AVERAGE;
            case 8:
                return HealthFields.FIELD_BLOOD_GLUCOSE_LEVEL;
            case 9:
                return HealthFields.FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE;
            case 10:
                return HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC;
            case 11:
                return HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC_AVERAGE;
            case 12:
                return HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC_MAX;
            case 13:
                return HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC_MIN;
            case 14:
                return HealthFields.FIELD_BLOOD_PRESSURE_MEASUREMENT_LOCATION;
            case 15:
                return HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC;
            case 16:
                return HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC_AVERAGE;
            case 17:
                return HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC_MAX;
            case 18:
                return HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC_MIN;
            case 19:
                return HealthFields.FIELD_BODY_POSITION;
            case 20:
                return HealthFields.FIELD_BODY_TEMPERATURE;
            case 21:
                return HealthFields.FIELD_BODY_TEMPERATURE_MEASUREMENT_LOCATION;
            case 22:
                return FIELD_BPM;
            case 23:
                return FIELD_CALORIES;
            case 24:
                return HealthFields.FIELD_CERVICAL_DILATION;
            case 25:
                return HealthFields.FIELD_CERVICAL_FIRMNESS;
            case 26:
                return HealthFields.FIELD_CERVICAL_MUCUS_AMOUNT;
            case 27:
                return HealthFields.FIELD_CERVICAL_MUCUS_TEXTURE;
            case 28:
                return HealthFields.FIELD_CERVICAL_POSITION;
            case 29:
                return FIELD_CIRCUMFERENCE;
            case 30:
                return FIELD_CONFIDENCE;
            case 31:
                return FIELD_DISTANCE;
            case ' ':
                return FIELD_DURATION;
            case '!':
                return zzaUo;
            case '\"':
                return zzaUp;
            case '#':
                return zzaUq;
            case '$':
                return FIELD_EXERCISE;
            case '%':
                return zzaUs;
            case '&':
                return zzaUt;
            case '\'':
                return zzaUr;
            case '(':
                return FIELD_FOOD_ITEM;
            case ')':
                return FIELD_HEIGHT;
            case '*':
                return FIELD_HIGH_LATITUDE;
            case '+':
                return FIELD_HIGH_LONGITUDE;
            case ',':
                return FIELD_LATITUDE;
            case '-':
                return FIELD_LONGITUDE;
            case '.':
                return FIELD_LOW_LATITUDE;
            case '/':
                return FIELD_LOW_LONGITUDE;
            case '0':
                return FIELD_MAX;
            case '1':
                return FIELD_MEAL_TYPE;
            case '2':
                return HealthFields.FIELD_MENSTRUAL_FLOW;
            case '3':
                return FIELD_MIN;
            case '4':
                return zzaUz;
            case '5':
                return zzaUy;
            case '6':
                return FIELD_NUM_SEGMENTS;
            case '7':
                return FIELD_NUTRIENTS;
            case '8':
                return FIELD_OCCURRENCES;
            case '9':
                return HealthFields.FIELD_OVULATION_TEST_RESULT;
            case ':':
                return HealthFields.FIELD_OXYGEN_SATURATION;
            case ';':
                return HealthFields.FIELD_OXYGEN_SATURATION_AVERAGE;
            case '<':
                return HealthFields.FIELD_OXYGEN_SATURATION_MAX;
            case '=':
                return HealthFields.FIELD_OXYGEN_SATURATION_MEASUREMENT_METHOD;
            case '>':
                return HealthFields.FIELD_OXYGEN_SATURATION_MIN;
            case '?':
                return HealthFields.FIELD_OXYGEN_SATURATION_SYSTEM;
            case '@':
                return HealthFields.FIELD_OXYGEN_THERAPY_ADMINISTRATION_MODE;
            case 'A':
                return zzaUw;
            case 'B':
                return zzaUx;
            case 'C':
                return zzaUu;
            case 'D':
                return zzaUv;
            case 'E':
                return zzaUA;
            case 'F':
                return HealthFields.FIELD_SUPPLEMENTAL_OXYGEN_FLOW_RATE;
            case 'G':
                return HealthFields.FIELD_SUPPLEMENTAL_OXYGEN_FLOW_RATE_AVERAGE;
            case 'H':
                return HealthFields.FIELD_SUPPLEMENTAL_OXYGEN_FLOW_RATE_MAX;
            case 'I':
                return HealthFields.FIELD_SUPPLEMENTAL_OXYGEN_FLOW_RATE_MIN;
            case 'J':
                return FIELD_PERCENTAGE;
            case 'K':
                return FIELD_REPETITIONS;
            case 'L':
                return FIELD_RESISTANCE;
            case 'M':
                return FIELD_RESISTANCE_TYPE;
            case 'N':
                return FIELD_REVOLUTIONS;
            case 'O':
                return FIELD_RPM;
            case 'P':
                return FIELD_SPEED;
            case 'Q':
                return FIELD_STEPS;
            case 'R':
                return FIELD_STEP_LENGTH;
            case 'S':
                return zzaUn;
            case 'T':
                return HealthFields.FIELD_TEMPORAL_RELATION_TO_MEAL;
            case 'U':
                return HealthFields.FIELD_TEMPORAL_RELATION_TO_SLEEP;
            case 'V':
                return FIELD_VOLUME;
            case 'W':
                return FIELD_WATTS;
            case 'X':
                return FIELD_WEIGHT;
            case 'Y':
                return zza.zzaUC;
            case 'Z':
                return zza.zzaUD;
            case '[':
                return zza.zzaUE;
            case '\\':
                return zza.zzaUF;
            case ']':
                return zza.zzaUG;
            default:
                return new Field(str, i, (Boolean) null);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Field) {
            Field field = (Field) obj;
            if (this.name.equals(field.name) && this.format == field.format) {
                return true;
            }
        }
        return false;
    }

    public final int getFormat() {
        return this.format;
    }

    public final String getName() {
        return this.name;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final Boolean isOptional() {
        return this.zzaUB;
    }

    public final String toString() {
        Object[] objArr = new Object[2];
        objArr[0] = this.name;
        objArr[1] = this.format == 1 ? "i" : APDataReportManager.MCARDVALUE_PRE;
        return String.format("%s(%s)", objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, getName(), false);
        zzd.zzc(parcel, 2, getFormat());
        zzd.zza(parcel, 3, isOptional(), false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zzI(parcel, zze);
    }
}
