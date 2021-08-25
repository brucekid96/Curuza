package com.curuza.domain.onboarding.auth;

import android.util.Log;

import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidentityprovider.model.UserNotFoundException;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Amplify;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class AuthService {

    private static final String DBG_TAG = AuthService.class.getSimpleName();
    private static final String VALID_PHONE_NUMBER_REGEX = "\\+257(71|72|76|79|75|61|68|69)[0-9]{6}";
    private static final String VALID_PASSWORD_REGEX = "[A-Za-z0-9^$*.?\"!@#%&,':;_~`]{6,99}";
    private static final String VALID_NAME_REGEX = "[A-zÀ-ú\\-]+( [A-zÀ-ú\\-]+)?";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(VALID_PHONE_NUMBER_REGEX);
    }

    public static Single<Boolean> isUserRegistered(String phoneNumber) {
        return Single.create(source -> {
            String dummyConfirmationCode = "000000";
            Amplify.Auth.confirmSignUp(
                    phoneNumber,
                    dummyConfirmationCode,
                    result -> Log.d(DBG_TAG, "onSuccess: " + result.toString()),
                    exception -> {
                        Log.d(DBG_TAG, "confirmSignUp exception: " + exception.toString());
                        if (exception.getCause() instanceof UserNotFoundException) {
                            source.onSuccess(false);
                        } else if (exception.getCause() instanceof NotAuthorizedException) {
                            source.onSuccess(true);
                        } else {
                            source.onError(exception);
                        }
                    }
            );
        });
    }


    public static Maybe<AuthUser> getCurrentUser() {
        return Maybe.create(source -> {
            AuthUser currentUser = Amplify.Auth.getCurrentUser();
            if (currentUser != null) {
                source.onSuccess(currentUser);
            } else {
                source.onComplete();
            }
        });
    }

    public static AuthUser getCurrentUserSync() {
        return Amplify.Auth.getCurrentUser();
    }

    // FIXME Sign in sometimes fails due to invalid tokens
    public static Single<AuthSignInResult> signIn(String phoneNumber, String password) {
        return Single.create(source ->
                Amplify.Auth.signIn(
                        phoneNumber,
                        password,
                        source::onSuccess,
                        source::onError));
    }

    public static Single<AuthSignUpResult> signUp(SignupSession signupSession) {
        return Single.create(source -> {
            List<AuthUserAttribute> userAttributes = Arrays.asList(
                    new AuthUserAttribute(AuthUserAttributeKey.givenName(), signupSession.getFirstName()),
                    new AuthUserAttribute(AuthUserAttributeKey.familyName(), signupSession.getLastName()));

            Amplify.Auth.signUp(
                    signupSession.getPhoneNumber(),
                    signupSession.getPassword(),
                    AuthSignUpOptions.builder().userAttributes(userAttributes).build(),
                    source::onSuccess,
                    error -> {
                        Log.d(DBG_TAG, "Sign up failed: " + error.toString());
                        source.onError(error);
                    });
        });
    }

    public static Completable signOut() {
        return Completable.create(source ->
                Amplify.Auth.signOut(
                        source::onComplete,
                        source::onError));
    }

    public static boolean isValidPassword(String password) {
        return password.matches(VALID_PASSWORD_REGEX);
    }



    public static boolean isValidName(String name) {
        return name.matches(VALID_NAME_REGEX);
    }

    public static String getRandomFirstName() {
        List<String> firstNames = Arrays.asList(
                "Alison", "Brice", "Chris", "Dorian", "Elis", "Franck", "Gary", "Hughes", "Igor", "John",
                "Kevin", "Loïc", "Mucó", "Nigels", "Oleg", "Patrick");
        return firstNames.get(new Random().nextInt(firstNames.size()));
    }

    public static String getRandomLastName() {
        List<String> lastNames = Arrays.asList(
                "Arakaza", "Buname", "Cishahayo", "Dushime", "Emerimana", "Fukamusenge", "Giriteka",
                "Havyarimana", "Irakoze", "Jimbere", "Kaze", "Mucowintore", "Nininahazwe", "Rama");

        return lastNames.get(new Random().nextInt(lastNames.size()));
    }

    public static String prettifyPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)) {
            return phoneNumber.substring(0, 4) + " " +
                    phoneNumber.substring(4, 6) + " " +
                    phoneNumber.substring(6, 9) + " " +
                    phoneNumber.substring(9);
        } else {
            return phoneNumber;
        }
    }

    public static String getRandomPhoneNumber() {
        String phoneNumber = "+257";
        List<String> carrierExtensions = Arrays.asList("61", "68", "69", "71", "72", "75", "76", "79");
        phoneNumber += carrierExtensions.get(new Random().nextInt(carrierExtensions.size()));

        for (int i = 0; i < 6; i++) {
            phoneNumber += new Random().nextInt(10);
        }

        return phoneNumber;
    }
}
