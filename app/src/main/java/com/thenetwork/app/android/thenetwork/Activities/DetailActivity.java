package com.thenetwork.app.android.thenetwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thenetwork.app.android.thenetwork.HelperUtils.GlideLoadImage;
import com.thenetwork.app.android.thenetwork.R;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";

    //private android.support.v7.widget.Toolbar mToolbar;
    private ImageView blogLikeBtn;
    private TextView blogLikeCount, commentCount;
    private KenBurnsView blogImage;
    private AppBarLayout appBarLayout;
    private TextView titleView, descView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ImageView detailFavourite, detailComments;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String blogTime;
    private String mTitle = "";
    private ImageView backToHome;
    TextView postTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DetailTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        final Animation likeAnim = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.like_animation);

        final String blogPostId = getIntent().getStringExtra("blog_id");
        final String user_id = getIntent().getStringExtra("user_id");
        final String thumb_url = getIntent().getStringExtra("thumb");

        titleView = findViewById(R.id.new_post_title);
        blogImage = findViewById(R.id.detail_image);
        blogLikeBtn = findViewById(R.id.detail_like_btn);
        blogLikeCount = findViewById(R.id.detail_like_count);
        commentCount = findViewById(R.id.detail_comment_count);
        detailComments = findViewById(R.id.detail_comment_btn);
        postTitle = findViewById(R.id.blog_detail_desc);
        backToHome = findViewById(R.id.back_view_post);


        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //Adding post to favourites

        //Getting comments count
        firebaseFirestore.collection("Posts/" + blogPostId + "/Comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                if (!documentSnapshots.isEmpty()) {
                    int commentCounts = documentSnapshots.size();
                    commentCount.setText(commentCounts + " Commments");
                } else {
                    commentCount.setText(0 + " Commments");
                }

            }
        });

        firebaseFirestore.collection("Posts").document(blogPostId).get()
                .addOnCompleteListener(DetailActivity.this, new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            blogTime = (task.getResult().get("timestamp").toString());
                            String title = task.getResult().getString("title");
                            String desc = task.getResult().getString("desc");
                            postTitle.setText(title);
//                            descView.setText(desc);

                            detailComments.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent commentIntent = new Intent(DetailActivity.this, CommentsActivity.class);
                                    commentIntent.putExtra("blogId", blogPostId);
                                    commentIntent.putExtra("userid", firebaseAuth.getCurrentUser().getUid());
                                    startActivity(commentIntent);
                                }
                            });

                            final String imageUrl = task.getResult().getString("image_url");
                            String thumbUrl = task.getResult().getString("thumb_url");
                            Log.i("URL", imageUrl + "   ");

                            //Opening full screen image
                            blogImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DetailActivity.this, com.thenetwork.app.android.thenetwork.Activities.FullScreenImageActivity.class);
                                    intent.putExtra("image_url", imageUrl);
                                    intent.putExtra("user_id", user_id);
                                    intent.putExtra("time", blogTime);
                                    startActivity(intent);
                                }
                            });
                            //Loading Image
                            GlideLoadImage.loadImage(DetailActivity.this, blogImage, thumb_url, imageUrl);
                        }
                    }
                });

        //Getting likes count
        firebaseFirestore.collection("Posts/" + blogPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                if (!documentSnapshots.isEmpty()) {
                    int likeCount = documentSnapshots.size();
                    blogLikeCount.setText(likeCount + " ");
                } else {
                    blogLikeCount.setText(0 + " ");
                }
            }
        });
        //Getting likes
        firebaseFirestore.collection("Posts/" + blogPostId + "/Likes")
                .document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                if (documentSnapshot.exists()) {
                    blogLikeBtn.setImageDrawable(getDrawable(R.drawable.ic_like));
                } else {
                    blogLikeBtn.setImageDrawable(getDrawable(R.drawable.ic_like_btn));
                }
            }
        });

        //Liking a post
        blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blogLikeBtn.startAnimation(likeAnim);

                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes")
                        .document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().exists()) {
                                Map<String, Object> likesMap = new HashMap<>();
                                likesMap.put("timestamp", System.currentTimeMillis());
                                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes")
                                        .document(firebaseAuth.getCurrentUser().getUid()).set(likesMap);

                            } else {
                                firebaseFirestore.collection("Posts/" + blogPostId + "/Likes")
                                        .document(firebaseAuth.getCurrentUser().getUid()).delete();
                            }
                        } else {
                            Toast.makeText(DetailActivity.this, "Whoops, Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
