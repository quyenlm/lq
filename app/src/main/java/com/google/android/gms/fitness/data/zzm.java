package com.google.android.gms.fitness.data;

import com.google.android.gms.fitness.data.DataType;

public final class zzm {
    private static DataType[] zzaTY = {DataType.zzaTN, DataType.TYPE_WORKOUT_EXERCISE, DataType.TYPE_ACTIVITY_SAMPLE, DataType.TYPE_ACTIVITY_SAMPLES, DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY, HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_BLOOD_GLUCOSE, HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY, HealthDataTypes.TYPE_BLOOD_PRESSURE, HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY, DataType.TYPE_BODY_FAT_PERCENTAGE, DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY, DataType.zzaTR, DataType.zzaTT, HealthDataTypes.TYPE_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY, DataType.zzaTQ, DataType.zzaTU, DataType.TYPE_BASAL_METABOLIC_RATE, DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY, DataType.TYPE_CALORIES_CONSUMED, DataType.TYPE_CALORIES_EXPENDED, HealthDataTypes.TYPE_CERVICAL_MUCUS, HealthDataTypes.TYPE_CERVICAL_POSITION, DataType.TYPE_CYCLING_PEDALING_CADENCE, DataType.TYPE_CYCLING_PEDALING_CUMULATIVE, DataType.TYPE_CYCLING_WHEEL_REVOLUTION, DataType.TYPE_CYCLING_WHEEL_RPM, DataType.TYPE_DISTANCE_CUMULATIVE, DataType.TYPE_DISTANCE_DELTA, DataType.zzaTM, DataType.zzaTS, DataType.TYPE_HEART_RATE_BPM, DataType.AGGREGATE_HEART_RATE_SUMMARY, DataType.TYPE_HEIGHT, DataType.AGGREGATE_HEIGHT_SUMMARY, DataType.AGGREGATE_LOCATION_BOUNDING_BOX, DataType.TYPE_LOCATION_SAMPLE, DataType.TYPE_LOCATION_TRACK, HealthDataTypes.TYPE_MENSTRUATION, DataType.TYPE_NUTRITION, DataType.TYPE_HYDRATION, DataType.AGGREGATE_NUTRITION_SUMMARY, HealthDataTypes.TYPE_OVULATION_TEST, HealthDataTypes.TYPE_OXYGEN_SATURATION, HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY, DataType.TYPE_POWER_SAMPLE, DataType.AGGREGATE_POWER_SUMMARY, DataType.zzaTP, DataType.zzaTO, DataType.TYPE_SPEED, DataType.AGGREGATE_SPEED_SUMMARY, DataType.TYPE_STEP_COUNT_CADENCE, DataType.zzaTL, DataType.TYPE_STEP_COUNT_CUMULATIVE, DataType.TYPE_STEP_COUNT_DELTA, DataType.zzaTK, HealthDataTypes.TYPE_VAGINAL_SPOTTING, DataType.TYPE_WEIGHT, DataType.AGGREGATE_WEIGHT_SUMMARY};
    private static DataType[] zzaTZ = {HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_BLOOD_GLUCOSE, HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY, HealthDataTypes.TYPE_BLOOD_PRESSURE, HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY, HealthDataTypes.TYPE_BODY_TEMPERATURE, HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY, HealthDataTypes.TYPE_CERVICAL_MUCUS, HealthDataTypes.TYPE_CERVICAL_POSITION, HealthDataTypes.TYPE_MENSTRUATION, HealthDataTypes.TYPE_OVULATION_TEST, HealthDataTypes.TYPE_OXYGEN_SATURATION, HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY, HealthDataTypes.TYPE_VAGINAL_SPOTTING};

