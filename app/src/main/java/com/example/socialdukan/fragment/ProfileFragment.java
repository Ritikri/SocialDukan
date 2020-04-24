package com.example.socialdukan.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.socialdukan.Dashboard;
import com.example.socialdukan.Edit_profile;
import com.example.socialdukan.Login_Student;
import com.example.socialdukan.R;
import com.example.socialdukan.Studentdetail;
import com.example.socialdukan.User;
import com.example.socialdukan.addexp1_model;
import com.example.socialdukan.addexp1_viewholder;
import com.example.socialdukan.addexp2_model;
import com.example.socialdukan.addexp2_viewholder;
import com.example.socialdukan.addexp_model;
import com.example.socialdukan.addexp_viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{

Button matri_btn,inter_btn,dip_btn,clg_btn;
    RecyclerView rv_exp,rv_exp1;//RITIK
    FirebaseRecyclerOptions<addexp_model> optionsexp;
    FirebaseRecyclerOptions<addexp1_model> optionsexp1;  //Ritik

    FirebaseRecyclerAdapter<addexp_model,addexp_viewholder> adapterexp;
    FirebaseRecyclerAdapter<addexp1_model,addexp1_viewholder>adapterexp1; //Ritik

    DatabaseReference databaseReferenceexprv,databaseReferenceexprv1;
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onStop() {
        super.onStop();
        adapterexp.stopListening();
        adapterexp1.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterexp.startListening();
        adapterexp1.startListening();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView dob,address,occ,wa_no;

    DatabaseReference reff;

    TextView name_user;
    TextView user_email;
    ImageView edit,imageuser;
    boolean perdet_1 = true;
    boolean ed_deta = true;
    boolean work_exp = true;
    boolean abili = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_profile, container, false );

        dob = view.findViewById( R.id.dob_text );
        address = view.findViewById( R.id.add_text );
        occ = view.findViewById( R.id.curr_occ_text );
        wa_no = view.findViewById( R.id.wa_no_text );
        imageuser = view.findViewById(R.id.image_user);
        name_user = view.findViewById(R.id.name_user);
        user_email = view.findViewById(R.id.user_email);
        edit=view.findViewById( R.id.edit_profile );
        matri_btn=view.findViewById( R.id.matr_btn );
        inter_btn=view.findViewById( R.id.inter_btn );
        dip_btn=view.findViewById( R.id.dip_btn );
        clg_btn=view.findViewById( R.id.college_btn );


        final RelativeLayout layout_profile1 = (RelativeLayout)view.findViewById(R.id.pers_detail1);
        final RelativeLayout perdet1 = (RelativeLayout)view.findViewById(R.id.layout_perdet1);
        perdet1.setVisibility(View.GONE);
        final RelativeLayout eddet1 = (RelativeLayout)view.findViewById(R.id.layout_eddet1);
        final RelativeLayout work_det = (RelativeLayout)view.findViewById(R.id.work_det);
        final RelativeLayout abi_det = (RelativeLayout)view.findViewById(R.id.abi_det);
        final RelativeLayout layout_profile2 = (RelativeLayout)view.findViewById(R.id.ed_detail1);
        final RelativeLayout layout_profile3 = (RelativeLayout)view.findViewById(R.id.pers_detail3);
        final RelativeLayout layout_profile4 = (RelativeLayout)view.findViewById(R.id.pers_detail4);
        final RelativeLayout layout_signout = (RelativeLayout)view.findViewById(R.id.layout_signout);
        rv_exp1 = view.findViewById(R.id.rv_exp1);
        rv_exp1.setHasFixedSize(true);
        rv_exp1.setLayoutManager(new LinearLayoutManager(getActivity()));

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

                            holder1.editexp.setVisibility(View.GONE);
                            holder1.expand = true;
                        }
                        else {

                            holder1.companynamelayout.setVisibility(View.GONE);
                            holder1.editexp.setVisibility(View.GONE);
                            holder1.expand = false;
                        }
                    }
                });



            }

            @NonNull
            @Override
            public addexp1_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp1_viewholder(LayoutInflater.from(getActivity()).inflate(R.layout.exp1_card_layout,parent,false));
            }
        };

        rv_exp1.setAdapter(adapterexp1);
        adapterexp1.startListening();

