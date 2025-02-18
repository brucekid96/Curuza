package com.curuza.utils;

import android.content.Context;

import androidx.core.os.ConfigurationCompat;

import com.curuza.R;
import com.franmontiel.localechanger.LocaleChanger;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleUtils {

    public static final Locale ENGLISH = new Locale("en", "US");
    public static final Locale FRENCH = new Locale("fr", "FR");
    public static final Locale KIRUNDI = new Locale("rn", "BI");
    public static final Locale SWAHILI = new Locale("sw", "TZ");
    public static final List<Locale> APP_LOCALES = Arrays.asList(ENGLISH, FRENCH, KIRUNDI,SWAHILI);


    public static Locale getCurrentLocale(Context context) {
        return ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
    }

    public static String getCurrentLocaleDisplayName(Context context) {
        Locale currentLocale = LocaleChanger.getLocale();
        if(currentLocale.getISO3Language().equals(FRENCH.getISO3Language())) {
            return context.getString(R.string.French);
        } else if (currentLocale.getISO3Language().equals(KIRUNDI.getISO3Language())) {
            return context.getString(R.string.kirundi);
        }else if (currentLocale.getISO3Language().equals(SWAHILI.getISO3Language())) {
            return context.getString(R.string.swahili);
        } else {
            return context.getString(R.string.English);
        }
    }
}
