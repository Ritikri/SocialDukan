package com.example.socialdukan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Studentdetail extends AppCompatActivity implements View.OnClickListener {
TextView schlstarty10,schlendy10,schlstarty12,schlendy12,collegestart,collegeend,companystartdate,companyenddate;
    EditText dob,adress,occupation,schoolname10,wanumber,
            percentage10,schoolname12,percentage12,collegename,
            collegecourse,collegedept,collegeper;
    EditText companyname,companyrole,companybenefits;
    //TextView textview;
    Button addpersonaldet,addschool10det,addschool12det,addcollegedet,addexpbtn,addnewexpbtn,updateexpbtn;
    Button addexpbtn1,addnewexpbtn1,updateexpbtn1;//RITIK
    Button addexpbtn2,addnewexpbtn2,updateexpbtn2;
    EditText companyname1;//RITIK
    EditText companyname2;
    Button submit,yesbtn,nobtn;
    String name,phno,email,uid;
    RadioButton radioButton_high, radioButton_diploma;
private String pres_doctor,pres_doctor1,pres_doctor2,pres_doctor3,pres_doctor4,pres_doctor5,pres_doctor6,pres_doctor7;
    EditText stream1,board,board1,dipclgname,dipcorsname,dippercentage;



    private int mYear, mMonth, mDay;
    private int c=0;

    TextView displayname,displayemail,displaymno;

    RecyclerView rv_exp,rv_exp1,rv_exp2;//RITIK
    FirebaseRecyclerOptions<addexp_model> optionsexp;
    FirebaseRecyclerOptions<addexp1_model> optionsexp1;  //Ritik
    FirebaseRecyclerOptions<addexp2_model> optionsexp2;
    FirebaseRecyclerAdapter<addexp_model,addexp_viewholder> adapterexp;
    FirebaseRecyclerAdapter<addexp1_model,addexp1_viewholder>adapterexp1; //Ritik
    FirebaseRecyclerAdapter<addexp2_model,addexp2_viewholder> adapterexp2;
    DatabaseReference databaseReferenceexprv,databaseReferenceexprv1,databaseReferenceexprv2;           //RITIK

    int expcounter=0;
    int skillcount=0;
    int achcount=0;



    protected void onStart(){
        super.onStart();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("uid").equalTo( FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User bnd_helper = dataSnapshot1.getValue(User.class);

                    String n = bnd_helper.name;
                    String n_one=bnd_helper.email;
                    String n_two=bnd_helper.contactn;
                    displayname.setText(n);
                    displayemail.setText( n_one );
                    displaymno.setText("+91 "+ n_two );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapterexp.startListening();
        adapterexp1.startListening();
        adapterexp2.startListening();
       // adapterexp1.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapterexp.stopListening();
        adapterexp1.stopListening();
        adapterexp2.stopListening();
       // adapterexp1.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetail);
        //textview=findViewById( R.id.txtview );

        //alreaady aopen hai to next pe jaega


        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),Dashboard.class );
            startActivity(mainActivity);
            finish();


        }


        submit=findViewById( R.id.submit );
        dob = findViewById(R.id.dob);
        adress = findViewById(R.id.adress);
        wanumber=findViewById( R.id.wanumber );
        yesbtn=findViewById( R.id.yes_exp );

        occupation = findViewById(R.id.occupation);
        schoolname10 = findViewById(R.id.schoolname10);
        schlstarty10 = findViewById(R.id.schlstarty10);
        schlendy10 = findViewById(R.id.schlendy10);
        percentage10 = findViewById(R.id.percentage10);
        schoolname12 = findViewById(R.id.schoolname12);
        schlstarty12 = findViewById(R.id.schlstarty12);
        schlendy12 = findViewById(R.id.schlendy12);
        percentage12 = findViewById(R.id.percentage12);
        collegename = findViewById(R.id.collegename);
        collegecourse = findViewById(R.id.collegecourse);
        collegedept = findViewById(R.id.collegedept);
        collegestart = findViewById(R.id.collegestart);
        collegeend = findViewById(R.id.collegeend);
        collegeper = findViewById(R.id.collegeper);
        final Spinner spinYear6 = (Spinner)findViewById(R.id.spinner6);
        final Spinner spinYear7 = (Spinner)findViewById(R.id.spinner7);
        companystartdate = findViewById(R.id.companystartdate);
        companyenddate = findViewById(R.id.companyenddate);
        companystartdate.setVisibility( View.GONE );
        companyenddate.setVisibility( View.GONE );
        spinYear6.setVisibility( View.GONE );
        spinYear7.setVisibility( View.GONE );
        final RelativeLayout layout_company = (RelativeLayout)findViewById(R.id.r_layout);
        final RelativeLayout layout_company1 = (RelativeLayout)findViewById(R.id.r_layout1);

        final RelativeLayout layout_addnewex = (RelativeLayout)findViewById(R.id.layout_addnewex);
        final RelativeLayout layout_askingex = (RelativeLayout)findViewById(R.id.layout_askingexp);
        final RelativeLayout layout_yes_no_ex = (RelativeLayout)findViewById(R.id.yes_no_exp);
        final RelativeLayout layout_workexptecxt = (RelativeLayout)findViewById(R.id.layout_workexp);
        yesbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_addnewex.setVisibility( View.VISIBLE );
                layout_askingex.setVisibility( View.GONE );
                layout_yes_no_ex.setVisibility( View.GONE );

            }
        } );


        submit.setOnClickListener( this );


        addnewexpbtn = findViewById(R.id.addnewexpshowbtn);


        stream1=findViewById( R.id.stream ); //12th
        board1=findViewById( R.id.board1 ); //12th
        board=findViewById( R.id.board );   //10th
        //stream1=findViewById( R.id.stream1 );
        //board1=findViewById( R.id.board1 );
        dipclgname=findViewById( R.id.dipclgname );
        dipcorsname=findViewById( R.id.dipcorsname );
        dippercentage=findViewById( R.id.dippercentage );

        nobtn=findViewById( R.id.no_exp );


        final View rlayout_high = findViewById( R.id.r_layout_high );
        final View rlayout1_high = findViewById( R.id.r_layout2_high );

        final View rlayout_yes_no = findViewById( R.id.yes_no_exp );
        final View rlayout_asking = findViewById( R.id.layout_askingexp );
        final View rlayout_divider = findViewById( R.id.divider );

        nobtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlayout_yes_no.setVisibility( View.GONE );
                rlayout_asking.setVisibility( View.GONE );
                rlayout_divider.setVisibility( View.GONE );
                layout_workexptecxt.setVisibility( View.GONE );

            }
        } );






        radioButton_high = (RadioButton)findViewById(R.id.radio_high);
        radioButton_diploma = (RadioButton)findViewById(R.id.radio_diploma);

        radioButton_high.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schoolname12.setVisibility( View.VISIBLE );
                rlayout_high.setVisibility( View.VISIBLE );
                rlayout1_high.setVisibility( View.VISIBLE );
                stream1.setVisibility( View.VISIBLE );
                board1.setVisibility( View.VISIBLE );
                percentage12.setVisibility( View.VISIBLE );

                dipclgname.setVisibility( View.GONE );
                dipcorsname.setVisibility( View.GONE );
                dippercentage.setVisibility( View.GONE );


            }
        } );

        radioButton_diploma.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlayout_high.setVisibility( View.GONE );
                rlayout1_high.setVisibility( View.GONE );
                schoolname12.setVisibility( View.GONE );
                stream1.setVisibility( View.GONE );
                board1.setVisibility( View.GONE );
                percentage12.setVisibility( View.GONE );


                dipclgname.setVisibility( View.VISIBLE );
                dipcorsname.setVisibility( View.VISIBLE );
                dippercentage.setVisibility( View.VISIBLE );







            }
        } );