//-------------------------------------------------------------------------------------------
        rv_exp = view.findViewById( R.id.rv_exp );
        rv_exp.setHasFixedSize( true );
      //  rv_exp.setLayoutManager( new LinearLayoutManager( this ) );
        rv_exp.setLayoutManager( new LinearLayoutManager(  getContext()) );

        databaseReferenceexprv = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() ).child( "cmpexp" );
        databaseReferenceexprv.keepSynced( true );

        optionsexp = new FirebaseRecyclerOptions.Builder<addexp_model>().setQuery( databaseReferenceexprv, addexp_model.class ).build();

        adapterexp = new FirebaseRecyclerAdapter<addexp_model, addexp_viewholder>( optionsexp ) {

            @Override
            protected void onBindViewHolder(@NonNull final addexp_viewholder holder, int position, @NonNull final addexp_model model) {
                holder.companynamelayout.setText( model.getCompanyname() );
                holder.companystartlayout.setText( model.getCompanystart() );
                holder.companyendlayout.setText( model.getCompanyend() );
                holder.companyrolelayout.setText( model.getCompanyrole() );
                holder.companybenefitslayout.setText( model.getCompanybenefits() );


                holder.companynamelayout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.expand == false) {
                            holder.companystartlayout.setVisibility( View.VISIBLE );
                            holder.companyendlayout.setVisibility( View.VISIBLE );
                            holder.companyrolelayout.setVisibility( View.VISIBLE );
                            holder.companybenefitslayout.setVisibility( View.VISIBLE );

                            holder.expand = true;

                        } else {
                            holder.companystartlayout.setVisibility( View.GONE );
                            holder.companyendlayout.setVisibility( View.GONE );
                            holder.companyrolelayout.setVisibility( View.GONE );
                            holder.companybenefitslayout.setVisibility( View.GONE );
                            holder.editexp.setVisibility( View.GONE );
                            holder.cancelbtn.setVisibility( View.GONE );
                            holder.expand = false;
                        }
                    }
                } );
            }

            @NonNull
            @Override
            public addexp_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp_viewholder( LayoutInflater.from( getActivity()).inflate( R.layout.exp_card_layout, parent, false ) );
            }



        };
        rv_exp.setAdapter(adapterexp);
        adapterexp.startListening();
        //----------------------------------------------------------------------------------------------------------------------------------
        final DatabaseReference databaseReferencemain = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReferencemain.keepSynced(true);
        databaseReferencemain.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if (user.profileimg!=null){
                        Picasso.get().load(user.profileimg).into(imageuser);
                    }
                    else {
                        imageuser.setImageResource(R.drawable.user);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//----------------------------------------------------------------------------------------------------------------------------------

        reff=FirebaseDatabase.getInstance().getReference().child( "Profile" ).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.keepSynced(true);
        reff.orderByChild("uid").equalTo("per"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Personaldet_md personaldet_md = dataSnapshot1.getValue(Personaldet_md.class);

                    dob.setText(personaldet_md.getDob());
                    address.setText(personaldet_md.getAdress());
                    occ.setText(personaldet_md.getOccupation());
                    wa_no.setText(personaldet_md.getWanumber());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


        //-------------------------------------------------------------------------------------------------

        /*layout_profile1.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), Profile_activity1.class);
            startActivity(intent);
    }
        } );*/
        layout_profile3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (work_exp){
                    work_exp = false;
                    work_det.setVisibility(View.VISIBLE);
                }
                else {
                    work_exp = true;
                    work_det.setVisibility(View.GONE);
                }
            }
        } );
        layout_profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_deta){
                    ed_deta = false;
                    eddet1.setVisibility(View.VISIBLE);
                    matri_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Profile_activity1.class);

                            startActivity(intent);

                        }
                    } );
                    inter_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Profile_activity2.class);

                            startActivity(intent);

                        }
                    } );
                    dip_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Profile_activity3.class);

                            startActivity(intent);

                        }
                    } );
                    clg_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), Profile_activity4.class);

                            startActivity(intent);

                        }
                    } );
                }
                else {
                    ed_deta = true;
                    eddet1.setVisibility(View.GONE);
                }
            }
        });
        layout_profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (perdet_1){
                    perdet_1 = false;
                    perdet1.setVisibility(View.VISIBLE);
                }
                else {
                    perdet_1 = true;
                    perdet1.setVisibility(View.GONE);
                }
            }
        });


        //-------------------------------------------------------------------------------------------------
        layout_profile4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abili){
                    abili = false;
                    abi_det.setVisibility(View.VISIBLE);
                }
                else {
                    abili = true;
                    abi_det.setVisibility(View.GONE);
                }
            }
        } );
        //-------------------------------------------------------------------------------------------------
        edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_profile.class);

                startActivity(intent);
            }
        } );

        //-------------------------------------------------------------------------------------------------
        layout_signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { AlertDialog.Builder builder = new AlertDialog.Builder( Objects.requireNonNull( getActivity() ) );
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.dukan_w);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mFirebaseAuth.getInstance().signOut();
                                //saving session

                                Intent intent = new Intent(getActivity(), Login_Student.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        } );
        //-------------------------------------------------------------------------------------------------
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);

                    name_user.setText(user.name);
                    user_email.setText(user.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }




    //-------------------------------------------------------------------------------------------------
}