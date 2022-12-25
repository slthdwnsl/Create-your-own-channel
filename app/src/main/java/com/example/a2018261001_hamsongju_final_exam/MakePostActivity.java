package com.example.a2018261001_hamsongju_final_exam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2018261001_hamsongju_final_exam.Models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MakePostActivity extends AppCompatActivity {

    ImageView exit, gallery;
    TextView upload_post;
    EditText txt_content;

    Uri selectedImage;

    final int REQ_IMAGE_PICK = 1000;

    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        exit = findViewById(R.id.exit);
        gallery = findViewById(R.id.gallery);
        upload_post = findViewById(R.id.upload_post);
        txt_content = findViewById(R.id.txt_content);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE_PICK);
            }
        });

        upload_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImage == null){          //사용자가 이미지를 선택안했을때
                    Toast.makeText(MakePostActivity.this,"이미지를 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                String text = txt_content.getText().toString();
                if(text.equals("")){
                    Toast.makeText(MakePostActivity.this,"내용을 작성해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                String fileName = UUID.randomUUID().toString();
                storage.getReference().child("image").child(fileName).putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();
                                                Log.d("AddFragment",imageUrl);
                                                Post post = new Post();
                                                String email = auth.getCurrentUser().getEmail();
                                                String text = txt_content.getText().toString();
                                                post.setUserId(email);
                                                post.setText(text);
                                                post.setImageUrl(imageUrl);

                                                uploadPost(post);
                                            }
                                        });
                            }
                        });
                Toast.makeText(MakePostActivity.this, "게시물 업로드됨",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void uploadPost(Post post) {
        firestore.collection("posts").add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        txt_content.setText("");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_IMAGE_PICK && resultCode == RESULT_OK){
            selectedImage = data.getData();
            gallery.setImageURI(selectedImage);
        }
    }
}