//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, years);

        final Spinner spinYear = (Spinner)findViewById(R.id.spinner);
        spinYear.setAdapter(adapter);
        spinYear.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               pres_doctor = spinYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years1 = new ArrayList<String>();
        int thisYear1 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear1; i++) {
            years1.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_item, years1);

        final Spinner spinYear1 = (Spinner)findViewById(R.id.spinner1);
        spinYear1.setAdapter(adapter1);
        spinYear1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor1 = spinYear1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years2 = new ArrayList<String>();
        int thisYear2 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear2; i++) {
            years2.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_item, years2);

        final Spinner spinYear2 = (Spinner)findViewById(R.id.spinner2);
        spinYear2.setAdapter(adapter2);
        spinYear2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor2 = spinYear2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years3 = new ArrayList<String>();
        int thisYear3 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear3; i++) {
            years3.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.spinner_item, years3);

        final Spinner spinYear3 = (Spinner)findViewById(R.id.spinner3);
        spinYear3.setAdapter(adapter3);
        spinYear3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor3 = spinYear3.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years4 = new ArrayList<String>();
        int thisYear4 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear4; i++) {
            years4.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,R.layout.spinner_item, years4);

        final Spinner spinYear4 = (Spinner)findViewById(R.id.spinner4);
        spinYear4.setAdapter(adapter4);
        spinYear4.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor4 = spinYear4.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years5 = new ArrayList<String>();
        int thisYear5 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear5; i++) {
            years5.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter5= new ArrayAdapter<String>(this,R.layout.spinner_item, years5);

        final Spinner spinYear5 = (Spinner)findViewById(R.id.spinner5);
        spinYear5.setAdapter(adapter5);
        spinYear5.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor5 = spinYear5.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years6 = new ArrayList<String>();
        int thisYear6 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear6; i++) {
            years6.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,R.layout.spinner_item, years6);


        spinYear6.setAdapter(adapter6);
        spinYear6.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor6 = spinYear6.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

