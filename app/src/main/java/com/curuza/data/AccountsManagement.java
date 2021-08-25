package com.curuza.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

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
    @ColumnInfo(name = "status")
    private Boolean mStatus;


    public AccountsManagement(@NonNull String mId, Uri mProfileImageUri, @NonNull String name, Boolean mStatus) {
        this.mId = mId;
        this.mProfileImageUri = mProfileImageUri;
        this.name = name;
        this.mStatus = mStatus;
    }


    @NonNull
    public String getmId() {
        return mId;
    }

    public void setmId(@NonNull String mId) {
        this.mId = mId;
    }

    public Uri getmProfileImageUri() {
        return mProfileImageUri;
    }

    public void setmProfileImageUri(Uri mProfileImageUri) {
        this.mProfileImageUri = mProfileImageUri;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Boolean getmStatus() {
        return mStatus;
    }

    public void setmStatus(Boolean mStatus) {
        this.mStatus = mStatus;
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
                + mStatus
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
        dest.writeValue(this.mStatus);
    }

    public void readFromParcel(Parcel source) {
        this.mId = source.readString();
        this.mProfileImageUri = source.readParcelable(Uri.class.getClassLoader());
        this.name = source.readString();
        this.mStatus = (Boolean) source.readValue(Boolean.class.getClassLoader());
    }

    protected AccountsManagement(Parcel in) {
        this.mId = in.readString();
        this.mProfileImageUri = in.readParcelable(Uri.class.getClassLoader());
        this.name = in.readString();
        this.mStatus = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
