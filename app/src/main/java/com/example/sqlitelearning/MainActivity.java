package com.example.sqlitelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtComputerName, edtComputerType;
    Button btnAdd, btnDelete;
    ListView listView;

    List<Computer> allComputer;
    ArrayList<String> computersName;
    MySQLiteHandler database;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtComputerName = findViewById(R.id.edtComputerName);
        edtComputerType = findViewById(R.id.edtComputertype);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);

        btnAdd.setOnClickListener(MainActivity.this);
        btnDelete.setOnClickListener(MainActivity.this);

        database = new MySQLiteHandler(MainActivity.this);
        allComputer = database.getAllComputers();
        computersName = new ArrayList<>();

        if(allComputer.size() > 0) {
            for(int i = 0 ; i < allComputer.size() ; i++){
                Computer computer = allComputer.get(i);
                computersName.add(computer.getComputerName() + " - " + computer.getComputerType());
            }
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, computersName);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Computer computer = new Computer(edtComputerName.getText().toString(), edtComputerType.getText().toString());
                if (edtComputerName.getText().toString().matches("") || edtComputerType.getText().toString().matches("")) {
                    return;
                }
                allComputer.add(computer);
                database.addComputer(computer);
                computersName.add(computer.getComputerName() + " - " + computer.getComputerType());
                edtComputerName.setText("");
                edtComputerType.setText("");
                break;
            case R.id.btnDelete:
                if(allComputer.size() > 0) {
                    computersName.remove(0);
                    database.deleteComputer(allComputer.get(0));
                    allComputer.remove(0);
                } else {
                    return;
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }
}