//------------------------------------------------------------------------------------------------//RITIK
        ArrayList<String> years7 = new ArrayList<String>();
        int thisYear7 = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1970; i <= thisYear7; i++) {
            years7.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this,R.layout.spinner_item, years7);


        spinYear7.setAdapter(adapter7);
        spinYear7.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pres_doctor7 = spinYear7.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );




//------------------------------------------------------------------------------------------------//RITIK


        /*spinYear.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schlstarty10.setText( finalYears );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );*/

        dob.setOnClickListener( this );

        companyname = findViewById(R.id.companyname);

        companyrole = findViewById(R.id.companyrole);
        companybenefits = findViewById(R.id.companybenefits);


//        expcount = findViewById(R.id.expxount);

        //textview.setPaintFlags(textview.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        addpersonaldet = findViewById(R.id.addpersonaldet_btn);
        addschool10det = findViewById(R.id.addschool10det_btn);
        addschool12det = findViewById(R.id.addschool12det_btn);
        addcollegedet = findViewById(R.id.addcollegedet_btn);
        addexpbtn = findViewById(R.id.addexp_btn);
        updateexpbtn = findViewById(R.id.updateexp_btn);


        displayname = findViewById(R.id.displayname);
           displayemail = findViewById(R.id.displayemail);
           displaymno=findViewById( R.id.displaymno );
        //------------------------------------------------------------------------------------------------//RITIK
        companyname1 = findViewById(R.id.companyname1);
        addexpbtn1 = findViewById(R.id.addexp_btn1);
        updateexpbtn1 = findViewById(R.id.updateexp_btn1);
        addnewexpbtn1 = findViewById(R.id.addnewexpshowbtn1);
        //------------------------------------------------------------------------------------------------------------------//ritik2
        companyname2 = findViewById(R.id.companyname2);
        addexpbtn2 = findViewById(R.id.addexp_btn2);
        updateexpbtn2 = findViewById(R.id.updateexp_btn2);
        addnewexpbtn2 = findViewById(R.id.addnewexpshowbtn2);
        //----------------------------------------------------------------------------------------------------------------

        companyname.setVisibility(View.GONE);
        companystartdate.setVisibility(View.GONE);
        companyenddate.setVisibility(View.GONE);
        companyrole.setVisibility(View.GONE);
        companybenefits.setVisibility(View.GONE);
        addexpbtn.setVisibility(View.GONE);
        updateexpbtn.setVisibility(View.GONE);
