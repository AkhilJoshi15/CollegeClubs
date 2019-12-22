package com.example.bharath.collegeclubs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FacSelStu extends AppCompatActivity {

    String fid,fclub;
        Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_sel_stu);

        fid=getIntent().getStringExtra("fid");
        fclub=getIntent().getStringExtra("fclub");

        final ListView listView= (ListView) findViewById(R.id.listView);
        submit= (Button) findViewById(R.id.submit);

        final List<ListViewItemDTO> initItemList=this.getInitViewItemDtoList();

        final ListViewItemCheckboxBaseAdapter listViewDataAdapter = new ListViewItemCheckboxBaseAdapter(getApplicationContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listView.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }else
                {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = initItemList.size();
                List<String> list=new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    ListViewItemDTO dto = initItemList.get(i);

                    if (dto.isChecked()) {
                        //dto.setChecked(false);

                        list.add(String.valueOf(dto));
                    }
                }

                listViewDataAdapter.notifyDataSetChanged();

                DBconnector dbcon=new DBconnector(getApplicationContext());
                HashMap<String,String> map=new HashMap<String, String>();
                for(int li=0;li<list.size();li++) {
                    map.put("sname", list.get(li).toString());
                    map.put("sclub", fclub);
                    dbcon.insert_vote(map);
                    Toast.makeText(getApplicationContext(),"SName: "+list.get(li).toString()+"\nSclub: "+fclub,Toast.LENGTH_LONG).show();
                }

                Intent intent=new Intent(getApplicationContext(),FacHome.class);
                intent.putExtra("fid",fid);
                intent.putExtra("fclub",fclub);
                Toast.makeText(getApplicationContext(),"Selection Success",Toast.LENGTH_LONG).show();
                startActivity(intent);


            }
        });

    }

    private List<ListViewItemDTO> getInitViewItemDtoList()
    {
        String itemTextArr[] = {"Android", "iOS", "Java", "JavaScript", "JDBC", "JSP", "Linux", "Python", "Servlet", "Windows","OS","ABC"};

        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = itemTextArr.length;

        for(int i=0;i<length;i++)
        {
            String itemText = itemTextArr[i];

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);

            ret.add(dto);
        }

        return ret;
    }

}
