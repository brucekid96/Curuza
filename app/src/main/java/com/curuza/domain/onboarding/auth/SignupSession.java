package com.curuza.domain.onboarding.auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class SignupSession implements Parcelable {
    public static final String SIGNUP_SESSION_EXTRA_KEY = "signup_session_key";
    public static final String IDENTITY_DOCUMENT_ID = "custom:identity_document_id";
    private String phoneNumber;
    private boolean isPhoneNumberVerified;
    private String password;
    private String firstName;
    private String lastName;

    public Uri getProfilePhotoUri() {
        return profilePhotoUri;
    }

    public SignupSession setProfilePhotoUri(Uri profilePhotoUri) {
        this.profilePhotoUri = profilePhotoUri;
        return this;
    }

    private Uri profilePhotoUri;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private boolean isPhoneNumberValid() {
        return AuthService.isValidPhoneNumber(this.phoneNumber);
    }

    private boolean isPhoneNumberVerified() {
        return isPhoneNumberVerified;
    }

    public SignupSession setPhoneNumberVerified(boolean phoneNumberVerified) {
        isPhoneNumberVerified = phoneNumberVerified;
        return this;
    }

    public SignupSession setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPasswordValid() {
        return AuthService.isValidPassword(this.password);
    }

    public SignupSession setPassword(String password) {
        this.password = password;
        return this;
    }



    public String getFirstName() {
        return firstName;
    }

    public SignupSession setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public SignupSession setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "SignupSession{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", isPhoneNumberVerified=" + isPhoneNumberVerified +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhotoUri=" + profilePhotoUri +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.password);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    public SignupSession() {
    }

    protected SignupSession(Parcel in) {
        this.phoneNumber = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public static final Parcelable.Creator<SignupSession> CREATOR = new Parcelable.Creator<SignupSession>() {
        @Override
        public SignupSession createFromParcel(Parcel source) {
            return new SignupSession(source);
        }

        @Override
        public SignupSession[] newArray(int size) {
            return new SignupSession[size];
        }
    };
}