//------------------------------------------------------------------------------------------------------------------ritik
        companyname1.setVisibility(View.GONE);

        addexpbtn1.setVisibility(View.GONE);
        updateexpbtn1.setVisibility(View.GONE);

        //------------------------------------------------------------------------------------------------------------------
        companyname2.setVisibility(View.GONE);

        addexpbtn2.setVisibility(View.GONE);
        updateexpbtn2.setVisibility(View.GONE);
        //------------------------------------------------------------------------------------------------------------------

        DatabaseReference databaseReferencemain = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReferencemain.keepSynced(true);
        databaseReferencemain.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.getName();
                email = user.getEmail();
                phno = user.getContactn();
                uid = user.getUid();

                displayname.setText(user.getName());
                displayemail.setText(user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addnewexpbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyname2.setVisibility(View.VISIBLE);

                companyname2.clearComposingText();

                addexpbtn2.setVisibility(View.VISIBLE);
                updateexpbtn2.setVisibility(View.GONE);
                addnewexpbtn2.setVisibility(View.GONE);

            }
        });
        //---------------------------------------------------------------------------

        addnewexpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyname1.setVisibility(View.VISIBLE);

                companyname1.clearComposingText();

                addexpbtn1.setVisibility(View.VISIBLE);
                updateexpbtn1.setVisibility(View.GONE);
                addnewexpbtn1.setVisibility(View.GONE);

            }
        });
        //---------------------------------------------------------------------------
        rv_exp2 = findViewById(R.id.rv_exp2);
        rv_exp2.setHasFixedSize(true);
        rv_exp2.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceexprv2 = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cmpach");
        databaseReferenceexprv2.keepSynced(true);

        optionsexp2  = new FirebaseRecyclerOptions.Builder<addexp2_model>().setQuery(databaseReferenceexprv2,addexp2_model.class).build();

        adapterexp2 = new FirebaseRecyclerAdapter<addexp2_model, addexp2_viewholder>(optionsexp2) {
            @Override
            protected void onBindViewHolder(@NonNull final addexp2_viewholder holder2, int position, @NonNull final addexp2_model model) {
                holder2.companynamelayout.setText(model.getAchivmnts());
                holder2.companynamelayout.setVisibility( View.VISIBLE );

                holder2.ach_show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!holder2.expand){

                            holder2.companynamelayout.setVisibility( View.VISIBLE );

                            holder2.editexp.setVisibility(View.VISIBLE);
                            holder2.expand = true;
                        }
                        else {

                            holder2.companynamelayout.setVisibility(View.GONE);
                            holder2.editexp.setVisibility(View.GONE);
                            holder2.expand = false;
                        }
                    }
                });

                holder2.editexp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        holder2.editexp.setVisibility(View.GONE);
                        holder2.expand = false;

                        companyname2.setVisibility(View.VISIBLE);


                        addexpbtn2.setVisibility(View.GONE);
                        updateexpbtn2.setVisibility(View.VISIBLE);
                        addnewexpbtn2.setVisibility(View.GONE);

                        companyname2.setText(model.getAchivmnts());



                        updateexpbtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!companyname2.getText().toString().isEmpty()) {

                                    Log.d( ">> NOTWORKING", "onComplete: Here COME IN LOOP " );
                                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
                                    databaseReferencecmpexp.keepSynced( true );
                                    databaseReferencecmpexp.child( "cmpach" ).child( model.getExpid() ).child( "achivmnts" ).setValue( companyname2.getText().toString() );


                                    databaseReferencecmpexp.child( "cmpach" ).child( model.getExpid() ).child( "expid" ).setValue( model.getExpid() );

                                    companyname2.setVisibility( View.GONE );


                                    addexpbtn2.setVisibility( View.GONE );
                                    updateexpbtn2.setVisibility( View.GONE );
                                    addnewexpbtn2.setVisibility( View.VISIBLE );
                                }
                                else {
                                    companyname2.setError("Empty");
                                }
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public addexp2_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp2_viewholder(LayoutInflater.from(Studentdetail.this).inflate(R.layout.exp2_card_layout,parent,false));
            }
        };

        rv_exp2.setAdapter(adapterexp2);
        adapterexp2.startListening();

          //---------------------------------------------------------------------------
        rv_exp1 = findViewById(R.id.rv_exp1);
        rv_exp1.setHasFixedSize(true);
        rv_exp1.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceexprv1 = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cmpskills");
        databaseReferenceexprv1.keepSynced(true);

        optionsexp1  = new FirebaseRecyclerOptions.Builder<addexp1_model>().setQuery(databaseReferenceexprv1,addexp1_model.class).build();

        adapterexp1 = new FirebaseRecyclerAdapter<addexp1_model, addexp1_viewholder>(optionsexp1) {
            @Override
            protected void onBindViewHolder(@NonNull final addexp1_viewholder holder1, int position, @NonNull final addexp1_model model) {
                holder1.companynamelayout.setText(model.getSkills());

                holder1.show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!holder1.expand){

                            holder1.companynamelayout.setVisibility(View.VISIBLE);

                            holder1.editexp.setVisibility(View.VISIBLE);
                            holder1.expand = true;
                        }
                        else {

                            holder1.companynamelayout.setVisibility(View.GONE);
                            holder1.editexp.setVisibility(View.GONE);
                            holder1.expand = false;
                        }
                    }
                });

                holder1.editexp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        holder1.editexp.setVisibility(View.GONE);
                        holder1.expand = false;

                        companyname1.setVisibility(View.VISIBLE);


                        addexpbtn1.setVisibility(View.GONE);
                        updateexpbtn1.setVisibility(View.VISIBLE);
                        addnewexpbtn1.setVisibility(View.GONE);

                        companyname1.setText(model.getSkills());



                        updateexpbtn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!companyname1.getText().toString().isEmpty()) {

                                    Log.d( ">> NOTWORKING", "onComplete: Here COME IN LOOP " );
                                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
                                    databaseReferencecmpexp.keepSynced( true );
                                    databaseReferencecmpexp.child( "cmpskills" ).child( model.getExpid() ).child( "skills" ).setValue( companyname1.getText().toString() );


                                    databaseReferencecmpexp.child( "cmpskills" ).child( model.getExpid() ).child( "expid" ).setValue( model.getExpid() );

                                    companyname1.setVisibility( View.GONE );


                                    addexpbtn1.setVisibility( View.GONE );
                                    updateexpbtn1.setVisibility( View.GONE );
                                    addnewexpbtn1.setVisibility( View.VISIBLE );
                                }
                                else {
                                    companyname1.setError("Empty");
                                }
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public addexp1_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp1_viewholder(LayoutInflater.from(Studentdetail.this).inflate(R.layout.exp1_card_layout,parent,false));
            }
        };

        rv_exp1.setAdapter(adapterexp1);
        adapterexp1.startListening();



        //------------------------------------------------------------------------------------------------------------------

        rv_exp = findViewById(R.id.rv_exp);
        rv_exp.setHasFixedSize(true);
        rv_exp.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceexprv = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cmpexp");
        databaseReferenceexprv.keepSynced(true);

        optionsexp = new FirebaseRecyclerOptions.Builder<addexp_model>().setQuery(databaseReferenceexprv,addexp_model.class).build();

        adapterexp = new FirebaseRecyclerAdapter<addexp_model, addexp_viewholder>(optionsexp) {
            @Override
            protected void onBindViewHolder(@NonNull final addexp_viewholder holder, int position, @NonNull final addexp_model model) {
                holder.companynamelayout.setText(model.getCompanyname());
                holder.companystartlayout.setText(model.getCompanystart());
                holder.companyendlayout.setText(model.getCompanyend());
                holder.companyrolelayout.setText(model.getCompanyrole());
                holder.companybenefitslayout.setText(model.getCompanybenefits());


                holder.companynamelayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.expand == false){
                            holder.companystartlayout.setVisibility(View.VISIBLE);
                            holder.companyendlayout.setVisibility(View.VISIBLE);
                            holder.companyrolelayout.setVisibility(View.VISIBLE);
                            holder.companybenefitslayout.setVisibility(View.VISIBLE);
                            holder.editexp.setVisibility(View.VISIBLE);
                            holder.cancelbtn.setVisibility( View.VISIBLE );
                            holder.expand = true;

                        }
                        else {
                            holder.companystartlayout.setVisibility(View.GONE);
                            holder.companyendlayout.setVisibility(View.GONE);
                            holder.companyrolelayout.setVisibility(View.GONE);
                            holder.companybenefitslayout.setVisibility(View.GONE);
                            holder.editexp.setVisibility(View.GONE);
                            holder.cancelbtn.setVisibility( View.GONE );
                            holder.expand = false;
                        }
                    }
                });


                holder.editexp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.companystartlayout.setVisibility(View.GONE);
                        holder.companyendlayout.setVisibility(View.GONE);
                        holder.companyrolelayout.setVisibility(View.GONE);
                        holder.companybenefitslayout.setVisibility(View.GONE);
                        holder.editexp.setVisibility(View.GONE);
                        holder.expand = false;

                        companyname.setVisibility(View.VISIBLE);
                        companystartdate.setVisibility(View.VISIBLE);
                        companyenddate.setVisibility(View.VISIBLE);
                        companyrole.setVisibility(View.VISIBLE);
                        companybenefits.setVisibility(View.VISIBLE);
                        addexpbtn.setVisibility(View.GONE);
                        updateexpbtn.setVisibility(View.VISIBLE);
                        addnewexpbtn.setVisibility(View.GONE);

                        companyname.setText(model.getCompanyname());
                        companystartdate.setText(model.getCompanystart());
                        companyenddate.setText(model.getCompanyend());
                        companyrole.setText(model.getCompanyrole());
                        companybenefits.setText(model.getCompanybenefits());





                        updateexpbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!companyname.getText().toString().isEmpty()){
                                    if (!companystartdate.getText().toString().isEmpty()){
                                        if (!companyenddate.getText().toString().isEmpty()){
                                            if (!companyrole.getText().toString().isEmpty()){
                                                if (!companybenefits.getText().toString().isEmpty()){

                                                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    databaseReferencecmpexp.keepSynced(true);
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("companyname").setValue(companyname.getText().toString());
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("companystart").setValue(pres_doctor6);
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("companyend").setValue(pres_doctor7);
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("companyrole").setValue(companyrole.getText().toString());
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("companybenefits").setValue(companybenefits.getText().toString());
                                                    databaseReferencecmpexp.child("cmpexp").child(model.getExpid()).child("expid").setValue(model.getExpid());

                                                    companyname.setVisibility(View.GONE);
                                                    companystartdate.setVisibility(View.GONE);
                                                    companyenddate.setVisibility(View.GONE);
                                                    companyrole.setVisibility(View.GONE);
                                                    companybenefits.setVisibility(View.GONE);
                                                    addexpbtn.setVisibility(View.GONE);
                                                    updateexpbtn.setVisibility(View.GONE);
                                                    addnewexpbtn.setVisibility(View.VISIBLE);

                                                }
                                                else {
                                                    companybenefits.setError("Empty");
                                                }
                                            }
                                            else {
                                                companyrole.setError("Empty");
                                            }
                                        }
                                        else {
                                            companyenddate.setError("Empty");
                                        }
                                    }
                                    else {
                                        companystartdate.setError("Empty");
                                    }
                                }
                                else {
                                    companyname.setError("Empty");
                                }
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public addexp_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp_viewholder(LayoutInflater.from(Studentdetail.this).inflate(R.layout.exp_card_layout,parent,false));
            }
        };

        rv_exp.setAdapter(adapterexp);
        adapterexp.startListening();

        //------------------------------------------------------------------------------------------------------------------

        addpersonaldet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dob.getText().toString().isEmpty())
                {c++;
                    if (!adress.getText().toString().isEmpty())
                    {c++;
                        if(!wanumber.getText().toString().isEmpty()) {c++;
                            if (!occupation.getText().toString().isEmpty()) {c++;
                                //----------------------------success


                                DatabaseReference databaseReferencepd = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
                                databaseReferencepd.keepSynced( true );
                                databaseReferencepd.child( "personaldet" ).child( "dob" ).setValue( dob.getText().toString() );
                                databaseReferencepd.child( "personaldet" ).child( "adress" ).setValue( adress.getText().toString() );
                                databaseReferencepd.child( "personaldet" ).child( "occupation" ).setValue( occupation.getText().toString() );
                                databaseReferencepd.child( "personaldet" ).child( "wanumber" ).setValue( wanumber.getText().toString() );

                                databaseReferencepd.child( "personaldet" ).child( "name" ).setValue( name );
                                databaseReferencepd.child( "personaldet" ).child( "uid" ).setValue( uid );
                                databaseReferencepd.child( "personaldet" ).child( "email" ).setValue( email );
                                databaseReferencepd.child( "personaldet" ).child( "phnumber" ).setValue( phno );


                                addpersonaldet.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_green_24dp, 0, 0, 0 );
                                addpersonaldet.setCompoundDrawablePadding( 5 );



                            } else {
                                occupation.setError( "Empty" );
                            }
                        }

                        else{
                            wanumber.setError( "Empty" );

                            }
                        }

                    else
                    {
                        adress.setError("Empty");
                    }

                }
                else
                {
                    dob.setError("Empty");
                }
            }
        });

        addschool10det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!board.getText().toString().isEmpty()){c++;
                if (!schoolname10.getText().toString().isEmpty()){c++;
                    if (!schlstarty10.getText().toString().isEmpty()){c++;
                        if (!schlendy10.getText().toString().isEmpty()){c++;
                            if (!percentage10.getText().toString().isEmpty()){c++;

                                DatabaseReference databaseReferenceschl10 = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                databaseReferenceschl10.keepSynced(true);
                                databaseReferenceschl10.child("school10").child("schoolname").setValue(schoolname10.getText().toString());
                                databaseReferenceschl10.child("school10").child("schoolstarty").setValue(pres_doctor);
                                databaseReferenceschl10.child("school10").child("schoolendy").setValue(pres_doctor1);
                                databaseReferenceschl10.child("school10").child("schoolper").setValue(percentage10.getText().toString());
                                databaseReferenceschl10.child("school10").child("schoolboard").setValue(board.getText().toString());
                                addschool10det.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_green_24dp,0,0,0);
                                addschool10det.setCompoundDrawablePadding(5);

                            }
                            else {
                                percentage10.setError("Empty");
                            }
                        }
                        else {
                            schlendy10.setError("Empty");
                        }
                    }
                    else {
                        schlstarty10.setError("Empty");
                    }
                }
                else {
                    schoolname10.setError("Empty");
                }
            }
                else {
                board.setError("Empty");
            }
            }
        });

        addschool12det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stream1.getText().toString().isEmpty()) {c++;

                if (!board1.getText().toString().isEmpty()) {c++;

                if (!schoolname12.getText().toString().isEmpty()) {c++;
                    if (!schlstarty12.getText().toString().isEmpty()) {c++;
                        if (!schlendy12.getText().toString().isEmpty()) {c++;
                            if (!percentage12.getText().toString().isEmpty()) {c++;

                                DatabaseReference databaseReferenceschl12 = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
                                databaseReferenceschl12.keepSynced( true );
                                databaseReferenceschl12.child( "school12" ).child( "schoolname" ).setValue( schoolname12.getText().toString() );
                                databaseReferenceschl12.child( "school12" ).child( "schoolstarty" ).setValue( pres_doctor2 );
                                databaseReferenceschl12.child( "school12" ).child( "schoolendy" ).setValue( pres_doctor3 );
                                databaseReferenceschl12.child( "school12" ).child( "schoolper" ).setValue( percentage12.getText().toString() );
                                databaseReferenceschl12.child( "school12" ).child( "schoolstream" ).setValue( stream1.getText().toString() );
                                databaseReferenceschl12.child( "school12" ).child( "schoolboard" ).setValue( board1.getText().toString() );

                                addschool12det.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_green_24dp, 0, 0, 0 );
                                addschool12det.setCompoundDrawablePadding( 5 );

                            } else {
                                percentage12.setError( "Empty" );
                            }
                        } else {
                            schlendy12.setError( "Empty" );
                        }
                    } else {
                        schlstarty12.setError( "Empty" );
                    }
                } else {
                    schoolname12.setError( "Empty" );
                }
            }
                else

            {
                board1.setError( "Empty" );
            }
        }
                else {
                    stream1.setError("Empty");
                }
            }
        });

        addcollegedet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!collegename.getText().toString().isEmpty()){c++;
                    if (!collegecourse.getText().toString().isEmpty()){c++;
                        if (!collegedept.getText().toString().isEmpty()){c++;
                            if (!collegestart.getText().toString().isEmpty()){c++;
                                if (!collegeend.getText().toString().isEmpty()){c++;
                                    if (!collegeper.getText().toString().isEmpty()){c++;

                                        DatabaseReference databaseReferenceclgdet = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        databaseReferenceclgdet.keepSynced(true);
                                        databaseReferenceclgdet.child("collegedet").child("collegename").setValue(collegename.getText().toString());
                                        databaseReferenceclgdet.child("collegedet").child("collegecourse").setValue(collegecourse.getText().toString());
                                        databaseReferenceclgdet.child("collegedet").child("collegedept").setValue(collegedept.getText().toString());
                                        databaseReferenceclgdet.child("collegedet").child("collegestart").setValue( pres_doctor4 );
                                        databaseReferenceclgdet.child("collegedet").child("collegeend").setValue(pres_doctor5);
                                        databaseReferenceclgdet.child("collegedet").child("collegeper").setValue(collegeper.getText().toString());

                                        addcollegedet.setCompoundDrawablePadding(5);
                                        addcollegedet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_green_24dp,0,0,0);

                                    }
                                    else {
                                        collegeper.setError("Empty");
                                    }
                                }
                                else {
                                    collegeend.setError("Empty");
                                }
                            }
                            else {
                                collegestart.setError("Empty");
                            }
                        }
                        else {
                            collegedept.setError("Empty");
                        }
                    }
                    else {
                        collegecourse.setError("Empty");
                    }
                }
                else {
                    collegename.setError("Empty");
                }
            }
        });

        addnewexpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyname.setVisibility(View.VISIBLE);
                layout_company.setVisibility( View.VISIBLE );
                layout_company1.setVisibility( View.VISIBLE );
                companystartdate.setVisibility(View.VISIBLE);
                companyenddate.setVisibility(View.VISIBLE);
                companyrole.setVisibility(View.VISIBLE);
                companybenefits.setVisibility(View.VISIBLE);
                companyname.clearComposingText();
                companystartdate.clearComposingText();
                companyenddate.clearComposingText();
                companyrole.clearComposingText();
                companybenefits.clearComposingText();
                addexpbtn.setVisibility(View.VISIBLE);
                updateexpbtn.setVisibility(View.GONE);
                addnewexpbtn.setVisibility(View.GONE);
                spinYear6.setVisibility( View.VISIBLE );
                spinYear7.setVisibility( View.VISIBLE );
            //    expcount.setVisibility(View.GONE);
            }
        });
        addnewexpbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyname2.setVisibility(View.VISIBLE);

                companyname2.clearComposingText();

                addexpbtn2.setVisibility(View.VISIBLE);
                updateexpbtn2.setVisibility(View.GONE);
                addnewexpbtn2.setVisibility(View.GONE);

            }
        });

        addexpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!companyname.getText().toString().isEmpty()){
                    if (!companystartdate.getText().toString().isEmpty()){
                        if (!companyenddate.getText().toString().isEmpty()){
                            if (!companyrole.getText().toString().isEmpty()){
                                if (!companybenefits.getText().toString().isEmpty()){

                                    expcounter++;

                                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    databaseReferencecmpexp.keepSynced(true);
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("companyname").setValue(companyname.getText().toString());
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("companystart").setValue(pres_doctor6);
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("companyend").setValue(pres_doctor7);
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("companyrole").setValue(companyrole.getText().toString());
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("companybenefits").setValue(companybenefits.getText().toString());
                                    databaseReferencecmpexp.child("cmpexp").child(String.valueOf(expcounter)).child("expid").setValue(String.valueOf(expcounter));

                                    companyname.setVisibility(View.GONE);
                                    companystartdate.setVisibility(View.GONE);
                                    companyenddate.setVisibility(View.GONE);
                                    companyrole.setVisibility(View.GONE);
                                    companybenefits.setVisibility(View.GONE);
                                    addexpbtn.setVisibility(View.GONE);
                                    updateexpbtn.setVisibility(View.GONE);
                                    addnewexpbtn.setVisibility(View.VISIBLE);
                                    layout_company.setVisibility( View.GONE );
                                    layout_company1.setVisibility( View.GONE );
                                    spinYear6.setVisibility( View.GONE );
                                    spinYear7.setVisibility( View.GONE );
                                }
                                else {
                                    companybenefits.setError("Empty");
                                }
                            }
                            else {
                                companyrole.setError("Empty");
                            }
                        }
                        else {
                            companyenddate.setError("Empty");
                        }
                    }
                    else {
                        companystartdate.setError("Empty");
                    }
                }
                else {
                    companyname.setError("Empty");
                }
            }
        });

        //----------------------------------------------------------------------------------------------------------------------------
        addexpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!companyname1.getText().toString().isEmpty()){
                                    skillcount++;

                                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    databaseReferencecmpexp.keepSynced(true);
                    databaseReferencecmpexp.child("cmpskills").child(String.valueOf(skillcount)).child("skills").setValue(companyname1.getText().toString());
                    databaseReferencecmpexp.child("cmpskills").child(String.valueOf(skillcount)).child("expid").setValue(String.valueOf( skillcount ));
                                    companyname1.setVisibility(View.GONE);
                                    addexpbtn1.setVisibility(View.GONE);
                                    updateexpbtn1.setVisibility(View.GONE);
                                    addnewexpbtn1.setVisibility(View.VISIBLE);
                                }

                     else {
                    companyname1.setError("Empty");
                }
            }
        });


        addexpbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!companyname2.getText().toString().isEmpty()){
                    achcount++;

                    DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    databaseReferencecmpexp.keepSynced(true);
                    databaseReferencecmpexp.child("cmpach").child(String.valueOf(achcount)).child("achivmnts").setValue(companyname2.getText().toString());
                    databaseReferencecmpexp.child("cmpach").child(String.valueOf(achcount)).child("expid").setValue(String.valueOf( achcount ));
                    companyname2.setVisibility(View.GONE);
                    addexpbtn2.setVisibility(View.GONE);
                    updateexpbtn2.setVisibility(View.GONE);
                    addnewexpbtn2.setVisibility(View.VISIBLE);
                }

                else {
                    companyname2.setError("Empty");
                }
            }
        });






    }

    private boolean restorePrefData() {


        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("myPrefs1",MODE_PRIVATE);
        Boolean isStudentActivityOpnendBefore = pref1.getBoolean("isIntroOpnend",false);
        return  isStudentActivityOpnendBefore;



    }
    private void savePrefsData() {

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("myPrefs1",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    @Override
    public void onClick(View v) {

        if (v == dob) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get( Calendar.YEAR );
            mMonth = c.get( Calendar.MONTH );
            mDay = c.get( Calendar.DAY_OF_MONTH );


            DatePickerDialog datePickerDialog = new DatePickerDialog( this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dob.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );

                        }
                    }, mYear, mMonth, mDay );
            datePickerDialog.show();
        }
        if(v==submit){
           
            if(c>=21){
                savePrefsData();
                Intent intent = new Intent( Studentdetail.this, Dashboard.class );
                startActivity( intent );
                finish();
            }
            else{
                Toast.makeText(Studentdetail.this,"PLease fill all the required details",Toast.LENGTH_SHORT).show();
            }


        }








    }


}
