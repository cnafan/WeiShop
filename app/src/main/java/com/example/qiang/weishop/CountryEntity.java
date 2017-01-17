package com.example.qiang.weishop;

/**
 * Created by qiang on 2017/1/11.
 * <p>
 * 0是带标题的
 */

/**
 * 0是带标题的
 */


class CountryEntity {
    private int itemtype;
    private String countryfirst;
    private String countryname;
    private int countrycount;

    CountryEntity() {
        this.countrycount = 0;
        this.countryname = "china";
        this.countryfirst = "a";
        this.itemtype = 0;
    }

    CountryEntity(int type, String name, int count) {
        this.itemtype = type;
        this.countryname = name;
        this.countrycount = count;
        this.countryfirst = (char) ((int) ((new ChineseCharToEn().getFirstLetter(name)).charAt(0)) - 32) + "";//小写转大写
    }

    int getItemId() {
        return this.itemtype;
    }

    int getCountrycount() {
        return this.countrycount;
    }

    String getItemFirst() {
        return this.countryfirst;
    }

    String getPublishDate() {
        return this.countryname;
    }

}
