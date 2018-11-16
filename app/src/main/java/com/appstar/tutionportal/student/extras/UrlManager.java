package com.appstar.tutionportal.student.extras;

public class UrlManager {

    //  public static final String BASE_URL = "http://192.168.0.115:8081/tuttor/";
    public static final String BASE_URL = "http://www.gload.in/CITUTApps/api/";
    public static final String GET_CATEGORIES = "api/all_categories";
    public static final String REGISTRATION = BASE_URL + "signup";
    //  public static final String GET_NEW_USER_OTP = BASE_URL + "api/get_new_user_otp";
    public static final String GET_NEW_USER_OTP = BASE_URL + "send-otp";
    public static final String GET_NEW_USER = BASE_URL + "Verify_phone";
    public static final String Login = BASE_URL + "getuserdata";
    public static final String Add_Institute = BASE_URL + "add_institute";
    public static final String GET_NEW_USER_OTP_login = BASE_URL + "login_verify_phone";
    public static final String ADD_CLASS = BASE_URL + "add_class";
    //  public static final String VERIFY_OTP = BASE_URL + "verify_otp.php";
    public static final String VERIFY_OTP = BASE_URL + "Otp_login";
    public static final String GET_CLASS = BASE_URL + "get-class";
    public static final String GET_SUBJECT = BASE_URL + "get-subject";
    public static final String GET_TEACHER_CLASS = BASE_URL + "get-teacher-detail";
    public static final String EDIT_TEACHER_PROFILE = BASE_URL + "update-personal-detail";
    public static final String UPDATE_TEACHER_PHONE = BASE_URL + "update-phone-sendotp";
    public static final String UPDATE_TEACHER_PHONE_OTP = BASE_URL + "update-phone-verifyotp";
    public static final String ADD_TEACHER_DETAI_MORE = BASE_URL + "update_teacher_detail";
    public static final String UPDATE_TEACHER_SERVICES = BASE_URL + "api/update_teacher_services";
    public static final String UPDATE_TEACHER_PROFILE_IMAGE = BASE_URL + "update-image";
    public static final String EDIT_TEACHER_CLASS = BASE_URL + "api/get_teacher_singleClass";
    public static final String ADD_CLASS_IMAGE = BASE_URL + "add-class-image";
    public static final String UPLOAD_TEACHER_IMAGE = BASE_URL + "api/upload_teacher_image";
    public static final String GET_TEACHER_CLASS_IMAGE = BASE_URL + "api/get_teacher_class_image";
    public static final String APP_URL = "";
    public static final String INSTITUTE_DATA = BASE_URL + "get-director-institute-branch";
    public static final String ADD_BRANCH = BASE_URL + "add_branch";
    public static final String ADD_SUBJECT_TEACHER = BASE_URL + "addsubject-to-teacher";
    public static final String GET_SUBECT_TO_TEACHER = BASE_URL + "get-teacher-to-subject";
    public static final String GET_TEACHER_DATA = BASE_URL + "get-teachers";
    public static final String GET_ALL_CLASSES_DIRECTOR = BASE_URL + "get-institute-classes";
    public static final String GET_ALL_BRANCH_CLASSES= BASE_URL + "get-branch-classes";
}
