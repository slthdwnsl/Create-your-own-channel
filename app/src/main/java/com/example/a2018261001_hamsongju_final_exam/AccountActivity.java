package com.example.a2018261001_hamsongju_final_exam;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView user_profile_image;
    TextView username, email;
    Button logoutBtn;

    TextView txt_channel, txt_settings, txt_help;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;

    String p;

    GoogleSignInClient googleSignInClient;
    public static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logoutBtn = findViewById(R.id.logout_btn);

        init();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gsc = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(AccountActivity.this, gsc);

        getData();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInClient.signOut();
                auth.signOut();
                Intent mainIntent = new Intent(AccountActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

        txt_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserHaveChannel();
            }
        });

        txt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        txt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="https://support.google.com/youtube/?hl=ko#topic=9257498";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void checkUserHaveChannel() {
        reference.child("Channels").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Intent intent = new Intent(AccountActivity.this,MainActivity.class);
                    intent.putExtra("type","channel");
                    startActivity(intent);
                }else {
                    showDialogue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogue() {
        Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.channel_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        EditText input_channel_name = dialog.findViewById(R.id.input_channel_name);
        EditText input_description = dialog.findViewById(R.id.input_description);
        TextView txt_create_channel = dialog.findViewById(R.id.txt_create_channel);

        txt_create_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input_channel_name.getText().toString();
                String description = input_description.getText().toString();

                if (name.isEmpty() || description.isEmpty()){
                    Toast.makeText(AccountActivity.this,"Fill requested fields",Toast.LENGTH_SHORT).show();
                }else {
                    createNewChannel(name,description,dialog);
                }
            }
        });
        dialog.show();
    }

    private void createNewChannel(String name, String description, Dialog dialog) {
        ProgressDialog progressDialog = new ProgressDialog(AccountActivity.this);
        progressDialog.setTitle("새 채널");
        progressDialog.setMessage("생성중...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String data = DateFormat.getDateInstance().format(new Date());

        HashMap<String, Object> map = new HashMap<>();
        map.put("channel_name",name);
        map.put("description",description);
        map.put("joined",data);
        map.put("uid",user.getUid());
        map.put("channel_logo",p);

        reference.child("Channels").child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(AccountActivity.this,name+"channel has been created",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(AccountActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        reference.child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String n = snapshot.child("username").getValue().toString();
                    String e = snapshot.child("email").getValue().toString();
                    p = snapshot.child("profile").getValue().toString();

                    username.setText(n);
                    email.setText(e);

                    try{
                        Picasso.get().load(p).placeholder(R.drawable.ic_baseline_account_circle_24).into(user_profile_image);
                    }catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user_profile_image = findViewById(R.id.user_profile_image);
        username = findViewById(R.id.user_channel_name);
        email = findViewById(R.id.email);
        txt_channel = findViewById(R.id.txt_channel_name);
        txt_settings = findViewById(R.id.txt_settings);
        txt_help = findViewById(R.id.txt_help);
    }
}
