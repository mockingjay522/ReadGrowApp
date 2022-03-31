package com.example.readgrow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BookDatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase shareBookDB;
    public BookDatabaseHelper( Context context) {
        super(context, "shareBook.db", null, 2);
        this.shareBookDB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//       this.shareBookDB.execSQL("CREATE TABLE admin (admin_id integer primary key," +
//                "name Text);");

        db.execSQL("CREATE TABLE book_reader (reader_id integer primary key," +
                " name Text," +
                " age integer," +
                " address Text," +
                " email Text," +
                " password Text);");

        db.execSQL("CREATE TABLE book_reader_interest (reader_id integer, interest Text," +
                " PRIMARY KEY (reader_id, interest)," +
                " FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id));");

        db.execSQL("CREATE TABLE book (book_id integer primary key," +
                "reader_id integer," +
                "title Text," +
                "author Text," +
                "publisher Text," +
                " publish_date Text," +
                "book_status integer," +
                " FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id));");

//        this.shareBookDB.execSQL("CREATE TABLE reader_message (message_id integer primary key," +
//                "sender_id integer," +
//                "receiver_id integer," +
//                "message_title text," +
//                "message_content text," +
//                "date text," +
//                "FOREIGN KEY(sender_id,receiver_id) REFERENCES book_reader(reader_id,reader_id));");
//
//        this.shareBookDB.execSQL("CREATE TABLE admin_message (message_id integer primary key," +
//                "admin_id integer," +
//                "reader_id integer," +
//                "message_title text," +
//                "message_content text," +
//                " date text," +
//                " FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id)," +
//                " FOREIGN KEY(admin_id) REFERENCES admin(admin_id));");
//
//
//        db.execSQL("CREATE TABLE share_book (share_id integer primary key," +
//                "book_id integer," +
//                "reader_id integer, " +
//                "date Text," +
//                "FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id));");
//
//        db.execSQL("CREATE TABLE give_book (give_id integer primary key," +
//                "book_id integer," +
//                "reader_id integer, " +
//                "date Text," +
//                "FOREIGN KEY(book_id) REFERENCES book(book_id)," +
//                "FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id));");
//
//        db.execSQL("CREATE TABLE rent_book (rent_id integer primary key," +
//                "book_id integer," +
//                "reader_id integer," +
//                "price real," +
//                "date Text," +
//                "address text," +
//                "postalCode text," +
//                "FOREIGN KEY(book_id) REFERENCES book(book_id)," +
//                "FOREIGN KEY(reader_id) REFERENCES book_reader(reader_id));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS admin");
        this.shareBookDB.execSQL("DROP TABLE IF EXISTS book_reader");
        this.shareBookDB.execSQL("DROP TABLE IF EXISTS book");
        this.shareBookDB.execSQL("DROP TABLE IF EXISTS book_reader_interest");
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS share_book");
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS give_book");
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS rent_book");
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS reader_message");
//        this.shareBookDB.execSQL("DROP TABLE IF EXISTS admin_message");
          onCreate(db);

    }

    //<editor-fold desc="This region includes all addition methods ">

    public int AddAdmin(String adminName){
        ContentValues adminTableValues = new ContentValues();
        adminTableValues.put("name",adminName);
        long recordId = this.shareBookDB.insert("admin",null,adminTableValues);
        Log.i("addRecordAdmin", String.valueOf(recordId));

        return (int)recordId;
    }

    /***
     * this method is used to add a new book reader
     * @param readerName
     *
     */
    public int AddReader(String readerName, int age, String address, String email, String password){
        ContentValues readerTableValues = new ContentValues();
        readerTableValues.put("name",readerName);
        readerTableValues.put("age",age);
        readerTableValues.put("address",address);
        readerTableValues.put("email",email);
        readerTableValues.put("password",password);

        long recordId = this.shareBookDB.insert("book_reader",null,readerTableValues);
        Log.i("addRecordReader", String.valueOf(recordId));
        return (int)recordId;

    }

    /***
     * this method add a book to the book's table
     * @param readerId
     * @param title
     * @param publisher
     * @param publishDate
     * @param book_status
     */
    public int AddBook(int readerId,String title,String author, String publisher,String publishDate, int book_status){
        ContentValues bookTableValues = new ContentValues();
        bookTableValues.put("reader_id",readerId);
        bookTableValues.put("title",title);
        bookTableValues.put("author",author);
        bookTableValues.put("publisher",publisher);
        bookTableValues.put("publish_date",publishDate);
        bookTableValues.put("book_status",book_status);

        long recordId = this.shareBookDB.insert("book",null,bookTableValues);
        Log.i("addRecordBook", String.valueOf(recordId));

        return (int)recordId;

    }

    /***
     * add a new book reader interest
     * @param readerId
     * @param interest
     */
    public int AddBookReaderInterest(int readerId, String interest ){
        ContentValues interestTableValues = new ContentValues();
        interestTableValues.put("reader_id",readerId);
        interestTableValues.put("interest",interest);

        long recordId = this.shareBookDB.insert("book_reader_interest",null,interestTableValues);
        Log.i("RecordInterest", String.valueOf(recordId));

        return (int)recordId;

    }

    /***
     * add a new a gaven book
     * @param bookId
     * @param readerId
     * @param date
     */
    public int AddGiveBook(int bookId, int readerId,String date ){
        ContentValues giveBookTableValues = new ContentValues();
        giveBookTableValues.put("book_id",bookId);
        giveBookTableValues.put("reader_id",readerId);
        giveBookTableValues.put("date",date);

        long recordId = this.shareBookDB.insert("give_book",null,giveBookTableValues);
        Log.i("addRecordGiveBook", String.valueOf(recordId));

        return (int)recordId;
    }

    /***
     * add a new share book
     * @param bookId
     * @param readerId
     * @param date
     */
    public int AddShareBook(int bookId, int readerId,String date){
        ContentValues shareBookTableValues = new ContentValues();
        shareBookTableValues.put("book_id",bookId);
        shareBookTableValues.put("reader_id",readerId);
        shareBookTableValues.put("date",date);

        long recordId = this.shareBookDB.insert("share_book",null,shareBookTableValues);
        Log.i("addRecordShareBook", String.valueOf(recordId));

        return (int)recordId;
    }

    /***
     * add a new rent book record
     * @param bookId
     * @param readerId
     * @param price
     * @param date
     * @param address
     * @param postalCode
     */
    public int AddRentBook(int bookId,int readerId, double price, String date,String address, String postalCode){

        ContentValues rentBookTableValues = new ContentValues();
        rentBookTableValues.put("book_id",bookId);
        rentBookTableValues.put("reader_id",readerId);
        rentBookTableValues.put("price",price);
        rentBookTableValues.put("date",date);
        rentBookTableValues.put("address",address);
        rentBookTableValues.put("postalCode",postalCode);

        long recordId =this.shareBookDB.insert("rent_book",null,rentBookTableValues);
        Log.i("addRecordRentBook", String.valueOf(recordId));

        return (int)recordId;
    }

    /***
     * add a new reader message
     * @param senderId
     * @param receiverId
     * @param messageTitle
     * @param messageContent
     * @param date
     */
    public int AddReaderMessage(int senderId,int receiverId, String messageTitle,String messageContent,String date){
        ContentValues readerMessageTableValues = new ContentValues();
        readerMessageTableValues.put("sender_id",senderId);
        readerMessageTableValues.put("receiver_id",receiverId);
        readerMessageTableValues.put("message_title",messageTitle);
        readerMessageTableValues.put("message_content",messageContent);
        readerMessageTableValues.put("date",date);

        long recordId =this.shareBookDB.insert("reader_message",null,readerMessageTableValues);
        Log.i("addRecordReaderMessage", String.valueOf(recordId));

        return (int)recordId;


    }

    /***
     * add a new admin message to a reader
     * @param adminId
     * @param readerId
     * @param messageTitle
     * @param message_content
     * @param date
     */
    public int AddAdminMessage(int adminId,int readerId, String messageTitle, String message_content,String date){
        ContentValues adminMessageTableValues = new ContentValues();
        adminMessageTableValues.put("admin_id",adminId);
        adminMessageTableValues.put("reader_id",readerId);
        adminMessageTableValues.put("message_title",messageTitle);
        adminMessageTableValues.put("message_content",message_content);
        adminMessageTableValues.put("date",date);

        long recordId =this.shareBookDB.insert("admin_message",null,adminMessageTableValues);
        Log.i("addRecordAdminMessage", String.valueOf(recordId));

        return (int)recordId;

    }

    //</editor-fold>

    //<editor-fold desc="This region includes all deletion methods ">
    public int DeleteAdminByID(int admin_id){
        String[] whereValue = {String.valueOf(admin_id)};
        int deletionResult = this.shareBookDB.delete("admin","admin_id=?",whereValue);
        Log.i("deletionAdmin",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteReaderMessageByID(int message_id){
        String[] whereValue = {String.valueOf(message_id)};
        int deletionResult = this.shareBookDB.delete("reader_message","message_id=?",whereValue);
        Log.i("deletionReaderMessage",String.valueOf(deletionResult));


        return deletionResult;

    }

    public int DeleteBookByID(int book_id){
        String[] whereValue = {String.valueOf(book_id)};
        int deletionResult = this.shareBookDB.delete("book","book_id=?",whereValue);
        Log.i("deletionBook",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteAdminMessageByID(int message_id){
        String[] whereValue = {String.valueOf(message_id)};
        int deletionResult = this.shareBookDB.delete("admin_message","message_id=?",whereValue);
        Log.i("deletionAdminMessage",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteBookReaderById(int reader_id){
        String[] whereValue = {String.valueOf(reader_id)};
        int deletionResult = this.shareBookDB.delete("book_reader","reader_id=?",whereValue);
        Log.i("deletionBookReader",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteBookReaderInterestById(int reader_id,String interest){
        String[] whereValue = {String.valueOf(reader_id),interest};
        int deletionResult = this.shareBookDB.delete("book_reader_interest","reader_id=? and interest=?",whereValue);
        Log.i("deletionInterest",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteGiveBookById(int give_id){
        String[] whereValue = {String.valueOf(give_id)};
        int deletionResult = this.shareBookDB.delete("give_book","give_id=?",whereValue);
        Log.i("deletionGiveBook",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteRentBookById(int rent_id){
        String[] whereValue = {String.valueOf(rent_id)};
        int deletionResult = this.shareBookDB.delete("rent_book","rent_id=?",whereValue);
        Log.i("deletionRentBook",String.valueOf(deletionResult));

        return deletionResult;

    }

    public int DeleteShareBookById(int share_id){
        String[] whereValue = {String.valueOf(share_id)};
        int deletionResult = this.shareBookDB.delete("share_book","share_id=?",whereValue);
        Log.i("deletionShareBook",String.valueOf(deletionResult));

        return deletionResult;

    }
    //</editor-fold>

    //<editor-fold desc="This region includes all update methods ">

    public int UpdateAdmin(int admin_id,String adminName){
        ContentValues adminTableValues = new ContentValues();
        adminTableValues.put("name",adminName);
        int numberOfRecords = this.shareBookDB.update("admin",adminTableValues,"admin_id=?", new String[]{
                String.valueOf(admin_id)});
        Log.i("updateRecordAdmin", String.valueOf(numberOfRecords));

        return numberOfRecords;
    }

    public int UpdateBook(int book_id,int readerId,String title,String author,String publisher,String publishDate, int book_status){
        ContentValues bookTableValues = new ContentValues();

        bookTableValues.put("reader_id",readerId);
        bookTableValues.put("title",title);
        bookTableValues.put("author",author);
        bookTableValues.put("publisher",publisher);
        bookTableValues.put("publish_date",publishDate);
        bookTableValues.put("book_status",book_status);

        int numberOfRecords = this.shareBookDB.update("book",bookTableValues,"book_id=?", new String[]{
                String.valueOf(book_id)});
        Log.i("updateRecordBook", String.valueOf(numberOfRecords));

        return numberOfRecords;
    }

    public int UpdateBookReader(int reader_id,String readerName, int age, String address, String email, String password){
        ContentValues readerTableValues = new ContentValues();

        readerTableValues.put("name",readerName);
        readerTableValues.put("age",age);
        readerTableValues.put("address",address);
        readerTableValues.put("email",email);
        readerTableValues.put("password",password);

        int numberOfRecords = this.shareBookDB.update("book_reader",readerTableValues,"reader_id=?", new String[]{
                String.valueOf(reader_id)});
        Log.i("updateBookReader", String.valueOf(numberOfRecords));

        return numberOfRecords;
    }


    //</editor-fold>

    //<editor-fold desc="This region includes display methods ">

    // get all a table's records
    public Cursor GetAllAdmins(){

        return  this.shareBookDB.rawQuery("Select * from admin",null);
    }

    public Cursor GetAllAdminMessage(){
        return  this.shareBookDB.rawQuery("Select * from admin_message",null);
    }

    public Cursor GetAllBooks(){
        return  this.shareBookDB.rawQuery("Select * from book",null);
    }

    public Cursor GetAllBookReader(){
        //return this.shareBookDB.rawQuery("Select * from book_reader",null);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "Select * from book_reader";
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public Cursor GetAllBookReaderInterest(){
        return  this.shareBookDB.rawQuery("Select * from book_reader_interest",null);
    }

    public Cursor GetAllGiveBook(){
        return  this.shareBookDB.rawQuery("Select * from give_book",null);
    }

    public Cursor GetAllReaderMessage(){
        return  this.shareBookDB.rawQuery("Select * from reader_message",null);
    }

    public Cursor GetAllRentBook(){
        return  this.shareBookDB.rawQuery("Select * from rent_book",null);
    }

    public Cursor GetAllShareBook(){
        return  this.shareBookDB.rawQuery("Select * from share_book",null);
    }

    //---- get a record by IDs
    public Cursor GetAdminById(int adminId){
        return  this.shareBookDB.rawQuery("Select * from admin Where admin_id = ?",new String[]{
                String.valueOf(adminId)
        });

    }

    public Cursor GetAdminMessageByAdminId(int adminId) {
        return this.shareBookDB.rawQuery("Select * from admin_message Where admin_id = ?", new String[]{
                String.valueOf(adminId)
        });
    }

    public Cursor GetAdminMessageByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from admin_message Where reader_id = ?",new String[]{
                String.valueOf(readerId)
        });
    }

    public Cursor GetBookById(int bookId){
        return  this.shareBookDB.rawQuery("Select * from book Where book_id = ?",new String[]{
                String.valueOf(bookId)
        });
    }

    public Cursor GetBooksByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from book Where reader_id = ?",new String[]{
                String.valueOf(readerId)
        });
    }

    public Cursor GetBookReaderById(int readerId){
        return  this.shareBookDB.rawQuery("Select * from book_reader Where reader_id = ?",new String[]{
                String.valueOf(readerId)
        });
    }

    public Cursor GetBookReaderInterestByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from book_reader_interest Where reader_id = ?",new String[]{
                String.valueOf(readerId)
        });
    }

    public Cursor GetBookReaderInterestByInterest(String interest){
        return  this.shareBookDB.rawQuery("Select * from book_reader_interest Where interest = ?",new String[]{interest});
    }

    public Cursor GetGiveBookByBookId(int bookId){
        return  this.shareBookDB.rawQuery("Select * from give_book Where book_id = ?",new String[]{String.valueOf(bookId)});
    }

    public Cursor GetGiveBookByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from give_book Where reader_id = ?",new String[]{String.valueOf(readerId)});
    }

    public Cursor GetReaderMessageBySenderAndReceiverID(int senderId,int receiverId ){
        return  this.shareBookDB.rawQuery("Select * from reader_message Where sender_id = ? and receiver_id=?",new String[]{
                String.valueOf(senderId),
                String.valueOf(receiverId),
        });
    }

    public Cursor GetReaderMessageBySenderID(int senderId ){
        return  this.shareBookDB.rawQuery("Select * from reader_message Where sender_id = ?",new String[]{
                String.valueOf(senderId)
        });
    }

    public Cursor GetReaderMessageByReceiverID(int receiverId ){
        return  this.shareBookDB.rawQuery("Select * from reader_message Where receiver_id = ?",new String[]{
                String.valueOf(receiverId)
        });
    }

    public Cursor GetRentBookByBookId(int bookId){
        return  this.shareBookDB.rawQuery("Select * from rent_book Where book_id = ?",new String[]{String.valueOf(bookId)});
    }

    public Cursor GetRentBookByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from rent_book Where reader_id = ?",new String[]{String.valueOf(readerId)});
    }

    public Cursor GetShareBookByBookId(int bookId){
        return  this.shareBookDB.rawQuery("Select * from share_book Where book_id = ?",new String[]{String.valueOf(bookId)});
    }

    public Cursor GetShareBookByReaderId(int readerId){
        return  this.shareBookDB.rawQuery("Select * from share_book Where reader_id = ?",new String[]{String.valueOf(readerId)});
    }
    //</editor-fold>


}
