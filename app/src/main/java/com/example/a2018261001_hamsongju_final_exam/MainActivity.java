package com.example.a2018261001_hamsongju_final_exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2018261001_hamsongju_final_exam.fragment.HomeFragment;
import com.example.a2018261001_hamsongju_final_exam.fragment.LibraryFragment;
import com.example.a2018261001_hamsongju_final_exam.fragment.CommunityFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    AppBarLayout appBarLayout;

    Fragment fragment;

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    private static final int PERMISSION = 101;
    private static final int PICK_VIDEO = 102;

    ImageView user_profile_image;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firestore;

    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frame_layout);
        appBarLayout = findViewById(R.id.appBar);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        user_profile_image = findViewById(R.id.user_profile_image);

        checkPermission();

        getProfileImage();

        GoogleSignInOptions gsc = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this,gsc);

        showFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        selectedFragment(homeFragment);
                        break;
                    case R.id.shorts:
                        Intent intent = new Intent(MainActivity.this, ShortsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.publish:
                        showPublishContentDialog();
                        break;
                    case R.id.forum:
                        CommunityFragment subscriptionsFragment = new CommunityFragment();
                        selectedFragment(subscriptionsFragment);
                        break;
                    case R.id.library:
                        LibraryFragment libraryFragment = new LibraryFragment();
                        selectedFragment(libraryFragment);
                        break;
                }

                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);

        user_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    startActivity(new Intent(MainActivity.this,AccountActivity.class));
                    getProfileImage();
                }else {
                    user_profile_image.setImageResource(R.drawable.ic_baseline_account_circle_24);
                    showDialogue();
                }
            }
        });
        showFragment();
    }

    private void showPublishContentDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.upload_dialogue);
        dialog.setCanceledOnTouchOutside(true);

        LinearLayout upload_linearlayout = dialog.findViewById(R.id.upload_linearlayout);
        LinearLayout post_linearlayout = dialog.findViewById(R.id.post_linearlayout);
        LinearLayout streaming = dialog.findViewById(R.id.streaming);

        upload_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "Select video"), PICK_VIDEO);
            }
        });

        post_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MakePostActivity.class);
                startActivity(intent);
            }
        });

        streaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        dialog.show();
    }

    private void showDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_signin_dialogue, viewGroup, false);

        builder.setView(view);

        TextView txt_google_signIn = view.findViewById(R.id.txt_google_signIn);
        txt_google_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        builder.create().show();
    }

    private void signIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                if (requestCode == RC_SIGN_IN) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if (task.isSuccessful()){

                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("username",account.getDisplayName());
                                    map.put("email",account.getEmail());
                                    map.put("profile",String.valueOf(account.getPhotoUrl()));
                                    map.put("uid",firebaseUser.getUid());
                                    map.put("search",account.getDisplayName().toLowerCase());

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                                    reference.child(firebaseUser.getUid()).setValue(map);
                                    getProfileImage();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    catch (Exception e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case PICK_VIDEO:
                if (resultCode == RESULT_OK && data != null) {
                    videoUri = data.getData();
                    Intent intent = new Intent(MainActivity.this,PublishContentActivity.class);
                    intent.putExtra("type","video");
                    intent.setData(videoUri);
                    startActivity(intent);
                }
        }
    }

    private void selectedFragment(Fragment fragment){
        setStatusBarColor("#FFFFFF");
        appBarLayout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Toast.makeText(this,"Notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void getProfileImage() {
        if(user==null){
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String p = snapshot.child("profile").getValue().toString();

                    Picasso.get().load(p).placeholder(R.drawable.ic_baseline_account_circle_24)
                            .into(user_profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFragment() {
        String type = getIntent().getStringExtra("type");
        if (type != null){
            switch(type){
                case "channel":
                    setStatusBarColor("#99FF0000");
                    appBarLayout.setVisibility(View.GONE);
                    fragment = ChannelDashboardFragment.newInstance();
                    break;
            }
            if (fragment != null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit();
            }else {
                Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setStatusBarColor(String color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION);
        } else {
            Log.d("tag","checkPermission: Permission granted");
        }
    }
}
