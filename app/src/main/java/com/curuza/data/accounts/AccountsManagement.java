package com.curuza.data.accounts;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts_table")
public class AccountsManagement  implements Parcelable {
    public static final String ACCOUNT_EXTRA = "account_code";
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;
    @ColumnInfo(name = "resourceId")
    private Uri mProfileImageUri;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "isSelected")
    private Boolean isSelected;


    public AccountsManagement(String id, Uri mProfileImageUri,  String name, Boolean isSelected) {
        mId = id;
        this.mProfileImageUri = mProfileImageUri;
        this.name = name;
        this.isSelected = isSelected;
    }


    @NonNull
    public String getId() {
        return mId;
    }

    public Uri getProfileImageUri() {
        return mProfileImageUri;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public void setmId(@NonNull String mId) {
        this.mId = mId;
    }



    public void setmProfileImageUri(Uri mProfileImageUri) {
        this.mProfileImageUri = mProfileImageUri;
    }



    public void setName(@NonNull String name) {
        this.name = name;
    }



    public void setmStatus(Boolean mStatus) {
        this.isSelected = mStatus;
    }

    public String toString() {
        return AccountsManagement.class.getSimpleName()
                + "["
                + "mId="
                + mId.toString()
                +"mProfileImageUri="
                + (mProfileImageUri == null ? "null" : mProfileImageUri.toString())
                + ","
                +"mName="
                + name
                + ","
                +"mStatus="
                + isSelected
                +
               "]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeParcelable(this.mProfileImageUri, flags);
        dest.writeString(this.name);
        dest.writeValue(this.isSelected);
    }

    public void readFromParcel(Parcel source) {
        this.mId = source.readString();
        this.mProfileImageUri = source.readParcelable(Uri.class.getClassLoader());
        this.name = source.readString();
        this.isSelected = (Boolean) source.readValue(View.class.getClassLoader());
    }

    protected AccountsManagement(Parcel in) {
        this.mId = in.readString();
        this.mProfileImageUri = in.readParcelable(Uri.class.getClassLoader());
        this.name = in.readString();
        this.isSelected = (Boolean) in.readValue(View.class.getClassLoader());
    }

    public static final Creator<AccountsManagement> CREATOR = new Creator<AccountsManagement>() {
        @Override
        public AccountsManagement createFromParcel(Parcel source) {
            return new AccountsManagement(source);
        }

        @Override
        public AccountsManagement[] newArray(int size) {
            return new AccountsManagement[size];
        }
    };
}