    public static DataType zzcZ(String str) {
        char c = 65535;
        switch (str.hashCode()) {
            case -2060095039:
                if (str.equals("com.google.cycling.wheel_revolution.rpm")) {
                    c = 29;
                    break;
                }
                break;
            case -2027664088:
                if (str.equals("com.google.calories.consumed")) {
                    c = 22;
                    break;
                }
                break;
            case -2023954015:
                if (str.equals("com.google.location.bounding_box")) {
                    c = '&';
                    break;
                }
                break;
            case -1939429191:
                if (str.equals("com.google.blood_glucose.summary")) {
                    c = 10;
                    break;
                }
                break;
            case -1783842905:
                if (str.equals("com.google.accelerometer")) {
                    c = 0;
                    break;
                }
                break;
            case -1757812901:
                if (str.equals("com.google.location.sample")) {
                    c = '\'';
                    break;
                }
                break;
            case -1659958877:
                if (str.equals("com.google.menstruation")) {
                    c = ')';
                    break;
                }
                break;
            case -1487055015:
                if (str.equals("com.google.body.temperature.basal.summary")) {
                    c = 7;
                    break;
                }
                break;
            case -1466904157:
                if (str.equals("com.google.floor_change.summary")) {
                    c = '!';
                    break;
                }
                break;
            case -1431431801:
                if (str.equals("com.google.height.summary")) {
                    c = '%';
                    break;
                }
                break;
            case -1248818137:
                if (str.equals("com.google.distance.delta")) {
                    c = 31;
                    break;
                }
                break;
            case -1196687875:
                if (str.equals("com.google.internal.session.v2")) {
                    c = '6';
                    break;
                }
                break;
            case -1102520626:
                if (str.equals("com.google.step_count.delta")) {
                    c = ':';
                    break;
                }
                break;
            case -1099695423:
                if (str.equals("com.google.activity.sample")) {
                    c = 2;
                    break;
                }
                break;
            case -1091068721:
                if (str.equals("com.google.height")) {
                    c = '$';
                    break;
                }
                break;
            case -1063046895:
                if (str.equals("com.google.step_length")) {
                    c = ';';
                    break;
                }
                break;
            case -922976890:
                if (str.equals("com.google.cycling.pedaling.cumulative")) {
                    c = 27;
                    break;
                }
                break;
            case -900592674:
                if (str.equals("com.google.cycling.pedaling.cadence")) {
                    c = 26;
                    break;
                }
                break;
            case -886569606:
                if (str.equals("com.google.location.track")) {
                    c = '(';
                    break;
                }
                break;
            case -777285735:
                if (str.equals("com.google.heart_rate.summary")) {
                    c = '#';
                    break;
                }
                break;
            case -661631456:
                if (str.equals("com.google.weight")) {
                    c = '>';
                    break;
                }
                break;
            case -424876584:
                if (str.equals("com.google.weight.summary")) {
                    c = '?';
                    break;
                }
                break;
            case -362418992:
                if (str.equals("com.google.body.temperature")) {
                    c = 16;
                    break;
                }
                break;
            case -217611775:
                if (str.equals("com.google.blood_glucose")) {
                    c = 9;
                    break;
                }
                break;
            case -185830635:
                if (str.equals("com.google.power.summary")) {
                    c = '1';
                    break;
                }
                break;
            case -177293656:
                if (str.equals("com.google.nutrition.summary")) {
                    c = ',';
                    break;
                }
                break;
            case -164586193:
                if (str.equals("com.google.activity.exercise")) {
                    c = 1;
                    break;
                }
                break;
            case -98150574:
                if (str.equals("com.google.heart_rate.bpm")) {
                    c = '\"';
                    break;
                }
                break;
            case -56824761:
                if (str.equals("com.google.calories.bmr")) {
                    c = 20;
                    break;
                }
                break;
            case -43729332:
                if (str.equals("com.google.body.hip.circumference")) {
                    c = 14;
                    break;
                }
                break;
            case 2484093:
                if (str.equals("com.google.body.waist.circumference")) {
                    c = 18;
                    break;
                }
                break;
            case 53773386:
                if (str.equals("com.google.blood_pressure.summary")) {
                    c = 12;
                    break;
                }
                break;
            case 269180370:
                if (str.equals("com.google.activity.samples")) {
                    c = 3;
                    break;
                }
                break;
            case 295793957:
                if (str.equals("com.google.sensor.events")) {
                    c = '3';
                    break;
                }
                break;
            case 296250623:
                if (str.equals("com.google.calories.bmr.summary")) {
                    c = 21;
                    break;
                }
                break;
            case 324760871:
                if (str.equals("com.google.step_count.cadence")) {
                    c = '8';
                    break;
                }
                break;
            case 378060028:
                if (str.equals("com.google.activity.segment")) {
                    c = 4;
                    break;
                }
                break;
            case 529727579:
                if (str.equals("com.google.power.sample")) {
                    c = '0';
                    break;
                }
                break;
            case 634821360:
                if (str.equals("com.google.sensor.const_rate_events")) {
                    c = '2';
                    break;
                }
                break;
            case 657433501:
                if (str.equals("com.google.step_count.cumulative")) {
                    c = '9';
                    break;
                }
                break;
            case 682891187:
                if (str.equals("com.google.body.fat.percentage")) {
                    c = 8;
                    break;
                }
                break;
            case 841663855:
                if (str.equals("com.google.activity.summary")) {
                    c = 5;
                    break;
                }
                break;
            case 877955159:
                if (str.equals("com.google.speed.summary")) {
                    c = '7';
                    break;
                }
                break;
            case 899666941:
                if (str.equals("com.google.calories.expended")) {
                    c = 23;
                    break;
                }
                break;
            case 936279698:
                if (str.equals("com.google.blood_pressure")) {
                    c = 11;
                    break;
                }
                break;
            case 946706510:
                if (str.equals("com.google.hydration")) {
                    c = '+';
                    break;
                }
                break;
            case 946938859:
                if (str.equals("com.google.stride_model")) {
                    c = '<';
                    break;
                }
                break;
            case 1098265835:
                if (str.equals("com.google.floor_change")) {
                    c = ' ';
                    break;
                }
                break;
            case 1111714923:
                if (str.equals("com.google.body.fat.percentage.summary")) {
                    c = 13;
                    break;
                }
                break;
            case 1214093899:
                if (str.equals("com.google.vaginal_spotting")) {
                    c = '=';
                    break;
                }
                break;
            case 1404118825:
                if (str.equals("com.google.oxygen_saturation")) {
                    c = '.';
                    break;
                }
                break;
            case 1439932546:
                if (str.equals("com.google.ovulation_test")) {
                    c = '-';
                    break;
                }
                break;
            case 1483133089:
                if (str.equals("com.google.body.temperature.basal")) {
                    c = 6;
                    break;
                }
                break;
            case 1524007137:
                if (str.equals("com.google.cycling.wheel_revolution.cumulative")) {
                    c = 28;
                    break;
                }
                break;
            case 1633152752:
                if (str.equals("com.google.nutrition")) {
                    c = '*';
                    break;
                }
                break;
            case 1674865156:
                if (str.equals("com.google.body.hip.circumference.summary")) {
                    c = 15;
                    break;
                }
                break;
            case 1819660853:
                if (str.equals("com.google.body.waist.circumference.summary")) {
                    c = 19;
                    break;
                }
                break;
            case 1921738212:
                if (str.equals("com.google.distance.cumulative")) {
                    c = 30;
                    break;
                }
                break;
            case 1925848149:
                if (str.equals("com.google.cervical_position")) {
                    c = 25;
                    break;
                }
                break;
            case 1975902189:
                if (str.equals("com.google.cervical_mucus")) {
                    c = 24;
                    break;
                }
                break;
            case 1980033842:
                if (str.equals("com.google.internal.session.debug")) {
                    c = '5';
                    break;
                }
                break;
            case 2051843553:
                if (str.equals("com.google.oxygen_saturation.summary")) {
                    c = '/';
                    break;
                }
                break;
            case 2053496735:
                if (str.equals("com.google.speed")) {
                    c = '4';
                    break;
                }
                break;
            case 2131809416:
                if (str.equals("com.google.body.temperature.summary")) {
                    c = 17;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return DataType.zzaTN;
            case 1:
                return DataType.TYPE_WORKOUT_EXERCISE;
            case 2:
                return DataType.TYPE_ACTIVITY_SAMPLE;
            case 3:
                return DataType.TYPE_ACTIVITY_SAMPLES;
            case 4:
                return DataType.TYPE_ACTIVITY_SEGMENT;
            case 5:
                return DataType.AGGREGATE_ACTIVITY_SUMMARY;
            case 6:
                return HealthDataTypes.TYPE_BASAL_BODY_TEMPERATURE;
            case 7:
                return HealthDataTypes.AGGREGATE_BASAL_BODY_TEMPERATURE_SUMMARY;
            case 8:
                return DataType.TYPE_BODY_FAT_PERCENTAGE;
            case 9:
                return HealthDataTypes.TYPE_BLOOD_GLUCOSE;
            case 10:
                return HealthDataTypes.AGGREGATE_BLOOD_GLUCOSE_SUMMARY;
            case 11:
                return HealthDataTypes.TYPE_BLOOD_PRESSURE;
            case 12:
                return HealthDataTypes.AGGREGATE_BLOOD_PRESSURE_SUMMARY;
            case 13:
                return DataType.AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY;
            case 14:
                return DataType.zzaTR;
            case 15:
                return DataType.zzaTT;
            case 16:
                return HealthDataTypes.TYPE_BODY_TEMPERATURE;
            case 17:
                return HealthDataTypes.AGGREGATE_BODY_TEMPERATURE_SUMMARY;
            case 18:
                return DataType.zzaTQ;
            case 19:
                return DataType.zzaTU;
            case 20:
                return DataType.TYPE_BASAL_METABOLIC_RATE;
            case 21:
                return DataType.AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY;
            case 22:
                return DataType.TYPE_CALORIES_CONSUMED;
            case 23:
                return DataType.TYPE_CALORIES_EXPENDED;
            case 24:
                return HealthDataTypes.TYPE_CERVICAL_MUCUS;
            case 25:
                return HealthDataTypes.TYPE_CERVICAL_POSITION;
            case 26:
                return DataType.TYPE_CYCLING_PEDALING_CADENCE;
            case 27:
                return DataType.TYPE_CYCLING_PEDALING_CUMULATIVE;
            case 28:
                return DataType.TYPE_CYCLING_WHEEL_REVOLUTION;
            case 29:
                return DataType.TYPE_CYCLING_WHEEL_RPM;
            case 30:
                return DataType.TYPE_DISTANCE_CUMULATIVE;
            case 31:
                return DataType.TYPE_DISTANCE_DELTA;
            case ' ':
                return DataType.zzaTM;
            case '!':
                return DataType.zzaTS;
            case '\"':
                return DataType.TYPE_HEART_RATE_BPM;
            case '#':
                return DataType.AGGREGATE_HEART_RATE_SUMMARY;
            case '$':
                return DataType.TYPE_HEIGHT;
            case '%':
                return DataType.AGGREGATE_HEIGHT_SUMMARY;
            case '&':
                return DataType.AGGREGATE_LOCATION_BOUNDING_BOX;
            case '\'':
                return DataType.TYPE_LOCATION_SAMPLE;
            case '(':
                return DataType.TYPE_LOCATION_TRACK;
            case ')':
                return HealthDataTypes.TYPE_MENSTRUATION;
            case '*':
                return DataType.TYPE_NUTRITION;
            case '+':
                return DataType.TYPE_HYDRATION;
            case ',':
                return DataType.AGGREGATE_NUTRITION_SUMMARY;
            case '-':
                return HealthDataTypes.TYPE_OVULATION_TEST;
            case '.':
                return HealthDataTypes.TYPE_OXYGEN_SATURATION;
            case '/':
                return HealthDataTypes.AGGREGATE_OXYGEN_SATURATION_SUMMARY;
            case '0':
                return DataType.TYPE_POWER_SAMPLE;
            case '1':
                return DataType.AGGREGATE_POWER_SUMMARY;
            case '2':
                return DataType.zzaTP;
            case '3':
                return DataType.zzaTO;
            case '4':
                return DataType.TYPE_SPEED;
            case '5':
                return DataType.zza.zzaTW;
            case '6':
                return DataType.zza.zzaTX;
            case '7':
                return DataType.AGGREGATE_SPEED_SUMMARY;
            case '8':
                return DataType.TYPE_STEP_COUNT_CADENCE;
            case '9':
                return DataType.TYPE_STEP_COUNT_CUMULATIVE;
            case ':':
                return DataType.TYPE_STEP_COUNT_DELTA;
            case ';':
                return DataType.zzaTK;
            case '<':
                return DataType.zzaTL;
            case '=':
                return HealthDataTypes.TYPE_VAGINAL_SPOTTING;
            case '>':
                return DataType.TYPE_WEIGHT;
            case '?':
                return DataType.AGGREGATE_WEIGHT_SUMMARY;
            default:
                return null;
        }
    }
}
