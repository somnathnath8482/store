package com.artix.store.Helper;

import static com.bumptech.glide.util.Preconditions.checkArgument;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.artix.store.R;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MethodClass {

    public static Dialog mDialog;
    public static LottieAlertDialog lottieAlertDialog;
    public static MethodClass methodClass;
    public static Activity m_activity;
    public static ImageView bttm_home_iv, bttm_notif_iv, bttm_search_iv, bttm_msg_iv, bttm_user_iv;

    public static String get_arrived_date(String hours) {

        final SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");

        Calendar actualDate = Calendar.getInstance();
        actualDate.add(Calendar.DAY_OF_MONTH, +Integer.parseInt(hours) / 24);
        Date date1 = actualDate.getTime();

        String date = sdf.format(actualDate.getTime());
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date1); // Thursday
        Log.d("nextdate", date);

        return hours.equals("24") ? "Tomorrow" : dayOfTheWeek + " " + date;
    }

    static String[] tempDay = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    public static String getDayOfWeek(int id) {
        return tempDay[id - 1];
    }

    public static int getDayOfWeek(String dayName) {
        for (int i = 0; i < tempDay.length; i++) {
            if (dayName.equals(tempDay[i])) {
                return i + 1;

            }
        }
        return 0;
    }
    /*public static boolean  get_result_from_webservice(final Activity activity, Error error) {

        if (error!=null){

            LottieAlertDialog lottieAlertDialog = new  LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)
                    .setPositiveButtonColor(Color.parseColor("#048B3A"))
                    .setNegativeButtonColor(Color.parseColor("#DA1606"))
                    .setNoneButtonColor(Color.parseColor("#038DFC"))
                    .setPositiveTextColor(Color.WHITE)
                    .setNegativeTextColor(Color.WHITE)
                    .setNoneTextColor(Color.WHITE)
                    .setTitle(error.getMessage())
                    .setDescription(error.getMeaning())
                    .setNoneText("OK")
                    .setNoneListener(new ClickListener() {
                        @Override
                        public void onClick(@NonNull LottieAlertDialog lottieAlertDialog) {
                            // activity.onBackPressed();
                            lottieAlertDialog.dismiss();
                        }
                    })
                    .build();

            lottieAlertDialog.setCancelable(false);
            lottieAlertDialog.setCanceledOnTouchOutside(false);
            lottieAlertDialog.show();


            return false;
        }


        return true;
    }
*/
    /*public static void set_toolbar(Activity activity, String title) {
        TextView title_tv = activity.findViewById(R.id.title_tv);
        ImageView back_iv = activity.findViewById(R.id.back_iv);
        RelativeLayout notification_layout = activity.findViewById(R.id.notification_layout);

        title_tv.setText(title);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activity.getLocalClassName().equals("Activity.NotificationActivity"))
                    go_to_next_activity(activity, NotificationActivity.class);
            }
        });
    }*/



    /*    public static void logout(Activity activity) {
            MethodClass.initialize_loader(activity);
            MethodClass.showProgressDialog(activity);
            String server_url = activity.getString(R.string.SERVER_URL) + "logout";
            Log.e("server_url", server_url);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, server_url, new JSONObject(), new General_Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("resp", response.toString());
                    try {
                        MethodClass.hideProgressDialog(activity);
                        JSONObject resultResponse = MethodClass.get_result_from_webservice(activity, response);
                        if (resultResponse != null) {
                            PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean("is_logged_in", false).commit();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                        }
                    } catch (Exception e) {
                        MethodClass.hideProgressDialog(activity);
                        MethodClass.error_alert(activity);
                        e.printStackTrace();
                        Log.e("JSONException", e.toString());
                    }
                }
            }, new General_Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MethodClass.hideProgressDialog(activity);
                    MethodClass.error_alert(activity);
                }
            }) {
                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("X-localization", PreferenceManager.getDefaultSharedPreferences(activity).getString("lang", "en"));
                    if (PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("is_logged_in", false)) {
                        headers.put("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(activity).getString("token", ""));
                    }
                    Log.e("getHeaders: ", headers.toString());
                    return headers;
                }
            };
            MySingleton.getInstance(activity).addToRequestQueue(request);
        }
    */

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public static void go_to_next_activity(Activity activity, Class next_activity) {
        activity.startActivity(new Intent(activity, next_activity));

    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = activity.getCurrentFocus();
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialize_loader(Activity activity) {
        mDialog = new Dialog(activity);
    }


    public static void hideProgressDialog(Activity activity) {
        try {
            if (mDialog != null && !activity.isFinishing()) {
                mDialog.dismiss();
                Log.e("hide_progress", "hideProgressDialog: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JSONObject Json_rpc_format(HashMap<String, String> params) {
       /* HashMap<String, Object> main_param = new HashMap<String, Object>();
        main_param.put("params", new JSONObject(params));
        main_param.put("jsonrpc", "2.0");
        Log.e("request", new JSONObject(main_param).toString());*/
        return new JSONObject(params);
    }

    public static HashMap<String, Object> Json_rpc_format_hashmap(HashMap<String, String> params) {
        HashMap<String, Object> main_param = new HashMap<String, Object>();
        main_param.put("jsonrpc", "2.0");
        main_param.put("params", params);
        Log.e("request", main_param.toString());
        return main_param;
    }

    public static JSONObject Json_rpc_format_obj(HashMap<String, Object> params) {
        HashMap<String, Object> main_param = new HashMap<String, Object>();
        main_param.put("params", new JSONObject(params));
        main_param.put("jsonrpc", "2.0");
        Log.e("request", new JSONObject(main_param).toString());
        return new JSONObject(main_param);
    }

    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static JSONObject get_result_from_webservice(final Activity activity, JSONObject response) {
        JSONObject result = null;

        if (response.has("code")) {
            try {
                int code = response.getInt("code");

                if (code==-1){
                    new LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)
                            .setTitle("Error")
                            .setDescription(response.getString("error"))
                            .setNoneText("OK")
                            .setNoneButtonColor(Color.parseColor("#0040ff"))
                            .setNoneListener(Dialog::dismiss).build().show();
                }
                else if (code==1){
                    result = response;

                }
                } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else {

            error_alert(activity);

        }
        return result;
    }

    public static String getRightAngleImage(String photoPath) {

        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }

            return rotateImage(degree, photoPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoPath;
    }

    public static String rotateImage(int degree, String imagePath) {

        if (degree <= 0) {
            return imagePath;
        }
        try {
            Bitmap b = BitmapFactory.decodeFile(imagePath);

            Matrix matrix = new Matrix();
            if (b.getWidth() > b.getHeight()) {
                matrix.setRotate(degree);
                b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
            }

            FileOutputStream fOut = new FileOutputStream(imagePath);
            String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);

            FileOutputStream out = new FileOutputStream(imagePath);
            if (imageType.equalsIgnoreCase("png")) {
                b.compress(Bitmap.CompressFormat.PNG, 100, out);
            } else if (imageType.equalsIgnoreCase("jpeg") || imageType.equalsIgnoreCase("jpg")) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
            fOut.flush();
            fOut.close();

            b.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public static void initialize_SweetAlertDialog(Activity activity, int dialog_type) {
        lottieAlertDialog = new LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR).build();
    }

    public static void network_error_alert(final Activity activity) {
        try {
            if (!activity.isFinishing()) {

                if (lottieAlertDialog.isShowing()) {
                    lottieAlertDialog.hide();
                }

                lottieAlertDialog.changeDialog(new LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)
                        .setPositiveButtonColor(Color.parseColor("#048B3A"))
                        .setNegativeButtonColor(Color.parseColor("#DA1606"))
                        .setNoneButtonColor(Color.parseColor("#038DFC"))
                        .setPositiveTextColor(Color.WHITE)
                        .setNegativeTextColor(Color.WHITE)
                        .setNoneTextColor(Color.WHITE)
                        .setTitle(activity.getResources().getString(R.string.network_error))
                        .setDescription(activity.getResources().getString(R.string.please_check_your_internet_connection))
                        .setNegativeText(activity.getResources().getString(R.string.settings))
                        .setPositiveText(activity.getResources().getString(R.string.ok))
                        .setNegativeListener(v -> {
                            v.hide();
                            activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 1);
                        }).setPositiveListener(v -> v.hide()));

                lottieAlertDialog.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void error_alert(final Activity activity) {
        try {
            if (!activity.isFinishing()) {

                if (lottieAlertDialog.isShowing()) {
                    lottieAlertDialog.hide();
                }

                lottieAlertDialog.changeDialog(new LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)
                        .setTitle(activity.getResources().getString(R.string.oops))
                        .setDescription(activity.getResources().getString(R.string.something_went_wrong))
                        .setNoneText(activity.getResources().getString(R.string.ok))
                        .setNoneListener(v -> {
                            v.hide();

                        }));

                lottieAlertDialog.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String checkNull(Object data) {
        if (Objects.equals(data, null) || Objects.equals(data, "null") || Objects.equals(data, "") || Objects.equals(data, "Select")) {
            return "";
        } else {
            return String.valueOf(data);
        }
    }

    public static String checkNull_1(Object data) {
        if (Objects.equals(data, null) || Objects.equals(data, "null") || Objects.equals(data, "") || Objects.equals(data, "Select")) {
            return "";
        } else {
            return " $" + data;
        }
    }

    public static String checkNull_2(Object data) {
        if (Objects.equals(data, null) || Objects.equals(data, "null") || Objects.equals(data, "") || Objects.equals(data, "Select")) {
            return "0.0";
        } else {
            return String.valueOf(data);
        }
    }

    /*  public static void set_terms_and_condition_tv(Activity activity, TextView prvcy_terms_tv) {

          //By clicking continuing, I agree to all Averyandeve\'s Privacy Policy and Terms of Service
          SpannableStringBuilder spanTxt = new SpannableStringBuilder("By clicking continuing, I agree to all Farfromadate ");
          spanTxt.append("Privacy Policy");
          spanTxt.setSpan(new ClickableSpan() {

              @Override
              public void updateDrawState(TextPaint ds) {
                  super.updateDrawState(ds);
                  //code for set color on linkable text
                  ds.setColor(ContextCompat.getColor(activity, R.color.navy_blue));
              }

              @Override
              public void onClick(@NonNull View widget) {

                  //the code after on click privacy policy
                  */
  /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quiz365.in/privacy-policy"));
                startActivity(browserIntent);*//*
                MethodClass.go_to_next_activity(activity, PrivacyPolicyActivity.class);

            }
        }, spanTxt.length() - "Privacy Policy".length(), spanTxt.length(), 0);

        spanTxt.append(" and ");
        spanTxt.append("Terms of Service");

        spanTxt.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //code for set color on linkable text
                ds.setColor(ContextCompat.getColor(activity, R.color.navy_blue));
            }

            @Override
            public void onClick(@NonNull View widget) {

                //the code after on click Terms of Use
                *//*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quiz365.in/terms-of-use"));
                startActivity(browserIntent);*//*
                MethodClass.go_to_next_activity(activity, TermsAndConditionActivity.class);


            }
        }, spanTxt.length() - "Terms of Service".length(), spanTxt.length(), 0);

        prvcy_terms_tv.setMovementMethod(LinkMovementMethod.getInstance());
        prvcy_terms_tv.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }
