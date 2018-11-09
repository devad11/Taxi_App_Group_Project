//package com.taxiproject.group6.taxiapp.classes;
//
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//
//import static com.firebase.ui.auth.AuthUI.TAG;
//
//public class DatabaseController {
//
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//            .setDisplayName("Jane Q. User")
//            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//            .build();
//
////    user.updateProfile(profileUpdates)
////            .addOnCompleteListener(new OnCompleteListener<Void>() {
////        @Override
////        public void onComplete (@NonNull Task < Void > task) {
////            if (task.isSuccessful()) {
////                Log.d(TAG, "User profile updated.");
////            }
////        }
////    });
//    if (user != null)
//    {
//        String email = user.getEmail();
//
//
//        // Check if user's email is verified
//        boolean emailVerified = user.isEmailVerified();
//
//        // The user's ID, unique to the Firebase project. Do NOT use this value to
//        // authenticate with your backend server, if you have one. Use
//        // FirebaseUser.getIdToken() instead.
//        String uid = user.getUid();
//    }
//}
