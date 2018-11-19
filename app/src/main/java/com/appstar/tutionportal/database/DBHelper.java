package com.appstar.tutionportal.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appstar.common.model.Address;
import com.appstar.tutionportal.Model.DirectorDetail;
import com.appstar.tutionportal.Model.StudentDetail;
import com.appstar.tutionportal.Model.TeacherDetail;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tutter";
    private static final int DB_VERSION = 1;
    private final String TEACHER_TABLE = "Teacher_Detail";
    private final String STUDENT_TABLE = "Student_Detail";
    private final String DIRECTER_TABLE = "Director_Detail";
    private final String LOCATION_TABLE = "LastLocation_Detail";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        createTables(sqLiteDatabase);
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        String QUERY_TEACHER = "CREATE TABLE IF NOT EXISTS " + TEACHER_TABLE + " (id text,fname text, lname text,phone text,email text," +
                "image text, services integer,gender text, bachelor text,bacheler_detail text, master text,master_detail text, " +
                "other_detail text, specialist text,dob date)";
        sqLiteDatabase.execSQL(QUERY_TEACHER);

        String QUERY_STUDENT = "CREATE TABLE IF NOT EXISTS " + STUDENT_TABLE + " (id text,fname text, lname text,phone text,email text," +
                " image text,gender text,pursuing text, pursuing_detail text,bachelor text,bacheler_detail text, master text," +
                "master_detail text, other_detail text, specialist text,dob date,state text, city text)";
        sqLiteDatabase.execSQL(QUERY_STUDENT);

        String QUERY_DIRECTOR = "CREATE TABLE IF NOT EXISTS " + DIRECTER_TABLE + " (id text,name text,phone text,email text," +
                " image text,gender text,pursuing text, pursuing_detail text,bachelor text,bacheler_detail text, master text," +
                "master_detail text, other_detail text, specialist text,dob date,state text, city text)";
        sqLiteDatabase.execSQL(QUERY_DIRECTOR);

        String QUERY_LOCATION = "CREATE TABLE IF NOT EXISTS " + LOCATION_TABLE + " (id integer,city text,address text,latitude text,longitude text," +
                " landmark text,fullAddress text,localAddress text)";
        sqLiteDatabase.execSQL(QUERY_LOCATION);


    }


    public void insertLastLocation(Address address) {

        String query = "SELECT * from " + LOCATION_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", address.getCity());
        contentValues.put("address", address.getAddress());
        contentValues.put("latitude", address.getLatitude());
        contentValues.put("longitude", address.getLongitude());
        contentValues.put("landmark", address.getLandmark());
        contentValues.put("fullAddress", address.getFullAddress());
        contentValues.put("localAddress", address.getLocalAddress());

        if (cursor != null&&cursor.moveToNext()) {
            database.update(LOCATION_TABLE, contentValues, null, null);
            cursor.close();
        } else {
            contentValues.put("id", 1);
            database.insert(LOCATION_TABLE, null, contentValues);
        }
    }

    public Address getLastLocation() {
        Address address = null;
        String query = "SELECT * from " + LOCATION_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.moveToNext()) {
                address = new Address();
                address.setCity(cursor.getString(cursor.getColumnIndex("city")));
                address.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                address.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                address.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
                address.setLandmark(cursor.getString(cursor.getColumnIndex("landmark")));
                address.setFullAddress(cursor.getString(cursor.getColumnIndex("fullAddress")));
                address.setLocalAddress(cursor.getString(cursor.getColumnIndex("localAddress")));
            }
            cursor.close();
        }

        return address;

    }


    public boolean insertStudentDetail(StudentDetail studentDetail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", studentDetail.getId());
        contentValues.put("fname", studentDetail.getFirstName());
        contentValues.put("lname", studentDetail.getLastName());
        contentValues.put("email", studentDetail.getEmail());
        contentValues.put("phone", studentDetail.getPhone());
        contentValues.put("image", studentDetail.getImage());
        contentValues.put("gender", studentDetail.getGender());
        contentValues.put("pursuing", studentDetail.getPursuing());
        contentValues.put("pursuing_detail", studentDetail.getPursuingDetail());
        contentValues.put("bachelor", studentDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", studentDetail.getBachelorDegreeDetail());
        contentValues.put("master", studentDetail.getMasterDegree());
        contentValues.put("master_detail", studentDetail.getMasterDegreeDetail());
        contentValues.put("other_detail", studentDetail.getOther());
        contentValues.put("specialist", studentDetail.getSpecialist());
        contentValues.put("dob", studentDetail.getDob());
        contentValues.put("state", studentDetail.getState());
        contentValues.put("city", studentDetail.getCity());
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TEACHER_TABLE, null, contentValues) > 0;

    }

    public StudentDetail getStudentDetail() {
        StudentDetail studentDetail = null;
        String query = "SELECT * from " + STUDENT_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                studentDetail = new StudentDetail();
                studentDetail.setId(cursor.getString(cursor.getColumnIndex("id")));
                studentDetail.setFirstName(cursor.getString(cursor.getColumnIndex("fname")));
                studentDetail.setLastName(cursor.getString(cursor.getColumnIndex("lname")));
                studentDetail.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                studentDetail.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                studentDetail.setImage(cursor.getString(cursor.getColumnIndex("image")));
                studentDetail.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                studentDetail.setPursuing(cursor.getString(cursor.getColumnIndex("pursuing")));
                studentDetail.setPursuingDetail(cursor.getString(cursor.getColumnIndex("pursuing_detail")));
                studentDetail.setBachelorDegree(cursor.getString(cursor.getColumnIndex("bachelor")));
                studentDetail.setBachelorDegreeDetail(cursor.getString(cursor.getColumnIndex("bacheler_detail")));
                studentDetail.setMasterDegree(cursor.getString(cursor.getColumnIndex("master")));
                studentDetail.setMasterDegreeDetail(cursor.getString(cursor.getColumnIndex("master_detail")));
                studentDetail.setOther(cursor.getString(cursor.getColumnIndex("other_detail")));
                studentDetail.setSpecialist(cursor.getString(cursor.getColumnIndex("specialist")));
                studentDetail.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                studentDetail.setState(cursor.getString(cursor.getColumnIndex("state")));
                studentDetail.setCity(cursor.getString(cursor.getColumnIndex("city")));
            }
            cursor.close();
        }

        return studentDetail;

    }

    public boolean updateStudentDetail(StudentDetail studentDetail) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put("id", studentDetail.getId());
        contentValues.put("fname", studentDetail.getFirstName());
        contentValues.put("lname", studentDetail.getLastName());
        contentValues.put("email", studentDetail.getEmail());
        contentValues.put("phone", studentDetail.getPhone());
        contentValues.put("image", studentDetail.getImage());
        contentValues.put("gender", studentDetail.getGender());
        contentValues.put("pursuing", studentDetail.getPursuing());
        contentValues.put("pursuing_detail", studentDetail.getPursuingDetail());
        contentValues.put("bachelor", studentDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", studentDetail.getBachelorDegreeDetail());
        contentValues.put("master", studentDetail.getMasterDegree());
        contentValues.put("master_detail", studentDetail.getMasterDegreeDetail());
        contentValues.put("other_detail", studentDetail.getOther());
        contentValues.put("specialist", studentDetail.getSpecialist());
        contentValues.put("dob", studentDetail.getDob());
        contentValues.put("state", studentDetail.getState());
        contentValues.put("city", studentDetail.getCity());
        SQLiteDatabase database = getWritableDatabase();
        return database.update(STUDENT_TABLE, contentValues, "id=?",
                new String[]{studentDetail.getId()}) > 0;

    }

    public boolean updateStudentDetailWithoutId(StudentDetail studentDetail) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put("id", studentDetail.getId());
        contentValues.put("fname", studentDetail.getFirstName());
        contentValues.put("lname", studentDetail.getLastName());
        contentValues.put("email", studentDetail.getEmail());
        contentValues.put("phone", studentDetail.getPhone());
        contentValues.put("image", studentDetail.getImage());
        contentValues.put("gender", studentDetail.getGender());
        contentValues.put("pursuing", studentDetail.getPursuing());
        contentValues.put("pursuing_detail", studentDetail.getPursuingDetail());
        contentValues.put("bachelor", studentDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", studentDetail.getBachelorDegreeDetail());
        contentValues.put("master", studentDetail.getMasterDegree());
        contentValues.put("master_detail", studentDetail.getMasterDegreeDetail());
        contentValues.put("other_detail", studentDetail.getOther());
        contentValues.put("specialist", studentDetail.getSpecialist());
        contentValues.put("dob", studentDetail.getDob());
        contentValues.put("state", studentDetail.getState());
        contentValues.put("city", studentDetail.getCity());
        SQLiteDatabase database = getWritableDatabase();
        return database.update(STUDENT_TABLE, contentValues, null,
                null) > 0;

    }

    public boolean insertTeacherDetail(TeacherDetail teacherDetail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", teacherDetail.getId());
        contentValues.put("fname", teacherDetail.getFirstName());
        contentValues.put("lname", teacherDetail.getLastName());
        contentValues.put("email", teacherDetail.getEmail());
        contentValues.put("phone", teacherDetail.getPhone());
        contentValues.put("image", teacherDetail.getImage());
        contentValues.put("services", teacherDetail.getServices());
        contentValues.put("gender", teacherDetail.getGender());
        contentValues.put("bachelor", teacherDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", teacherDetail.getBachelorDegreeDetail());
        contentValues.put("master", teacherDetail.getMasterDegree());
        contentValues.put("master_detail", teacherDetail.getMasterDegreeDatail());
        contentValues.put("other_detail", teacherDetail.getOther());
        contentValues.put("specialist", teacherDetail.getSpecialist());
        contentValues.put("dob", teacherDetail.getDob());
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TEACHER_TABLE, null, contentValues) > 0;
    }

    public boolean updateTeacherDetail(TeacherDetail teacherDetail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", teacherDetail.getId());
        contentValues.put("fname", teacherDetail.getFirstName());
        contentValues.put("lname", teacherDetail.getLastName());
        contentValues.put("email", teacherDetail.getEmail());
        contentValues.put("phone", teacherDetail.getPhone());
        contentValues.put("image", teacherDetail.getImage());
        contentValues.put("services", teacherDetail.getServices());
        contentValues.put("gender", teacherDetail.getGender());
        contentValues.put("bachelor", teacherDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", teacherDetail.getBachelorDegreeDetail());
        contentValues.put("master", teacherDetail.getMasterDegree());
        contentValues.put("master_detail", teacherDetail.getMasterDegreeDatail());
        contentValues.put("other_detail", teacherDetail.getOther());
        contentValues.put("specialist", teacherDetail.getSpecialist());
        contentValues.put("dob", teacherDetail.getDob());
        SQLiteDatabase database = getWritableDatabase();
        return database.update(TEACHER_TABLE, contentValues, "id=?",
                new String[]{teacherDetail.getId()}) > 0;
    }

    public boolean updateTeacherDetailWithoutId(TeacherDetail teacherDetail) {
        ContentValues contentValues = new ContentValues();
        //  contentValues.put("id", teacherDetail.getId());
        contentValues.put("fname", teacherDetail.getFirstName());
        contentValues.put("lname", teacherDetail.getLastName());
        contentValues.put("email", teacherDetail.getEmail());
        contentValues.put("phone", teacherDetail.getPhone());
        contentValues.put("image", teacherDetail.getImage());
        contentValues.put("services", teacherDetail.getServices());
        contentValues.put("gender", teacherDetail.getGender());
        contentValues.put("bachelor", teacherDetail.getBachelorDegree());
        contentValues.put("bacheler_detail", teacherDetail.getBachelorDegreeDetail());
        contentValues.put("master", teacherDetail.getMasterDegree());
        contentValues.put("master_detail", teacherDetail.getMasterDegreeDatail());
        contentValues.put("other_detail", teacherDetail.getOther());
        contentValues.put("specialist", teacherDetail.getSpecialist());
        contentValues.put("dob", teacherDetail.getDob());
        SQLiteDatabase database = getWritableDatabase();
        return database.update(TEACHER_TABLE, contentValues, null,
                null) > 0;
    }

    public TeacherDetail getTeacherDetail() {
        TeacherDetail teacherDetail = null;
        String query = "SELECT * from " + TEACHER_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                teacherDetail = new TeacherDetail();
                teacherDetail.setId(cursor.getString(cursor.getColumnIndex("id")));
                teacherDetail.setFirstName(cursor.getString(cursor.getColumnIndex("fname")));
                teacherDetail.setLastName(cursor.getString(cursor.getColumnIndex("lname")));
                teacherDetail.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                teacherDetail.setServices(cursor.getString(cursor.getColumnIndex("services")));
                teacherDetail.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                teacherDetail.setBachelorDegree(cursor.getString(cursor.getColumnIndex("bachelor")));
                teacherDetail.setBachelorDegreeDetail(cursor.getString(cursor.getColumnIndex("bacheler_detail")));
                teacherDetail.setMasterDegree(cursor.getString(cursor.getColumnIndex("master")));
                teacherDetail.setMasterDegreeDatail(cursor.getString(cursor.getColumnIndex("master_detail")));
                teacherDetail.setOther(cursor.getString(cursor.getColumnIndex("other_detail")));
                teacherDetail.setSpecialist(cursor.getString(cursor.getColumnIndex("specialist")));
                teacherDetail.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                teacherDetail.setImage(cursor.getString(cursor.getColumnIndex("image")));
                teacherDetail.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            }
            cursor.close();
        }

        return teacherDetail;

    }

    public boolean insertDirectorDetail(DirectorDetail director) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put("id", studentDetail.getId());
        contentValues.put("name", director.getName());
        contentValues.put("email", director.getEmail());
        contentValues.put("phone", director.getPhone());
        contentValues.put("image", director.getImage());
        contentValues.put("gender", director.getGender());
    /*contentValues.put("pursuing", studentDetail.getPursuing());
    contentValues.put("pursuing_detail", studentDetail.getPursuingDetail());
    contentValues.put("bachelor", studentDetail.getBachelorDegree());
    contentValues.put("bacheler_detail", studentDetail.getBachelorDegreeDetail());
    contentValues.put("master", studentDetail.getMasterDegree());
    contentValues.put("master_detail", studentDetail.getMasterDegreeDetail());
    contentValues.put("other_detail", studentDetail.getOther());
    contentValues.put("specialist", studentDetail.getSpecialist());
    contentValues.put("dob", studentDetail.getDob());
    contentValues.put("state", studentDetail.getState());
    contentValues.put("city", studentDetail.getCity());*/
        SQLiteDatabase database = getWritableDatabase();
        return database.update(DIRECTER_TABLE, contentValues, null,
                null) > 0;
    }


    public DirectorDetail getDirectorDetail() {
        DirectorDetail directorDetail = null;
        String query = "SELECT * from " + DIRECTER_TABLE;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                directorDetail = new DirectorDetail();
                directorDetail.setDirectorId(cursor.getInt(cursor.getColumnIndex("id")));
                directorDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
                directorDetail.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                directorDetail.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
           /* teacherDetail.setServices(cursor.getString(cursor.getColumnIndex("services")));
            teacherDetail.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            teacherDetail.setBachelorDegree(cursor.getString(cursor.getColumnIndex("bachelor")));
            teacherDetail.setBachelorDegreeDetail(cursor.getString(cursor.getColumnIndex("bacheler_detail")));
            teacherDetail.setMasterDegree(cursor.getString(cursor.getColumnIndex("master")));
            teacherDetail.setMasterDegreeDatail(cursor.getString(cursor.getColumnIndex("master_detail")));
            teacherDetail.setOther(cursor.getString(cursor.getColumnIndex("other_detail")));
            teacherDetail.setSpecialist(cursor.getString(cursor.getColumnIndex("specialist")));
            teacherDetail.setDob(cursor.getString(cursor.getColumnIndex("dob")));
            teacherDetail.setImage(cursor.getString(cursor.getColumnIndex("image")));
            teacherDetail.setPhone(cursor.getString(cursor.getColumnIndex("phone")));*/
            }
            cursor.close();
        }
        return directorDetail;
    }

    public void onLogOutUser() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TEACHER_TABLE, null, null);
        db.delete(STUDENT_TABLE, null, null);

    }

}