*/
    public static void set_bg_color(Activity activity, View view, int color) {
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(activity, color));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(ContextCompat.getColor(activity, color));

        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(ContextCompat.getColor(activity, color));
        }
    }


    public static String getHTMLText(String data) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT));
        } else {
            return String.valueOf(Html.fromHtml(data));
        }*/


        return  HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
    }

    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    /*  public static int getCategoryPosition(ArrayList<StringWithTag> ArrayList, Object id) {
          for (int i = 0; i < ArrayList.size(); i++) {
              if (ArrayList.get(i).value_id.equals(id)) {
                  return i;
              }
          }
          return 0;
      }
  */
    public static class StringWithTag {
        public String sting;
        public Object tag;

        public StringWithTag(String sting, Object tagPart) {
            this.sting = sting;
            this.tag = tagPart;
        }

        @Override
        public String toString() {
            return sting;
        }
    }

    public static long get_diff_between_two_dates_in_milisec(String date_str) {
        Log.e("date_str", "___" + date_str);
        Date current_date = Calendar.getInstance().getTime();
        Date from_date = null;
        long current_date_in_mil_sec = 0, from_date_in_mil_sec = 0, diff = 0;


        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));//set Kuwait time zone

        try {
            if (!Objects.equals(date_str, "") && !Objects.equals(date_str, "null") && !Objects.equals(date_str, null)) {
                from_date = inputFormat.parse(date_str);
            } else {
                from_date = current_date;
            }
            from_date_in_mil_sec = from_date.getTime();
            current_date_in_mil_sec = current_date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        diff = current_date_in_mil_sec - from_date_in_mil_sec;
        Log.e("diff", "_" + (diff / 60000));

        return diff;
    }


    public static String get_time_ago(String date_formate, String date_str) {

        Log.e("date_str", "___" + date_str);
        Date current_date = Calendar.getInstance().getTime();
        Date from_date = null;
        long current_date_in_mil_sec = 0, from_date_in_mil_sec = 0, diff = 0;


        String inputPattern = date_formate;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));//set india time zone

        try {
            if (!Objects.equals(date_str, "") && !Objects.equals(date_str, "null") && !Objects.equals(date_str, null)) {
                from_date = inputFormat.parse(date_str);
            } else {
                from_date = current_date;
            }
            from_date_in_mil_sec = from_date.getTime();
            current_date_in_mil_sec = current_date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        diff = current_date_in_mil_sec - from_date_in_mil_sec;

        long inSecTime = diff / 1000;
        long inMinTime = inSecTime / 60;
        String time_ago_str = "0 minute ago";
        try {
            int time_ago_in_min = 0;
            time_ago_in_min = Integer.parseInt(String.valueOf(inMinTime));
            if (time_ago_in_min >= 60) {
                int hour = 0, day = 0, year = 0;
                hour = time_ago_in_min / 60;

                if (hour >= 24) {

                    day = hour / 24;
                    if (day >= 365) {
                        year = day / 365;
                        if (year == 1)
                            time_ago_str = year + " year ago";
                        else
                            time_ago_str = year + " years ago";

                    } else {
                        if (day == 1)
                            time_ago_str = day + " day ago";
                        else
                            time_ago_str = day + " days ago";
                    }

                } else {
                    if (time_ago_in_min == 1)
                        time_ago_str = hour + " hour ago";
                    else
                        time_ago_str = hour + " hours ago";
                }

            } else {
                if (time_ago_in_min == 0 || time_ago_in_min == 1)
                    time_ago_str = time_ago_in_min + " minute ago";
                else
                    time_ago_str = time_ago_in_min + " minutes ago";

            }


        } catch (Exception e) {
            e.printStackTrace();

        }


        return time_ago_str;
    }

    public static String getDate(String inputPattern, String outputPattern, String data) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = inputFormat.parse(data);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ParseException", e.toString());
        }
        return data;
    }


    public static String getTime(String inputPattern, String data) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = inputFormat.parse(data);

            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");

            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ParseException", e.toString());
        }
        return data;
    }

    public static long getTime_Long(String inputPattern, String data) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = inputFormat.parse(data);

            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a");

            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ParseException", e.toString());
        }
        return 0;
    }


    /*public String getForegroundActivity() {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        return taskInfo.get(0).topActivity.getClassName();
        *//*ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.d("topActivity", "CURRENT Package ::" + componentInfo.getPackageName());
        return componentInfo.getPackageName();*//*
    }*/

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(Date date) {
        long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return "in the future";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "moments ago";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 2 * HOUR_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static <T> T defaultWhenNull(@Nullable T object, @NonNull T def) {
        if (object instanceof String) {
            if (((String) object).isEmpty()) {
                return def;
            }
        }
        return (object == null) ? def : object;
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return n + "th";
        }
        switch (n % 10) {
            case 1:
                return n + "st";
            case 2:
                return n + "nd";
            case 3:
                return n + "rd";
            default:
                return n + "th";
        }


    }

    public static String Convert24to12(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
            Log.d("convertedTime : ", convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }

    public static void openFile(Activity activity, File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void hide_keyboard(Activity activity) {
        try{
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = activity.getCurrentFocus();
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String dateConvertIntoMonth(String fromDate){
        String s="";
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = form.parse(fromDate);
            s=st_Nd_Rd_Th(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM");
        String newDateStr = postFormater.format(date);
        String full_date=s + " " + newDateStr;
        return full_date;
    }

    private static String st_Nd_Rd_Th(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int day=cal.get(Calendar.DATE);
        if (day >= 11 && day <= 13) {
            return day + "th";
        }
        switch (day % 10) {
            case 1:  return day + "st";
            case 2:  return day + "nd";
            case 3:  return day + "rd";
            default: return day + "th";
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            char[] initial = resName.toLowerCase(Locale.ROOT).toCharArray();
            String initial_char = "img_"+initial[0];

            Field idField = c.getDeclaredField(initial_char);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // This is for text with Drawable
    public static Drawable getTextDrawable(@ColorInt int iColor) {
        Shape shape = new Shape(){

            @Override
            public void draw(Canvas canvas, Paint paint)
            {
                paint.setColor(Color.BLUE);

                paint.setTextSize(100);

//                int radii = dpToPx(3);
                int radii = 3;

                canvas.drawText("Hello Canvas", canvas.getWidth() - 150, canvas.getHeight() / 2, paint);

                canvas.drawCircle(canvas.getWidth() - radii * 2, canvas.getHeight() /2 - radii, radii, paint);
            }
        };
        shape.getHeight();

        Drawable drawable = new ShapeDrawable(shape);

        return drawable;
    }
    public static  boolean IsDeletedUser(String status,Activity activity){

        if (status==null){
            return true;
        }
        if (status.equalsIgnoreCase("D")){

            LottieAlertDialog lottieAlertDialog = new  LottieAlertDialog.Builder(activity, DialogTypes.TYPE_ERROR)
                    .setPositiveButtonColor(Color.parseColor("#048B3A"))
                    .setNegativeButtonColor(Color.parseColor("#DA1606"))
                    .setNoneButtonColor(Color.parseColor("#038DFC"))
                    .setPositiveTextColor(Color.WHITE)
                    .setNegativeTextColor(Color.WHITE)
                    .setNoneTextColor(Color.WHITE)
                    .setTitle("User Is  Deleted")
                    .setDescription("You are trying to communicate with a deleted user , As the account is deleted you cannot Communicate with The User")
                    .setNoneText("OK")
                    .setNoneListener(new ClickListener() {
                        @Override
                        public void onClick(@NonNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();

                        }
                    })
                    .build();

            lottieAlertDialog.setCancelable(false);
            lottieAlertDialog.setCanceledOnTouchOutside(false);
            lottieAlertDialog.show();


            return false;
        }


        return true;

    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int printDifference(String sDate, String eDate) {
        //milliseconds

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(sDate);
            endDate = simpleDateFormat.parse(eDate);

            long different = endDate.getTime() - startDate.getTime();

            System.out.println("startDate : " + startDate);
            System.out.println("endDate : "+ endDate);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

        /*long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
*/
      /*  System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);*/
            return Math.toIntExact(elapsedDays);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return 0;

    }

    public static String CheckEmpty(EditText editText) throws Exception {
        if (editText.getText().toString().trim().equals("")){
            Toast.makeText(editText.getContext(), "Please "+editText.getHint(), Toast.LENGTH_SHORT).show();
            throw new Exception(editText.getId()+"");
        }
        else {
            return editText.getText().toString().trim();
        }
    }

}
