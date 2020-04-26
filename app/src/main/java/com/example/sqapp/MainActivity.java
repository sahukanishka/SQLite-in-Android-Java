package com.example.sqapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editName , editTotal_present ,editId;
    Button btnAdd , btnView ,btnUpdate ,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editName);
        editTotal_present = (EditText) findViewById(R.id.editTotal_present);
        editId = (EditText) findViewById(R.id.editId);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete) ;
        AddData();
        viewAll();
        updateData();
        DeleteData();
    }

    public  void AddData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = mydb.insertData(editName.getText().toString(),
                                editTotal_present.getText().toString());
                         if(isInserted == true){
                             Toast.makeText(MainActivity.this,"Data inserted ",Toast.LENGTH_LONG).show();
                        }
                         else {
                             Toast.makeText(MainActivity.this,"Data not inserted ",Toast.LENGTH_LONG).show();
                         }
                    }
                }
        );

    }
//to view all data class
    public void viewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = mydb.getAllData();
                        if(res.getCount() == 0 ) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                          buffer.append("id: "+ res.getString(0)+"\n") ;
                            buffer.append("Name: "+ res.getString(1)+"\n") ;
                                buffer.append("TOTAL_PRESENT: "+ res.getString(2)+"\n") ;
                        }
                        //show messange
                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }

    public  void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public  void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = mydb.updateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editTotal_present.getText().toString());
                        if(isUpdate == true){
                            Toast.makeText(MainActivity.this,"Data Updated ",Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deleteRows =mydb.deleteData(editId.getText().toString());
                        if (deleteRows > 0){
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
