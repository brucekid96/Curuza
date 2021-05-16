package com.curuza.domain;

import com.curuza.R;
import com.curuza.data.help.Help;

import java.util.Arrays;
import java.util.List;

public class SampleData {

    public static List<Help> getSampleHelp() {
        Help[] helpArray = {

                new Help(1, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(2, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(3, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(4, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(5, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(6, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(7, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(8, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(9, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),
                new Help(10, R.drawable.ic_baseline_remove_product,"Remove Product","L'écran «marchandises» contient la liste de toutes vos marchandises,"),

        };
        return  Arrays.asList(helpArray);
    }
}
