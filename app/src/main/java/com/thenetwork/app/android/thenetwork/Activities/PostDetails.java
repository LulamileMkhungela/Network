package com.thenetwork.app.android.thenetwork.Activities;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

class PostDetails   {
    @ServerTimestamp
private Date time;
        private String postText;
        private String imageUrl;
        private String hashTags;
        private String author;
        private String postId;

        public PostDetails(){
            //Do not delete
        }

        public PostDetails(String postText, String imageUrl, String hashTags, String author, String postId) {
            this.postText = postText;
            this.imageUrl = imageUrl;
            this.hashTags = hashTags;
            this.author = author;
            this.postId = postId;
            this.time = null;
        }

        public Date getTime() {
            return time;
        }

        public String getPostText() {
            return postText;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getHashTags() {
            return hashTags;
        }

        public String getAuthor() {
            return author;
        }

        public String getPostId() {
            return postId;
        }
}